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

public class DepersonalizationProcessorTest {
	
	/*
	 * @Mock JobLauncher jobLauncher;
	 * 
	 * @Mock Job processAnonymizeJob;
	 * 
	 * @Mock ModuleMgmtProcessor moduleMgmtProcessor;
	 * 
	 * @InjectMocks DepersonalizationProcessor depersonalizationProcessor;
	 * 
	 * @Before public void init() { MockitoAnnotations.initMocks(this); }
	 * 
	 * @Test public void depersonalizationInitializeTest() throws GdprException {
	 * long runId=7L; Date moduleStartDateTime = null; Date moduleEndDateTime =
	 * null; moduleStartDateTime = new Date(); moduleEndDateTime = new Date();
	 * String moduleStatus= GlobalConstants.STATUS_SUCCESS; String
	 * depersonalizationDataStatus = GlobalConstants.MSG_DEPERSONALIZATION_JOB;
	 * RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId,
	 * GlobalConstants.MODULE_DEPERSONALIZATION,
	 * GlobalConstants.SUB_MODULE_ANONYMIZE_JOB_INITIALIZE, moduleStatus,
	 * moduleStartDateTime, moduleEndDateTime, depersonalizationDataStatus);
	 * RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test",
	 * "XX"); Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(
	 * runModuleMgmt);
	 * depersonalizationProcessor.depersonalizationInitialize(runId); }
	 */
	
}
