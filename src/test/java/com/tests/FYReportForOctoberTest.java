/*package com.tests;

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

import com.qa.base.BaseTest;
import com.qa.util.ExcelUtil;
import com.qa.dto.WFP;
import com.qa.pages.FYDashBoard;
import com.qa.pages.ProjectHealthDashBoard;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class FYReportForOctoberTest extends BaseTest{

	String account = null;
	Map<String,String> mapAccountName = null;
	String strTotalRevenue = null;
	double uiTotalRevenue;
	double excelTotalRevenue;
	String strEmploymentCost = null;
	double uiEmploymentCost;
	double excelEmploymentCost;
	String strOtherCost = null;
	double uiOtherCost;
	double excelOtherCost;
	String strTotalSeatCost = null;
	double uiTotalSeatCost;
	double excelTotalSeatCost;
	String strGMWithoutSeatCost = null;
	double uiGMWithoutSeatCost;
	double excelGMWithoutSeatCost;
	String strGMWithSeatCost = null;
	double uiGMWithSeatCost;
	double excelGMWithSeatCost;
	public static boolean flag = false;
	ProjectHealthDashBoard projecthealthdashBoard;
	FYDashBoard fydashboard;
	

	public FYReportForOctoberTest(){

		super();
	}


	public FYDashBoard login(){

		projecthealthdashBoard = new ProjectHealthDashBoard();
		fydashboard = projecthealthdashBoard.login();
		return fydashboard;
	}


	@Test
	public void accrualReport() throws IOException, InterruptedException {

		fydashboard = login();
		Thread.sleep(5000);
		String filename = prop.getProperty("filename");
		paretntTest = extent.createTest("Accrual Report For October");
		List<WFP> wfpList = ExcelUtil.readFromExcel(filename, "October");
		BigDecimal bd;
		double diff;

		for (int i = 0; i < wfpList.size(); i++){

			WFP wfp = wfpList.get(i);
			account = wfp.getAccountName();
			mapAccountName = fydashboard.getAccountNameForOctober(account);
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
				}
				

				strEmploymentCost = mapAccountName.get("EmploymentCost");
				strEmploymentCost = strEmploymentCost.replace(",", "");
				uiEmploymentCost = Double.parseDouble(strEmploymentCost);
				bd = new BigDecimal(uiEmploymentCost).setScale(2, RoundingMode.HALF_UP);
				uiEmploymentCost = bd.doubleValue();
				excelEmploymentCost = wfp.getSumOfEmpCost();
				bd = new BigDecimal(excelEmploymentCost).setScale(2, RoundingMode.HALF_UP);
				excelEmploymentCost = bd.doubleValue();
				diff = Math.abs(uiEmploymentCost - excelEmploymentCost);
				
				if(uiEmploymentCost == excelEmploymentCost){
					paretntTest.log(Status.INFO, MarkupHelper.createLabel("Employment Cost matches for Account: "+account+" for October; UI: "+uiEmploymentCost+" Excel: "+excelEmploymentCost, ExtentColor.GREEN));
				}else{
					if(diff<1.00){
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Employment Cost Difference<1 for Account: "+account+" for October; UI: "+uiEmploymentCost+" Excel: "+excelEmploymentCost+" Defferenece: "+diff, ExtentColor.CYAN));
					}else{
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Employment Cost does not match for Account: "+account+" for October; UI: "+uiEmploymentCost+" Excel: "+excelEmploymentCost+" Defferenece: "+diff, ExtentColor.ORANGE));
					}
				}
				

				strOtherCost = mapAccountName.get("OtherCost");
				strOtherCost = strOtherCost.replace(",", "");
				uiOtherCost = Double.parseDouble(strOtherCost);
				bd = new BigDecimal(uiOtherCost).setScale(2, RoundingMode.HALF_UP);
				uiOtherCost = bd.doubleValue();
				excelOtherCost = wfp.getSumOfDirectCost();
				bd = new BigDecimal(excelOtherCost).setScale(2, RoundingMode.HALF_UP);
				excelOtherCost = bd.doubleValue();
				diff = Math.abs(uiOtherCost - excelOtherCost);
				
				if(uiOtherCost == excelOtherCost){
					paretntTest.log(Status.INFO, MarkupHelper.createLabel("Other Direct Cost matches for Account: "+account+" for October; UI: "+uiOtherCost+" Excel: "+excelOtherCost, ExtentColor.GREEN));
				}else{
					if(diff<1.00){
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Other Direct Cost Difference<1 for Account: "+account+" for October; UI: "+uiOtherCost+" Excel: "+excelOtherCost+" Defferenece: "+diff, ExtentColor.CYAN));
					}else{
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Other Direct Cost does not match for Account: "+account+" for October; UI: "+uiOtherCost+" Excel: "+excelOtherCost+" Defferenece: "+diff, ExtentColor.ORANGE));
					}
				}
				

				strTotalSeatCost = mapAccountName.get("TotalSeatCost");
				strTotalSeatCost = strTotalSeatCost.replace(",", "");
				uiTotalSeatCost = Double.parseDouble(strTotalSeatCost);
				bd = new BigDecimal(uiTotalSeatCost).setScale(2, RoundingMode.HALF_UP);
				uiTotalSeatCost = bd.doubleValue();
				excelTotalSeatCost = wfp.getSumOfSeatCost();
				bd = new BigDecimal(excelTotalSeatCost).setScale(2, RoundingMode.HALF_UP);
				excelTotalSeatCost = bd.doubleValue();
				diff = Math.abs(uiTotalSeatCost - excelTotalSeatCost);
				
				if(uiTotalSeatCost == excelTotalSeatCost){
					paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Seat Cost matches for Account: "+account+" for October; UI: "+uiTotalSeatCost+" Excel: "+excelTotalSeatCost, ExtentColor.GREEN));
				}else{
					if(diff<1.00){
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Seat Cost Difference<1 for Account: "+account+" for October; UI: "+uiTotalSeatCost+" Excel: "+excelTotalSeatCost+" Defferenece: "+diff, ExtentColor.CYAN));
					}else{
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Seat Cost does not match for Account: "+account+" for October; UI: "+uiTotalSeatCost+" Excel: "+excelTotalSeatCost+" Defferenece: "+diff, ExtentColor.ORANGE));
					}
				}
				
				strGMWithSeatCost = mapAccountName.get("GMWithSeatCost");
				strGMWithSeatCost = strGMWithSeatCost.replace(",", "");
				uiGMWithSeatCost = Double.parseDouble(strGMWithSeatCost);
				bd = new BigDecimal(uiGMWithSeatCost).setScale(2, RoundingMode.HALF_UP);
				uiGMWithSeatCost = bd.doubleValue();
				excelGMWithSeatCost = wfp.getSumOfGMWithSeatCost();
				excelGMWithSeatCost = excelGMWithSeatCost*100;
				bd = new BigDecimal(excelGMWithSeatCost).setScale(2, RoundingMode.HALF_UP);
				excelGMWithSeatCost = bd.doubleValue();
				diff = Math.abs(uiGMWithSeatCost - excelGMWithSeatCost);
				
								
				if(uiGMWithSeatCost == excelGMWithSeatCost){
					paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM With Seat Cost matches for Account: "+account+" for October; UI: "+uiGMWithSeatCost+" Excel: "+excelGMWithSeatCost, ExtentColor.GREEN));
				}else{
					if(diff<1.00){
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM With Seat Cost Difference<1 for Account: "+account+" for October; UI: "+uiGMWithSeatCost+" Excel: "+excelGMWithSeatCost+" Defferenece: "+diff, ExtentColor.CYAN));
					}else{
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM With Seat Cost does not match for Account: "+account+" for October; UI: "+uiGMWithSeatCost+" Excel: "+excelGMWithSeatCost+" Defferenece: "+diff, ExtentColor.ORANGE));
					}
				}
				

				strGMWithoutSeatCost = mapAccountName.get("GMWithoutSeatCost");
				strGMWithoutSeatCost = strGMWithoutSeatCost.replace(",", "");
				uiGMWithoutSeatCost = Double.parseDouble(strGMWithoutSeatCost);
				bd = new BigDecimal(uiGMWithoutSeatCost).setScale(2, RoundingMode.HALF_UP);
				uiGMWithoutSeatCost = bd.doubleValue();
				excelGMWithoutSeatCost = wfp.getSumOfGMWithoutSeatCost();
				excelGMWithoutSeatCost = excelGMWithoutSeatCost*100;
				bd = new BigDecimal(excelGMWithoutSeatCost).setScale(2, RoundingMode.HALF_UP);
				excelGMWithoutSeatCost = bd.doubleValue();
				diff = Math.abs(uiGMWithoutSeatCost - excelGMWithoutSeatCost);
				
								
				if(uiGMWithoutSeatCost == excelGMWithoutSeatCost){
					paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM Without Seat Cost matches for Account: "+account+" for October; UI: "+uiGMWithoutSeatCost+" Excel: "+excelGMWithoutSeatCost, ExtentColor.GREEN));
				}else{
					if(diff<1.00){
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM Without Seat Cost Difference<1 for Account: "+account+" for October; UI: "+uiGMWithoutSeatCost+" Excel: "+excelGMWithoutSeatCost+" Defferenece: "+diff, ExtentColor.CYAN));
					}else{
						paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM Without Seat Cost does not match for Account: "+account+" for October; UI: "+uiGMWithoutSeatCost+" Excel: "+excelGMWithoutSeatCost+" Defferenece: "+diff, ExtentColor.ORANGE));
					}
				}				
				
			}

			uiTotalRevenueForOctober = fydashboard.getTotalRevenueForOctober(account);
			if(uiTotalRevenueForOctober == null){
				paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Revenue for Account: "+account+" for October is not present in the Dashboard", ExtentColor.YELLOW));
			}else{
			uiTotalRevenueForOctober = uiTotalRevenueForOctober.replace(",", "");
			excelTotalRevenueForOctober = wfp.getSumOfOctRev();
			paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Revenue for Account: "+account+" for October; UI: "+uiTotalRevenueForOctober+" Excel: "+excelTotalRevenueForOctober, ExtentColor.GREEN));
			}

			uiEmploymentCostForOctober = fydashboard.getEmploymentCostForOctober(account);
			if(uiEmploymentCostForOctober == null){
				paretntTest.log(Status.INFO, MarkupHelper.createLabel("Employment Cost for Account: "+account+" for October is not present in the Dashboard", ExtentColor.YELLOW));
			}else{
			uiEmploymentCostForOctober = uiEmploymentCostForOctober.replace(",", "");
			excelEmploymentCostForOctober = wfp.getSumOfOctEmpCost();
			paretntTest.log(Status.INFO, MarkupHelper.createLabel("Employment Cost for Account: "+account+" for October; UI: "+uiEmploymentCostForOctober+" Excel: "+excelEmploymentCostForOctober, ExtentColor.GREEN));
			}

			uiTotalSeatCostForOctober = fydashboard.getTotalSeatCostForOctober(account);
			if(uiTotalSeatCostForOctober == null){
				paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Seat Cost for Account: "+account+" for October is not present in the Dashboard", ExtentColor.YELLOW));
			}else{
			uiTotalSeatCostForOctober = uiTotalSeatCostForOctober.replace(",", "");
			excelTotalSeatCostForOctober = wfp.getSumOfOctSeatCost();
			paretntTest.log(Status.INFO, MarkupHelper.createLabel("Total Seat Cost for Account: "+account+" for October; UI: "+uiTotalSeatCostForOctober+" Excel: "+excelTotalSeatCostForOctober, ExtentColor.GREEN));
			}

			uiGMWithoutSeatCostForOctober = fydashboard.getGMWithoutSeatCostForOctober(account);
			if(uiGMWithoutSeatCostForOctober == null){
				paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM Without Seat Cost for Account: "+account+" for October is not present in the Dashboard", ExtentColor.YELLOW));
			}else{
			uiGMWithoutSeatCostForOctober = uiGMWithoutSeatCostForOctober.replace(",", "");
			excelGMWithoutSeatCostForOctober = wfp.getSumOfOctGMWithoutSeatCost();
			paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM Without Seat Cost for Account: "+account+" for October; UI: "+uiGMWithoutSeatCostForOctober+" Excel: "+excelGMWithoutSeatCostForOctober, ExtentColor.GREEN));
			}

			uiGMWithSeatCostForOctober = fydashboard.getGMWithSeatCostForOctober(account);
			if(uiGMWithSeatCostForOctober == null){
				paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM With Seat Cost for Account: "+account+" for October is not present in the Dashboard", ExtentColor.YELLOW));
			}else{
			uiGMWithSeatCostForOctober = uiGMWithSeatCostForOctober.replace(",", "");
			excelGMWithSeatCostForOctober = wfp.getSumOfOctGMWithSeatCost();
			paretntTest.log(Status.INFO, MarkupHelper.createLabel("GM With Seat Cost for Account: "+account+" for October; UI: "+uiGMWithSeatCostForOctober+" Excel: "+excelGMWithSeatCostForOctober, ExtentColor.GREEN));
			}
		}



		for (int j = 0; j < monthwiseRevList.size(); j++) {
				MonthWiseEmpRevenue monthlyRev = monthwiseRevList.get(j);

				if(wfpRev.getEmpName().equalsIgnoreCase(monthlyRev.getEmpName())){

					flag = true;
					ifEmpNameFound(wfpRev.getEmpName(),wfpRev.getSumOfOctRev(),monthlyRev.getTotalRev());
					break;
				}
			}

			if(flag){

				//System.out.println(empRev.getEmpName()+" found in sheet 2");
				flag = false;
			}
			else{
				paretntTest.log(Status.INFO, MarkupHelper.createLabel(wfpRev.getEmpName()+" is not found in October Sheet", ExtentColor.RED));
			}

	}

	public static void ifEmpNameFound(String empName, float sumOfOctRev, float totalRev){

		if(sumOfOctRev == totalRev){

			paretntTest.log(Status.INFO, MarkupHelper.createLabel("Amount matches for: "+empName+" "+sumOfOctRev+" "+totalRev, ExtentColor.GREEN));
		}
		else{
			paretntTest.log(Status.INFO, MarkupHelper.createLabel("Amount does not match for: "+empName+" "+sumOfOctRev+" "+totalRev, ExtentColor.YELLOW));
		}

	}

}
*/