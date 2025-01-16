package com.fsd.lambda.drools;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import com.fsd.lambda.constants.FSDConstants;
import com.fsd.lambda.entity.AssetData;
import com.fsd.lambda.model.Alert;
import com.fsd.lambda.model.Condition;
import com.fsd.lambda.service.RestService;
import com.fsd.lambda.utility.FSDUtils;

public class ConditionRuleProcessor {

	RestService restService = new RestService();

	public void processConditionRules(AssetData assetData) {

		// GET CONDITIONS
		List<Condition> conditionList = restService.getConditionByAssetId(assetData.getAssetId());
		System.out.println(FSDConstants.INDENT + "\tCondition List Retrieved: " + conditionList);

		// CREATE RULES FROM TEMPLATE
		String drl = generateRules(conditionList);

		System.out.println("STEP 6:\tGenerate DRL File\n" + drl);

		// CREATE KIE SESSION
		StatelessKieSession kieSession = createKieSession(drl);

		// SET GLOBALS
		Alert alert = initializeAlert(assetData);
		Condition newCondition = new Condition();
		kieSession.getGlobals().set(FSDConstants.DROOL_GLOBAL_ALERT, alert);
		kieSession.getGlobals().set(FSDConstants.DROOL_GLOBAL_CONDITION, newCondition);

		// APPLY RULES
		kieSession.execute(assetData);

		System.out.println("STEP 7:\tApply Rule for Asset Data");
		System.out.println(FSDConstants.INDENT + "\tAlert: " + alert);
		System.out.println(FSDConstants.INDENT + "\tCondition: " + newCondition);

		// PERFORM RULE ACTIONS
		updateCondition(newCondition, conditionList);

		createAlert(alert);

	}

	private String generateRules(List<Condition> conditionList) {

		List<Map<String, String>> ruleList = new ArrayList<>();
		ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();

		for (Condition condition : conditionList) {

			Map<String, String> rule = new HashMap<>();
			rule.put(FSDConstants.DROOL_PARAM_NAME, condition.getConditionName());
			rule.put(FSDConstants.DROOL_PARAM_RULE, FSDUtils.conditionRule(condition));
			rule.put(FSDConstants.DROOL_PARAM_ACTION, FSDUtils.conditionAction(condition));

			ruleList.add(rule);

		}

		return objectDataCompiler.compile(ruleList,
				Thread.currentThread().getContextClassLoader().getResourceAsStream(FSDConstants.DRL_TEMPLATE_FILE));
	}

	private StatelessKieSession createKieSession(String drl) {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(FSDConstants.DRL_RULE_PATH, drl);
		kieServices.newKieBuilder(kieFileSystem).buildAll();

		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
		return kieContainer.getKieBase().newStatelessKieSession();

	}

	private Alert initializeAlert(AssetData asset) {
		Alert alert = new Alert();
		alert.setAssetId(asset.getAssetId());
		alert.setAlertDescription(
				String.format(FSDConstants.ALERT_DESCRIPTION_FORMAT, asset.getAssetName(), asset.getDeviceId()));
		alert.setAlertDateTime(Instant.now().toString());
		alert.setCreatedBy(FSDConstants.APP_NAME);
		alert.setNotificationDetails(FSDConstants.ALERT_PLATFORM);
		alert.setStatus(FSDConstants.ALERT_STATUS);

		return alert;
	}

	private void updateCondition(Condition newCondition, List<Condition> conditionList) {

		Optional<Condition> conditionResult = conditionList.stream()
				.filter(c -> c.getConditionId() == newCondition.getConditionId()
						&& !c.getSeverity().equalsIgnoreCase(newCondition.getSeverity()))
				.findFirst();
		if (conditionResult.isPresent()) {
			Condition conditionToUpdate = conditionResult.get();
			conditionToUpdate.setSeverity(newCondition.getSeverity());
			conditionToUpdate.setModifiedBy(FSDConstants.APP_NAME);
			restService.updateCondition(conditionToUpdate);
		}
	}

	private void createAlert(Alert alert) {
		if (alert.getUserId() != 0) {

			restService.createAlert(alert);
		}
	}
}
