package readExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	Workbook workbook = null;

	public void getWorkbook(String filePath, String fileName)
			throws FileNotFoundException, IOException {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);

		} else if (fileExtensionName.equals(".xls")) {
			workbook = new HSSFWorkbook(inputStream);
		}
	}

	public Sheet readExcel(String filePath, String fileName, String sheetName)
			throws IOException {
		getWorkbook(filePath, fileName);
		System.out.println(fileName);
		if (workbook != null) {
			Sheet Sheet = workbook.getSheet(sheetName);
			return Sheet;
			
		} else {
			return null;
			
		}
	}

	public Sheet readFirstSheet(String filePath, String fileName)
			throws IOException {
		getWorkbook(filePath, fileName);
		if (workbook != null) {
			Sheet excelSheet = workbook.getSheetAt(1);
			return excelSheet;
		} else {
			return null;
		}
	}
	
}