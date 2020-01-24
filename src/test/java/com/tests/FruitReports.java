package com.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;

import com.util.ExcelUtil;
import com.dto.Fruits;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class FruitReports{

	String expFruit = null;
	String actFruit = null;
	Map<String,String> mapAccountName = null;
	boolean flag = false;
	/*BigDecimal bd;
	double diff;*/
	
	

	@Test
	public void fruitReport() throws IOException, InterruptedException {
		
		List<String> actFruitList = new ArrayList<String>();
		actFruitList.add("Mango");
		actFruitList.add("Orange");
		actFruitList.add("Cucumber");
		actFruitList.add("Apple");
		
		String filename = System.getProperty("user.dir")+"\\testdata.xlsx";
		List<Fruits> expFruitList = ExcelUtil.readFromExcel(filename, "fruits");
		
		for (int i = 0; i < expFruitList.size(); i++){
			
			Fruits fruit = expFruitList.get(i);
			expFruit = fruit.getFruit();
			
			for(int j=0; j<actFruitList.size(); j++){
				
				if(expFruit.equals(actFruitList.get(j))){
					
					flag = true;
					break;
				}
				
				flag = false;
			}
			
			if(flag){
				System.out.println(expFruit+" is found on the page!!!");
			}else{
				System.out.println(expFruit+" is not present on the page!!!");
			}
			
		}
		
	}
	
}
			
			/*mapAccountName = fydashboard.getAccountNameForOctober(account);
			if(null == mapAccountName){
				paretntTest.log(Status.INFO, MarkupHelper.createLabel(account+" is not present in the Dashboard", ExtentColor.PURPLE));
			}else{
				
				strTotalRevenue = mapAccountName.get("TotalRevenue");
				strTotalRevenue = strTotalRevenue.replace(",", "");
				uiTotalRevenue = Double.parseDouble(strTotalRevenue);
				bd = new BigDecimal(uiTotalRevenue).setScale(2, RoundingMode.HALF_UP);
				uiTotalRevenue = bd.doubleValue();
				excelTotalRevenue = wfp.getSumOfRev();
				bd = new BigDecimal(excelTotalRevenue).setScale(2, RoundingMode.HALF_UP);
				excelTotalRevenue = bd.doubleValue();
				diff = Math.abs(uiTotalRevenue - excelTotalRevenue);
				
				if(uiTotalRevenue == excelTotalRevenue){
					paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Revenue matches for Account: "+account+" for October; UI: "+uiTotalRevenue+" Excel: "+excelTotalRevenue, ExtentColor.GREEN));
				}else{
					if(diff<1.00){
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Revenue Difference<1 for Account: "+account+" for October; UI: "+uiTotalRevenue+" Excel: "+excelTotalRevenue+" Defferenece: "+diff, ExtentColor.CYAN));
					}else{
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Revenue does not match for Account: "+account+" for October; UI: "+uiTotalRevenue+" Excel: "+excelTotalRevenue+" Defferenece: "+diff, ExtentColor.ORANGE));
					}
				}*/

