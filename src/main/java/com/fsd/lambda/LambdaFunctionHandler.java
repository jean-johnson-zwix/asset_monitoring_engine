package com.fsd.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fsd.lambda.drools.ConditionRuleProcessor;
import com.fsd.lambda.entity.AssetData;
import com.fsd.lambda.service.DynamoDbService;
import com.fsd.lambda.utility.FSDUtils;

public class LambdaFunctionHandler implements RequestHandler<Map<String, Object>, String> {

	ConditionRuleProcessor conditionProcessor = new ConditionRuleProcessor();

	DynamoDbService dynamoDbService = new DynamoDbService();

	@Override
	public String handleRequest(Map<String, Object> input, Context context) {

		// TELEMETRY DATA RECEIVED
		String telemetryData = FSDUtils.toJson(input);
		System.out.println("STEP 1:\tReceive Telemetry Data: " + telemetryData);

		try {

			// MAP TO ENTITY
			AssetData assetData = FSDUtils.toPojo(telemetryData, AssetData.class);
			assetData.setTimestamp(FSDUtils.timeNow());

			System.out.println("STEP 2:\tCovert to Asset Data: " + assetData);

			// STORE IN DYNAMODB
			dynamoDbService.storeInDb(assetData);

			// PROCESS RAW DATA
			conditionProcessor.processConditionRules(assetData);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return "Done";
	}

}
