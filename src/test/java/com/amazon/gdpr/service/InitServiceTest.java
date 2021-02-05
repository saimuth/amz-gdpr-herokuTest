package com.amazon.gdpr.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.processor.AnonymizationFileProcessor;
import com.amazon.gdpr.processor.BackupTableProcessor;
import com.amazon.gdpr.processor.ModuleMgmtProcessor;
import com.amazon.gdpr.processor.ReOrganizeInputProcessor;
import com.amazon.gdpr.processor.RunMgmtProcessor;
import com.amazon.gdpr.processor.SummaryDataProcessor;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.view.GdprInput;

public class InitServiceTest {
	@InjectMocks
	InitService initService;

	
	@Mock
	RunMgmtProcessor runMgmtProcessor;

	@Mock
	AnonymizationFileProcessor anonymizationProcessor;

	@Mock
	BackupTableProcessor bkpupTableProcessor;

	@Mock
	SummaryDataProcessor summaryDataProcessor;

	@Mock
	JobLauncher jobLauncher;

	@Mock
	Job processGdprDepersonalizationJob;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;
	/*
	 * @Mock ReOrganizeInputService reOrganizeInputService;
	 */
	@Mock
	ReOrganizeInputProcessor reOrganizeInputProcessor;

	@Mock
	BackupService backupService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initServiceTest() throws GdprException {
		String runName = "Test";
		Long runId = 2L;
		int insertRunAnonymizationCounts = 2;
		String initServiceReturnStatus = "";
		String processBackupTableStatus = "";
		String summaryStatus = "Summary details : " + "2";
		String reOrganizeDataStatus = "Reorganizing GDPR_Depersonalization__c initiated. ";
		List<String> selectedCountries = lstselectedCountries();
		//String expectedRes = "null; Run Anonymization Initiated Count : 2; ; Reorganizing GDPR_Depersonalization__c initiated. ; Summary details : 2";
		String expectedRes="null; Run Anonymization Initiated Count : 2; ; Summary details : 2; Reorganizing GDPR_Depersonalization__c initiated. ";
		Mockito.when(runMgmtProcessor.initializeRun(runName)).thenReturn(runId);
		Mockito.when(anonymizationProcessor.loadRunAnonymization(runId, selectedCountries))
				.thenReturn(insertRunAnonymizationCounts);
		Mockito.when(bkpupTableProcessor.processBkpupTable(runId)).thenReturn(processBackupTableStatus);
		Mockito.when(summaryDataProcessor.processSummaryData(runId)).thenReturn(summaryStatus);
		Mockito.when(reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries)).thenReturn(reOrganizeDataStatus);
		String backupServiceStatus="GlobalConstants.MSG_BACKUPSERVICE_JOB";
	//	Mockito.when(backupService.backupService(runId)).thenReturn(backupServiceStatus);
		String actualRes = initService.initService(runName, selectedCountries);
		assertEquals(expectedRes, actualRes);
	}
	private List<String> lstselectedCountries() {
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		return selectedCountries;
	}
	@Test(expected=Exception.class)
	public void initServiceExceptionTest() throws GdprException {
		String runName = "Test";
		Long runId = 2L;
		int insertRunAnonymizationCounts = 2;
		String initServiceReturnStatus = "";
		String processBackupTableStatus = "";
		String summaryStatus = "Summary details : " + "2";
		String reOrganizeDataStatus = "Reorganizing GDPR_Depersonalization__c initiated. ";
		List<String> selectedCountries = lstselectedCountries();
		String expectedRes="null; Run Anonymization Initiated Count : 2; ; Summary details : 2; Reorganizing GDPR_Depersonalization__c initiated. ";
		Mockito.when(runMgmtProcessor.initializeRun(runName)).thenThrow(Exception.class);
		Mockito.when(anonymizationProcessor.loadRunAnonymization(runId, selectedCountries))
				.thenReturn(insertRunAnonymizationCounts);
		Mockito.when(bkpupTableProcessor.processBkpupTable(runId)).thenReturn(processBackupTableStatus);
		Mockito.when(summaryDataProcessor.processSummaryData(runId)).thenReturn(summaryStatus);
		Mockito.when(reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries)).thenReturn(reOrganizeDataStatus);
		initService.initService(runName, selectedCountries);
	}
	 

	@Test
	public void initializeTest() throws GdprException {
		Long runId = 2L;
		int insertRunAnonymizationCounts = 2;
		String[] initializationStatus = new String[2];
		initializationStatus[0] = "SUCCESS";
		initializationStatus[1] ="Run Anonymization Initiated Count : 2; ; Summary details : 2; Reorganizing GDPR_Depersonalization__c initiated. ";
		String processBackupTableStatus = "";
		String summaryStatus = "Summary details : " + "2";
		List<String> selectedCountries = lstselectedCountries();
		String reOrganizeDataStatus = "Reorganizing GDPR_Depersonalization__c initiated. ";
		Mockito.when(anonymizationProcessor.loadRunAnonymization(runId, selectedCountries))
		.thenReturn(insertRunAnonymizationCounts);
		Mockito.when(bkpupTableProcessor.processBkpupTable(runId)).thenReturn(processBackupTableStatus);
		Mockito.when(summaryDataProcessor.processSummaryData(runId)).thenReturn(summaryStatus);
		Mockito.when(reOrganizeInputProcessor.reOrganizeData(runId, selectedCountries)).thenReturn(reOrganizeDataStatus);
		//String backupServiceStatus = backupService.backupService(runId);
		String backupServiceStatus="GlobalConstants.MSG_BACKUPSERVICE_JOB";
	//	Mockito.when(backupService.backupService(runId)).thenReturn(backupServiceStatus);
		String[] initializationStatusRes = initService.initialize(runId, selectedCountries);
		System.out.println(":"+initializationStatusRes[1]);
	    assertArrayEquals(initializationStatus, initializationStatusRes);
	}

	@Test
	public void initializeInsertRunAnonymizationZeroCountsTest() throws GdprException {
		Long runId = 2L;
		int insertRunAnonymizationCounts = 0;
		String[] initializationStatus = new String[2];
		initializationStatus[0] = "FAILURE";
		initializationStatus[1] = "No Run Anonymization rows to process. ";
		List<String> selectedCountries=lstselectedCountries();
		Mockito.when(anonymizationProcessor.loadRunAnonymization(runId,selectedCountries)).thenReturn(insertRunAnonymizationCounts);
		String[] initializationStatusRes = initService.initialize(runId,selectedCountries);
		assertArrayEquals(initializationStatus, initializationStatusRes);
	}
	
	@Test
	public void loadGdprFormTest() throws GdprException {
		GdprInput gdprInput = new GdprInput();
		List<String> lstSelectedRegion = new ArrayList<String>();
		lstSelectedRegion.add("BEL");
		lstSelectedRegion.add("AUT");
		List<String> lstRegion = new ArrayList<String>();
		lstRegion.add("EUR-EU");
		List<String> lstRegionsCountry = new ArrayList<String>();
		lstRegionsCountry.add("EUR-EU");
		Map<String, List<String>> mapRegionCountry = new HashMap<String, List<String>>();
		mapRegionCountry.put("EUR-EU", lstSelectedRegion);
		List<String> lstCountry = new ArrayList<String>();
		lstCountry.add("AUT");
		lstCountry.add("BEL");
		//gdprInput.setErrorStatus(" ");
		gdprInput.setLstRegion(lstRegion);
		gdprInput.setLstCountry(lstCountry);
		//latest
	//	gdprInput.setLstSelectedRegion(lstSelectedRegion);
	//	gdprInput.setLstSelectedCountryCode(new ArrayList<String>());
		gdprInput.setMapRegionCountry(mapRegionCountry);
		Mockito.when(runMgmtProcessor.loadCountryDetail()).thenReturn(gdprInput);
		GdprInput gdprInputRes =	initService.loadGdprForm();
	    assertEquals(gdprInput.getLstRegion(), gdprInputRes.getLstRegion());
		assertEquals(gdprInput.getMapRegionCountry(), gdprInputRes.getMapRegionCountry());
	}	
	
	/*
	 * @Test(expected=Exception.class) public void loadGdprFormExceptionTest()
	 * throws GdprException { GdprInput gdprInput = new GdprInput(); List<String>
	 * lstSelectedRegion = new ArrayList<String>(); lstSelectedRegion.add("BEL");
	 * lstSelectedRegion.add("AUT"); List<String> lstRegion = new
	 * ArrayList<String>(); lstRegion.add("EUR-EU"); List<String> lstRegionsCountry
	 * = new ArrayList<String>(); lstRegionsCountry.add("EUR-EU"); Map<String,
	 * List<String>> mapRegionCountry = new HashMap<String, List<String>>();
	 * mapRegionCountry.put("EUR-EU", lstSelectedRegion); List<String> lstCountry =
	 * new ArrayList<String>(); lstCountry.add("AUT"); lstCountry.add("BEL");
	 * //gdprInput.setErrorStatus(" "); gdprInput.setLstRegion(lstRegion);
	 * gdprInput.setLstCountry(lstCountry); //latest //
	 * gdprInput.setLstSelectedRegion(lstSelectedRegion); //
	 * gdprInput.setLstSelectedCountryCode(new ArrayList<String>());
	 * gdprInput.setErrorStatus(GlobalConstants.STATUS_FAILURE);
	 * gdprInput.setRunStatus(GlobalConstants.ERR_LOAD_FORM);
	 * gdprInput.setMapRegionCountry(mapRegionCountry);
	 * Mockito.when(runMgmtProcessor.loadCountryDetail()).thenThrow(Exception.class)
	 * ; initService.loadGdprForm();
	 * 
	 * }
	 */
}
