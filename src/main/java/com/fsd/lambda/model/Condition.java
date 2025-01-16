package com.fsd.lambda.model;

import lombok.Data;

@Data
public class Condition {

	private long conditionId;
	private long assetId;
	private String conditionName;
	private String severity;
	private String recommendation;
	private String description;
	private String source;
	private String parameter;
	private String triggerCondition;
	private String conditionExpression;
	private String createdBy;
	private String createdOn;
	private String modifiedBy;
	private String modifiedOn;

}
