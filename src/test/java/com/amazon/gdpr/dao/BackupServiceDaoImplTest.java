package com.amazon.gdpr.dao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazon.gdpr.model.BackupServiceOutput;

public class BackupServiceDaoImplTest {
	@InjectMocks
	BackupServiceDaoImpl backupServiceDaoImpl;
	@Mock
	JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(backupServiceDaoImpl, "jdbcTemplate", jdbcTemplate);

	}

	@Test
	public void insertBackupTableTest() {
		backupServiceDaoImpl.insertBackupTable(any(String.class));
	}

	@Test
	public void updateSummaryTableTest() {
		backupServiceDaoImpl.updateSummaryTable(lstBackupServiceOutput());
	}

	@Test
	public void gdprDepStatusUpdateTest() {
		backupServiceDaoImpl.gdprDepStatusUpdate(anyLong(), anyString());
	}

	@Test
	public void tagStatusUpdateTest() {
		backupServiceDaoImpl.tagStatusUpdate(anyList(), anyString());
	}

	@Test
	public void anonymizeArchivalTablesTest() {
		backupServiceDaoImpl.anonymizeArchivalTables(anyList(), anyString());
	}

	@Test
	public void tagArchivalTablesTest() {
		backupServiceDaoImpl.tagArchivalTables(anyList(), anyString());
	}


	@Test
	public void updateTagTablesTest() {
		backupServiceDaoImpl.updateTagTables(anyList(), anyString());
	}

	private List<BackupServiceOutput> lstBackupServiceOutput() {
		List<BackupServiceOutput> lstBackupServiceOutput = new ArrayList<BackupServiceOutput>();
		BackupServiceOutput backupServiceOutput1 = new BackupServiceOutput(1521, 9, 15);
		lstBackupServiceOutput.add(backupServiceOutput1);
		return lstBackupServiceOutput;

	}
}
