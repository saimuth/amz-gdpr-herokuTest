package com.amazon.gdpr.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.service.BackupService;
import com.amazon.gdpr.service.InitService;
import com.amazon.gdpr.view.GdprInput;
//@RunWith(MockitoJUnitRunner.class)
public class GdprProcessControllerTest {

	/*
	 * int runId = 0;
	 * 
	 * @InjectMocks private GdprProcessController gdprProcessController;
	 * 
	 * @Mock BackupService backpupService;
	 * 
	 * @Mock InitService initService;
	 * 
	 * private MockMvc mockMvc;
	 * 
	 * @Mock BackupService backupService;
	 * 
	 * @Mock RunMgmtDaoImpl runMgmtDaoImpl;
	 * 
	 * @Before public void setup() { MockitoAnnotations.initMocks(this);
	 * 
	 * mockMvc = MockMvcBuilders.standaloneSetup(gdprProcessController).build(); }
	 */

	/*
	 * @Test public void gdprInputFormTest() throws Exception {
	 * 
	 * mockMvc.perform(get("/gdprInputT")) .andExpect(status().isOk())
	 * .andExpect(view().name("gdprInput"))
	 * .andExpect(model().attribute("gdprInput", instanceOf(GdprInput.class)));
	 * 
	 * }
	 */

	/*
	 * @Test public void herokuDepersonalizationTest() throws Exception { GdprInput
	 * gdprInput = new GdprInput(); List<String> lstSelectedRegion = new
	 * ArrayList<String>(); lstSelectedRegion.add("BEL");
	 * lstSelectedRegion.add("AUT");
	 * 
	 * List<String> lstRegion = new ArrayList<String>(); lstRegion.add("EUR-EU");
	 * List<String> lstRegionsCountry = new ArrayList<String>();
	 * lstRegionsCountry.add("EUR-EU"); Map<String, List<String>> mapRegionCountry =
	 * new HashMap<String, List<String>>(); mapRegionCountry.put("EUR-EU",
	 * lstSelectedRegion); List<String> lstCountry = new ArrayList<String>();
	 * lstCountry.add("AUT"); lstCountry.add("BEL");
	 * gdprInput.setLstRegion(lstRegion); gdprInput.setLstCountry(lstCountry);
	 * gdprInput.setMapRegionCountry(mapRegionCountry); String runName = "TestRun";
	 * String status = "Success"; long runId = 7L;
	 * when(initService.loadGdprForm()).thenReturn(gdprInput);
	 * when(initService.initService(runName,
	 * gdprInput.getLstCountry())).thenReturn(status);
	 * when(runMgmtDaoImpl.fetchLastRunDetail().getRunId()).thenReturn(runId);
	 * gdprInput.setRunName(runName); gdprInput.setRunStatus(status);
	 * gdprInput.setLstSelectedCountry(gdprInput.getLstCountry());
	 * 
	 * mockMvc.perform(post("/gdprSubmit") .param("runName",
	 * "TestRun").param("runStatus", "success")
	 * 
	 * .flashAttr("gdprInput", new GdprInput()))
	 * 
	 * }
	 */

//git

	/*
	 * @Test public void backupInitializeTest() {
	 * 
	 * List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
	 * RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT",
	 * 1, "APPLICATION__C", "select * from XX", " Alter Table XX"); RunSummaryMgmt
	 * runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1,
	 * "INTERVIEW__C", "select * from XX", " Alter Table XX");
	 * lstRunSummaryMgmt.add(runSummaryMgmt2);
	 * lstRunSummaryMgmt.add(runSummaryMgmt1);
	 * 
	 * Map<String, RunSummaryMgmt> runSummaryMgmtMap = new HashMap<String,
	 * RunSummaryMgmt>(); runSummaryMgmtMap.put("Test", runSummaryMgmt1); Long runId
	 * = 2L;
	 * 
	 * mockMvc.perform(get("/salesforceDetailsT")).andExpect(status().isOk()); }
	 */

}
