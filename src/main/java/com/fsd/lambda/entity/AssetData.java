package com.fsd.lambda.entity;

import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@ToString
@DynamoDbBean
@Setter
public class AssetData {

	private String timestamp;

	private int assetId;
	private String messageDateTime;
	private String deviceId;
	private String assetName;
	private int minValue;
	private int inputValue;
	private int maxValue;
	private String status;

	@DynamoDbPartitionKey
	public String getTimestamp() {
		return timestamp;
	}

	public int getAssetId() {
		return assetId;
	}

	public String getMessageDateTime() {
		return messageDateTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getAssetName() {
		return assetName;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getInputValue() {
		return inputValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public String getStatus() {
		return status;
	}

}
