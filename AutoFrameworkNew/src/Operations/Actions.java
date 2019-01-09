
package Operations;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;










import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Actions {
	WebDriver driver;

	public Actions(WebDriver driver) {
		this.driver = driver;

	}

	/*
	 * Actions included in the Excel File: Value is Test Data from Excel
	 * objectName is xpath from excel objectType is attribute from excel
	 */
	public void perform(String operation, String objectName, String objectType,
			String value) throws Exception {
			
	
		System.out.println("");
		switch (operation.toUpperCase()) {
		
		case "CLICK":
			// To click on an element
			WebElement element = driver.findElement(this.getObject(objectName,
					objectType));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript(
					"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					element);
			Thread.sleep(1000);
			executor.executeScript("arguments[0].setAttribute('style', '');",
					element);
			executor.executeScript("arguments[0].click();", element);
			
			break;

		case "UPLOAD":
			
			
			/*driver.findElement(this.getObject(objectName, objectType)).click();
			Thread.sleep(10000);
			 Robot robot = new Robot();
			 robot.delay(1000);
			 //	robot.keyPress(KeyEvent.VK_CONTROL);
			 	robot.keyPress(KeyEvent.VK_D);
	            robot.keyPress(KeyEvent.VK_O);
	            robot.keyRelease(KeyEvent.VK_C);
	            //robot.keyRelease(KeyEvent.VK_CONTROL);
	            Thread.sleep(1000);
	            robot.keyPress(KeyEvent.VK_ENTER);
	            robot.keyRelease(KeyEvent.VK_ENTER);*/
			
			
			driver.findElement(this.getObject(objectName, objectType)).sendKeys(value);
			
			break;
			
			
			
		case "DEFAULTCLICK":	
			
			driver.findElement(this.getObject(objectName,objectType)).click();
			break;
			
			
		case "WAIT":
			int waitTime = (int) Double.parseDouble(value);
			Thread.sleep(waitTime);
			break;

			
		case "SETTEXT":

			// Send Keys or Set Text in the fields
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(this
					.getObject(objectName, objectType)));
			JavascriptExecutor executor1 = (JavascriptExecutor) driver;
			WebElement element1 = driver.findElement(this.getObject(objectName,
					objectType));
			executor1
					.executeScript(
							"arguments[0].setAttribute('style', 'background: yellow;  border: 2px solid red;');",
							element1);
			Thread.sleep(1000);
			executor1.executeScript(
					"arguments[0].setAttribute('style', 'color: black;');",
					element1);
			executor1.executeScript("arguments[0].click();", element1);
			executor1.executeScript("arguments[0].focus();", element1);
			// element1.sendKeys(new String[]{value});
			driver.findElement(this.getObject(objectName, objectType))
					.sendKeys(value);
			break;

		case "GOTOURL":
			// Get url of application
			driver.get(value);
			driver.manage().window().maximize();
			break;

		case "SWITCH":
			// Switch the Frame
			driver.switchTo().frame(value);
			break;

		case "SWITCHTODEFAULT":
			// Switch the Default Frame
			driver.switchTo().defaultContent();
			break;

		case "SCREENSHOT":
			// To take screenshot
			String screenShotFolderPath = System.getProperty("user.dir") + "/"
					+ "test-output" + "/" + "Screenshot_For_Passed_Test_Cases";
			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
			sdf1.setTimeZone(TimeZone.getTimeZone("IST"));
			Date date = new Date();
			Screenshot screenshot = new AShot().shootingStrategy(
					(ShootingStrategy) ShootingStrategies.viewportPasting(2000)).takeScreenshot(
					driver);
			ImageIO.write(screenshot.getImage(), "PNG",

			new File(screenShotFolderPath + "\\" + browserName + "-" + value
					+ "-" + sdf1.format(date) + ".png"));
			break;
			
		case "VERIFYTEXT":
			String expText = value;
			String actText = driver.findElement(
					this.getObject(objectName, objectType)).getText();
			JavascriptExecutor executor2 = (JavascriptExecutor) driver;
			WebElement element3 = driver.findElement(this.getObject(objectName,
					objectType));
			executor2
					.executeScript(
							"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; ');",
							element3);
			Thread.sleep(1000);
			executor2.executeScript("arguments[0].setAttribute('style', '');",
					element3);
			System.out.println(actText);
			
			if (actText.equals(expText)) {
				System.out.println("1) Expected text '" + expText
						+ "' present in the web page.");
			
			} else {
				System.out.println("2) Expected text '" + expText
						+ "' is not present in the web page.");
			}
			break;
		
			
			
		case "SWITCHADVFRAME":
			
		WebElement iframe = driver.findElement(this.getObject(objectName, objectType));
		driver.switchTo().frame(iframe);
		
		
		break;

		
			
		case "VERIFYIMAGE":
			driver.switchTo().frame(value);
			WebElement ImageFile = driver.findElement(this.getObject(
					objectName, objectType));
			Boolean ImagePresent = (Boolean) ((JavascriptExecutor) driver)
					.executeScript(
							"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
							ImageFile);
			if (!ImagePresent) {
				System.out.println("Image not displayed.");
			} else {
				System.out.println("Image displayed.");
			}
			break;

		case "CLEARTEXT":
			driver.findElement(this.getObject(objectName, objectType)).clear();
			driver.findElement(this.getObject(objectName,objectType)).sendKeys(""+Keys.DELETE);
			break;
			
		
		
			
		case "SCROLLGRID":
			Thread.sleep(2000);
			WebElement scrollArea = driver.findElement(this.getObject(
					objectName, objectType));
			JavascriptExecutor executor4 = (JavascriptExecutor) driver;
			executor4
					.executeScript(
							"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
							scrollArea);
			executor4.executeScript("arguments[0].setAttribute('style', '');",
					scrollArea);
			executor4.executeScript("arguments[0].scrollIntoView();",
					scrollArea);
			Thread.sleep(2000);
			break;

		case "ENTER":
			driver.findElement(this.getObject(objectName, objectType)).sendKeys(""+Keys.ENTER);
		break;
			
		
		
		
		case "SCROLLUP":
			WebElement scrollUP = driver.findElement(this.getObject(objectName,
					objectType));
			JavascriptExecutor executor8 = (JavascriptExecutor) driver;
			executor8.executeScript("arguments[0].scrollIntoView();", scrollUP);
			break;

			
		case "SETRESOLUTION":

			String[] parts = value.split(",");
			String part1 = parts[0];
			String part2 = parts[1];
			int x1 = (int) Double.parseDouble(part1);
			int y1 = (int) Double.parseDouble(part2);
			driver.manage().window().setSize(new Dimension(x1, y1));
			break;

		case "DROPDOWN":
			Thread.sleep(1000);

			int x = (int) Double.parseDouble(value);
			WebElement identifier = driver.findElement(this.getObject(
					objectName, objectType));
			JavascriptExecutor executor5 = (JavascriptExecutor) driver;
			executor5
					.executeScript(
							"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
							identifier);
			Thread.sleep(1000);
			executor5.executeScript("arguments[0].setAttribute('style', '');",
					identifier);
			Select select = new Select(identifier);
			select.selectByIndex(x);
			break;
			
			
		case "BACKSPACE":
			driver.findElement(this.getObject(objectName, objectType)).sendKeys(Keys.BACK_SPACE);
			break;
			
			
		case "CLICKLINKTEXT":
			driver.findElement(this.getObject(objectName, objectType)).click();
			break;

		case "CLOSE":
			// To close driver
			driver.manage().deleteAllCookies();
			driver.close();
			break;

		default:
			break;
		}
	}
	

	// Elements included in Excel File
	private By getObject(String objectName, String objectType) throws Exception {
		// Find element by XPath
		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(objectName);
		}
		// find element by class name
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(objectName);
		}
		// find element by name
		else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(objectName);
		}
		// Find element by css selector
		else if (objectType.equalsIgnoreCase("CSSSELECTOR")) {
			return By.cssSelector(objectName);
		}
		// find element by ID
		else if (objectType.equalsIgnoreCase("ID")) {
			return By.id(objectName);
		}
		// find element by link
		else if (objectType.equalsIgnoreCase("LINKTEXT")) {
			return By.linkText(objectName);
		}
		// find element by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(objectName);
		} else {
			throw new Exception("Wrong object type");
		}
	}
}