package ru.levelp.at.levelp.at.homework7.step;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework5.PageLoginInMail;

public class MailAgentLogin {
    protected final WebDriver driver;

    protected PageLoginInMail pageLoginInMail;

    public MailAgentLogin(WebDriver driver) {
        this.driver = driver;
        pageLoginInMail = new PageLoginInMail(driver);
    }

    @Step("Авторизация в почте Mail.ru")
    public void loginToMailAgent(String login, String password) {

        pageLoginInMail.openSite();
        pageLoginInMail.pushLoginButton();
        pageLoginInMail.enterLogin(login);
        pageLoginInMail.enterPassword(password);
        driver.navigate().refresh();
    }
}
