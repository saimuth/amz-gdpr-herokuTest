package com.amazon.gdpr.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.gdpr.dao.GdprInputDaoImpl;
import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.model.gdpr.input.ImpactField;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;
import com.amazon.gdpr.util.SqlQueriesConstant;
import com.amazon.gdpr.view.AnonymizationInputView;

/****************************************************************************************
 * This Processor manages the data load and fetches of the GDPR related tables
 ****************************************************************************************/
@Component
public class GdprDataProcessor {

	public static String CURRENT_CLASS = GlobalConstants.CLS_GDPRDATAPROCESSOR;
	
	@Autowired
	GdprInputDaoImpl gdprInputDaoImpl;

	@Autowired
	GdprOutputDaoImpl gdprOutputDaoImpl;

	public Boolean reOrganizeGdprData(int runId) {
		Boolean gdprDataProcessStatus = false;

		if (!gdprDataProcessStatus) {
			// load ModuleMgmt
			// load ErrorMgmt
		}
		return gdprDataProcessStatus;
	}

	/**
	 * This method navigates the list of AnonymizationInputView to identify new
	 * InputField added and inserts it into table
	 * 
	 * @param lstAnonymizationInputView
	 */
	public int loadInputFieldDetails(List<AnonymizationInputView> lstAnonymizationInputView) throws GdprException {
		String CURRENT_METHOD = "loadInputFieldDetails";
		System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + ":: Inside method");

		Map<String, String> mapImpactTable = new HashMap<String,String>();
		List<ImpactField> lstImpactField = new ArrayList<ImpactField>();
		int insertImpactFieldCount = 0;
		Set<ImpactField> setImpactField = new HashSet<ImpactField>();
		List<ImpactField> lstImpactFieldUpdated = new ArrayList<ImpactField>();
		String errorDetails = "";
		
		try {
			mapImpactTable = gdprInputDaoImpl.fetchImpactTableMap(GlobalConstants.IMPACTTABLE_MAP_NAMETOID);
			lstImpactField = gdprInputDaoImpl.fetchImpactField();
		} catch (Exception exception) {
			System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + " :: " + GlobalConstants.ERR_GDPR_INPUT_FETCH);
			exception.printStackTrace();
			errorDetails = exception.getMessage();
			throw new GdprException(GlobalConstants.ERR_GDPR_INPUT_FETCH, errorDetails);
		}
		try {
			//System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + " ::  MapImpactTable fetched");
			for (AnonymizationInputView anonymizationInputView : lstAnonymizationInputView) {
				String strImpactTableId = mapImpactTable.get(anonymizationInputView.object);
				//System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + " :: Table Name : "+anonymizationInputView.object+"strImpactTableId : " + strImpactTableId);

				if (strImpactTableId == null || GlobalConstants.EMPTY_STRING.compareTo(strImpactTableId) == 0) {
					continue;
				}
				int impactTableId = Integer.parseInt(strImpactTableId);
				ImpactField impactField = new ImpactField(impactTableId, anonymizationInputView.fieldLabel,
						anonymizationInputView.apiName, anonymizationInputView.type);
				setImpactField.add(impactField);
			}
			//System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + " ::  setImpactField loaded. ");
			if (setImpactField != null && setImpactField.size() > 0) {				
				lstImpactFieldUpdated.addAll(setImpactField);				
				if (lstImpactField != null){					
					lstImpactFieldUpdated.removeAll(lstImpactField);
				}
				Collections.sort(lstImpactFieldUpdated, new SortByTableId());				
				gdprInputDaoImpl.batchInsertImpactField(lstImpactFieldUpdated, SqlQueriesConstant.BATCH_ROW_COUNT);				
				insertImpactFieldCount = lstImpactFieldUpdated.size();
				System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + " :: insertCount : " + insertImpactFieldCount);
			}
		} catch (Exception exception) {
			System.out.println(CURRENT_CLASS + " ::: " + CURRENT_METHOD + " :: " + GlobalConstants.ERR_IMPACT_FIELD_INSERT);
			exception.printStackTrace();
			errorDetails = exception.getMessage();
			throw new GdprException(GlobalConstants.ERR_IMPACT_FIELD_INSERT, errorDetails);
		}		
		return insertImpactFieldCount;
	}
	
	
	class SortByTableId implements Comparator<ImpactField> { 
	    public int compare(ImpactField a, ImpactField b)  { 
	        return a.getImpactTableId() - b.getImpactTableId(); 
	    } 
	} 
}