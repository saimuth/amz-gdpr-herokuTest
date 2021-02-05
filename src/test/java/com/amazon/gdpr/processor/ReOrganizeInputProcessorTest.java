package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class ReOrganizeInputProcessorTest {
	@Mock
	JobLauncher jobLauncher;

	@Mock
	Job processreorganizeInputJob;

	@Mock
	RunMgmtProcessor runMgmtProcessor;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@InjectMocks
	ReOrganizeInputProcessor reOrganizeInputProcessor;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void reOrganizeDataTest() throws GdprException {
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		long runId=3L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String resActual=GlobalConstants.MSG_REORGANIZEINPUT_JOB;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		String res=reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries);
		assertEquals(resActual, res);
	}
}
