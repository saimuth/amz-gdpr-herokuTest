package com.amazon.gdpr.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.tree.RowMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.util.ReflectionTestUtils;

//import com.amazon.gdpr.dao.GdprOutputDaoImpl.SummaryDataRowMapper;
import com.amazon.gdpr.dao.RunMgmtDaoImpl.RunMgmtRowMapper;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.util.SqlQueriesConstant;

@RunWith(MockitoJUnitRunner.class)
public class RunMgmtDaoImplTest {

	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(runMgmtDaoImpl, "jdbcTemplate", jdbcTemplate);

	}

	private static String RUNMGMT_LAST_RUN = SqlQueriesConstant.RUNMGMT_LAST_RUN;

	@Test
	public void fetchLastRunDetailTest() {
		List<RunMgmt> lstrunMgmt = new ArrayList<RunMgmt>();
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");
		lstrunMgmt.add(runMgmt);
		// List<RunMgmt> lstLastRunMgmt = jdbcTemplate.query(RUNMGMT_LAST_RUN, new
		// RunMgmtRowMapper());
		Mockito.when(jdbcTemplate.query(any(String.class), any(RunMgmtRowMapper.class))).thenReturn(lstrunMgmt);
		runMgmtDaoImpl.fetchLastRunDetail();
	}

	@Test
	public void initiateNewRunTest() {
		String runName = "Test";
		runMgmtDaoImpl.initiateNewRun(runName);
	}

	@Test
	public void insertModuleUpdatesTest() {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");

		runMgmtDaoImpl.insertModuleUpdates(runModuleMgmt);
	}

	@Test
	public void prevModuleRunStatusTest() {
		long runId = 7L;
		String moduleName = "Initialization Module";
		int count = 1;
		List<RunModuleMgmt> lstrunModuleMgmtRes = lstrunModuleMgmIntializationFailure();
		Mockito.when(jdbcTemplate.queryForObject(any(String.class), (Object[]) anyVararg(), eq(Integer.class)))
				.thenReturn(count);
		List<RunModuleMgmt> countRes = runMgmtDaoImpl.prevModuleRunStatus(runId);
		System.out.println("countRes:" + countRes);
	}

	@Test
	public void updateRunCommentsTest() {
		long runId = 7L;
		String runComments = "Run Test";
		runMgmtDaoImpl.updateRunComments(runId, runComments);
	}
	@Test
	public void updateRunStatusTest() {
		long runId=7L;
        String runStatus="TestStatus";
        String runComments="TestCheck";
        runMgmtDaoImpl.updateRunStatus(runId, runStatus, runComments);
	}

	private List<RunModuleMgmt> lstrunModuleMgmIntializationFailure() {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		List<RunModuleMgmt> lstrunModuleMgmt = new ArrayList<RunModuleMgmt>();
		RunModuleMgmt runModuleMgmt2 = new RunModuleMgmt();
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

}
