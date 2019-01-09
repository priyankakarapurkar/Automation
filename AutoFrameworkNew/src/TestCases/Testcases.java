package TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import Operations.Actions;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import readExcel.ReadExcel;
import readExcel.WriteExcel;

public class Testcases {
	WebDriver driver;
	String browser;
	String filePath = System.getProperty("user.dir") + "/" + "test-output";
	
	
	// To launch the Different Browsers according to the TestNG file order

	@Parameters("browser")
	@BeforeMethod
	public void beforeTest(String browser) throws MalformedURLException {
		
		// Launching the Chrome Browser
		this.browser = browser;
		if (browser.equalsIgnoreCase("chrome")) {
			
			// For Running in Node
		/*	File file = new File("user.dir//chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.VISTA);
			driver  = new RemoteWebDriver(new URL("http://192.168.24.40:5566/wd/hub"), cap);
			*/
			
			
			//caps.setBrowserName("chrome");
			File file = new File("chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			// For Capturing the Logs in Result Excel File
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			//Launching Browser
			driver = new ChromeDriver();
			
		} else if (browser.equalsIgnoreCase("ie")) {
			System.getProperty("webdriver.edge.driver",
					"user.dir\\IEDriverServer.exe");
			
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			driver = new EdgeDriver();
			// Launching the firefox Browser
			
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"user.dir\\geckodriver.exe");
			driver = new FirefoxDriver();

		}
	}

	@Test(priority = 1)
	public void commonCommunities() throws Exception {

		ReadExcel file = new ReadExcel();

		Sheet sheet = file.readFirstSheet(
				System.getProperty("user.dir") + "\\", "TestCase.xlsx");

		if (sheet != null) {

			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

			for (int i = 1; i < rowCount + 1; i++) {
				Row row = sheet.getRow(i);

				if (row.getCell(1) != null
						&& row.getCell(2).toString().equals("YES")) {
					this.Communities(row.getCell(1).toString());
					System.out.println(row.getCell(1));
				} else if (row.getCell(1) != null
						&& row.getCell(2).toString().equals("NO")) {
					System.out.println(row.getCell(1) + " is Skipped");
				}
			}
		} else {
			System.out.println("Sheet not found");
		}
	}

	// Reading the Excel file and Performing the actions written in the file
	public void Communities(String sheetName) throws Exception {
		ReadExcel file = new ReadExcel();
		Actions operation = new Actions(driver);
		String implicitwait;
		Sheet Sheet = file.readExcel(System.getProperty("user.dir") + "\\",
				"TestCase.xlsx", sheetName);

		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		HashMap<Integer, String> hml = new HashMap<Integer, String>();

		createSceenshotFolder("Screenshot_For_Passed_Test_Cases");
		createSceenshotFolder("Screenshot_For_Failed_Test_Cases");
		int rowCount = Sheet.getLastRowNum() - Sheet.getFirstRowNum();
		implicitwait = Sheet.getRow(2).getCell(5).toString();
		int x = (int) Double.parseDouble(implicitwait);
		driver.manage().timeouts().implicitlyWait(x, TimeUnit.SECONDS);
		boolean toRun = true;

		for (int i = 1; i < rowCount + 1; i++) {
			Row row = Sheet.getRow(i);

			if (row.getCell(0) == null && toRun) {
				System.out.println(row.getCell(2) + "----" + row.getCell(3)
						+ "----" + row.getCell(4) + "----" + row.getCell(5));
				try {

					hm.put(i, "Pass");
					

					operation
							.perform(
									row.getCell(2) == null ? "Not a Valid KeyWord"
											: row.getCell(2).toString(),
									row.getCell(4) == null ? null : row
											.getCell(4).toString(),
									row.getCell(3) == null ? null : row
											.getCell(3).toString(),
									row.getCell(5) == null ? null : row
											.getCell(5) + "");
					try{
						LogEntries logEntries = driver.manage().logs()
								.get(LogType.BROWSER);
						for (LogEntry entry : logEntries) {
							hml.put(i, entry.getMessage());
					}}
					catch(Exception e){
						System.out.println("No Log File Created");
					}
					
					
				}
				 catch (Exception e) {
					hm.put(i, "Fail");
					System.out.println(i + 1);
					TakesScreenshot ts = (TakesScreenshot) driver;
					File source = ts.getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(source, new File(filePath + "/"
							+ "Screenshot_For_Failed_Test_Cases" + "/"
							+ (i + 1) + "_line_no_failed.png"));

					System.out.println(e);

				}

			} else if (row.getCell(0) != null) {
				// testCasesNames.add(row.getCell(0).toString());
				System.out.println("New Testcase->" + row.getCell(0)
						+ " Started");
				System.out.println(row.getCell(0) + "Passed");

				toRun = row.getCell(1).toString().equals("YES");

			}
		}
		writeData(hm, hml, sheetName);
	}

	private void writeData(HashMap<Integer, String> hm,
			HashMap<Integer, String> hml, String sheetName) throws IOException {
		String filePath = System.getProperty("user.dir");
		String OutputPath = System.getProperty("user.dir") + "/"
				+ "test-output" + "/" + "Results";
		File src = new File(filePath + "\\" + "TestCase.xlsx");
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		sdf1.setTimeZone(TimeZone.getTimeZone("IST"));
		Date date = new Date();
		FileOutputStream fout = new FileOutputStream(new File(OutputPath + "\\"
				+ browserName + "-" + sdf1.format(date) + "TestOutput.xlsx"));
		FileInputStream fis;
		fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh1 = wb.getSheet(sheetName);
		Row outrow = sh1.getRow(0);
		writeColumnData(hm, 6, "Results",sh1,outrow );
		writeColumnData(hml, 7, "Logs",sh1,outrow );
		
		
		XSSFCellStyle my_style = wb.createCellStyle();
		my_style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		my_style.setFillPattern(FillPatternType.LEAST_DOTS);
		sh1.getRow(0).getCell(6).setCellStyle(my_style);
		
		
		XSSFCellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		style.setFillPattern(FillPatternType.LEAST_DOTS);
		sh1.getRow(0).getCell(7).setCellStyle(style);
		wb.write(fout);
		fis.close();
	}

	private void writeColumnData(HashMap<Integer, String> hm, int columnNo,
			String columnName,XSSFSheet sh1,Row outrow )
			throws IOException {
		
		
		
		
		
		
		Cell cell = outrow.createCell(columnNo);
		cell.setCellValue(columnName);
		for (Map.Entry m : hm.entrySet()) {
			Row result = sh1.getRow(Integer.parseInt(m.getKey().toString()));
			Cell cellRes = result.createCell(columnNo);
			cellRes.setCellValue(m.getValue().toString());
		}
	}

	private void createSceenshotFolder(String folderName) {
		String newFolderPath = filePath;
		String screenShotPath = newFolderPath + "/" + folderName;
		File f = new File(screenShotPath);
		f.mkdir();
	}

}
