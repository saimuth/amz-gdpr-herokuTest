package com.amazon.gdpr.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazon.gdpr.dao.RunSummaryDaoImpl.RunSummaryMgmtRowMapper;
import com.amazon.gdpr.dao.RunSummaryDaoImpl.SummaryDataRowMapper;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.model.gdpr.output.SummaryData;
import com.amazon.gdpr.util.SqlQueriesConstant;

public class RunSummaryDaoImplTest {
	@InjectMocks
	RunSummaryDaoImpl runSummaryDaoImpl;
	@Mock
	JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(runSummaryDaoImpl, "jdbcTemplate", jdbcTemplate);

	}

	@Test
	public void batchInsertRunSummaryMgmtTest() {
		int batchSize = 100;
		runSummaryDaoImpl.batchInsertRunSummaryMgmt(lstRunSummaryMgmt(), batchSize);
	}

	@Test
	public void fetchSummaryDetailsTest() {
		Long runId = 2L; //
		Mockito.when(jdbcTemplate.query(any(String.class), (Object[]) anyVararg(), any(SummaryDataRowMapper.class)))
				.thenReturn(lstSummaryData());
		List<SummaryData> lstSummaryData = runSummaryDaoImpl.fetchSummaryDetails(runId);
		assertEquals(lstSummaryData().size(), lstSummaryData.size());
	}
	
	@Test
	public void insertRunSummaryMgmtTest() {
		runSummaryDaoImpl.insertRunSummaryMgmt(lstRunSummaryMgmt());

	}

	@Test
	public void tagDataCountUpdateTest() {
		runSummaryDaoImpl.tagDataCountUpdate(anyString());
	}
	@Test
	public void depersonalizationCountUpdateTest() {
		runSummaryDaoImpl.depersonalizationCountUpdate(7,2L);	
	}
	
	@Test
	public void fetchRunSummaryDetailTest() {
	
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from XX", " Alter Table XX");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from XX", " Alter Table XX");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		Mockito.when(jdbcTemplate.query(any(String.class), (Object[]) anyVararg(), any(RunSummaryMgmtRowMapper.class)))
				.thenReturn(lstRunSummaryMgmt);
		Long runId = 3L;
		List<RunSummaryMgmt> lstRunSummaryMgmtRes = runSummaryDaoImpl.fetchRunSummaryDetail(runId);
		assertEquals(lstRunSummaryMgmt.size(), lstRunSummaryMgmtRes.size());
	}

	@Test
	public void fetchRunSummaryDetailTestwithTwoParam() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from XX", " Alter Table XX");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from XX", " Alter Table XX");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		Mockito.when(jdbcTemplate.query(any(String.class), (Object[]) anyVararg(), any(RunSummaryMgmtRowMapper.class)))
				.thenReturn(lstRunSummaryMgmt);
		Long runId = 3L;
		Long summaryId=7L;
		RunSummaryMgmt lstRunSummaryMgmtRes = runSummaryDaoImpl.fetchRunSummaryDetail(runId,summaryId);
	}
	
	
	private List<SummaryData> lstSummaryData() {
		List<SummaryData> lstSummaryData = new ArrayList<SummaryData>();
		SummaryData summaryData1 = new SummaryData(2, "EUR-EU", "AUT", "APPLICATION__C", "SF_ARCHIVE",
				"FELONY_CONVICTION_QUESTION_2__C", "TEXT", "PRIVACY DELETED", 2);
		SummaryData summaryData2 = new SummaryData(2, "EUR-EU", "BEL", "APPLICATION__C", "SF_ARCHIVE", "CANDIDATE_C",
				"TEXT", "PRIVACY DELETED", 2);
		lstSummaryData.add(summaryData2);
		lstSummaryData.add(summaryData1);
		return lstSummaryData;
	}

	
	private List<RunSummaryMgmt> lstRunSummaryMgmt() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();
		RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "APPLICATION__C",
				"select * from XX", " Alter Table XX");
		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT", 1, "INTERVIEW__C",
				"select * from XX", " Alter Table XX");
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		lstRunSummaryMgmt.add(runSummaryMgmt1);
		return lstRunSummaryMgmt;
	}
}
