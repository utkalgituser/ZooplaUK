package com.zoopla.uk.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import com.zoopla.uk.drivers.InitializeDriver;
import com.zoopla.uk.testbase.TestBase;

/**
 * 
 * @author UTKAL
 * 
 */

public class ExcelDataProviderLib extends TestBase {
	private Object[][] excelData;
	private Workbook wb;
	private FileInputStream fis;
	private DataFormatter dataFormatter;
	private static final Logger log = LogManager.getLogger(ExcelDataProviderLib.class);

	/**
	 * this method reads the sheet from excel and return a two dimensional object
	 * array.
	 * 
	 * @return return a two dimensional object array.
	 */
	@DataProvider(name = "getDataFromExcel")
	public Object[][] getDataFromExcel() {
		try {
			log.debug("+++++++++++++++++++ Start of Data Provider Library +++++++++++++++++++");
			String filePath = System.getProperty("user.dir") + ConfigFileRead.readkey("excelfilepath");
			String sheetName = ConfigFileRead.readkey("sheetname");
			fis = new FileInputStream(filePath);
			wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(sheetName);
			int rowCount = sh.getLastRowNum() + 1;
			int cellCount = sh.getRow(0).getLastCellNum();
			log.debug("Last row number and last cell number are respectively " + rowCount + " , " + cellCount);
			excelData = new Object[rowCount - 1][cellCount];
			// DataFormatter class provides function to convert any type of cell to String
			// format using formatCellValue method.
			dataFormatter = new DataFormatter();
			for (int i = 1; i < rowCount; i++) {
				for (int j = 0; j < cellCount; j++) {
					// log.debug("i and j value are " + i + " , " + j);
					Row row = sh.getRow(i);
					String value = dataFormatter.formatCellValue(row.getCell(j));
					log.info("Cell value is " + value);
					excelData[i - 1][j] = value;
				}
			}
			log.debug("+++++++++++++++++++ End of Data Provider Library +++++++++++++++++++");
		} catch (IOException e) {
			log.error("Excel read/write error....");
			log.error(e.getCause().toString());
			return null;
		} catch (EncryptedDocumentException e) {
			log.error(e.getCause().toString());
		} catch (Exception e) {
			log.error(e.getCause().toString());
		} finally {
			try {
				fis.close();
				wb.close();
				log.debug("Finally block in data provider. Excel Sheet closed");
			} catch (Exception e) {
				log.error("Issue with excel please check whether sheet is saved and closed");
				log.error(e.getCause().toString());
			}
		}
		return excelData;
	}

	public void logInfo(String data) {
		log.info(data);
		if (InitializeDriver.isreporterLogRequired) {
			Reporter.log(data);
		}
	}

	public static void logDebug(String data) {
		if (log.isDebugEnabled()) {
			log.debug(data);
			if (InitializeDriver.isreporterLogRequired) {
				Reporter.log(data, true);
			}
		}
	}

	public static void logError(String data) {
		log.error(data);
		if (InitializeDriver.isreporterLogRequired) {
			Reporter.log(data, true);
		}
	}
}