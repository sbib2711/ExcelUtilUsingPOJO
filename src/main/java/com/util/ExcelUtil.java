package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dto.Fruits;

public class ExcelUtil {
	
public static List<Fruits> readFromExcel(String filename, String sheetname) throws IOException {
		
		List<Fruits> fruitList = new ArrayList<Fruits>();
		Fruits fruit;
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(filename));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetname);
			int lastRowNum = sheet.getLastRowNum();

			for (int i = 1; i <= lastRowNum; i++) {
				
				fruit = new Fruits();
				int lastCellNum = sheet.getRow(i).getLastCellNum();
				
				for (int j = 0; j < lastCellNum; j++) {
					
					switch (j) {
					case 0:
						fruit.setFruit(sheet.getRow(i).getCell(j).getStringCellValue());
						break;
						
					}
				}
				
				fruitList.add(fruit);
							
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				file.close();
			}
		}
		
		return fruitList;
	}
	
}


