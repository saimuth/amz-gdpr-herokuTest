package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.input.Country;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.view.GdprInput;

public class RunMgmtProcessorTest {
	@InjectMocks
	RunMgmtProcessor runMgmtProcessor;
	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;

	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;
	@Mock
	GdprInputFetchDaoImpl gdprInputFetchDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void initializeRunPositiveTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleEndDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("SUCCESS");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doNothing().when(runMgmtDaoImpl).initiateNewRun(runName);
		// runId = runMgmtDaoImpl.fetchLastRunDetail().getRunId();
		// Mockito.when(runMgmtDaoImpl.fetchLastRunDetail().getRunId()).thenReturn(runId);
		runMgmtProcessor.initializeRun(runName);
	}

	@Test
	public void initializeRunExceptionTest() throws GdprException {
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("FAILURE");
		String runName = "Test";
		Long runId = 2L;
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).initiateNewRun(runName);

		runMgmtProcessor.initializeRun(runName);
	}
	
	
	/*
	 * @Test public void initializeRunException2Test() throws GdprException { Date
	 * moduleStartDateTime = null; Date moduleEndDateTime = null; Date
	 * runStartDateTime = null; Date runEndDateTime = null; moduleStartDateTime =
	 * new Date(); moduleEndDateTime = new Date(); RunModuleMgmt runModuleMgmt = new
	 * RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
	 * "SUCCESS", moduleStartDateTime, moduleStartDateTime,
	 * "Run Anonymization Initiated Count : 0"); RunErrorMgmt runErrorMgmt = new
	 * RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX"); RunMgmt runMgmt = new
	 * RunMgmt(); runMgmt.setRunId(2L); runMgmt.setRunName("Test");
	 * runMgmt.setRunStatus("FAILURE"); String runName = "Test"; Long runId = 2L;
	 * Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenReturn(runMgmt);
	 * Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt
	 * (runModuleMgmt);
	 * Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
	 * Mockito.doThrow(Exception.class).when(runMgmtDaoImpl).initiateNewRun(runName)
	 * ;
	 * 
	 * runMgmtProcessor.initializeRun(runName); }
	 */

	@Test(expected = Exception.class)
	public void oldRunVerificationTest() throws GdprException {
		RunMgmt runMgmt = new RunMgmt();
		runMgmt.setRunId(2L);
		runMgmt.setRunName("Test");
		runMgmt.setRunStatus("FAILURE"); // RunMgmt
		runMgmt = runMgmtDaoImpl.fetchLastRunDetail();
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.when(runMgmtDaoImpl.fetchLastRunDetail()).thenThrow(Exception.class);
		runMgmtProcessor.oldRunVerification();

	}

	@Test
	public void loadCountryDetailTest() throws GdprException {

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
		Mockito.when(gdprInputFetchDaoImpl.fetchAllCountries()).thenReturn(mockCountryDetails());
		GdprInput gdprInputRes = runMgmtProcessor.loadCountryDetail();
		assertEquals(gdprInput.getLstRegion(), gdprInputRes.getLstRegion());
		assertEquals(gdprInput.getMapRegionCountry(), gdprInputRes.getMapRegionCountry());

	}

	@Test(expected = Exception.class)
	public void loadCountryDetailExceptionTest() throws GdprException {
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		////Update from Git
	    Mockito.when(gdprInputFetchDaoImpl.fetchAllCountries()).thenThrow(Exception.class);
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		runMgmtProcessor.loadCountryDetail();

	}

	private List<Country> mockCountryDetails() {
		List<Country> country = new ArrayList<Country>();

		Country country2 = new Country("EUR-EU", "AUT");
		Country country3 = new Country("EUR-EU", "BEL");
		/*
		 * Country country4 = new Country("EUR-EU","CHE"); Country country5 = new
		 * Country("EUR-EU","CZE"); Country country6 = new Country("EUR-EU","DEU");
		 * Country country7 = new Country("EUR-EU","DNK"); Country country8 = new
		 * Country("EUR-EU","ESP"); Country country9 = new Country("EUR-EU","FIN");
		 * Country country10 = new Country("EUR-EU","FRA");
		 * 
		 * Country country11 = new Country("EUR-EU","GBR"); Country country12 = new
		 * Country("EUR-EU","HRV"); Country country13 = new Country("EUR-EU","ITA");
		 * Country country14 = new Country("EUR-EU","LUX"); Country country15 = new
		 * Country("EUR-EU","NLD"); Country country16 = new Country("EUR-EU","NOR");
		 * Country country17 = new Country("EUR-EU","POL"); Country country18 = new
		 * Country("EUR-EU","PRT"); Country country19 = new Country("EUR-EU","ROI");
		 * Country country20 = new Country("EUR-EU","ROM"); Country country21 = new
		 * Country("EUR-EU","SVK"); Country country22 = new Country("EUR-EU","SWE");
		 * country.add(country22); country.add(country21); country.add(country20);
		 * country.add(country19); country.add(country18); country.add(country17);
		 * country.add(country16); country.add(country15); country.add(country14);
		 * country.add(country13); country.add(country12); country.add(country11);
		 * 
		 * country.add(country10); country.add(country9); country.add(country8);
		 * country.add(country7); country.add(country6); country.add(country5);
		 * country.add(country4);
		 */
		country.add(country3);
		country.add(country2);

		return country;
	}

}
