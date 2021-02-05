package com.amazon.gdpr.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.processor.DataLoadProcessor;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.processor.RunMgmtProcessor;
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ReOrganizeInputService.class)
public class ReOrganizeInputServiceTest {
	 @Before
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	    }
		@Mock
		JobLauncher jobLauncher;
		
		@Mock
	    Job processGdprDepersonalizationJob;
		
		@Mock
		RunMgmtProcessor runMgmtProcessor;

		@Mock
		ModuleMgmtProcessor moduleMgmtProcessor;
		
		@Mock
		DataLoadProcessor dataLoadProcessor;
		
		@Mock
		GdprInputDaoImpl gdprInputDaoImpl;
	 @Test
	public void reOrganizeDataTest() {
		
	}
}
