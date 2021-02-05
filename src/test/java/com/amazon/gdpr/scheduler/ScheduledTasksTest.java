package com.amazon.gdpr.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazon.gdpr.dao.BackupTableProcessorDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.gdpr.output.BackupTableDetails;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.processor.BackupTableProcessor;
import com.amazon.gdpr.util.GdprException;
@RunWith(MockitoJUnitRunner.class)
public class ScheduledTasksTest {
	
	public static String CURRENT_CLASS = "ScheduledTasks";

	@Mock
	BackupTableProcessor backupTableProcessor;

	@Mock
	private BackupTableProcessorDaoImpl backupTableProcessorDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@InjectMocks
	ScheduledTasks scheduledTasks;
	
	@Test
	public void refreshBackupTableSchedulerTest() throws GdprException {
		List<BackupTableDetails> lstBackupTableDetails= new ArrayList<BackupTableDetails>();
		BackupTableDetails backupTableDetails1= new BackupTableDetails("APPLICATION__C", "EMAIL_NOTIFICATION_PROMPT_MEMO__C");
		BackupTableDetails backupTableDetails2= new BackupTableDetails("INTERVIEW__C", "CANDIDATE_LAST_NAME__C");
		lstBackupTableDetails.add(backupTableDetails2);
		lstBackupTableDetails.add(backupTableDetails1);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails);
		Mockito.when(backupTableProcessor.refreshBackupTables(lstBackupTableDetails)).thenReturn(Boolean.TRUE);
		scheduledTasks.refreshBackupTableScheduler();
	}
	
	@Test(expected=Exception.class)
	public void refreshBackupTableSchedulerExceptionTest() throws GdprException {
		List<BackupTableDetails> lstBackupTableDetails= new ArrayList<BackupTableDetails>();
		BackupTableDetails backupTableDetails1= new BackupTableDetails("APPLICATION__C", "EMAIL_NOTIFICATION_PROMPT_MEMO__C");
		BackupTableDetails backupTableDetails2= new BackupTableDetails("INTERVIEW__C", "CANDIDATE_LAST_NAME__C");
		lstBackupTableDetails.add(backupTableDetails2);
		lstBackupTableDetails.add(backupTableDetails1);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails);
		Mockito.when(backupTableProcessor.refreshBackupTables(lstBackupTableDetails)).thenThrow(Exception.class);
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		scheduledTasks.refreshBackupTableScheduler();
	}

}
