package com.amazon.gdpr.processor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;

import java.util.ArrayList;
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

import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.gdpr.input.ImpactField;
import com.amazon.gdpr.model.gdpr.output.RunErrorMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.util.SqlQueriesConstant;
import com.amazon.gdpr.view.AnonymizationInputView;

public class GDPRDataProcessorTest {

	@Mock
	GdprInputDaoImpl gdprInputDaoImpl;
	@Mock
	GdprOutputDaoImpl gdprOutputDaoImpl;
	@InjectMocks
	GdprDataProcessor gdprDataProcessor;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loadInputFieldDetailsTest() throws GdprException {
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactField()).thenReturn(lstImpactFieldData());
		int batchSize = 1000;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doNothing().when(gdprInputDaoImpl).batchInsertImpactField(lstImpactFieldData(), batchSize);
		int insertImpactFieldCount = 3;
		int res = gdprDataProcessor.loadInputFieldDetails(lstAnonymizationInputViewData());
		assertEquals(insertImpactFieldCount, res);
	}

	@Test
	public void loadInputFieldDetailsInsertImpactFieldCountTest() throws GdprException {
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactField()).thenReturn(lstImpactFieldData());
		int batchSize = 1000;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doNothing().when(gdprInputDaoImpl).batchInsertImpactField(lstImpactFieldUpdatedData(), batchSize);
		int insertImpactFieldCount = 3;
		int res = gdprDataProcessor.loadInputFieldDetails(lstAnonymizationInputViewData());
		assertEquals(insertImpactFieldCount, res);

	}

	@Test(expected = Exception.class)
	public void loadInputFieldDetailsExceptionTest() throws GdprException {
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactField()).thenThrow(Exception.class);
		int batchSize = 1000;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doThrow(Exception.class).when(gdprInputDaoImpl).batchInsertImpactField(anyList(), anyInt());
		int insertImpactFieldCount = 0;
		gdprDataProcessor.loadInputFieldDetails(lstAnonymizationInputViewData());
	}

	/* Method to check last exception */
	@Test(expected = Exception.class)
	public void loadInputFieldDetailsExceptionTest1() throws GdprException {
		Mockito.when(gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID))
				.thenReturn(mapImpactTableData());
		Mockito.when(gdprInputDaoImpl.fetchImpactField()).thenReturn(lstImpactFieldData());
		int batchSize = 1000;
		RunErrorMgmt runErrorMgmt = new RunErrorMgmt(1L, 2L, "Test", "XX", "Test", "XX");
		Mockito.doNothing().when(gdprOutputDaoImpl).loadErrorDetails(runErrorMgmt);
		Mockito.doThrow(Exception.class).when(gdprInputDaoImpl).batchInsertImpactField(anyList(), anyInt());
		int insertImpactFieldCount = 0;
		gdprDataProcessor.loadInputFieldDetails(lstAnonymizationInputViewData());
	}

	@Test
	public void reOrganizeGdprDataTest() {
		int runId = 7;
		Boolean gdprDataProcessStatus = false;
		boolean res = gdprDataProcessor.reOrganizeGdprData(runId);
		assertEquals(Boolean.FALSE, res);
	}

	private List<ImpactField> lstImpactFieldData() {
		List<ImpactField> lstImpactField = new ArrayList<ImpactField>();
		ImpactField impactField1 = new ImpactField(1, 1, "TALEO HIRING AREA MANAGER",
				"LEGACY_TALEO_HIRING_AREA_MANAGER__C", "TEXT");
		ImpactField impactField2 = new ImpactField(2, 1, "CANDIDATE LAST NAME", "CANDIDATE_LAST_NAME__C", "TEXT");
		ImpactField impactField3 = new ImpactField(3, 1, "NAME", "APP_CANDIDATE_NAME__C", "TEXT");
		ImpactField impactField4 = new ImpactField(4, 1, "TALEO RECRUITER", "LEGACY_TALEO_RECRUITER__C", "TEXT");
		ImpactField impactField5 = new ImpactField(5, 1, "ESIGNATURE", "ESIGNATURE_READ_GENERAL__C", "TEXT");
		lstImpactField.add(impactField1);
		lstImpactField.add(impactField2);
		lstImpactField.add(impactField3);
		lstImpactField.add(impactField4);
		lstImpactField.add(impactField5);
		return lstImpactField;
	}

	private Map<String, String> mapImpactTableData() {

		Map<String, String> mapImpactTable = new HashMap<String, String>();
		mapImpactTable.put("APPLICATION__C", "1");
		mapImpactTable.put("INTERVIEW__C", "2");
		mapImpactTable.put("TASK", "3");
		mapImpactTable.put("EMAILMESSAGE", "4");
		mapImpactTable.put("ASSESSMENT__C", "5");
		mapImpactTable.put("ERROR_LOG__C", "6");
		mapImpactTable.put("ATTACHMENT", "7");
		mapImpactTable.put("RESPONSE__C", "8");
		mapImpactTable.put("RESPONSE_ANSWER__C", "9");
		mapImpactTable.put("INTEGRATION_TRANSACTION__C", "10");
		return mapImpactTable;

	}

	private List<AnonymizationInputView> lstAnonymizationInputViewData() {
		/*
		 * public AnonymizationInputView(String object, String fieldLabel, String
		 * apiName, String type, String categoryName, String recommendedTransformation,
		 * String chosenTransformation, String region, String countryCode) {
		 */

		List<AnonymizationInputView> lstAnonymizationInputView = new ArrayList<AnonymizationInputView>();
		AnonymizationInputView anonymizationInputView1 = new AnonymizationInputView("APPLICATION__C",
				"Email Notification Prompt 1 Text", "Email_Notification_Prompt_1_Text__c", "Formula (Text)",
				"Pre-Hire: Candidate-provided data of unsuccessful candidates", "Null/Empty/Privacy Deleted",
				"Privacy Deleted", "EUR-EU", "AUT");

		AnonymizationInputView anonymizationInputView2 = new AnonymizationInputView("APPLICATION__C",
				"Candidate Last Name", "Candidate_Last_Name__c", "Text(255)",
				"Pre-Hire: Candidate-provided data of unsuccessful candidates", "Null/Empty/Privacy Deleted",
				"Privacy Deleted", "EUR-EU", "AUT");

		AnonymizationInputView anonymizationInputView3 = new AnonymizationInputView("ASSESSMENT__C", "ADPScreeningID",
				"ADPScreeningID__c", "Text(255)", "Pre-Hire: Candidate-provided data of unsuccessful candidates",
				"Null/Empty/Privacy Deleted", "Privacy Deleted", "EUR-EU", "AUT");
		lstAnonymizationInputView.add(anonymizationInputView2);
		lstAnonymizationInputView.add(anonymizationInputView1);
		lstAnonymizationInputView.add(anonymizationInputView3);
		return lstAnonymizationInputView;
	}

	private List<ImpactField> lstImpactFieldUpdatedData() {
		/*
		 * ImpactField impactField = new ImpactField(impactTableId,
		 * anonymizationInputView.fieldLabel, anonymizationInputView.apiName,
		 * anonymizationInputView.type); }
		 */

		List<ImpactField> lstImpactFieldUpdated = new ArrayList<ImpactField>();
		ImpactField impactField1 = new ImpactField(1, "Email Notification Prompt 1 Text",
				"Email_Notification_Prompt_1_Text__c", "Formula (Text)");
		ImpactField impactField2 = new ImpactField(1, "Candidate Last Name", "Candidate_Last_Name__c", "Text(255)");
		ImpactField impactField3 = new ImpactField(5, "ADPScreeningID", "ADPScreeningID__c", "Text(255)");
		lstImpactFieldUpdated.add(impactField3);
		lstImpactFieldUpdated.add(impactField2);
		lstImpactFieldUpdated.add(impactField1);
		return lstImpactFieldUpdated;

	}

}
