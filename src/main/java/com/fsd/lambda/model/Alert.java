package com.fsd.lambda.model;

import java.time.Instant;

import lombok.Data;

@Data
public class Alert {

	private long alertId;
	private long assetId;
	private long conditionId;
	private long userId;
	private String alertName;
	private String alertDescription;
	private int alertValue;
	private String alertDateTime;
	private boolean isNotificationSent;
	private String createdBy;
	private Instant createdOn;
	private Instant hourResetDateTime;
	private String notificationDetails;
	private String status;
	private String alertType;
	private Instant shiftResetDateTime;
	private String alertDuration;
	private boolean isViewed;
	private String liveViewDetails;
	private boolean isDeleted;
	private boolean isArchived;
}
