package readExcel;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WriteExcel {

	WebDriver driver;
    String baseUrl = "http://www.google.com";
    String expectedTitle = "Google";

    @BeforeTest
    public void setUp() throws MalformedURLException {
        File file = new File("user.dir//chromedriver.exe");
        //File file = new File("C://Drivers//IEDriverServer.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setBrowserName("chrome");
        cap.setPlatform(Platform.VISTA);
        driver  = new RemoteWebDriver(new URL("http://192.168.24.40:5566/wd/hub"), cap);
    }}
	/*public void analyzeLog() throws Throwable {
		try {
			File statText = new File("C:\\Tresa\\statsTest.log");
			FileOutputStream is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);

			LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
			
			for (LogEntry entry : logEntries)

			{
				w.write(new Date(entry.getTimestamp()) + " " + entry.getLevel()
						+ " " + entry.getMessage());
				w.write("\r\n");

			}
			w.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testMethod() throws Throwable {
		driver.get("https://uat-eprise.cs91.force.com/e360/ENT_Community_Login_Frm");
		driver.switchTo().frame("iframe");
		driver.findElement(By.xpath("//input[@name='thePage:theForm:j_id9']"))
				.sendKeys("testsatish");
		driver.findElement(By.xpath("//input[@name='thePage:theForm:j_id11']"))
				.sendKeys("test1234");
		driver.findElement(By.id("thePage:theForm:btnLogin1")).click();

		System.out.println("SF login successful");
		Thread.sleep(10000);

		driver.switchTo().defaultContent();

		driver.findElement(
				By.xpath("//span[@class='light nav-item'][.='LIHTC']")).click();
		driver.findElement(
				By.xpath("//div[@id='navbar-collapse']/ul[1]/li[3]/ul/li[5]/a"))
				.click();
		Thread.sleep(1000);
		analyzeLog();
	}

}
*/