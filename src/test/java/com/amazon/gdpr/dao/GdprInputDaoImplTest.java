package com.amazon.gdpr.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.amazon.gdpr.dao.GdprInputDaoImpl.AnonymizationDetailRowMapper;
import com.amazon.gdpr.dao.GdprInputDaoImpl.CategoryRowMapper;
import com.amazon.gdpr.dao.GdprInputDaoImpl.ImpactFieldRowMapper;
import com.amazon.gdpr.dao.GdprInputDaoImpl.ImpactTableDetailsRowMapper;
import com.amazon.gdpr.dao.GdprInputDaoImpl.ImpactTableRowMapper;
import com.amazon.gdpr.dao.RunMgmtDaoImpl.RunMgmtRowMapper;
import com.amazon.gdpr.model.gdpr.input.AnonymizationDetail;
import com.amazon.gdpr.model.gdpr.input.Category;
import com.amazon.gdpr.model.gdpr.input.ImpactField;
import com.amazon.gdpr.model.gdpr.input.ImpactTable;
import com.amazon.gdpr.model.gdpr.input.ImpactTableDetails;
import com.amazon.gdpr.model.gdpr.output.RunMgmt;
import com.amazon.gdpr.util.SqlQueriesConstant;
@RunWith(MockitoJUnitRunner.class)
public class GdprInputDaoImplTest {
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	GdprInputDaoImpl gdprInputDaoImpl;

	
	@Before
	public void setUp() throws Exception {
	    MockitoAnnotations.initMocks(this);
	    ReflectionTestUtils.setField(gdprInputDaoImpl ,"jdbcTemplate" , 
	    		jdbcTemplate);

	}
	@Test
	public void fetchImpactTableTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(ImpactTableRowMapper.class))).thenReturn(lstImpactFieldData());
		List<ImpactTable> res=gdprInputDaoImpl.fetchImpactTable();
		assertEquals(lstImpactFieldData().size(), res.size());
	}
	@Test
	public void fetchImpactTableDetailsMapTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(ImpactTableDetailsRowMapper.class))).thenReturn(lstImpactTableDetails());
		List<ImpactTableDetails> res=gdprInputDaoImpl.fetchImpactTableDetailsMap();
		assertEquals(lstImpactTableDetails().size(), res.size());
	}
	@Test
	public void fetchAnonymizationDetailsTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(AnonymizationDetailRowMapper.class))).thenReturn(lstAnonymizationDetailData());
		List<AnonymizationDetail> res=gdprInputDaoImpl.fetchAnonymizationDetails();
		assertEquals(lstAnonymizationDetailData().size(), res.size());
	}
	@Test
	public void fetchCategoryDetailsTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(CategoryRowMapper.class))).thenReturn(lastCategoryData());
		gdprInputDaoImpl.fetchCategoryDetails();
		//map to return
	}
	@Test
	public void fetchImpactTableMapTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(ImpactTableRowMapper.class))).thenReturn(lastCategoryData());
				String mapType="Test";
		gdprInputDaoImpl.fetchImpactTableMap(mapType);
	}
	@Test
	public void batchInsertAnonymizationDetailTest() {
		int batchSize=100;
		gdprInputDaoImpl.batchInsertAnonymizationDetail(anonymizationDetailData(), batchSize);
	}
	@Test
	public void batchInsertImpactFieldTest() {
		int batchSize=100;
		gdprInputDaoImpl.batchInsertImpactField(lstImpactFieldData(),batchSize);
	}
	@Test
	public void fetchImpactFieldTest() {
		//List<ImpactField> lstImpactField = jdbcTemplate.query(SqlQueriesConstant.IMPACT_FIELD_FETCH, new ImpactFieldRowMapper());		
		Mockito.when(jdbcTemplate.query(any(String.class), any(ImpactFieldRowMapper.class))).thenReturn(lstImpactFieldData());
		List<ImpactField> lstImpactFieldRes =gdprInputDaoImpl.fetchImpactField();
		assertEquals(lstImpactFieldData().size(), lstImpactFieldRes.size());
	}
	@Test	
	public void fetchImpactFieldMapTest() {
		Mockito.when(jdbcTemplate.query(any(String.class), any(ImpactFieldRowMapper.class))).thenReturn(lstImpactFieldData());
		Map<String, String> mapImpactFieldRes=gdprInputDaoImpl.fetchImpactFieldMap();
		System.out.println("Res:: "+mapImpactFieldRes);
		//assertEquals(mapImpactFieldData().size(), mapImpactFieldRes.size());
	}
	@Test
	public void  getMapFieldCategoryTest() {
		Map<String, String> res=gdprInputDaoImpl.getMapFieldCategory();
	}

	@Test
	public void setMapFieldCategoryTest() {
		gdprInputDaoImpl.setMapFieldCategory(mapImpactFieldData());
	}	
	@Test
	public void batchUpdateAnonymizationDetailTest() {
		int batchSize=100;
		gdprInputDaoImpl.batchUpdateAnonymizationDetail(anonymizationDetailData(), batchSize);
	}
	
	
	private Map<String, String> mapImpactFieldData() {
		Map<String, String> mapImpactField = new HashMap<String, String>();
		mapImpactField.put("1", "LEGACY_TALEO_HIRING_AREA_MANAGER__C");
		mapImpactField.put("2", "LAST NAME	CANDIDATE_LAST_NAME__C");
		mapImpactField.put("3", "PP_CANDIDATE_NAME__C");
		mapImpactField.put("4", "RECRUITER	LEGACY_TALEO_RECRUITER__C");
		mapImpactField.put("5", "ESIGNATURE_READ_GENERAL__C");
		mapImpactField.put("6", "BGC_AUTHORIZATION2__C");
		mapImpactField.put("7", "EMAIL_NOTIFICATION_RICH_TEXT_2__C");
		mapImpactField.put("8", "EMAIL_NOTIFICATION_PROMPT_3_TEXT__C");
		mapImpactField.put("9", "LEGACY_TALEO_APP_INTERVIEW_INFO_DE__C");
		mapImpactField.put("10", "LEGACY_TALEO_SCHEDULING_INFORMATION__C");

		return mapImpactField;
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
	private Map<String, String> mapImpactTableData() {
		Map<String, String> mapImpactTable = new HashMap<String, String>();
		mapImpactTable.put("1", "APPLICATION__C");
		mapImpactTable.put("2", "INTERVIEW__C");
		mapImpactTable.put("3", "TASK");
		mapImpactTable.put("4", "EMAILMESSAGE");
		mapImpactTable.put("5", "ASSESSMENT__C");
		mapImpactTable.put("6", "ERROR_LOG__C");
		mapImpactTable.put("7", "ATTACHMENT");
		mapImpactTable.put("8", "RESPONSE__C");
		mapImpactTable.put("9", "RESPONSE_ANSWER__C");
		mapImpactTable.put("10", "INTEGRATION_TRANSACTION__C");
		return mapImpactTable;
	}
	private List<Category> lastCategoryData(){
		List<Category> lstAnonymizationDetailUpdated = new ArrayList<Category>();
		Category category1= new Category(1, "ACCESS CONTROL","  ","ACTIVE");
		Category category2= new Category(2, "BACKGROUND CHECK","  ","ACTIVE");
		Category category3= new Category(3, "PRE-HIRE: AMAZON ASSESSMENT OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)","  ","ACTIVE");
		Category category4= new Category(4, "PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)","  ","ACTIVE");
		Category category5= new Category(5, "PRE-HIRE: MASTER DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)","  ","ACTIVE");
		return lstAnonymizationDetailUpdated;
		
	}
	
	private Map<String, String> mapCategoryData() {
		Map<String, String> mapCategory = new HashMap<String, String>();
		mapCategory.put("1", "ACCESS CONTROL");
		mapCategory.put("2", "BACKGROUND CHECK");
		mapCategory.put("3", "PRE-HIRE: AMAZON ASSESSMENT OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)");
		mapCategory.put("4", "PRE-HIRE: CANDIDATE-PROVIDED DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)");
		mapCategory.put("5", "PRE-HIRE: MASTER DATA OF UNSUCCESSFUL CANDIDATES (WITHOUT CONSENT)");
		return mapCategory;
	}
	private List<AnonymizationDetail> lstAnonymizationDetailData() {

		List<AnonymizationDetail> lstAnonymizationDetailUpdated = new ArrayList<AnonymizationDetail>();
		AnonymizationDetail anonymizationDetail1 = new AnonymizationDetail(35, 3, 0, "EUR-EU", "AUT", "PRIVACY DELETED",
				"ACTIVE");
		AnonymizationDetail anonymizationDetail2 = new AnonymizationDetail(34, 3, 0, "EUR-EU", "BEL", "PRIVACY DELETED",
				"ACTIVE");
		lstAnonymizationDetailUpdated.add(anonymizationDetail2);
		lstAnonymizationDetailUpdated.add(anonymizationDetail1);
		return lstAnonymizationDetailUpdated;

	}
	
	private List<ImpactTableDetails> lstImpactTableDetails(){
		List<ImpactTableDetails> lstImpactTableDetails = new ArrayList<ImpactTableDetails>();
		ImpactTableDetails lstImpactTableDetails1 = new ImpactTableDetails("APPLICATION__C",
				"LEGACY_TALEO_HIRING_AREA_MANAGER__C", "TEXT");
		ImpactTableDetails lstImpactTableDetails2 = new ImpactTableDetails("APPLICATION__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails3 = new ImpactTableDetails("APPLICATION__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails4 = new ImpactTableDetails("INTERVIEW__C", "CANDIDATE_LAST_NAME__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails5 = new ImpactTableDetails("TASK", "DESCRIPTION", "TEXT");
		ImpactTableDetails lstImpactTableDetails6 = new ImpactTableDetails("ASSESSMENT__C", "ADPSCREENINGID__C",
				"TEXT");
		ImpactTableDetails lstImpactTableDetails7 = new ImpactTableDetails("RESPONSE__C", "INTERVIEWER_COMMENT__C",
				"TEXT");
		lstImpactTableDetails.add(lstImpactTableDetails1);
		lstImpactTableDetails.add(lstImpactTableDetails2);
		lstImpactTableDetails.add(lstImpactTableDetails3);
		lstImpactTableDetails.add(lstImpactTableDetails4);
		lstImpactTableDetails.add(lstImpactTableDetails5);
		lstImpactTableDetails.add(lstImpactTableDetails6);
		lstImpactTableDetails.add(lstImpactTableDetails7);
		return lstImpactTableDetails;
	}
	 private List<ImpactField> lstImpactFieldData(){
		 List<ImpactField> lstImpactField= new ArrayList<ImpactField>(); 
			ImpactField impactField1= new ImpactField(1,1,"TALEO HIRING AREA MANAGER","LEGACY_TALEO_HIRING_AREA_MANAGER__C","TEXT");
			ImpactField impactField2= new ImpactField(2,1,"CANDIDATE LAST NAME","CANDIDATE_LAST_NAME__C","TEXT");
			ImpactField impactField3= new ImpactField(3,1,"NAME","APP_CANDIDATE_NAME__C","TEXT");
			ImpactField impactField4= new ImpactField(4,1,"TALEO RECRUITER","LEGACY_TALEO_RECRUITER__C","TEXT");
			ImpactField impactField5= new ImpactField(5,1,"ESIGNATURE","ESIGNATURE_READ_GENERAL__C","TEXT");
			lstImpactField.add(impactField1);
			lstImpactField.add(impactField2);
			lstImpactField.add(impactField3);
			lstImpactField.add(impactField4);
			lstImpactField.add(impactField5);
		 return lstImpactField ;
	 }
}
