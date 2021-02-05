package com.amazon.gdpr.processor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

import com.amazon.gdpr.dao.BackupServiceDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.dao.RunSummaryDaoImpl;
import com.amazon.gdpr.util.GdprException;

public class AnonymizeProcessorTest {
	@InjectMocks
	AnonymizeProcessor aonymizeProcessor;

	@Mock
	JobLauncher jobLauncher;
	
	@Mock
    Job processAnonymizeJob;
	
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	
	@Mock
	RunSummaryDaoImpl runSummaryDaoImpl;
	
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;
	
	@Mock
	BackupServiceDaoImpl backupServiceDaoImpl;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void depersonalizationInitializeTest() throws GdprException {
		long runId=7L;
		aonymizeProcessor.depersonalizationInitialize(runId);
	}
}
