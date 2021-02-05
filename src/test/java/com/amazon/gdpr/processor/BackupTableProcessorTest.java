package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.amazon.gdpr.dao.BackupTableProcessorDaoImpl;
import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.gdpr.input.ImpactTableDetails;
import com.amazon.gdpr.model.gdpr.output.BackupTableDetails;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.util.GdprException;

public class BackupTableProcessorTest {
	@InjectMocks
	BackupTableProcessor backupTableProcessor;
	@Mock
	private BackupTableProcessorDaoImpl backupTableProcessorDaoImpl;
	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processBkpupTablePositiveTest() throws GdprException {
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails())).thenReturn(Boolean.TRUE);
		boolean bkpupTableCheckStatus = true;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
				.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenReturn(lstImpactTableDetails());
		long runId = 2L;
		String str = backupTableProcessor.processBkpupTable(runId);
		assertEquals("", str);
	}

	// Need to check negative scenario
	@Test
	public void processBkpupTableNegativeTest() throws GdprException {
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails())).thenReturn(Boolean.TRUE);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		boolean bkpupTableCheckStatus = false;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class)))
				.thenReturn(bkpupTableCheckStatus);
		Mockito.when(backupTableProcessorDaoImpl.fetchBackupTableDetails()).thenReturn(lstBackupTableDetails()); 
		// doThrow().when(gdprInputDaoImpl).
		Mockito.when(gdprInputDaoImpl.fetchImpactTableDetailsMap()).thenReturn(lstImpactTableDetails());
		long runId = 2L;
		String str = backupTableProcessor.processBkpupTable(runId);
		assertEquals("", str);
	}

	@Test(expected = Exception.class)
	public void bkpupTableCheckTest() throws GdprException {
		boolean bkpupTableCheckStatus = false;
		Mockito.when(backupTableProcessorDaoImpl.alterBackupTable(any(String[].class))).thenThrow(Exception.class);
		backupTableProcessor.bkpupTableCheck(lstBackupTableDetails(), lstImpactTableDetails());
	}

	private List<BackupTableDetails> lstBackupTableDetails() {
		List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
		BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_application__c", "id");
		BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_assessment__c", "id");
		BackupTableDetails lstBackupTableDetails3 = new BackupTableDetails("bkp_emailmessage", "id");
		BackupTableDetails lstBackupTableDetails4 = new BackupTableDetails("bkp_error_log__c", "id");
		BackupTableDetails lstBackupTableDetails5 = new BackupTableDetails("bkp_integration_transaction__c", "id");
		BackupTableDetails lstBackupTableDetails6 = new BackupTableDetails("bkp_interview__c", "id");
		BackupTableDetails lstBackupTableDetails7 = new BackupTableDetails("bkp_note", "id");
		BackupTableDetails lstBackupTableDetails8 = new BackupTableDetails("bkp_response__c", "id");
		BackupTableDetails lstBackupTableDetails9 = new BackupTableDetails("bkp_response_answer__c", "id");
		BackupTableDetails lstBackupTableDetails10 = new BackupTableDetails("bkp_task", "id");
		BackupTableDetails lstBackupTableDetails11 = new BackupTableDetails("bkp_user", "id");
		BackupTableDetails lstBackupTableDetails12 = new BackupTableDetails("bkp_attachment", "id");
		lstBackupTableDetails.add(lstBackupTableDetails2);
		lstBackupTableDetails.add(lstBackupTableDetails1);
		lstBackupTableDetails.add(lstBackupTableDetails3);
		lstBackupTableDetails.add(lstBackupTableDetails4);
		lstBackupTableDetails.add(lstBackupTableDetails5);
		lstBackupTableDetails.add(lstBackupTableDetails6);
		lstBackupTableDetails.add(lstBackupTableDetails7);
		lstBackupTableDetails.add(lstBackupTableDetails8);
		lstBackupTableDetails.add(lstBackupTableDetails9);
		lstBackupTableDetails.add(lstBackupTableDetails10);
		lstBackupTableDetails.add(lstBackupTableDetails11);
		lstBackupTableDetails.add(lstBackupTableDetails12);
		return lstBackupTableDetails;
	}

	private List<ImpactTableDetails> lstImpactTableDetails() {
		List<ImpactTableDetails> lstImpactTableDetails = new ArrayList<ImpactTableDetails>();
		ImpactTableDetails lstImpactTableDetails1 = new ImpactTableDetails("APPLICATION__C",
				"LEGACY_TALEO_HIRING_AREA_MANAGER__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails2 = new ImpactTableDetails("APPLICATION__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails3 = new ImpactTableDetails("APPLICATION__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails4 = new ImpactTableDetails("INTERVIEW__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails5 = new ImpactTableDetails("TASK", "DESCRIPTION", "TEXT");
		ImpactTableDetails lstImpactTableDetails6 = new ImpactTableDetails("ASSESSMENT__C", "ADPSCREENINGID__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails7 = new ImpactTableDetails("RESPONSE__C", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails8 = new ImpactTableDetails("EMAILMESSAGE", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails9 = new ImpactTableDetails("ERROR_LOG__C", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails10 = new ImpactTableDetails("ATTACHMENT", "INTERVIEWER_COMMENT__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails11 = new ImpactTableDetails("RESPONSE_ANSWER__C",
				"INTERVIEWER_COMMENT__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails12 = new ImpactTableDetails("INTEGRATION_TRANSACTION__C",
				"INTERVIEWER_COMMENT__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails13 = new ImpactTableDetails("USER", "INTERVIEWER_COMMENT__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails14 = new ImpactTableDetails("NOTE", "INTERVIEWER_COMMENT__C", "TEXT");
		lstImpactTableDetails.add(lstImpactTableDetails1);
		lstImpactTableDetails.add(lstImpactTableDetails2);
		lstImpactTableDetails.add(lstImpactTableDetails3);
		lstImpactTableDetails.add(lstImpactTableDetails4);
		lstImpactTableDetails.add(lstImpactTableDetails5);
		lstImpactTableDetails.add(lstImpactTableDetails6);
		lstImpactTableDetails.add(lstImpactTableDetails7);
		lstImpactTableDetails.add(lstImpactTableDetails8);
		lstImpactTableDetails.add(lstImpactTableDetails9);
		lstImpactTableDetails.add(lstImpactTableDetails10);
		lstImpactTableDetails.add(lstImpactTableDetails11);
		lstImpactTableDetails.add(lstImpactTableDetails12);
		lstImpactTableDetails.add(lstImpactTableDetails13);
		lstImpactTableDetails.add(lstImpactTableDetails14);
		return lstImpactTableDetails;
	}

	@Test
	public void refreshBackupTablesPositiveTest() throws GdprException {
		List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
		BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_assessment__c", "adpscreeningid__c");
		BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_interview__c",
				"candidate_last_name__c");
		lstBackupTableDetails.add(lstBackupTableDetails2);
		lstBackupTableDetails.add(lstBackupTableDetails1);
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails)).thenReturn(Boolean.TRUE);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Boolean refreshBkpupTableStatus = backupTableProcessor.refreshBackupTables(lstBackupTableDetails);
		assertEquals(Boolean.TRUE, refreshBkpupTableStatus);

	}

	@Test(expected = Exception.class)
	public void refreshBackupTablesNegativeTest() throws GdprException {
		List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
		BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_assessment__c", "adpscreeningid__c");
		BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_interview__c",
				"candidate_last_name__c");
		lstBackupTableDetails.add(lstBackupTableDetails2);
		lstBackupTableDetails.add(lstBackupTableDetails1);
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.when(backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails)).thenThrow(Exception.class);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		// Boolean refreshBkpupTableStatus =
		backupTableProcessor.refreshBackupTables(lstBackupTableDetails);
	}

}
