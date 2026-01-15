package com.orangehrm.utilities;

import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final String File_Path=System.getProperty("user.dir")+"/src/test/resources/testdata/Test_data.xlsx";
	
	@DataProvider(name="validLogindata")
	public static Object[][] validLogindata(){
		return getSheetData("validLoginTest");
	}
	
	@DataProvider(name="invalidLogindata")
	public static Object[][] invalidLogindata(){
		return getSheetData("invalidLoginTest");
	}
	
	private static Object[][] getSheetData(String sheetName){
		List<String[]> sheetData = ExcelUtility.getSheetData(File_Path, sheetName);
		
		Object[][] data = new Object[sheetData.size()][sheetData.get(0).length];
		
		for(int i=0; i<sheetData.size();i++) {
			data[i]=sheetData.get(i);
		}
		return data;
	}
	
	
	
}
