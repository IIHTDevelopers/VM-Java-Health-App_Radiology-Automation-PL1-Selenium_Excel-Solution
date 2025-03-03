package testcases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;
import pages.StartupPage;
import pages.radiology_page;
import testBase.AppTestBase;
import testBase.UserActions;
import testdata.LocatorsFactory;

public class radiology_testcase extends AppTestBase {
	Map<String, String> configData;
	Map<String, String> loginCredentials;
	String expectedDataFilePath = testDataFilePath + "expected_data.xlsx";
	String loginFilePath = loginDataFilePath + "Login.xlsx";
	StartupPage startupPage;
	String randomInvoiceNumber;
	LocatorsFactory locatorsFactoryInstance;
	UserActions userActionsInstance;
	radiology_page radiology_pageInstance;

	@Parameters({ "browser", "environment" })
	@BeforeClass(alwaysRun = true)
	public void initBrowser(String browser, String environment) throws Exception {
		configData = new FileOperations().readExcelPOI(config_filePath, environment);
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", browser);
		boolean isValidUrl = new ApiHelper().isValidUrl(configData.get("url"));
		Assert.assertTrue(isValidUrl,
				configData.get("url") + " might be Server down at this moment. Please try after sometime.");
		initialize(configData);
		startupPage = new StartupPage(driver);
	}

	@Test(priority = 1, groups = { "sanity" }, description = "1. Login in the healthapp application\r\n"
			+ "2. Scroll down menu till radiology\r\n" + "3. Click on the radiology")
	public void verifyRadiologyModule() throws Exception {
		radiology_pageInstance = new radiology_page(driver);
		Map<String, String> radiologyExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath,
				"radiology");
		Map<String, String> loginData = new FileOperations().readExcelPOI(loginFilePath, "credentials");
		Assert.assertTrue(radiology_pageInstance.loginToHealthAppByGivenValidCredetial(loginData),
				"Login failed, Invalid credentials ! Please check manually");
		radiology_pageInstance.scrollDownAndClickRadiologyTab();
		System.out.println("Verification Page url : " + radiologyExpectedData.get("URL"));
		Assert.assertEquals(radiology_pageInstance.verifyRadiologyPageUrl(), radiologyExpectedData.get("URL"));
	}

	@Test(priority = 2, groups = { "sanity" }, description = "Pre condition: User should be logged in \r\n"
			+ "1. Click on the radiology Module drop-down arrow")
	public void verifyRadiologySubModules() throws Exception {
		radiology_pageInstance = new radiology_page(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);
		Assert.assertTrue(radiology_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getPageBarFixedLocator("List Requests")));
		Assert.assertTrue(radiology_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getPageBarFixedLocator("List Reports")));
		Assert.assertTrue(radiology_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getPageBarFixedLocator("Edit Doctors")));
		Assert.assertTrue(radiology_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getPageBarFixedLocator("Ward Billing")));
	}

	@Test(priority = 3, groups = { "sanity" }, description = "1. Login in the healthapp application\r\n"
			+ "2. Scroll down menu till radiology\r\n" + "3. Click on the radiology and verify list request module\r\n")

	public void verifyUrlOfTheRadiologyModule() throws Exception {
		radiology_pageInstance = new radiology_page(driver);
		Map<String, String> radiologyExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath,
				"radiology");

		Assert.assertEquals(radiology_pageInstance.verifyUrlRadiologyModule(), radiologyExpectedData.get("URL"));
	}

	@Test(priority = 4, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on radiology module\r\n"
					+ "1. Click on the radiology module drop-down arrow \r\n" + "2. Click on list request section")
	public void verifyListRequestsComponents() throws Exception {
		radiology_pageInstance = new radiology_page(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String toDate = currentDate.format(formatter);
		Thread.sleep(3000); // Waiting for data to load
		radiology_pageInstance.applyDateFilter("01-01-2023", toDate);
		Assert.assertTrue(radiology_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.getButtonLocatorsBytext("OK")));
		Assert.assertTrue(radiology_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.getButtonLocatorsBytext("First")));
		Assert.assertTrue(radiology_pageInstance.highlightAndVerifyWhetherElementIsDisplayed(
				locatorsFactoryInstance.getButtonLocatorsBytext("Previous")));
		Assert.assertTrue(radiology_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.getButtonLocatorsBytext("Next")));
		Assert.assertTrue(radiology_pageInstance
				.highlightAndVerifyWhetherElementIsDisplayed(locatorsFactoryInstance.getButtonLocatorsBytext("Last")));
		Assert.assertTrue(radiology_pageInstance.verifySearchBarIsDisplayed());
		Assert.assertTrue(radiology_pageInstance.verifyDateRangeButtonIsDisplayed());
		Assert.assertTrue(radiology_pageInstance.verifyFilterDropdownIsDisplayed());
		Assert.assertTrue(radiology_pageInstance.verifyFromDateFieldIsDisplayed());
		Assert.assertTrue(radiology_pageInstance.verifyToDateFieldIsDisplayed());
		Assert.assertTrue(radiology_pageInstance.verifyStarIconIsDisplayed());
	}

	@Test(priority = 5, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on radiology module\r\n"
					+ "1. Click on the list requests \r\n" + "2. Click on the edit doctors\r\n"
					+ "3. Click on Ward Billing\r\n" + "4. Click on List Requests")
	public void verifyNavigationToAnotherSubModuleAfterOpeningTheListRequestsSection() throws Exception {
		radiology_pageInstance = new radiology_page(driver);
		locatorsFactoryInstance = new LocatorsFactory(driver);
		Assert.assertTrue(
				radiology_pageInstance
						.verifySelectedTabIsActiveOrNot(locatorsFactoryInstance.getPageBarFixedLocator("List Reports")),
				"List Reports page is not active");
		Assert.assertTrue(
				radiology_pageInstance
						.verifySelectedTabIsActiveOrNot(locatorsFactoryInstance.getPageBarFixedLocator("Edit Doctors")),
				"Edit Doctors page is not active");
		Assert.assertTrue(
				radiology_pageInstance
						.verifySelectedTabIsActiveOrNot(locatorsFactoryInstance.getPageBarFixedLocator("Ward Billing")),
				"Ward Billing page is not active");
		Assert.assertTrue(
				radiology_pageInstance
						.verifySelectedTabIsActiveOrNot(locatorsFactoryInstance.getPageBarFixedLocator("List Requests")),
				"List Requests page is not active");
	}

	@Test(priority = 6, groups = { "sanity" }, description = "Pre condition: User should be logged in\r\n"
			+ "1.  Select \"All\" drop down option from the \"Filter\" field \r\n"
			+ "2.  Scroll all the way to the bottom of the page")
	public void performScrollOperation() throws Exception {
		radiology_pageInstance = new radiology_page(driver);

		Assert.assertTrue(radiology_pageInstance.performScrollOperation(), "Scroll operation is performed");
		;
	}

	@Test(priority = 7, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on Radiology > List Request section \r\n"
					+ "1. Hover the mouse over the star/favourite icon. \r\n"
					+ "2. Verify that a tooltip with the text \"Remember this date\" appears when hovering over the star.")
	public void verifyToolTipText() throws Exception {
		radiology_pageInstance = new radiology_page(driver);

		Map<String, String> radiologyExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath,
				"radiology");
		Assert.assertEquals(radiology_pageInstance.verifyToolTipText(), radiologyExpectedData.get("favouriteIcon"));
	}

	@Test(priority = 8, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on Radiology > List Requests section\r\n"
					+ "1. Navigate to the Radiology module.\r\n" + "2. Click on the \"List Requests\" section.\r\n"
					+ "3. Click on \"FROM\" and select \"Jan 2020\"\r\n"
					+ "4. Click on \"TO\" and selct \"march 2024\"\r\n" + "5. Click on \"star\" tooltip\r\n"
					+ "6. Click on \"OK\" button\r\n"
					+ "7. Navigate back to the \"List Requests\" section of the Radiology module.")
	public void verifyDatesAreRemeberedCorrectly() throws Exception {
		radiology_pageInstance = new radiology_page(driver);

		LocalDate currentDate = LocalDate.now();
		LocalDate date7DaysAgo = currentDate.minusDays(50);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String toDate = currentDate.format(formatter);
		String fromDate = date7DaysAgo.format(formatter);

		System.out.println("From Date : " + fromDate + ", To Date : " + toDate);
		Assert.assertTrue(radiology_pageInstance.verifyDatesAreRememberedCorrectly(fromDate, toDate));
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("before closing the browser");
		browserTearDown();
	}

	@AfterMethod
	public void retryIfTestFails() throws Exception {
		startupPage.navigateToUrl(configData.get("url"));
	}
}
