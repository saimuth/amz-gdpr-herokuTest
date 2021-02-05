package com.amazon.gdpr.batch;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.util.StringUtils;

import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class TaggingJobCompletionListener extends JobExecutionListenerSupport {
	
	@Autowired
	ModuleMgmtProcessor moduleMgmtProcessor;
	
	@Autowired
	RunMgmtDaoImpl runMgmtDaoImpl;
	
	@Autowired
	RunSummaryDaoImpl runSummaryDaoImpl;
	
	private static String CURRENT_CLASS		 		= GlobalConstants.CLS_TAGGINGCOMPLETIONLISTENER;
	String jobRelatedName = "";
	Date moduleStartDateTime = null;
	Date moduleEndDateTime = null;
	String failureStatus = null;
	Boolean exceptionOccured = false;
	
	
	public TaggingJobCompletionListener(String jobRelatedName) {
		this.jobRelatedName = jobRelatedName;
	}
		
	@Override
	public void afterJob(JobExecution jobExecution) {		
		String CURRENT_METHOD = "afterJob";		
		System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: Inside method");
		
		JobParameters jobParameters = jobExecution.getJobParameters();
		
		long runId = jobParameters.getLong(GlobalConstants.JOB_INPUT_RUN_ID);
		long jobId = jobParameters.getLong(GlobalConstants.JOB_INPUT_JOB_ID);
		long runSummaryId = jobParameters.getLong(GlobalConstants.JOB_INPUT_RUN_SUMMARY_ID);
		String tableName = jobParameters.getString(GlobalConstants.JOB_INPUT_TABLE_NAME);
		Date moduleStartDateTime = jobParameters.getDate(GlobalConstants.JOB_INPUT_START_DATE);
		/*String countryCode = jobParameters.getString(GlobalConstants.JOB_INPUT_COUNTRY_CODE);
		int categoryId = Integer.parseInt(jobParameters.getString(GlobalConstants.JOB_INPUT_CATEGORY_ID));*/
		String taggingData = "";
		String errorMessage = "";
		moduleStartDateTime = jobExecution.getStartTime();
		moduleEndDateTime = jobExecution.getEndTime();			
		String moduleStatus = "";
		String jobExitStatus = jobExecution.getExitStatus().getExitCode();
		
		List<Throwable> lstFailureExceptions = jobExecution.getAllFailureExceptions();
		for(Throwable failureException : lstFailureExceptions){
			errorMessage = errorMessage + failureException.getMessage(); 
		}	
		
		String batchJobStatus = "BATCH JOB COMPLETED SUCCESSFULLY for runId - "+runId+" for runSummaryId - "+ runSummaryId
				+" with status -"+jobExitStatus;
		
		try {
			String sqlQuery = "UPDATE GDPR.RUN_SUMMARY_MGMT SET TAGGED_ROW_COUNT = (SELECT COUNT(TAG.ID) COUNT FROM "+tableName+
					" TAG, GDPR.RUN_SUMMARY_MGMT RSM WHERE TAG.RUN_ID = RSM.RUN_ID AND TAG.CATEGORY_ID = RSM.CATEGORY_ID "
					+ "AND TAG.COUNTRY_CODE = RSM.COUNTRY_CODE AND RSM.SUMMARY_ID = "+runSummaryId+" ) WHERE SUMMARY_ID = "+runSummaryId;
			runSummaryDaoImpl.tagDataCountUpdate(sqlQuery);
			taggingData = GlobalConstants.MSG_TAGDATA_COUNT_UPDATE;
		} catch(Exception exception) {
			exceptionOccured = true;
			taggingData = GlobalConstants.ERR_TAGDATA_COUNT_UPDATE;
			errorMessage = errorMessage + exception.getMessage();
			System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: Exception : "+exception.getMessage());
		}
		if (jobExitStatus.equalsIgnoreCase(ExitStatus.COMPLETED.getExitCode()) || jobExitStatus.equalsIgnoreCase(ExitStatus.FAILED.getExitCode()) ) {
			System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: "+jobRelatedName+ " "+batchJobStatus);
			System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: "+jobExecution.getExitStatus().getExitCode());
			System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: "+jobExecution.getExitStatus().getExitCode().toString());
			try {
				switch (jobExecution.getExitStatus().getExitCode().toString()) {					
					case "COMPLETED" :
						moduleStatus = GlobalConstants.STATUS_SUCCESS;
						break;
					case "FAILED" : 
						moduleStatus = GlobalConstants.STATUS_FAILURE;
						break;
					default :
						moduleStatus = GlobalConstants.STATUS_SUCCESS;
						break;
				}
				taggingData = GlobalConstants.MSG_TAGGING_DATA + "for runId - "+runId+" for runSummaryId - "+ runSummaryId + taggingData;
				RunModuleMgmt runModuleMgmt = new RunModuleMgmt(runId, GlobalConstants.MODULE_DEPERSONALIZATION, GlobalConstants.SUB_MODULE_TAGGED_DATA,
						moduleStatus, moduleStartDateTime, moduleEndDateTime, taggingData, errorMessage);
				moduleMgmtProcessor.initiateModuleMgmt(runModuleMgmt);
				runMgmtDaoImpl.updateRunComments(runId, taggingData + errorMessage);
			} catch(GdprException exception) {
				exceptionOccured = true;
				System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: Exception : "+exception.getExceptionMessage());
			}
		}		
	}
}