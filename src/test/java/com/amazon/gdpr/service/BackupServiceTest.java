package com.amazon.gdpr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.processor.RunMgmtProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

public class BackupServiceTest {

	@Mock
	JobLauncher jobLauncher;

	@Mock
	Job processGdprBackupServiceJob;

	@Mock
	RunMgmtProcessor runMgmtProcessor;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@InjectMocks
	BackupService backupService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void backupServiceInitiateTest() {
		long runId=7L;
		backupService.backupServiceInitiate(runId);
	}
	
	
	
	/*
	 * @Test public void backupServiceTest() throws GdprException { long runId = 7L;
	 * // List<RunModuleMgmt> lstRunModuleMgmt = //
	 * gdprOutputDaoImpl.fetchLastModuleData(runId);
	 * 
	 * Date moduleStartDateTime = null; Date moduleEndDateTime = null;
	 * moduleStartDateTime = new Date(); moduleEndDateTime = new Date(); String
	 * moduleStatus = GlobalConstants.STATUS_SUCCESS; String backupServiceStatus =
	 * GlobalConstants.MSG_BACKUPSERVICE_JOB; RunModuleMgmt runModuleMgmt = new
	 * RunModuleMgmt(runId, GlobalConstants.MODULE_BACKUPSERVICE,
	 * GlobalConstants.SUB_MODULE_BACKUPSERVICE_JOB_INITIALIZE, moduleStatus,
	 * moduleStartDateTime, moduleEndDateTime, backupServiceStatus);
	 * Mockito.when(gdprOutputDaoImpl.fetchLastModuleData(runId)).thenReturn(
	 * lstRunModuleMgmt());
	 * Mockito.when(gdprOutputDaoImpl.fetchLastModuleData(runId)).thenReturn(
	 * lstRunModuleMgmt());
	 * Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(
	 * runModuleMgmt); backupService.backupService(runId); }
	 * 
	 * @Test(expected=Exception.class) public void backupServiceExceptionTest()
	 * throws GdprException { long runId = 7L; // List<RunModuleMgmt>
	 * lstRunModuleMgmt = // gdprOutputDaoImpl.fetchLastModuleData(runId);
	 * 
	 * Date moduleStartDateTime = null; Date moduleEndDateTime = null;
	 * moduleStartDateTime = new Date(); moduleEndDateTime = new Date(); String
	 * moduleStatus = GlobalConstants.STATUS_SUCCESS; String backupServiceStatus =
	 * GlobalConstants.MSG_BACKUPSERVICE_JOB; RunModuleMgmt runModuleMgmt = new
	 * RunModuleMgmt(runId, GlobalConstants.MODULE_BACKUPSERVICE,
	 * GlobalConstants.SUB_MODULE_BACKUPSERVICE_JOB_INITIALIZE, moduleStatus,
	 * moduleStartDateTime, moduleEndDateTime, backupServiceStatus);
	 * //Mockito.when(gdprOutputDaoImpl.fetchLastModuleData(runId)).thenReturn(
	 * lstRunModuleMgmt());
	 * Mockito.when(gdprOutputDaoImpl.fetchLastModuleData(runId)).thenThrow(
	 * Exception.class);
	 * Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(
	 * runModuleMgmt); backupService.backupService(runId); }
	 */
	
	/*
	 * @Test(expected=Exception.class) public void backupServiceExceptionTest2()
	 * throws GdprException { long runId = 7L; // List<RunModuleMgmt>
	 * lstRunModuleMgmt = // gdprOutputDaoImpl.fetchLastModuleData(runId);
	 * 
	 * Date moduleStartDateTime = null; Date moduleEndDateTime = null;
	 * moduleStartDateTime = new Date(); moduleEndDateTime = new Date(); String
	 * moduleStatus = GlobalConstants.STATUS_SUCCESS; String backupServiceStatus =
	 * GlobalConstants.MSG_BACKUPSERVICE_JOB; RunModuleMgmt runModuleMgmt = new
	 * RunModuleMgmt(runId, GlobalConstants.MODULE_BACKUPSERVICE,
	 * GlobalConstants.SUB_MODULE_BACKUPSERVICE_JOB_INITIALIZE, moduleStatus,
	 * moduleStartDateTime, moduleEndDateTime, backupServiceStatus);
	 * //Mockito.when(gdprOutputDaoImpl.fetchLastModuleData(runId)).thenReturn(
	 * lstRunModuleMgmt());
	 * Mockito.when(gdprOutputDaoImpl.fetchLastModuleData(runId)).thenReturn(
	 * lstRunModuleMgmt());
	 * Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt
	 * (runModuleMgmt);
	 * //doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
	 * backupService.backupService(runId); }
	 */
	@Test
	public void performBackupTest() {
		Map<String, Set<String>> map= new HashMap<String, Set<String>>();
		Set<String> setList= new HashSet<String>();
		setList.add("Test");
		map.put("Test",setList);
		Map<String, RunSummaryMgmt> mapRunSummaryMgmt= new HashMap<String, RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(7L, 2, "EUR-EU", "AUT", 2, "APPLICATION__C",
				"select * from XX", " Alter Table XX");
		mapRunSummaryMgmt.put("Test", runSummaryMgmt1);
		backupService.performBackup(map, mapRunSummaryMgmt);
	}
	@Test
	public void fetchParentIds() {
		backupService.fetchParentIds();
	}
	
	

	
	private List<RunModuleMgmt> lstRunModuleMgmtException() {
		long runId = 7L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String moduleStatus = GlobalConstants.STATUS_SUCCESS;
		List<RunModuleMgmt> lstRunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt(runId, "Initialization Module",
				"ReOrganize Input data Batch Sub ModuleTest", moduleStatus, moduleStartDateTime, moduleEndDateTime,
				GlobalConstants.ERR_TAGGED_JOB_RUN);
		RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt(runId, "Initialization Module",
				"ReOrganize Input Data Job Sub Module", moduleStatus, moduleStartDateTime, moduleEndDateTime,
				GlobalConstants.ERR_TAGGED_JOB_RUN);
		RunModuleMgmt runModuleMgmt3 = new RunModuleMgmt(runId, "Initialization Module",
				"Summary Data Initialize Sub Module", moduleStatus, moduleStartDateTime, moduleEndDateTime,
				GlobalConstants.ERR_TAGGED_JOB_RUN);
		lstRunModuleMgmt.add(runModuleMgmt3);
		lstRunModuleMgmt.add(runModuleMgmt2);
		lstRunModuleMgmt.add(runModuleMgmt1);
		return lstRunModuleMgmt;
	}

	private List<RunModuleMgmt> lstRunModuleMgmt() {
		long runId = 7L;
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		String moduleStatus = GlobalConstants.STATUS_SUCCESS;
		List<RunModuleMgmt> lstRunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt1 = new RunModuleMgmt(runId, "Initialization Module",
				"ReOrganize Input data Batch Sub Module", moduleStatus, moduleStartDateTime, moduleEndDateTime,
				GlobalConstants.ERR_TAGGED_JOB_RUN);
		RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt(runId, "Initialization Module",
				"ReOrganize Input Data Job Sub Module", moduleStatus, moduleStartDateTime, moduleEndDateTime,
				GlobalConstants.ERR_TAGGED_JOB_RUN);
		RunModuleMgmt runModuleMgmt3 = new RunModuleMgmt(runId, "Initialization Module",
				"Summary Data Initialize Sub Module", moduleStatus, moduleStartDateTime, moduleEndDateTime,
				GlobalConstants.ERR_TAGGED_JOB_RUN);
		lstRunModuleMgmt.add(runModuleMgmt3);
		lstRunModuleMgmt.add(runModuleMgmt2);
		lstRunModuleMgmt.add(runModuleMgmt1);
		return lstRunModuleMgmt;
	}
}
