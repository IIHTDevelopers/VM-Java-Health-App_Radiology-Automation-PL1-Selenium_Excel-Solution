package pages;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class radiology_page extends StartupPage {

//	TC-1 Locator
	public By getUsernameTextfieldLocator() {
		return By.id("username_id");
	}
	public By getPasswordTextboxLocator() {
		return By.xpath("//input[@id='password']");
	}
	public By getSignInButtonLocator() {
		return By.xpath("//button[@id='login']");
	}
	public By getRadiologyLocator() {
		return By.xpath("//a[@href='#/Radiology']");
	}
//	TC-2 Locator
	
//	TC-3 Locator
	public By getPageBarFixedLocator(String navBarName) {
		if (navBarName.equalsIgnoreCase("list requests")) {
			navBarName = "ImagingRequisitionList";
		} else if (navBarName.equalsIgnoreCase("list reports")) {
			navBarName = "ImagingReportsList";
		} else if (navBarName.equalsIgnoreCase("edit doctors")) {
			navBarName = "EditDoctors";
		} else if (navBarName.equalsIgnoreCase("ward billing")) {
			navBarName = "InpatientList";
		}
		return By.xpath("//ul[@class='page-breadcrumb']/li/a[@href='#/Radiology/" + navBarName + "']");
	}
//	TC-4 Locators
	public By calendarFromDropdown() {
		return By.xpath("(//input[@id='date'])[1]");
	}
	public By calendarToDropdown() {
		return By.xpath("(//input[@id='date'])[2]");
	}
	public By searchBarId() {
		return By.id("quickFilterInput");
	}
	public By getDateRangeButton() {
		return By.cssSelector("td [data-hover='dropdown']");
	}
	public By getFilterDropdownLocator() {
		return By.xpath("//b[text()='Filter']/../imaging-type-selector/select");
	}
	public By getStarIconLocator() {
		return By.xpath("//i[contains(@class,'icon-favourite')]/..");
	}



//	TC-6 Locators
	public By getButtonLocatorsBytext(String buttonName) {
		return By.xpath("//button[contains(text(),'" + buttonName + "')]");
	}
//	TC-7 Locators
	public By favouriteOrStarIcon() {
		return By.xpath("//i[contains(@class,'icon-favourite')]");
	}
	
//	TC-8 Locators
	public By getOkButtonLocator() {
		return By.xpath("//button[@class='btn green btn-success']");
	}

	public radiology_page(WebDriver driver) {
		super(driver);
	}

	/**
	 * @Test1.1 about this method loginTohealthAppByGivenValidCredetial()
	 * 
	 * @param : Map<String, String>
	 * @description : fill usernameTextbox & passwordTextbox and click on sign in
	 *              button
	 * @return : Boolean
	 * @author : Yaksha
	 */
	public boolean loginToHealthAppByGivenValidCredetial(Map<String, String> expectedData) throws Exception {
		Boolean textIsDisplayed = false;
		try {
			WebElement usernametextFieldWebElement = commonEvents.findElement(getUsernameTextfieldLocator());
			commonEvents.highlightElement(usernametextFieldWebElement);
			commonEvents.sendKeys(getUsernameTextfieldLocator(), expectedData.get("username"));

			WebElement passwordtextFieldWebElement = commonEvents.findElement(getPasswordTextboxLocator());
			commonEvents.highlightElement(passwordtextFieldWebElement);
			commonEvents.sendKeys(getPasswordTextboxLocator(), expectedData.get("password"));

			WebElement signinButtonWebElement = commonEvents.findElement(getPasswordTextboxLocator());
			commonEvents.highlightElement(signinButtonWebElement);
			commonEvents.click(getSignInButtonLocator());
			textIsDisplayed = true;
		} catch (Exception e) {
			throw e;
		}
		return textIsDisplayed;
	}

	/**
	 * @Test1.2 about this method scrollDownAndClickRadiologyTab()
	 * 
	 * @param : null
	 * @description : verify the radiology tab, scroll to it, and click it
	 * @return : String
	 * @author : YAKSHA
	 */
	public void scrollDownAndClickRadiologyTab() throws Exception {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement radiologyTab = commonEvents.findElement(getRadiologyLocator());
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", radiologyTab);
			jsExecutor.executeScript("window.scrollBy(0, -50)");
			commonEvents.highlight(radiologyTab);
			commonEvents.click(radiologyTab);

			// Wait for the URL to contain "Verification/Inventory"
			commonEvents.waitForUrlContains("Radiology/ImagingRequisitionList", 10);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test1.3 about this method verifyRadiologyPageUrl()
	 * 
	 * @param : null
	 * @description : verify radiology page url
	 * @return : String
	 * @author : YAKSHA
	 */
	public String verifyRadiologyPageUrl() throws Exception {
		try {
			String titleToVerify = commonEvents.getCurrentUrl();
			return titleToVerify;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test2 @Test4.2 @Test4.3 @Test4.4 @Test4.5 @Test4.6
	 * about this method highlightAndVerifyWhetherElementIsDisplayed
	 * 
	 * @param element : By - Locator of the element to be highlighted and verified
	 * @description : This method verifies whether an element is displayed on the
	 *              page, highlights it if displayed, and returns true if displayed.
	 * @return : boolean - true if the element is displayed, otherwise false
	 * @author : YAKSHA
	 */
	public boolean highlightAndVerifyWhetherElementIsDisplayed(By element) {
		boolean isElementDisplayed = false;
		try {
			if (commonEvents.isDisplayed(element)) {
				WebElement elementToHighlight = commonEvents.findElement(element);
				commonEvents.highlight(elementToHighlight);
				isElementDisplayed = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isElementDisplayed;
	}

	/**
	 * @Test3 about this method verifyUrlRadiologyModule()
	 * 
	 * @description This method verifies that the "List Requests" tab is present in
	 *              the Radiology module and returns the current URL of the page.
	 * @return String - The current URL of the page after verifying the "List
	 *         Requests" tab.
	 * @throws Exception - If there is an issue locating the "List Requests" tab or
	 *                   verifying its text.
	 * @autor YAKSHA
	 */
	public String verifyUrlRadiologyModule() throws Exception {
		try {
			// Locate the "List Requests" tab element
			WebElement listRequesttab = commonEvents.findElement(getPageBarFixedLocator("List Requests"));
			System.out.println("List req tab > " + listRequesttab.getText().trim());

			// Verify that the "List Requests" tab text matches the expected value
			Assert.assertEquals(listRequesttab.getText().trim(), "List Requests");

			// Get the current URL of the page
			String verifyListRequestUrl = commonEvents.getCurrentUrl();
			System.out.println("verifyListRequestUrl: " + verifyListRequestUrl);

			return verifyListRequestUrl;

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test4.1 about this method applyDateFilter()
	 * 
	 * @param : String, String
	 * @description : Applies the date filter with date range
	 * @return : void
	 * @throws : Exception - if there is an issue finding or filling the date fields
	 * @author : YAKSHA
	 */
	
	public void clickButtonByText(String buttonText){
			WebElement buttonToClick = commonEvents
					.findElement(By.xpath("//button[contains(text(),'" + buttonText + "')]"));
			commonEvents.highlight(buttonToClick).click(buttonToClick);
		
	}
	public boolean applyDateFilter(String fromDate, String toDate) throws Exception {
		try {
			String fromDay, fromMonth, fromYear, toDay, toMonth, toYear;
			fromDay = fromDate.split("-")[0];
			fromMonth = fromDate.split("-")[1];
			fromYear = fromDate.split("-")[2];
			toDay = toDate.split("-")[0];
			toMonth = toDate.split("-")[1];
			toYear = toDate.split("-")[2];
			WebElement fromDateDropdown = commonEvents.findElement(calendarFromDropdown());
			WebElement toDateDropdown = commonEvents.findElement(calendarToDropdown());
			commonEvents.highlight(fromDateDropdown).sendKeys(fromDateDropdown, fromDay)
					.sendKeys(fromDateDropdown, fromMonth).sendKeys(fromDateDropdown, fromYear);
			commonEvents.highlight(toDateDropdown).sendKeys(toDateDropdown, toDay).sendKeys(toDateDropdown, toMonth)
					.sendKeys(toDateDropdown, toYear);
			clickButtonByText("OK");
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	/**
	 * @Test4.7 about this method verifySearchBarIsDisplayed()
	 * 
	 * @param : null
	 * @description : This method verifies if the search bar is visible
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the search bar or it is
	 *           not visible
	 * @author : YAKSHA
	 */
	public boolean verifySearchBarIsDisplayed() {
		return highlightAndVerifyWhetherElementIsDisplayed(searchBarId());
	}

	/**
	 * @Test4.8 about this method verifyDateRangeButtonIsDisplayed()
	 * 
	 * @param : null
	 * @description : This method verifies if the date range button is visible
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the date range button or
	 *           it is not visible
	 * @author : YAKSHA
	 */
	public boolean verifyDateRangeButtonIsDisplayed() {
		return highlightAndVerifyWhetherElementIsDisplayed(getDateRangeButton());
	}

	/**
	 * @Test4.9 about this method verifyFilterDropdownIsDisplayed()
	 * 
	 * @param : null
	 * @description : This method verifies if the filter dropdown is visible
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the filter dropdown or it
	 *           is not visible
	 * @author : YAKSHA
	 */
	public boolean verifyFilterDropdownIsDisplayed() {
		return highlightAndVerifyWhetherElementIsDisplayed(getFilterDropdownLocator());
	}

	/**
	 * @Test4.10 about this method verifyFromDateFieldIsDisplayed()
	 * 
	 * @param : null
	 * @description : This method verifies if the "from date" field is visible
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the "from date" field or
	 *           it is not visible
	 * @author : YAKSHA
	 */
	public boolean verifyFromDateFieldIsDisplayed() {
		return highlightAndVerifyWhetherElementIsDisplayed(calendarFromDropdown());
	}
	/**
	 * @Test4.11 about this method verifyToDateFieldIsDisplayed()
	 * 
	 * @param : null
	 * @description : This method verifies if the "to date" field is visible
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the "to date" field or it
	 *           is not visible
	 * @author : YAKSHA
	 */
	public boolean verifyToDateFieldIsDisplayed() {
		return highlightAndVerifyWhetherElementIsDisplayed(calendarToDropdown());
	}

	/**
	 * @Test4.12 about this method verifyStarIconIsDisplayed()
	 * 
	 * @param : null
	 * @description : This method verifies if the start icon is visible
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the start icon or it is
	 *           not visible
	 * @author : YAKSHA
	 */
	public boolean verifyStarIconIsDisplayed() {
		return highlightAndVerifyWhetherElementIsDisplayed(getStarIconLocator());
	}

	/**
	 * @Test5 about this method
	 * verifySelectedTabIsActiveOrNot()
	 * 
	 * @param : element - the locator of the tab to be verified
	 * @description : This method verifies if the specified tab is displayed, clicks
	 *              it, and returns whether its "class" attribute contains "active".
	 *              This can be used to determine if the tab is active based on its
	 *              class attributes.
	 * @return : boolean - true if the "class" attribute of the tab contains
	 *         "active", false otherwise
	 * @throws : Exception - if there is an issue locating, highlighting, clicking
	 *           the tab, or getting its attribute
	 * @author : YAKSHA
	 */
	public boolean verifySelectedTabIsActiveOrNot(By element) throws Exception {
		boolean isActive = false;
		try {
			if (commonEvents.isDisplayed(element)) {
				WebElement tabs = commonEvents.findElement(element);
				commonEvents.highlight(tabs);
				commonEvents.click(tabs);
				String locatorAttributeValue = commonEvents.getAttribute(tabs, "class");
				isActive = locatorAttributeValue.contains("active");
			}
		} catch (Exception e) {
			throw e;
		}
		return isActive;
	}

	/**
	 * @Test6 about this method performScrollOperation()
	 * @param : null
	 * @description : Scrolls till Radiology tab, selects it, and clicks "List
	 *              Requests" tab
	 * @return : boolean
	 * @throws : Exception - if there is an issue finding the tab
	 * @author : YAKSHA
	 */
	public boolean performScrollOperation() {
		boolean isNextButtonDisplayed = false;
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement nextButton = commonEvents.findElement(getButtonLocatorsBytext("Next"));
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", nextButton);
			jsExecutor.executeScript("window.scrollBy(0, -50)");
			commonEvents.highlight(nextButton);
			isNextButtonDisplayed = commonEvents.isDisplayed(nextButton);
			System.out.println("isNextButtonDisplayed >> " + isNextButtonDisplayed);
			isNextButtonDisplayed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNextButtonDisplayed;
	}

	/**
	 * @Test7 about this method verifyToolTipText()
	 * @param : null
	 * @description : Verify the text of the tooltip
	 * @return : String
	 * @throws : Exception - if there is an issue finding the text
	 * @author : YAKSHA
	 */
	public String verifyToolTipText() {
		String toolTipValue = "";
		try {
			WebElement toolTip = commonEvents.findElement(favouriteOrStarIcon());
			toolTipValue = commonEvents.highlight(toolTip).getAttribute(toolTip, "title");
			System.out.println("Tool tip title : " + toolTipValue);
		} catch (Exception e) {
			throw e;
		}
		return toolTipValue;
	}

	/**
	 * @Test8 about this method verifyDatesAreRememberedCorrectly()
	 * 
	 * @param fromDate - the expected "from" date in the format "dd-MM-yyyy"
	 * 
	 * @param toDate - the expected "to" date in the format "dd-MM-yyyy"
	 * 
	 * @description : This method selects the "from" and "to" dates in the calendar,
	 * clicks the OK button, navigates away to another tab, returns to the original
	 * tab, and verifies if the dates are remembered correctly.
	 * 
	 * @return : boolean - true if the dates are remembered correctly, false
	 * otherwise
	 * 
	 * @throws : Exception - if there is an issue locating, highlighting, or
	 * clicking elements, or if there is an issue with date selection or
	 * verification
	 * 
	 * @author : YAKSHA
	 * 
	 * @throws Exception
	 */
	public boolean verifyDatesAreRememberedCorrectly(String fromDate, String toDate) throws Exception {
		try {
			// Split the fromDate and toDate into day, month, and year components
			String[] fromDateComponents = fromDate.split("-");
			String fromDay = fromDateComponents[0];
			String fromMonth = fromDateComponents[1];
			String fromYear = fromDateComponents[2];

			String[] toDateComponents = toDate.split("-");
			String toDay = toDateComponents[0];
			String toMonth = toDateComponents[1];
			String toYear = toDateComponents[2];

			// Locate the date dropdowns and OK button
			WebElement fromDateDropdown = commonEvents.findElement(calendarFromDropdown());
			WebElement toDateDropdown = commonEvents.findElement(calendarToDropdown());
			WebElement okButton = commonEvents.findElement(getOkButtonLocator());

			// Highlight and set the "from" date
			commonEvents.highlight(fromDateDropdown).sendKeys(fromDateDropdown, fromDay)
					.sendKeys(fromDateDropdown, fromMonth).sendKeys(fromDateDropdown, fromYear);

			// Highlight and set the "to" date
			commonEvents.highlight(toDateDropdown).sendKeys(toDateDropdown, toDay).sendKeys(toDateDropdown, toMonth)
					.sendKeys(toDateDropdown, toYear);

			// Locate and click the tooltip
			WebElement toolTip = commonEvents.findElement(getStarIconLocator());
			commonEvents.click(toolTip);

			// Highlight and click the OK button
			commonEvents.highlight(okButton).click(okButton);

			// Navigate to "List Reports" and "List Requests"
			commonEvents.click(getPageBarFixedLocator("List Reports"));
			commonEvents.click(getPageBarFixedLocator("List Requests"));

			// Wait for the OK button to be visible
			commonEvents.waitTillElementVisible(getOkButtonLocator(), 10000);

			// Construct the actual dates from the selected components
			String actualFromDate = fromDay + "-" + fromMonth + "-" + fromYear;
			String actualToDate = toDay + "-" + toMonth + "-" + toYear;

			System.out.println("Actual from date : " + actualFromDate);
			System.out.println("Actual to date : " + actualToDate);

			// Verify if the remembered dates match the expected dates
			if (actualFromDate.equals(fromDate) && actualToDate.equals(toDate)) {
				System.out.println("Returned true");
				return true;
			}

		} catch (Exception e) {
			throw e;
		}
		return false;
	}


}
