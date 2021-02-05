package com.amazon.gdpr.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.gdpr.dao.GdprOutputDaoImpl;
import com.amazon.gdpr.dao.RunMgmtDaoImpl;
import com.amazon.gdpr.model.gdpr.output.RunModuleMgmt;
import com.amazon.gdpr.util.GdprException;
import com.amazon.gdpr.util.GlobalConstants;

/****************************************************************************************
 * This processor processes the Module details
 ****************************************************************************************/
@Component
public class ModuleMgmtProcessor {
	private static String CURRENT_CLASS		 		= GlobalConstants.CLS_MODULEMGMT_PROCESSOR;
		
	@Autowired
	RunMgmtDaoImpl runMgmtDaoImpl;
	
	@Autowired
	GdprOutputDaoImpl gdprOutputDaoImpl;
	
	/**
	 * After the completion of each module/ submodule the status of the module will be updated
	 * @param runModuleMgmt The details of the Module are passed on as input
	 * @return Boolean The status of the RunModuleMgmt table update
	 */
	public void initiateModuleMgmt(RunModuleMgmt runModuleMgmt) throws GdprException {
		String CURRENT_METHOD = "initiateModuleMgmt";		
		System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: Module updates in progress.");
		String moduleMgmtStatus = "";
		
		try {
			runMgmtDaoImpl.insertModuleUpdates(runModuleMgmt);
			moduleMgmtStatus = GlobalConstants.MSG_MODULE_STATUS_INSERT + runModuleMgmt.getModuleName() + 
					GlobalConstants.SPACE_STRING + runModuleMgmt.getSubModuleName();
			
		} catch(Exception exception) {
			moduleMgmtStatus = GlobalConstants.ERR_MODULE_MGMT_INSERT + runModuleMgmt.getModuleName() + 
					GlobalConstants.SPACE_STRING + runModuleMgmt.getSubModuleName();
			System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: "+moduleMgmtStatus);
			exception.printStackTrace();
			String errorDetails = exception.getStackTrace().toString();
			throw new GdprException(moduleMgmtStatus, errorDetails);
		}
		
	}
	
	public String prevJobModuleStatus(long runId) throws GdprException {
		String CURRENT_METHOD = "prevJobModuleStatus";		
		System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: Verification of Previous module status in progress.");
		String moduleMgmtStatus = "";
		String prevModuleStatus = GlobalConstants.STATUS_FAILURE;
		String errorDetails = "";
		
		try {
			List<RunModuleMgmt> lstRunModuleMgmt = runMgmtDaoImpl.prevModuleRunStatus(runId);
			int totalCount = 0;
			int successCount = 0;
			int failureCount = 0;
			for(RunModuleMgmt runModuleMgmt : lstRunModuleMgmt) {
				successCount = (runModuleMgmt.getModuleStatus().equalsIgnoreCase(GlobalConstants.STATUS_SUCCESS)) ? successCount + 1 : successCount;
				failureCount = (runModuleMgmt.getModuleStatus().equalsIgnoreCase(GlobalConstants.STATUS_FAILURE)) ? failureCount + 1 : failureCount;
				totalCount = totalCount + 1;
			}
			if(failureCount == 0 && successCount == totalCount)
				prevModuleStatus = GlobalConstants.STATUS_SUCCESS;
			
		} catch(Exception exception) {
			moduleMgmtStatus = GlobalConstants.ERR_MODULE_MGMT_FETCH;
			System.out.println(CURRENT_CLASS+" ::: "+CURRENT_METHOD+" :: "+moduleMgmtStatus);
			exception.printStackTrace();
			errorDetails = exception.getMessage();
			throw new GdprException(moduleMgmtStatus, errorDetails);
		}
		return prevModuleStatus;
	}
}