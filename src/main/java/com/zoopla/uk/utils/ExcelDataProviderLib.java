package com.zoopla.uk.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

import com.zoopla.uk.base.TestBase;

/**
 * 
 * @author UTKAL
 *			
 */
public class ExcelDataProviderLib extends TestBase {
	private Object[][] excelData;
	private Workbook wb;
	private FileInputStream fis;

	private static final Logger log = Logger.getLogger(ExcelDataProviderLib.class.getName());

	@DataProvider(name = "getDataFromExcel")
	public Object[][] getDataFromExcel() {

		try {
			log.debug("In data provider library +++++++++++");
			String filePath = System.getProperty("user.dir") + ConfigFileRead.readConfigFile("excelfilepath");
			String sheetName = ConfigFileRead.readConfigFile("sheetname");
			fis = new FileInputStream(filePath);
			wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(sheetName);
			int rowCount = sh.getLastRowNum() + 1;
			int cellCount = sh.getRow(0).getLastCellNum();
			log.debug("last cell number is " + cellCount);
			excelData = new Object[rowCount - 1][cellCount];
			for (int i = 1; i < rowCount; i++) {
				// System.out.println("row is " + i);
				for (int j = 0; j < cellCount - 1; j++) {
					Row r = sh.getRow(i);
					// System.out.println("column is " + j);
					String value = r.getCell(j).getStringCellValue();
					log.debug("Cell value is " + value);
					// [i - 1] because needs to start from 0, otherwise first
					// run value will be null
					excelData[i - 1][j] = value;
					// System.out.println("j is " + j);
				}
			}
			log.debug("Excel read complete------------------");
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("XXXXXXXXXXXXX Smoething wrong");
			return null;
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				wb.close();
				log.debug("finally block in data provider");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return excelData;
	}
}