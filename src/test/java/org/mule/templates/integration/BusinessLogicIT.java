/**
 * Mule Anypoint Template
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import static junit.framework.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleException;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;

import com.mulesoft.module.batch.BatchTestHelper;

/**
 * The objective of this class is to validate the correct behavior of the flows
 * for this Anypoint Template that make calls to external systems.
 * 
 */
public class BusinessLogicIT extends AbstractTemplateTestCase {

	private static final String TEMPLATE_PREFFIX = "wdayf2sfdc-salesitem2product-migration";
	protected static final int TIMEOUT_SEC = 240;
	private static SubflowInterceptingChainLifecycleWrapper retrieveProductSFDC;
	private static SubflowInterceptingChainLifecycleWrapper retievePricebookEntrySFDC;
	private static SubflowInterceptingChainLifecycleWrapper deleteSFDC;
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final String DESCRIPTION = "Test description.";
	private static final Integer UNIT_PRICE = 100;
	private static final String SALES_ITEM_NAME = TEMPLATE_PREFFIX + System.currentTimeMillis();
	private final List<String> idsToDeleteSFDC = new ArrayList<String>();
	private BatchTestHelper helper;
	private Map<String,Object> salesItemMap;
	
	@Before
	public void setUp() throws Exception {
		helper = new BatchTestHelper(muleContext);

		retrieveProductSFDC = getSubFlow("retrieveProductSFDC");
		retrieveProductSFDC.initialise();

		retievePricebookEntrySFDC = getSubFlow("retievePricebookEntrySFDC");
		retievePricebookEntrySFDC.initialise();

		deleteSFDC = getSubFlow("deleteSFDC");
		deleteSFDC.initialise();
		
		final Properties props = new Properties();
    	try {
    		props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
    	} catch (Exception e) {
    	   logger.error("Error occured while reading mule.test.properties", e);
    	}
    	
    	createTestDataInSandBox();
	}
	
	@After
	public void tearDown() throws MuleException, Exception {
		cleanUpSandboxData();
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testMainFlow() throws Exception {
		runFlow("mainFlow");

		// Wait for the batch job executed by the poll flow to finish
		helper.awaitJobTermination(TIMEOUT_SEC * 1000, 500);
		helper.assertJobWasSuccessful();

		Map<String, Object> retrieveProductMap = new HashMap<String, Object>();
		retrieveProductMap.put("Name", SALES_ITEM_NAME);
		Map<String, Object> payload = invokeRetrieveFlow(retrieveProductSFDC,
				retrieveProductMap);
		
		assertEquals("The Product name should have been sync", SALES_ITEM_NAME, (String)payload.get("Name"));
		assertEquals("The Product description should have been sync", DESCRIPTION, (String)payload.get("Description"));
		String productIdSFDC = (String)payload.get("Id");

		payload = invokeRetrieveFlow(retievePricebookEntrySFDC, payload);
		assertEquals("The Unit price for Products should have been sync", UNIT_PRICE, new Integer((int)Double.parseDouble((String)payload.get("UnitPrice"))));
		idsToDeleteSFDC.add((String) payload.get("Id"));
		idsToDeleteSFDC.add(productIdSFDC);
		
	}

	private void cleanUpSandboxData() throws MuleException, Exception {
		deleteSFDC.process(getTestEvent(idsToDeleteSFDC, MessageExchangePattern.REQUEST_RESPONSE));
		idsToDeleteSFDC.clear();		
	}
	
	private void createTestDataInSandBox() throws MuleException, Exception {
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("createNewSalesItemWDAYF");
		flow.initialise();
		logger.info("Creating a workday sales item...");
		try {
			flow.process(getTestEvent(prepareNewSalesItemMap(), MessageExchangePattern.REQUEST_RESPONSE));						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String,Object> prepareNewSalesItemMap(){
		logger.info("Sales item name: " + SALES_ITEM_NAME);
		salesItemMap = new HashMap<>();
		salesItemMap.put("name", SALES_ITEM_NAME);
		salesItemMap.put("description", DESCRIPTION);
		salesItemMap.put("unitPrice", UNIT_PRICE);
		return salesItemMap;
	}		
	
}
