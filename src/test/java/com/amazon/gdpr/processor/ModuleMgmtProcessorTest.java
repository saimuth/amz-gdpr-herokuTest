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

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class ModuleMgmtProcessorTest {
	int runId = 0;
	@InjectMocks
	ModuleMgmtProcessor moduleMgmtProcessor;
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initiateModuleMgmtPositiveTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");

		Mockito.doNothing().when(runMgmtDaoImpl).insertModuleUpdates(runModuleMgmt);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		moduleMgmtProcessor.initiateModuleMgmt(runModuleMgmt);

	}

	@Test(expected=Exception.class)
	public void initiateModuleMgmtExceptionTest() throws GdprException {

		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date(); //
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");

		//Mockito.doNothing().when(runMgmtDaoImpl).insertModuleUpdates(runModuleMgmt);
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).insertModuleUpdates(runModuleMgmt);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		moduleMgmtProcessor.initiateModuleMgmt(runModuleMgmt);
	}

	/*
	 * @Test(expected=Exception.class) public void prevJobModuleStatusFalseTest()
	 * throws GdprException { //int count =
	 * runMgmtDaoImpl.prevModuleRunStatus(runId, moduleName); long runId=3L; String
	 * moduleName="Initialization Module"; int count = 1; String prevModuleStatus =
	 * GlobalConstants.STATUS_SUCCESS;
	 * Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId,
	 * moduleName)).thenReturn(lstrunModuleMgmt()); String
	 * res=moduleMgmtProcessor.prevJobModuleStatus(runId, moduleName);
	 * assertEquals(prevModuleStatus, res); }
	 */
	
	@Test
	public void prevJobModuleStatusIntializationSuccessTest() throws GdprException {
		//int count = runMgmtDaoImpl.prevModuleRunStatus(runId, moduleName);
		long runId=2L;
		String moduleName=GlobalConstants.MODULE_INITIALIZATION;
		int count = 1;
		String prevModuleStatus = GlobalConstants.STATUS_SUCCESS;
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenReturn(lstrunModuleMgmtIntializationSuccess());
		String res=moduleMgmtProcessor.prevJobModuleStatus(runId);
	  //  assertEquals(prevModuleStatus, res);
	}
	
	@Test
	public void prevJobModuleStatusIntializationFailureTest() throws GdprException {
		//int count = runMgmtDaoImpl.prevModuleRunStatus(runId, moduleName);
		long runId=2L;
		String moduleName=GlobalConstants.MODULE_INITIALIZATION;
		int count = 1;
		String prevModuleStatus = GlobalConstants.STATUS_FAILURE;
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenReturn(lstrunModuleMgmIntializationFailure());
		String res=moduleMgmtProcessor.prevJobModuleStatus(runId);
		System.out.println("::"+res);
	    assertEquals(prevModuleStatus, res);
	}
	
	@Test
	public void prevJobModuleStatusBackUpSuccessTest() throws GdprException {
		//int count = runMgmtDaoImpl.prevModuleRunStatus(runId, moduleName);
		long runId=2L;
		String moduleName=GlobalConstants.MODULE_DATABACKUP;
		int count = 1;
		String prevModuleStatus = GlobalConstants.STATUS_SUCCESS;
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenReturn(lstrunModuleMgmIntializationFailure());
		String res=moduleMgmtProcessor.prevJobModuleStatus(runId);
		System.out.println("::"+res);
	  //  assertEquals(prevModuleStatus, res);
	}
	private List<RunModuleMgmt> lstrunModuleMgmt1(){
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt= new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1= new RunModuleMgmt();
		/*
		 * RunModuleMgmt runModuleMgmt1= new RunModuleMgmt(1L,2L,
		 * "GlobalConstants.MODULE_DATABACKUP", "Anonymize Initialize Sub Module",
		 * "GlobalConstants.STATUS_SUCCESS", moduleStartDateTime, moduleStartDateTime,
		 * "Run Anonymization Initiated Count : 0",1);
		 */
	runModuleMgmt1.setComments("Run Anonymization Initiated Count : 0");
	runModuleMgmt1.setCount(1);
	runModuleMgmt1.setModuleEndDateTime(moduleEndDateTime);
	runModuleMgmt1.setModuleStartDateTime(moduleStartDateTime);
	runModuleMgmt1.setSubModuleName("Anonymize Initialize Sub Module");
	runModuleMgmt1.setModuleName("GlobalConstants.MODULE_DATABACKUP");
	runModuleMgmt1.setRunId(2L);
	runModuleMgmt1.setModuleStatus("GlobalConstants.STATUS_SUCCESS");
	runModuleMgmt1.setModuleId(1L);
	
	lstrunModuleMgmt.add(runModuleMgmt1);
	return lstrunModuleMgmt;
	}
	private List<RunModuleMgmt> lstrunModuleMgmtIntializationSuccess(){
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt= new ArrayList<RunModuleMgmt>();
		/*
		 * RunModuleMgmt runModuleMgmt1= new RunModuleMgmt(1L,2L,
		 * "GlobalConstants.MODULE_INITIALIZATION", "Anonymize Initialize Sub Module",
		 * "GlobalConstants.STATUS_SUCCESS", moduleStartDateTime, moduleStartDateTime,
		 * "Run Anonymization Initiated Count : 0",4);
		 */
	RunModuleMgmt runModuleMgmt2= new RunModuleMgmt();
	runModuleMgmt2.setComments("Run Anonymization Initiated Count : 0");
	runModuleMgmt2.setCount(4);
	runModuleMgmt2.setModuleEndDateTime(moduleEndDateTime);
	runModuleMgmt2.setModuleStartDateTime(moduleStartDateTime);
	runModuleMgmt2.setSubModuleName("Anonymize Initialize Sub Module");
	runModuleMgmt2.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
	runModuleMgmt2.setRunId(2L);
	runModuleMgmt2.setModuleStatus("GlobalConstants.STATUS_SUCCESS");
	runModuleMgmt2.setModuleId(1L);
	lstrunModuleMgmt.add(runModuleMgmt2);
	
	lstrunModuleMgmt.add(runModuleMgmt2);
	
	return lstrunModuleMgmt;
	}
	private List<RunModuleMgmt> lstrunModuleMgmIntializationFailure(){
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt= new ArrayList<RunModuleMgmt>();
		/*
		 * RunModuleMgmt runModuleMgmt1= new RunModuleMgmt(1L,2L,
		 * "GlobalConstants.MODULE_INITIALIZATION", "Anonymize Initialize Sub Module",
		 * "GlobalConstants.STATUS_SUCCESS", moduleStartDateTime, moduleStartDateTime,
		 * "Run Anonymization Initiated Count : 0",4);
		 */
	RunModuleMgmt runModuleMgmt2= new RunModuleMgmt();
	runModuleMgmt2.setComments("Run Anonymization Initiated Count : 0");
	runModuleMgmt2.setCount(4);
	runModuleMgmt2.setModuleEndDateTime(moduleEndDateTime);
	runModuleMgmt2.setModuleStartDateTime(moduleStartDateTime);
	runModuleMgmt2.setSubModuleName("Anonymize Initialize Sub Module");
	runModuleMgmt2.setModuleName("GlobalConstants.MODULE_INITIALIZATION");
	runModuleMgmt2.setRunId(2L);
	runModuleMgmt2.setModuleStatus("GlobalConstants.STATUS_SUCCESS");
	runModuleMgmt2.setModuleId(1L);
	lstrunModuleMgmt.add(runModuleMgmt2);
	return lstrunModuleMgmt;
	}
	
	
	/*
	 * @Test public void prevJobModuleStatusTrueTest1() throws GdprException { long
	 * runId=3L; String moduleName=GlobalConstants.MODULE_INITIALIZATION; int count
	 * = 5; Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId,
	 * moduleName)).thenReturn(count); boolean
	 * res=moduleMgmtProcessor.prevJobModuleStatus(runId, moduleName);
	 * assertEquals(Boolean.TRUE, res); }
	 * 
	 * @Test public void prevJobModuleStatusTrueTest2() throws GdprException { long
	 * runId=3L; String moduleName=GlobalConstants.MODULE_DATABACKUP; int count = 6;
	 * Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId,
	 * moduleName)).thenReturn(count); boolean
	 * res=moduleMgmtProcessor.prevJobModuleStatus(runId, moduleName);
	 * assertEquals(Boolean.TRUE, res); }
	 * 
	 * @Test public void prevJobModuleStatusTrueTest3() throws GdprException { long
	 * runId=3L; String moduleName=GlobalConstants.MODULE_DEPERSONALIZATION; int
	 * count = 7; Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId,
	 * moduleName)).thenReturn(count); boolean
	 * res=moduleMgmtProcessor.prevJobModuleStatus(runId, moduleName);
	 * assertEquals(Boolean.TRUE, res); }
	 */
	  
	  @Test(expected=Exception.class)
      public void prevJobModuleStatusExceptionTest() throws GdprException {
		long runId=3L;
		String moduleName=GlobalConstants.MODULE_DEPERSONALIZATION;
		int count = 7;
		Mockito.when(runMgmtDaoImpl.prevModuleRunStatus(runId)).thenThrow(Exception.class);
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		moduleMgmtProcessor.prevJobModuleStatus(runId);
	}
}

