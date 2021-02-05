package com.amazon.gdpr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class WelcomePageControllerTest {

	@InjectMocks
	private WelcomePageController WelcomePageController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(WelcomePageController).build();
	}

	@Test
	public void welcomeTest() throws Exception {

		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("welcome"));

	}

}
