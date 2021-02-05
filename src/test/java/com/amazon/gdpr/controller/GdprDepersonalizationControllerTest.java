package com.amazon.gdpr.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.amazon.gdpr.service.InitService;

public class GdprDepersonalizationControllerTest {

	private MockMvc mockMvc;

	@Test
	public void test() {
		
	}
	/*
	 * @InjectMocks GdprDepersonalizationController gdprDepersonalizationController;
	 * 
	 * @Mock InitService initService;
	 * 
	 * @Before public void setup() { MockitoAnnotations.initMocks(this);
	 * 
	 * mockMvc =
	 * MockMvcBuilders.standaloneSetup(gdprDepersonalizationController).build(); }
	 */

	/*
	 * @Test public void herokuDepersonalizationTest() throws Exception { String
	 * runName = "TestRun"; String countries = "CZE"; long runId = 2L; // latest
	 * String initServiceStatus =
	 * "null; Run Anonymization Initiated Count : 2; ; Reorganizing GDPR_Depersonalization__c initiated. ; Summary details : 2"
	 * ; //Update from Git //when(initService.initService(runName,
	 * countries)).thenReturn(initServiceStatus);
	 * when(initService.getRunId()).thenReturn(runId);
	 * 
	 * mockMvc.perform(get("/gdprSubmit").param("runName",
	 * runName).param("runStatus", initServiceStatus))
	 * .andExpect(MockMvcResultMatchers.status().isOk()); // .flashAttr("gdprInput",
	 * // new GdprInput())); }
	 */

}
