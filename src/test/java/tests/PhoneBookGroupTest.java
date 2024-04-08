package tests;

import config.BaseTest;
import helpers.AlertHandler;
import helpers.RetryAnalizer;
import helpers.TopMenuItem;
import io.qameta.allure.Allure;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;

public class PhoneBookGroupTest extends BaseTest {


    @Test(groups = "group1",description = "The test checks the empty field warning declaration.", retryAnalyzer = RetryAnalizer.class)
    @Parameters("browser")
    public void registrationWithoutPassword(@Optional("chrome") String browser) throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");

        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Click by Login button");
        LoginPage loginPage = mainPage.openTopMenu(getDriver(), TopMenuItem.LOGIN.toString());
        Allure.step("Click by Reg button");
        String expectedString = "Wrong";

        Alert alert= loginPage.fillEmailField("myemail@mail.com").clickByRegistartionButton();
        boolean isAlertHandled = AlertHandler.handleAlert(alert, expectedString);
        Assert.assertTrue(isAlertHandled);
    }
}
