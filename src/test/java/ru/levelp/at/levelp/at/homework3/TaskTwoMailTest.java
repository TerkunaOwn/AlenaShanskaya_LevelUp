package ru.levelp.at.levelp.at.homework3;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Verify;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TaskTwoMailTest extends LocatorsBaseTest {
    String subject = "My beautiful theme to test the word Test";

    @Test
    void testUsingFilterToSendEmail() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));

        driver.navigate().to("https://mail.ru/");

        // 1. Войти в почту

        wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("[data-testid='enter-mail-primary']"))).click(); //нажатие на КНОПКУ "ВОЙТИ"

        WebElement frame = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("iframe.ag-popup__frame__layout__iframe"))); //нашли фрейм, в котором открылась авторизация
        driver.switchTo().frame(frame); //переключились на него

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='username']")))
            .sendKeys(userLogin); //вводим адресс почты уже во фрейме
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='username']")))
            .sendKeys(Keys.ENTER); //имитируем нажатие клавиши ENTER

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='password']")))
            .sendKeys(userPassword); //вводим пароль
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='password']")))
            .sendKeys(Keys.ENTER); //имитируем нажатие клавиши ENTER

        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ph-project__account")));

        // 2.Assert, что вход выполнен успешно

        assertThat(userLogin).isEqualTo(driver.findElement(
            By.cssSelector("div.ph-project__account")).getAttribute("aria-label")); //сравниваю адреса почты
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".ph-project-promo-close-icon"))).click(); //закрыть всплывашку

        // 2.1.Заходим в отправленные и проверяем колличество писем ДО создания нового

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(buttonSent))).click();
        wait.until(ExpectedConditions.titleIs(pageTitleSent));

        final int sendLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

        // 2.2. Заходим в папку Тест и проверяем колличество писем ДО создания нового

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(buttonTest))).click();
        wait.until(ExpectedConditions.urlToBe(urlPageTest));

        final int packageLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

        // 3.Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(buttonCompose))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[type='text']"))).sendKeys(to);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[name='Subject']"))).sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div.cke_editable"))).sendKeys(bodyText);

        // 4.Отправить письмо

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-test-id='send']"))).click(); //нажатие на КНОПКУ "Отправить"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class*='button2_close']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("span[class*='button2_close']"))).click();

        // 5. Verify, что письмо появилось в папке отправленные

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(buttonSent))).click();
        wait.until(ExpectedConditions.titleIs(pageTitleSent));
        wait.until(ExpectedConditions.numberOfElementsToBe(
            By.cssSelector("a.js-letter-list-item"), sendLetterCountBefore + 1));

        // 6. Verify, что письмо появилось в папке Тест

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(buttonTest))).click();
        wait.until(ExpectedConditions.urlToBe(urlPageTest));

        wait.until(ExpectedConditions.numberOfElementsToBe(
            By.cssSelector("a.js-letter-list-item"), packageLetterCountBefore + 1));

        // 7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("a.js-letter-list-item" + ":nth-of-type(1)"))).click();

        String emailAddresLeter = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("span.letter-contact"))).getAttribute("title");
        Verify.verify(emailAddresLeter.equals(to));

        String subjectMail = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div[class='thread__subject-line'] h2.thread-subject"))).getText();
        Verify.verify(subjectMail.equals(subject));

        String emailValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div.js-readmsg-msg > div > div > div > div"))).getText();
        Verify.verify(emailValue.equals(bodyText));

        // 8. Выйти из учётной записи

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("span[class*='ph-dropdown-icon']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }
}

