package com.amazon.gdpr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SalesforceDetailControllerTest {

	@InjectMocks
	private SalesforceDetailController salesforceDetailController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(salesforceDetailController).build();
	}

	/*
	 * @Test public void salesforceDetailsTest() throws Exception {
	 * 
	 * mockMvc.perform(get("/salesforceDetailsT")).andExpect(status().isOk())
	 * .andExpect(view().name("salesforceDetails"));
	 * 
	 * }
	 */
}
