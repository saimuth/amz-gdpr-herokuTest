package com.amazon.gdpr.processor;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.gdpr.output.DataLoad;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.util.GdprException;

public class DataLoadProcessorTest {
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@InjectMocks
	DataLoadProcessor dataLoadProcessor;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void updateDataLoadTest() throws GdprException {
		/*
		 * long now = System.currentTimeMillis(); Timestamp sqlTimestamp = new
		 * Timestamp(now); DataLoad dataLoad = new DataLoad("APPLIVATION_C", "AUT",
		 * sqlTimestamp);
		 */
		
		Long runId = 2L;
		int mockRes = 0;
		Mockito.when(gdprOutputDaoImpl.updateDataLoad(runId)).thenReturn(mockRes);
		dataLoadProcessor.updateDataLoad(runId);
	}

	@Test(expected=Exception.class)
	public void updateDataLoadExceptionTest() throws GdprException {
		Long runId = 2L;
		Mockito.when(gdprOutputDaoImpl.updateDataLoad(anyInt())).thenThrow(Exception.class);
		dataLoadProcessor.updateDataLoad(runId);
	}

}
