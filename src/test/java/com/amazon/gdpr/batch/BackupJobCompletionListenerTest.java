package com.amazon.gdpr.batch;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

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
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.processor.TagDataProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

@RunWith(MockitoJUnitRunner.class)
public class BackupJobCompletionListenerTest {

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	TagDataProcessor tagDataProcessor;

	@InjectMocks
	BackupJobCompletionListener backupJobCompletionListener;
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Before
	public void setUp() throws GdprException {
		
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void afterJobTest() throws GdprException {

		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		String failureStatus = "";
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		Long runId = 7L;

		JobParameter jobParameter = new JobParameter(runId);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_REORGANIZE_INPUT_RUNID, jobParameter);
		JobParameters jobParameters = new JobParameters(obj);
		// JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution(7L,
		// jobParameters);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		jobExecution.setStartTime(moduleStartDateTime);
		jobExecution.setEndTime(moduleEndDateTime);
		jobExecution.setStatus(BatchStatus.COMPLETED);
		jobExecution.addFailureException(new Exception());
		String errorMessage = jobExecution.getAllFailureExceptions().toString();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION,
				GlobalConstants.SUB_MODULE_BACKUPSERVICE_DATA, GlobalConstants.STATUS_SUCCESS, moduleStartDateTime,
				moduleEndDateTime, GlobalConstants.MSG_BACKUPSERVICE_INPUT, errorMessage);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(tagDataProcessor).taggingInitialize(runId);
		Mockito.doNothing().when(runMgmtDaoImpl).updateRunComments(runId,
				GlobalConstants.MSG_BACKUPSERVICE_INPUT + errorMessage);

		backupJobCompletionListener.afterJob(jobExecution);
	}
	
	@Test(expected=Exception.class)
	public void afterJobExceptionTest() throws GdprException {

		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		String failureStatus = "";
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		Long runId = 7L;

		JobParameter jobParameter = new JobParameter(runId);
		Map<String, JobParameter> obj = new HashMap<String, JobParameter>();
		obj.put(GlobalConstants.JOB_REORGANIZE_INPUT_RUNID, jobParameter);
		JobParameters jobParameters = new JobParameters(obj);
		JobExecution jobExecution = new JobExecution(7L, jobParameters);
		jobExecution.setStartTime(moduleStartDateTime);
		jobExecution.setEndTime(moduleEndDateTime);
		jobExecution.setStatus(BatchStatus.COMPLETED);
		jobExecution.addFailureException(new Exception());
		String errorMessage = jobExecution.getAllFailureExceptions().toString();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_INITIALIZATION,
				GlobalConstants.SUB_MODULE_BACKUPSERVICE_DATA, GlobalConstants.STATUS_SUCCESS, moduleStartDateTime,
				moduleEndDateTime, GlobalConstants.MSG_BACKUPSERVICE_INPUT, errorMessage);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.doThrow(Exception.class).when(tagDataProcessor).taggingInitialize(anyInt());
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).updateRunComments(anyInt(),
			anyString());
		backupJobCompletionListener.afterJob(jobExecution);
	}

}
