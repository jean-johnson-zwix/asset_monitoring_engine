package com.fsd.lambda.service;

import com.fsd.lambda.constants.FSDConstants;
import com.fsd.lambda.entity.AssetData;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDbService {

	public void storeInDb(AssetData assetData) {

		// DYNAMODB CLIENT
		System.out.println(
				"STEP 3:\tInitialize Client for " + FSDConstants.TABLE_NAME + " in region " + FSDConstants.REGION);
		DynamoDbClient dynamoClient = DynamoDbClient.builder().region(Region.of(FSDConstants.REGION)).build();
		DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoClient).build();
		DynamoDbTable<AssetData> assetTable = enhancedClient.table(FSDConstants.TABLE_NAME,
				TableSchema.fromBean(AssetData.class));

		// UPDATE DYNAMODB
		assetTable.updateItem(assetData);
		System.out.println("STEP 4:\tUpdate Asset Data in Table");

	}
}
