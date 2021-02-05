package com.amazon.gdpr.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.amazon.gdpr.processor.TagDataProcessor;
import com.amazon.gdpr.util.GdprException;

public class DepersonalizationServiceTest {

	@Mock
	TagDataProcessor tagDataProcessor;
	@InjectMocks
	DepersonalizationService depersonalizationService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void depersonalizeTest() throws GdprException {
		//tagDataProcessor.taggingInitialize(runId);
		Long runId=7L;
		Mockito.doNothing().when(tagDataProcessor).taggingInitialize(runId);
		depersonalizationService.depersonalize(runId);
		
	}
}
