package com.fsd.lambda;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

public class LambdaFunctionHandlerTests {

	private static Map<String, Object> input;

	@BeforeClass
	public static void createInput() throws IOException {

		input = new HashMap<>();
		input.put("assetId", 11);
		input.put("messageDateTime", "2021-05-27T17:39:38.456932600Z");
		input.put("deviceId", 4);
		input.put("assetName", "Asset 11");
		input.put("inputValue", 70);
		input.put("minValue", 15);
		input.put("maxValue", 170);
		input.put("status", "DOWN");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();
		ctx.setFunctionName("Test Lambda");
		return ctx;
	}

	@Test
	public void testLambdaFunctionHandler() {
		LambdaFunctionHandler handler = new LambdaFunctionHandler();
		Context ctx = createContext();

		String output = handler.handleRequest(input, ctx);

		Assert.assertEquals("Done", output);
	}
}
