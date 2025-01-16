package com.fsd.lambda.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fsd.lambda.constants.FSDConstants;
import com.fsd.lambda.model.Alert;
import com.fsd.lambda.model.Condition;
import com.fsd.lambda.utility.FSDUtils;

public class RestService {

	RestTemplate restTemplate;

	public RestService() {

		DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(FSDConstants.BASE_URL);
		restTemplate = new RestTemplate();
		restTemplate.setUriTemplateHandler(uriBuilderFactory);
	}

	public List<Condition> getConditionByAssetId(long assetId) {

		System.out.println("STEP 5:\tGet Condition For Asset: " + assetId);

		String url = FSDConstants.GET_CONDITION_URL + assetId;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		System.out.println(FSDConstants.INDENT + "\tResponse: " + response);
		return FSDUtils.toListOfCondition(response.getBody());

	}

	public void updateCondition(Condition condition) {

		System.out.println(FSDConstants.INDENT + "\tRULE ACTION: Update Condition: " + condition);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Condition> conditionEntity = new HttpEntity<>(condition, headers);
		ResponseEntity<String> response = restTemplate.exchange(FSDConstants.UPDATE_CONDITION_URL, HttpMethod.POST,
				conditionEntity, String.class);
		System.out.println(FSDConstants.INDENT + "\tUpdate Condition Response: " + response);

	}

	public void createAlert(Alert alert) {

		System.out.println(FSDConstants.INDENT + "\tRULE ACTION: Create Alert: " + alert);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Alert> alertEntity = new HttpEntity<>(alert, headers);

		ResponseEntity<String> response = restTemplate.exchange(FSDConstants.CREATE_ALERT_URL, HttpMethod.POST,
				alertEntity, String.class);
		System.out.println(FSDConstants.INDENT + "\tCreate Alert Response: " + response);
	}
}
