package com.amazon.gdpr.batch;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.DataLoad;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.DataLoadProcessor;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.processor.TagDataProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

@RunWith(MockitoJUnitRunner.class)
public class ReorganizeInputCompletionListenerTest {
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	DataLoadProcessor dataLoadProcessor;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@InjectMocks
	ReorganizeInputCompletionListener reorganizeInputCompletionListener;

	@Before
	public void setUp() throws GdprException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void afterJobTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		String moduleStatus = "";
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		Long runId = 7L;
		String countryCode = "AUT";
		Long runSummaryId = 7L;

		JobParameter jobParameter = new JobParameter(runId);
		JobParameter jobParameter1 = new JobParameter(countryCode);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_REORGANIZE_INPUT_RUNID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_RUN_SUMMARY_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_COUNTRY_CODE, jobParameter1);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		jobExecution.setStartTime(moduleStartDateTime);
		jobExecution.setEndTime(moduleEndDateTime);
		jobExecution.addFailureException(new Exception());
		jobExecution.setExitStatus(ExitStatus.COMPLETED);
		String errorMessage = jobExecution.getAllFailureExceptions().toString();
		String reOrganizeDataStatus = GlobalConstants.MSG_REORGANIZEINPUT + countryCode;
		moduleStatus = GlobalConstants.STATUS_SUCCESS;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION,
				GlobalConstants.SUB_MODULE_REORGANIZE_DATA, moduleStatus, moduleStartDateTime, new Date(),
				reOrganizeDataStatus, errorMessage);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunComments(runId, reOrganizeDataStatus);
		reorganizeInputCompletionListener.afterJob(jobExecution);
	}
	
	@Test(expected=Exception.class)
	public void afterJobExceptionTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		String moduleStatus = "";
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		Long runId = 7L;
		String countryCode = "AUT";
		Long runSummaryId = 7L;

		JobParameter jobParameter = new JobParameter(runId);
		JobParameter jobParameter1 = new JobParameter(countryCode);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_REORGANIZE_INPUT_RUNID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_RUN_SUMMARY_ID, jobParameter);
		obj.put(GlobalConstants.JOB_INPUT_COUNTRY_CODE, jobParameter1);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		jobExecution.setStartTime(moduleStartDateTime);
		jobExecution.setEndTime(moduleEndDateTime);
		jobExecution.addFailureException(new Exception());
		jobExecution.setExitStatus(ExitStatus.COMPLETED);
		String errorMessage = jobExecution.getAllFailureExceptions().toString();
		String reOrganizeDataStatus = GlobalConstants.MSG_REORGANIZEINPUT + countryCode;
		moduleStatus = GlobalConstants.STATUS_SUCCESS;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION,
				GlobalConstants.SUB_MODULE_REORGANIZE_DATA, moduleStatus, moduleStartDateTime, new Date(),
				reOrganizeDataStatus, errorMessage);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).updateRunComments(anyInt(), anyString());
		reorganizeInputCompletionListener.afterJob(jobExecution);
	}
}
