package ru.levelp.at.homework5;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PageLoginInMail extends DriverHelper {

    private static final String MAIL_URL = "https://mail.ru/";

    @FindBy(css = "[data-testid='enter-mail-primary']")
    private WebElement loginButton;

    @FindBy(css = "iframe[class='ag-popup__frame__layout__iframe']")
    private WebElement switchToFrame;

    @FindBy(css = "[name='username']")
    private WebElement enterLogin;

    @FindBy(css = "[name='password']")
    private WebElement enterPassword;

    public PageLoginInMail(WebDriver driver) {
        super(driver);
    }

    public void openSite() {
        driver.navigate().to(MAIL_URL);
    }

    public void pushLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOf(switchToFrame));
        driver.switchTo().frame(switchToFrame); //нашли фрейм, в котором открылась авторизация и переключились на него
    }

    public void enterLogin(String login) {
        wait.until(ExpectedConditions.visibilityOf(enterLogin)).sendKeys(login);
        wait.until(ExpectedConditions.visibilityOf(enterLogin)).sendKeys(Keys.ENTER);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(enterPassword)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(enterPassword)).sendKeys(Keys.ENTER);
    }
}
