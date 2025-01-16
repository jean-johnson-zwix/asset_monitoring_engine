package com.fsd.lambda.utility;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.fsd.lambda.constants.FSDConstants;
import com.fsd.lambda.model.Condition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FSDUtils {

	private FSDUtils() {
		// PRIVATE CONSTRUCTOR
	}

	private static Gson gson = new Gson();

	public static String toJson(Map<String, Object> mapData) {
		return gson.toJson(mapData);
	}

	public static <T> T toPojo(String jsonData, Class<T> clazz) {
		return gson.fromJson(jsonData, clazz);
	}

	public static List<Condition> toListOfCondition(String jsonData) {
		return gson.fromJson(jsonData, new TypeToken<List<Condition>>() {
		}.getType());

	}

	public static String timeNow() {
		return Instant.now().toString();
	}

	public static String triggerConditionSymbol(String trigger) {

		trigger = trigger.toUpperCase().replace(" ", "");

		if (trigger.equalsIgnoreCase(FSDConstants.LESSTHAN))
			return "<";
		if (trigger.equalsIgnoreCase(FSDConstants.GREATERTHAN))
			return ">";
		if (trigger.equalsIgnoreCase(FSDConstants.EQUALTO))
			return "==";
		return null;
	}

	public static String conditionRule(Condition condition) {
		return String.format(FSDConstants.DROOL_RULE_FORMAT, condition.getSource(),
				FSDUtils.triggerConditionSymbol(condition.getTriggerCondition()), condition.getParameter());
	}

	public static String conditionAction(Condition condition) {

		// SET CONDITION
		if (condition.getConditionExpression().startsWith(FSDConstants.SET_EXPRESSION_PREFIX)) {
			return String.format(FSDConstants.SET_ACTION_FORMAT, condition.getConditionId(),
					condition.getConditionExpression().split(" ")[3]);
		}

		// ALERT CONDITION
		if (condition.getConditionExpression().startsWith(FSDConstants.ALERT_EXPRESSION_PREFIX)) {
			return String.format(FSDConstants.ALERT_ACTION_FORMAT, condition.getConditionExpression().split(" ")[2],
					condition.getConditionId(),
					String.format(FSDConstants.ALERT_NAME_FORMAT, condition.getConditionName()));
		}

		return null;
	}
}
