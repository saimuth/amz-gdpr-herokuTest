package com.amazon.gdpr.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.amazon.gdpr.processor.GdprDataProcessor;

public class FileUploadControllerTest {

	/*
	 * @Mock AnonymizationProcessor anonymizationProcessor;
	 * 
	 * @Mock GdprDataProcessor gdprDataProcessor;
	 * 
	 * @InjectMocks FileUploadController fileUploadController;
	 * 
	 * private MockMvc mockMvc;
	 * 
	 * @Before public void setup() { MockitoAnnotations.initMocks(this);
	 * 
	 * mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build(); }
	 * 
	 * @Test public void loadAnonymizationFormTest() throws Exception {
	 * mockMvc.perform(get("/fileUploadT")).andExpect(status().isOk()).andExpect(
	 * view().name("fileUpload")) .andExpect(model().attribute("message",
	 * "Please upload Anonymization File only. ")); }
	 * 
	 * @Test public void uploadMultipartFileTest() throws Exception {
	 * 
	 * MockMvc mockMvc =
	 * MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	 * mockMvc.perform(MockMvcRequestBuilders.multipart("/upload") .file(firstFile)
	 * 
	 * .andExpect(status().is(200)) // .andExpect(content().string("success"));
	 * InputStream uploadStream =
	 * FileUploadController.class.getClassLoader().getResourceAsStream(
	 * "C:\\HeroKu_Test\\HerokuAnonymization-V0.1.xlsx"); MockMultipartFile file =
	 * new MockMultipartFile("file", uploadStream); assert uploadStream != null;
	 * 
	 * //when this.mockMvc.perform(fileUpload("/processFile") .file(file)) //then
	 * .andExpect(status().isOk());
	 * 
	 * }
	 */
}
