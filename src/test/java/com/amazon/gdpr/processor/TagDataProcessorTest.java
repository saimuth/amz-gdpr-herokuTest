package com.amazon.gdpr.processor;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class TagDataProcessorTest {
	@Mock
	JobLauncher jobLauncher;

	@Mock
	Job processTaggingJob;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@InjectMocks
	TagDataProcessor tagDataProcessor;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void taggingInitializeTest() throws GdprException {
		long runId=7L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		/*
		 * RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module",
		 * "Anonymize Initialize Sub Module", "SUCCESS", moduleStartDateTime,
		 * moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		 */
		String moduleStatus= GlobalConstants.STATUS_SUCCESS;
		String taggingDataStatus = GlobalConstants.ERR_TAGGED_JOB_RUN;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION, 
				GlobalConstants.SUB_MODULE_TAG_JOB_INITIALIZE, moduleStatus, moduleStartDateTime, 
				moduleEndDateTime, taggingDataStatus);
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		tagDataProcessor.taggingInitialize(runId);
	}
	
	public void runTest() {
	}
}
