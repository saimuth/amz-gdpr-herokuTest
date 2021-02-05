package com.amazon.gdpr.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.amazon.gdpr.dao.BackupTableProcessorDaoImpl.BackupTableDetailsRowMapper;
import com.amazon.gdpr.dao.RunMgmtDaoImpl.RunMgmtRowMapper;
import com.amazon.gdpr.model.gdpr.output.BackupTableDetails;
import com.amazon.gdpr.util.SqlQueriesConstant;

@RunWith(MockitoJUnitRunner.class)
public class BackupTableProcessorDaoImplTest {

	@Mock
	JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	BackupTableProcessorDaoImpl backupTableProcessorDaoImpl;

	
	@Before
	public void setUp() throws Exception {
	    MockitoAnnotations.initMocks(this);
	    ReflectionTestUtils.setField(backupTableProcessorDaoImpl ,"jdbcTemplate" , 
	    		jdbcTemplate);

	}
	@Test
	public void alterBackupTableTest() {
		backupTableProcessorDaoImpl.alterBackupTable(any(String[].class));
	}
	@Test
	public void getBackupTableListTest() {
		Set lstBackupTables = new HashSet();
		lstBackupTables.add("GDPR.bkp_assessment__c");
		lstBackupTables.add("GDPR.bkp_interview__c");
		Set res=backupTableProcessorDaoImpl.getBackupTableList(lstBackupTableDetails());
		assertEquals(lstBackupTables.size(), res.size());
	}
	
	@Test
	public void refreshBackupTablesTrueTest() {
		 Boolean bkpupRefreshStatus = true;
		boolean res=backupTableProcessorDaoImpl.refreshBackupTables(lstBackupTableDetails());
		assertEquals(Boolean.TRUE, res);
	}
	
	
	@Test
	public void fetchBackupTableDetailsTest() {
	
		Mockito.when(jdbcTemplate.query(any(String.class), any(BackupTableDetailsRowMapper.class))).thenReturn(lstBackupTableDetails());
		List<BackupTableDetails> lstBackupTableDetails=backupTableProcessorDaoImpl.fetchBackupTableDetails();
		assertEquals(lstBackupTableDetails.size(), lstBackupTableDetails().size());
	}
	
	private List<BackupTableDetails> lstBackupTableDetails(){
	List<BackupTableDetails> lstBackupTableDetails = new ArrayList<BackupTableDetails>();
	BackupTableDetails lstBackupTableDetails1 = new BackupTableDetails("bkp_assessment__c", "adpscreeningid__c");
	BackupTableDetails lstBackupTableDetails2 = new BackupTableDetails("bkp_interview__c",
			"candidate_last_name__c");
	lstBackupTableDetails.add(lstBackupTableDetails2);
	lstBackupTableDetails.add(lstBackupTableDetails1);
	return lstBackupTableDetails;
	}
}
