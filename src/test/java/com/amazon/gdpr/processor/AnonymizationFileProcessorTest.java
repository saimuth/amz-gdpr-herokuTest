package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.input.AnonymizationDetail;
import com.amazon.gdpr.model.gdpr.input.Country;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.view.AnonymizationInputView;

public class AnonymizationFileProcessorTest {

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@InjectMocks
	AnonymizationFileProcessor anonymizationProcessor;
	@Mock
	ModuleMgmtProcessor moduleMgmtProcessor;

	@Mock
	GdprInputFetchDaoImpl gdprInputFetchDaoImpl;

	@Mock
	RunMgmtDaoImpl runMgmtDaoImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loadRunAnonymizationPositiveTest() throws GdprException {
		int insertRunAnonymizationCount = 1;
		long runId = 2L;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		List<Country> lstCountry = mockCountryDetails();
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		String countryCode = "AUT";
		String countryRegion = "EUR-EU";
		Mockito.when(gdprInputFetchDaoImpl.fetchCountry(selectedCountries)).thenReturn(lstCountry);
		Mockito.doNothing().when(moduleMgmtProcessor).initiateModuleMgmt(runModuleMgmt);
		int batchInsertRunAnonymizeRes = 1;
		Mockito.when(gdprOutputDaoImpl.batchInsertRunAnonymizeMapping(runId, countryCode, countryRegion))
				.thenReturn(batchInsertRunAnonymizeRes);
		int insertRunAnonymizationCountRes = anonymizationProcessor.loadRunAnonymization(runId, selectedCountries);
		assertEquals(insertRunAnonymizationCount, insertRunAnonymizationCountRes);
	}

	@Test(expected = Exception.class)
	public void loadRunAnonymizationExceptionTest() throws GdprException {
		long runId = 2L;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		List<Country> lstCountry = mockCountryDetails();
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		String countryCode = "AUT";
		String countryRegion = "EUR-EU";
		Mockito.when(gdprInputFetchDaoImpl.fetchCountry(selectedCountries)).thenThrow(Exception.class);
		int batchInsertRunAnonymizeRes = 1;
		Mockito.when(gdprOutputDaoImpl.batchInsertRunAnonymizeMapping(runId, countryCode, countryRegion))
				.thenReturn(batchInsertRunAnonymizeRes);
		anonymizationProcessor.loadRunAnonymization(runId, selectedCountries);

	}

	@Test(expected = Exception.class)
	public void loadRunAnonymizationException2Test() throws GdprException {
		int insertRunAnonymizationCount = 1;
		long runId = 2L;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		Mockito.doThrow(Exception.class).when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		List<Country> lstCountry = mockCountryDetails();
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		String countryCode = "AUT";
		String countryRegion = "EUR-EU";
		Mockito.when(gdprInputFetchDaoImpl.fetchCountry(selectedCountries)).thenReturn(lstCountry);
		int batchInsertRunAnonymizeRes = 1;
		Mockito.when(gdprOutputDaoImpl.batchInsertRunAnonymizeMapping(runId, countryCode, countryRegion))
				.thenThrow(Exception.class);
		anonymizationProcessor.loadRunAnonymization(runId, selectedCountries);

	}

	@Test(expected = Exception.class)
	public void loadRunAnonymizationException3Test() throws GdprException {
		int insertRunAnonymizationCount = 1;
		long runId = 2L;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"SUCCESS", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");

		List<Country> lstCountry = mockCountryDetails();
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		String countryCode = "AUT";
		String countryRegion = "EUR-EU";
		Mockito.when(gdprInputFetchDaoImpl.fetchCountry(selectedCountries)).thenThrow(Exception.class);
		int batchInsertRunAnonymizeRes = 1;
		Mockito.when(gdprOutputDaoImpl.batchInsertRunAnonymizeMapping(runId, countryCode, countryRegion))
				.thenThrow(Exception.class);
		Mockito.doThrow(Exception.class).when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		anonymizationProcessor.loadRunAnonymization(runId, selectedCountries);

	}

	@Test(expected=Exception.class)
	public void loadRunAnonymizationExceptionTest4() throws GdprException {
		int insertRunAnonymizationCount = 1;
		long runId = 2L;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Date moduleStartDateTime = null;
		Date moduleEndDateTime = null;
		Date runStartDateTime = null;
		Date runEndDateTime = null;
		moduleStartDateTime = new Date();
		moduleEndDateTime = new Date();
		RunModuleMgmt runModuleMgmt = new RunModuleMgmt(2L, "Initialization Module", "Anonymize Initialize Sub Module",
				"FAILURE", moduleStartDateTime, moduleStartDateTime, "Run Anonymization Initiated Count : 0");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		List<Country> lstCountry = mockCountryDetails();
		List<String> selectedCountries = new ArrayList<String>();
		selectedCountries.add("AUT");
		selectedCountries.add("BEL");
		String countryCode = "AUT";
		String countryRegion = "EUR-EU";
		Mockito.when(gdprInputFetchDaoImpl.fetchCountry(selectedCountries)).thenReturn(lstCountry);
		int batchInsertRunAnonymizeRes = 1;
		Mockito.when(gdprOutputDaoImpl.batchInsertRunAnonymizeMapping(runId, countryCode, countryRegion))
				.thenReturn(batchInsertRunAnonymizeRes);
		Mockito.doThrow(Exception.class).when(moduleMgmtProcessor).initiateModuleMgmt(anyObject());
		int insertRunAnonymizationCountRes = anonymizationProcessor.loadRunAnonymization(runId, selectedCountries);
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

	@Test
	public void loadAnonymizationDetailsTest() throws GdprException {
		int rowCount = 10;
		int insertCount = 0;
		List<AnonymizationDetail> lstAnonymizationDetail = anonymizationDetailData();
		Mockito.when(gdprInputDaoImpl.fetchAnonymizationDetails()).thenReturn(lstAnonymizationDetail);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		// git
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactFieldMap()).thenReturn(mapImpactFieldData());
		Mockito.doNothing().when(gdprInputDaoImpl).batchInsertAnonymizationDetail(lstAnonymizationDetail, rowCount);
		int response = anonymizationProcessor.loadAnonymizationDetails(anonymizationProcessorData());
		assertEquals(insertCount, response);

	}

	@Test
	public void loadAnonymizationDetailsInsertRowTest() throws GdprException {
		int rowCount = 10;
		int insertCount = 2;
		List<AnonymizationDetail> lstAnonymizationDetail = anonymizationDetailData();
		Mockito.when(gdprInputDaoImpl.fetchAnonymizationDetails()).thenReturn(lstAnonymizationDetail);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactFieldMap()).thenReturn(mapImpactFieldData());
		Mockito.doNothing().when(gdprInputDaoImpl).batchInsertAnonymizationDetail(lstAnonymizationDetail, rowCount);
		int response = anonymizationProcessor.loadAnonymizationDetails(anonymizationProcessorInsertData());
		assertEquals(insertCount, response);
	}

	@Test(expected = Exception.class)
	public void loadAnonymizationDetailsInsertRowExceptionTest() throws GdprException {
		int rowCount = 10;
		int insertCount = 2;
		List<AnonymizationDetail> lstAnonymizationDetail = anonymizationDetailData();
		Mockito.when(gdprInputDaoImpl.fetchAnonymizationDetails()).thenReturn(lstAnonymizationDetail);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactFieldMap()).thenReturn(mapImpactFieldData());
		Mockito.doThrow(Exception.class).when(gdprInputDaoImpl).batchInsertAnonymizationDetail(anyList(), anyInt());
		int response = anonymizationProcessor.loadAnonymizationDetails(anonymizationProcessorInsertData());
		assertEquals(insertCount, response);
	}

	@Test(expected = Exception.class)
	public void loadAnonymizationDetailsExceptionTest() throws GdprException {
		int rowCount = 10;
		int insertCount = 0;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		List<AnonymizationDetail> lstAnonymizationDetail = anonymizationDetailData();
		Mockito.when(gdprInputDaoImpl.fetchAnonymizationDetails()).thenThrow(Exception.class);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactFieldMap()).thenReturn(mapImpactFieldData());
		Mockito.doNothing().when(gdprInputDaoImpl).batchInsertAnonymizationDetail(lstAnonymizationDetail, rowCount);
		anonymizationProcessor.loadAnonymizationDetails(anonymizationProcessorData());

	}

	// need check rows count > size
	@Test
	public void loadAnonymizationDetailsrowsTest() throws GdprException {
		int rowCount = 10;
		int insertCount = 0;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		List<AnonymizationDetail> lstAnonymizationDetail = anonymizationDetailData();
		Mockito.when(gdprInputDaoImpl.fetchAnonymizationDetails()).thenReturn(lstAnonymizationDetail);
		Mockito.when(gdprInputDaoImpl.fetchCategoryDetails()).thenReturn(mapCategoryData());
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactFieldMap()).thenReturn(mapImpactFieldData());
		Mockito.doNothing().when(gdprInputDaoImpl).batchInsertAnonymizationDetail(lstAnonymizationUpdatedDetail(),
				rowCount);
		int res = anonymizationProcessor.loadAnonymizationDetails(anonymizationProcessorData());

	}

	/*
	 * @Test public void parseAnonymizationFileTest() throws IOException { Date date
	 * = new Date(); boolean success = false; FileInputStream fileInputStream = new
	 * FileInputStream("File Location.xls"); HSSFWorkbook workbook = new
	 * HSSFWorkbook(fileInputStream); Sheet sheet =
	 * workbook.getSheet(GlobalConstants.FILE_SHEET_NAME); }
	 */
	private List<AnonymizationDetail> lstAnonymizationUpdatedDetail() {
		List<AnonymizationDetail> lstAnonymizationUpdatedDetail = new ArrayList<AnonymizationDetail>();
		AnonymizationDetail anonymizationDetail1 = new AnonymizationDetail(32, 3, "EUR-EU", "AUT", "PRIVACY DELETED",
				GlobalConstants.STATUS_ACTIVE);
		AnonymizationDetail anonymizationDetail2 = new AnonymizationDetail(30, 5, "EUR-EU", "AUT", "PRIVACY DELETED",
				GlobalConstants.STATUS_ACTIVE);
		lstAnonymizationUpdatedDetail.add(anonymizationDetail2);
		lstAnonymizationUpdatedDetail.add(anonymizationDetail1);
		return lstAnonymizationUpdatedDetail;
	}

	private Map<String, String> mapImpactFieldData() {
		Map<String, String> mapImpactField = new HashMap<String, String>();
		mapImpactField.put("2LEGACY_TALEO_HIRING_AREA_MANAGER__C", "1");
		mapImpactField.put("2CANDIDATE_LAST_NAME__C", "2");
		mapImpactField.put("2APP_CANDIDATE_NAME__C", "3");
		mapImpactField.put("2LEGACY_TALEO_RECRUITER__C", "4");
		mapImpactField.put("2ESIGNATURE_READ_GENERAL__C", "5");
		mapImpactField.put("2BGC_AUTHORIZATION2__C", "6");
		mapImpactField.put("2EMAIL_NOTIFICATION_RICH_TEXT_2__C", "7");
		mapImpactField.put("2LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C", "8");
		mapImpactField.put("2LEGACY_TALEO_SCHEDULING_INFORMATION__C", "9");
		mapImpactField.put("2EMAIL_NOTIFICATION_PROMPT_3_TEXT__C", "10");
		mapImpactField.put("3CANDIDATE_EMAIL__C", "31");
		mapImpactField.put("2EMAIL_NOTIFICATION_BODY__C", "21");

		return mapImpactField;
	}

	private Map<String, String> mapImpactTableData() {
		Map<String, String> mapImpactTable = new HashMap<String, String>();
		mapImpactTable.put("GDPR_DEPERSONALIZATION", "1");
		mapImpactTable.put("APPLICATION__C", "2");
		mapImpactTable.put("INTERVIEW__C", "3");
		mapImpactTable.put("TASK", "4");
		mapImpactTable.put("EMAILMESSAGE", "5");
		mapImpactTable.put("ASSESSMENT__C", "6");
		mapImpactTable.put("ERROR_LOG__C", "7");
		mapImpactTable.put("ATTACHMENT", "8");
		mapImpactTable.put("RESPONSE__C", "9");
		mapImpactTable.put("RESPONSE_ANSWER__C", "10");
		mapImpactTable.put("INTEGRATION_TRANSACTION__C", "11");
		return mapImpactTable;
	}

	private Map<String, String> mapCategoryData() {
		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("ACCESS CONTROL", "1");
		mapCategory.put("BACKGROUND CHECK", "1");
		mapCategory.put("PRE-HIRE: AMAZON ASSESSMENT OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)", "3");
		mapCategory.put("PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)", "4");
		mapCategory.put("PRE-HIRE: MASTER DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)", "5");
		return mapCategory;
	}

	private List<AnonymizationInputView> anonymizationProcessorInsertData() {

		List<AnonymizationInputView> lstAnonymizationInputView = new ArrayList<AnonymizationInputView>();
		AnonymizationInputView anonymizationInputView1 = new AnonymizationInputView("APPLICATION__C",
				"Email Notification Body", "EMAIL_NOTIFICATION_BODY__C", "Textarea (32768)",
				"PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");

		AnonymizationInputView anonymizationInputView2 = new AnonymizationInputView("INTERVIEW__C", "Candidate Email",
				"CANDIDATE_EMAIL__C", "Formula (Text)",
				"PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");
		lstAnonymizationInputView.add(anonymizationInputView2);
		lstAnonymizationInputView.add(anonymizationInputView1);
		return lstAnonymizationInputView;
	}

	private List<AnonymizationInputView> anonymizationProcessorData() {

		List<AnonymizationInputView> lstAnonymizationInputView = new ArrayList<AnonymizationInputView>();
		AnonymizationInputView anonymizationInputView1 = new AnonymizationInputView("Application_c",
				"Email Notification Body", "EMAIL_NOTIFICATION_BODY__C", "Textarea (32768)",
				"Pre-Hire: Candidate-provided data of unsuccessful candidates", "Null/Empty/Privacy Deleted",
				"Privacy Deleted", "EUR-EU", "AUT");

		AnonymizationInputView anonymizationInputView2 = new AnonymizationInputView("Interview_c", "Candidate Email",
				"CANDIDATE_EMAIL__C", "Formula (Text)", "Pre-Hire: Candidate-provided data of unsuccessful candidates",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");
		lstAnonymizationInputView.add(anonymizationInputView2);
		lstAnonymizationInputView.add(anonymizationInputView1);
		return lstAnonymizationInputView;
	}

	private List<AnonymizationDetail> anonymizationDetailData() {

		List<AnonymizationDetail> lstAnonymizationDetailUpdated = new ArrayList<AnonymizationDetail>();
		AnonymizationDetail anonymizationDetail1 = new AnonymizationDetail(35, 3, 0, "EUR-EU", "AUT", "PRIVACY DELETED",
				"ACTIVE");
		AnonymizationDetail anonymizationDetail2 = new AnonymizationDetail(34, 3, 0, "EUR-EU", "BEL", "PRIVACY DELETED",
				"ACTIVE");
		lstAnonymizationDetailUpdated.add(anonymizationDetail2);
		lstAnonymizationDetailUpdated.add(anonymizationDetail1);
		return lstAnonymizationDetailUpdated;

	}
}
