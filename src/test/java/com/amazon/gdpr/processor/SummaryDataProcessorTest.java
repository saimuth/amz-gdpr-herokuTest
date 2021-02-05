
package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.model.gdpr.output.SummaryData;
import com.amazon.gdpr.util.GdprException;

@RunWith(MockitoJUnitRunner.class)
public class SummaryDataProcessorTest {

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	@InjectMocks
	SummaryDataProcessor SummaryDataProcessor;

	@Mock
	TagQueryProcessor tagQueryProcessor;

	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processSummaryDataTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenReturn(lstSummaryData());
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(tagQueryProcessor.updateSummaryQuery(runId, lstRunSummaryMgmt())).thenReturn(lstRunSummaryMgmt());
		String summaryDataProcessStatus = "Summary details : 0";
		String summaryDataProcessStatusRes = SummaryDataProcessor.processSummaryData(runId);
		//System.out.println("Res: " + summaryDataProcessStatusRes);
		 assertEquals(summaryDataProcessStatus,summaryDataProcessStatusRes);
	}

	/*
	 * @Test(expected = Exception.class) public void
	 * processSummaryDataExceptionTest5() throws GdprException { Date
	 * moduleStartDateTime = null; Date moduleEndDateTime = null;
	 * moduleStartDateTime = new Date(); moduleEndDateTime = new Date();
	 * RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module",
	 * "Anonymize Initialize Sub Module", "FAILURE", moduleStartDateTime,
	 * moduleStartDateTime, "Run Anonymization Initiated Count : 0"); RunErrorMgmt
	 * runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX"); int
	 * batchSize = 100; Long runId = 7L;
	 * Mockito.doNothing().when(runSummaryDaoImpl).batchInsertRunSummaryMgmt(
	 * lstRunSummaryMgmt(), batchSize);
	 * Mockito.when(tagQueryProcessor.updateSummaryQuery(runId,
	 * lstRunSummaryMgmt())).thenThrow(Exception.class);
	 * Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(
	 * runModuleMgmt);
	 * 
	 * SummaryDataProcessor.processSummaryData(runId); }
	 */
	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest4() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(anyInt())).thenThrow(Exception.class);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.when(tagQueryProcessor.updateSummaryQuery(anyInt(), anyList())).thenThrow(Exception.class);
		SummaryDataProcessor.processSummaryData(runId);
	}
	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest3() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(anyInt())).thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.when(tagQueryProcessor.updateSummaryQuery(anyInt(), anyList())).thenThrow(Exception.class);
		SummaryDataProcessor.processSummaryData(runId);
	}
	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest2() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Long runId = 7L;
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Mockito.when(tagQueryProcessor.updateSummaryQuery(anyInt(), anyList())).thenThrow(Exception.class);
		SummaryDataProcessor.processSummaryData(runId);
	}

	@Test(expected = Exception.class)
	public void processSummaryDataExceptionTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		Long runId = 7L;
		 SummaryDataProcessor.processSummaryData(runId);
	}

	@Test
	public void extractSummaryDetailsTest() throws GdprException {
		Long runId = 2L;

		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenReturn(lstSummaryData());
		List<SummaryData> lstSummaryDataResult = SummaryDataProcessor.extractSummaryDetails(runId);
		assertEquals(lstSummaryData().size(), lstSummaryDataResult.size());

	}

	@Test(expected = Exception.class)
	public void extractSummaryDetailsNegativeTest() throws GdprException {
		Long runId = 7L;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		// Error below line
		Mockito.when(runSummaryDaoImpl.fetchSummaryDetails(runId)).thenThrow(Exception.class);
		SummaryDataProcessor.extractSummaryDetails(runId);

	}

	@Test
	public void loadRunSummaryMgmtPositiveTest() throws GdprException {
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		int batchSize = 1000;
		Mockito.doNothing().when(runSummaryDaoImpl).batchInsertRunSummaryMgmt(lstRunSummaryMgmt(), batchSize);
		long runId = 7L;
		SummaryDataProcessor.loadRunSummaryMgmt(runId, lstRunSummaryMgmt());
	}

	@Test(expected = Exception.class)
	public void loadRunSummaryMgmtExceptionTest() throws GdprException {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from XX", " Alter Table XX"); //
		runSummaryMgmt1.setTaggedQueryLoad("Update table APPLICATION__C set ..where ");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from XX", " Alter Table XX");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");

		int batchSize = 10;
		Mockito.doThrow(Exception.class).when(runSummaryDaoImpl).batchInsertRunSummaryMgmt(anyList(), anyInt());
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		long runId = 1L; // Update from git
		SummaryDataProcessor.loadRunSummaryMgmt(runId, lstRunSummaryMgmt);
	}

	@Test
	public void transformSummaryDetailsTest() throws GdprException {
		long runId = 7L;
		List<RunSummaryMgmt> lstRunSummaryMgmtRes = SummaryDataProcessor.transformSummaryDetails(runId,
				lstSummaryData());
		assertEquals(lstRunSummaryMgmt().size(), lstRunSummaryMgmtRes.size());
	}

	// need

	/*
	 * @Test(expected = Exception.class) public void
	 * transformSummaryDetailsExceptionTest() throws GdprException { long runId =
	 * 7L; //RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX",
	 * "Test", "XX"); // gdprOutputDaoImpl.loadErrorDetails(runErrorMgmt);
	 * //Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
	 * // List<RunSummaryMgmt> lstRunSummaryMgmtRes =
	 * SummaryDataProcessor.transformSummaryDetails(anyInt(), anyList()); }
	 */

	private List<RunSummaryMgmt> lstRunSummaryMgmt() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(7L, 2, "EUR-EU", "AUT", 2, "APPLICATION__C",
				"select * from XX", " Alter Table XX");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 3, "INTERVIEW__C",
				"select * from XX", " Alter Table XX");
		RunSummaryMgmt runSummaryMgmt3 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 4, "TASK", "select * from XX",
				" Alter Table XX");
		RunSummaryMgmt runSummaryMgmt4 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 8, "ATTACHMENT", "select * from XX",
				" Alter Table XX");
		RunSummaryMgmt runSummaryMgmt5 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 9, "RESPONSE__C",
				"select * from XX", " Alter Table XX");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		lstRunSummaryMgmt.add(runSummaryMgmt3);
		lstRunSummaryMgmt.add(runSummaryMgmt4);
		lstRunSummaryMgmt.add(runSummaryMgmt5);
		return lstRunSummaryMgmt;
	}

	private List<SummaryData> lstSummaryData() {
		List<SummaryData> lstSummaryData = new ArrayList<SummaryData>();
		SummaryData summaryData1 = new SummaryData(2, "EUR-EU", "AUT", "APPLICATION__C", "SF_ARCHIVE",
				"FELONY_CONVICTION_QUESTION_2__C", "TEXT", "PRIVACY DELETED", 2);
		SummaryData summaryData2 = new SummaryData(2, "EUR-EU", "BEL", "INTERVIEW__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "NULL", 3);
		SummaryData summaryData3 = new SummaryData(2, "EUR-EU", "BEL", "TASK", "SF_ARCHIVE", "CANDIDATE_C", "TEXT",
				"EMPTY", 4);
		SummaryData summaryData4 = new SummaryData(2, "EUR-EU", "BEL", "ATTACHMENT", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "ALL ZEROS", 8);
		SummaryData summaryData5 = new SummaryData(2, "EUR-EU", "BEL", "RESPONSE__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "PRIVACY DELETED", 9);
//		3	EUR-EU	AUT	ASSESSMENT__C	SF_ARCHIVE	ADPSCREENINGID__C	TEXT	PRIVACY DELETED	6
//		3	EUR-EU	AUT	RESPONSE__C	SF_ARCHIVE	INTERVIEWER_COMMENT__C	TEXT	PRIVACY DELETED	9		
		lstSummaryData.add(summaryData2);
		lstSummaryData.add(summaryData1);
		lstSummaryData.add(summaryData3);
		lstSummaryData.add(summaryData4);
		lstSummaryData.add(summaryData5);
		return lstSummaryData;
	}
}
