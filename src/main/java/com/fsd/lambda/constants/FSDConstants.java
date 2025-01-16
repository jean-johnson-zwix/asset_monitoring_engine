package com.fsd.lambda.constants;

public class FSDConstants {

	private FSDConstants() {
		// PRIVATE CONSTRUCTOR
	}

	public static final String TABLE_NAME = "jean-fsd-AssetData";

	public static final String REGION = "us-east-1";

	public static final String BASE_URL = "http://localhost:8080";

	public static final String GET_CONDITION_URL = "/condition/getConditionByAsset/";

	public static final String UPDATE_CONDITION_URL = "/condition/updateCondition";

	public static final String CREATE_ALERT_URL = "/alert/createAlert";

	public static final String SET_ACTION_FORMAT = "condition.setConditionId(%s);\n" + "condition.setSeverity(\"%s\");";

	public static final String SET_EXPRESSION_PREFIX = "SET";

	public static final String ALERT_ACTION_FORMAT = "alert.setUserId(%s);\n" + "alert.setConditionId(%s);\n"
			+ "alert.setAlertName(\"%s\");";

	public static final String ALERT_EXPRESSION_PREFIX = "ALERT";

	public static final String ALERT_NAME_FORMAT = "%s Alert";

	public static final String LESSTHAN = "LESSTHAN";

	public static final String GREATERTHAN = "GREATERTHAN";

	public static final String EQUALTO = "EQUALTO";

	public static final String DRL_TEMPLATE_FILE = "rule-template.drl";

	public static final String DRL_RULE_PATH = "src/main/resources/rule.drl";

	public static final String ALERT_DESCRIPTION_FORMAT = "Alert of Asset %s [Device Id: %s]";

	public static final String ALERT_PLATFORM = "Web UI";

	public static final String ALERT_STATUS = "PENDING";

	public static final String DROOL_GLOBAL_ALERT = "alert";

	public static final String DROOL_GLOBAL_CONDITION = "condition";

	public static final String DROOL_PARAM_NAME = "conditionName";

	public static final String DROOL_PARAM_RULE = "conditionRule";

	public static final String DROOL_PARAM_ACTION = "conditionAction";

	public static final String DROOL_RULE_FORMAT = "%s %s %s";

	public static final String INDENT = "       ";

	public static final String APP_NAME = "AssetModellingEngine";
}
