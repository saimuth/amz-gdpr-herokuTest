package com.amazon.gdpr.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

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

import com.amazon.gdpr.dao.GdprInputFetchDaoImpl;
import com.amazon.gdpr.model.gdpr.input.ImpactTable;
import com.amazon.gdpr.model.gdpr.output.RunSummaryMgmt;
import com.amazon.gdpr.util.GlobalConstants;

public class TagQueryProcessorTest {

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	GdprInputFetchDaoImpl gdprInputFetchDaoImpl;
	@InjectMocks
	TagQueryProcessor tagQueryProcessor;

	@Test
	public void fetchImpactTableMapTest() { 
		Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenReturn(lstImpactTable());
		Long runId = 7L;
		Map<String, ImpactTable> mapImpactTable = mapImpactTable();
		Map<String, ImpactTable> mapImpactTableRes = tagQueryProcessor.fetchImpactTableMap(runId);
		assertEquals(mapImpactTableRes.size(), mapImpactTable.size());
	}
	
	/*
	 * @Test(expected=Exception.class) public void
	 * fetchImpactTableMapExceptionTest() { Long runId = 7L; Map<String,
	 * ImpactTable> mapImpactTable = mapImpactTable();
	 * Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenThrow(Exception.
	 * class); //Map<String, ImpactTable> mapImpactTableRes =
	 * tagQueryProcessor.fetchImpactTableMap(runId); }
	 */

	@Test
	public void updateSummaryQueryTest() {
		Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenReturn(lstImpactTable());
		Long runId = 7L;
		tagQueryProcessor.updateSummaryQuery(runId, lstRunSummaryMgmt());
	}

	@Test
	public void fetchCompleteTaggedQueryTest() {

		String tableName = "RESPONSE__C";
		long runId = 7L;
		tagQueryProcessor.fetchCompleteTaggedQuery(tableName, mapImpactTable(), mapSummaryInputs(), runId);
	}

	/*
	 * @Test public void updateSummaryQueryTest() {
	 * Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenReturn(
	 * lstImpactTable()); Long runId = 7L; String tableName="RESPONSE__C";
	 * Mockito.when(tagQueryProcessor.fetchCompleteTaggedQuery(tableName,
	 * mapImpactTable(),mapSummaryInputs())).thenReturn(anyString());
	 * tagQueryProcessor.updateSummaryQuery(runId, lstRunSummaryMgmt()); }
	 */

	private List<RunSummaryMgmt> lstRunSummaryMgmt() {
		List<RunSummaryMgmt> lstRunSummaryMgmt = new ArrayList<RunSummaryMgmt>();

		/*
		 * RunSummaryMgmt runSummaryMgmt1 = new RunSummaryMgmt(1L, 1, "EUR-EU", "AUT",
		 * 2, "APPLICATION__C", "select * from XX", " Alter Table XX");
		 */

		RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL", 2, "RESPONSE__C",
				"select * from XX", " Alter Table XX");
		String tableName = "";
		/*
		 * Map<String, ImpactTable> mapImpactTable = mapImpactTable(); Map<String,
		 * String> mapSummaryInputs = mapSummaryInputs();
		 * runSummaryMgmt1.setTaggedQueryLoad(fetchCompleteTaggedQuery(tableName,
		 * mapImpactTable, mapSummaryInputs));
		 */
		/*
		 * RunSummaryMgmt runSummaryMgmt2 = new RunSummaryMgmt(7L, 2, "EUR-EU", "BEL",
		 * 3, "INTERVIEW__C", "select * from XX", " Alter Table XX");
		 * 
		 * 
		 */
		lstRunSummaryMgmt.add(runSummaryMgmt2);
		// lstRunSummaryMgmt.add(runSummaryMgmt1);
		return lstRunSummaryMgmt;
	}

	// Need to check Exception
	/*
	 * @Test(expected=Exception.class) public void
	 * fetchImpactTableMapExceptionTest() { //List<ImpactTable> lstImpactTable =
	 * gdprInputFetchDaoImpl.fetchImpactTable();
	 * Mockito.when(gdprInputFetchDaoImpl.fetchImpactTable()).thenThrow(Exception.
	 * class); Long runId=3L; Map<String, ImpactTable>
	 * mapImpactTable=mapImpactTable();
	 * tagQueryProcessor.fetchImpactTableMap(runId);
	 * 
	 * }
	 */

	private List<ImpactTable> lstImpactTable() {
		List<ImpactTable> lstImpactTable = new ArrayList<ImpactTable>();

		ImpactTable impactTable1 = new ImpactTable(2, "SF_ARCHIVE", "APPLICATION__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable2 = new ImpactTable(3, "SF_ARCHIVE", "INTERVIEW__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable3 = new ImpactTable(9, "SF_ARCHIVE", "RESPONSE__C", "ID", "CHILD", "SF_ARCHIVE",
				"APPLICATION__C", "APPLICATION__C", "SFID");
		ImpactTable impactTable4 = new ImpactTable(1, "GDPR", "GDPR_DEPERSONALIZATION", "CATEGORY_ID,COUNTRY_CODE",
				"PARENT", "NA", "NA", "NA", "NA");
		lstImpactTable.add(impactTable1);

		lstImpactTable.add(impactTable2);
		lstImpactTable.add(impactTable3);
		lstImpactTable.add(impactTable4);
		return lstImpactTable;
	}

	private Map<String, ImpactTable> mapImpactTable() {
		Map<String, ImpactTable> mapImpactTable = new HashMap<String, ImpactTable>();

		ImpactTable impactTable1 = new ImpactTable(2, "SF_ARCHIVE", "APPLICATION__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable2 = new ImpactTable(3, "SF_ARCHIVE", "INTERVIEW__C", "ID", "CHILD", "GDPR",
				"GDPR_DEPERSONALIZATION", "CANDIDATE__C", "CANDIDATE__C");
		ImpactTable impactTable3 = new ImpactTable(9, "SF_ARCHIVE", "RESPONSE__C", "ID", "CHILD", "SF_ARCHIVE",
				"APPLICATION__C", "APPLICATION__C", "SFID");
		ImpactTable impactTable4 = new ImpactTable(1, "GDPR", "GDPR_DEPERSONALIZATION", "CATEGORY_ID,COUNTRY_CODE",
				"PARENT", "NA", "NA", "NA", "NA");

		/*
		 * ImpactTable(int impactTableId, String impactSchema, String impactTableName,
		 * String impactColumns, String impactTableType, String parentSchema, String
		 * parentTable, String impactTableColumn, String parentTableColumn)
		 */

		mapImpactTable.put("APPLICATION__C", impactTable1);
		mapImpactTable.put("INTERVIEW__C", impactTable2);
		mapImpactTable.put("GDPR_DEPERSONALIZATION", impactTable4);
		mapImpactTable.put("RESPONSE__C", impactTable3);
		return mapImpactTable;

	}

	private Map<String, String> mapSummaryInputs() {
		Map<String, String> mapSummaryInputs = new HashMap<String, String>();
		/*
		 * mapSummaryInputs.put("GlobalConstants.KEY_CATEGORY_ID", "1");
		 * mapSummaryInputs.put("GlobalConstants.KEY_COUNTRY_CODE", "AUT");
		 */
		mapSummaryInputs.put("CATEGORY_ID", "2");
		mapSummaryInputs.put("COUNTRY_CODE", "BEL");
		return mapSummaryInputs;

	}

}
