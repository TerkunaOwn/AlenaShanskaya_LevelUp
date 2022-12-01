package ru.levelp.at.levelp.at.homework3;

import com.google.common.base.Verify;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskTwoUsingFilterToSendEmailTest extends LocatorsBaseTest{
    String bodyText = "My beautiful letter text";
    String subject = "My beautiful theme to test the word Test";
    String to = "ashanskaya_spam@mail.ru";

    @BeforeEach
    void setUp(){
       super.setUp();
    }

    @Test
    void testUsingFilterToSendEmail() {

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(5000));

        driver.navigate().to("https://mail.ru/");

//---------------------1. Войти в почту

        driver.findElement(By.cssSelector("[data-testid='enter-mail-primary']")).click(); //нажатие на КНОПКУ "ВОЙТИ"

        WebElement frame = driver.findElement(By.cssSelector("iframe.ag-popup__frame__layout__iframe")); //нашли фрейм, в котором открылась авторизация
        WebDriver frameDriver = driver.switchTo().frame(frame); //переключились на него
        frameDriver.findElement(By.cssSelector("[name='username']")).sendKeys("ashanskaya_spam@mail.ru"); //вводим адресс почты уже во фрейме
        var enteredAccount = "ashanskaya_spam@mail.ru";

        frameDriver.findElement(By.cssSelector("[name='username']")).sendKeys(Keys.ENTER); //имитируем нажатие клавиши ENTER
       // frameDriver.findElement(By.cssSelector("[data-test-id='next-button']")).click(); //клик на КНОПКУ "Ввести пароль"

        frameDriver.findElement(By.cssSelector("[name='password']")).sendKeys("Pavel1508"); //вводим пароль

        frameDriver.findElement(By.cssSelector("[name='password']")).sendKeys(Keys.ENTER); //имитируем нажатие клавиши ENTER
      // frameDriver.findElement(By.cssSelector("[data-test-id='submit-button']")).click(); //клик на КНОПКУ "ВОЙТИ"

//---------------------2.Assert, что вход выполнен успешно

        driver.navigate().refresh();

        assertThat(enteredAccount).isEqualTo(driver.findElement(By.cssSelector("div.ph-project__account")).getAttribute("aria-label")); //сравниваю адреса почты

//--------------------- 2.1.Заходим в отправленные и проверяем колличество писем ДО создания нового

        driver.navigate().to("https://e.mail.ru/sent/");

        int sendLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

//-------------------- 2.2. Заходим в папку Тест и проверяем колличество писем ДО создания нового

        driver.navigate().to("https://e.mail.ru/1/");

        int packageLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

//---------------------3.Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)

        driver.navigate().to("https://e.mail.ru/compose/");
        //driver.findElement(By.cssSelector(".compose-button__wrapper")).click(); //нажали на кнопку "Написать письмо"

        driver.findElement(By.cssSelector(".container--H9L5q")).click();
        driver.findElement(By.cssSelector("[type='text']")).sendKeys(to);

        driver.findElement(By.cssSelector("[name='Subject']")).click();
        driver.findElement(By.cssSelector("[name='Subject']")).sendKeys(subject);

        driver.findElement(By.cssSelector("div.cke_editable")).sendKeys(bodyText);

//---------------------4.Отправить письмо

        driver.findElement(By.cssSelector("[data-test-id='send']")).click(); //нажатие на КНОПКУ "Отправить"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class='button2 button2_has-ico button2_has-ico-s button2_close button2_pure button2_short button2_hover-support']")));
        driver.findElement(By.cssSelector("span[class='button2 button2_has-ico button2_has-ico-s button2_close button2_pure button2_short button2_hover-support']")).click();

//---------------------5. Verify, что письмо появилось в папке отправленные

        driver.navigate().to("https://e.mail.ru/sent/");
        wait.until(ExpectedConditions.urlToBe("https://e.mail.ru/sent/"));
        driver.navigate().refresh();

        List<WebElement> sendtLetter = driver.findElements(By.cssSelector("a.js-letter-list-item"));
        int sendLetterCountAfter = sendtLetter.size();

        Verify.verify(sendLetterCountBefore+1 == sendLetterCountAfter);

        WebElement letterElement = sendtLetter.get(0);
        String hrefLastLetter = letterElement.getAttribute("href");

//---------------------6. Verify, что письмо появилось в папке Тест

        driver.navigate().to("https://e.mail.ru/1/");
        wait.until(ExpectedConditions.urlToBe("https://e.mail.ru/1/"));

        int packageLetterCountAfter = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();
        Verify.verify(packageLetterCountBefore+1 == packageLetterCountAfter);

//---------------------7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        driver.navigate().to(hrefLastLetter);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class='button2 button2_has-ico button2_has-ico-s button2_reply button2_pure button2_hover-support js-shortcut']")));

        String emailAddresLeter = driver.findElement(By.cssSelector("span.letter-contact")).getAttribute("title");

        Verify.verify(emailAddresLeter.equals(to));

        String subjectMail = driver.findElement(By.cssSelector("div[class='thread__subject-line'] h2.thread-subject")).getText();

        Verify.verify(subjectMail.equals("Self: " + subject));

        String emailValue = driver.findElement(By.cssSelector("div.js-readmsg-msg > div > div > div > div")).getText();
        Verify.verify(emailValue.equals(bodyText));

//---------------------8. Выйти из учётной записи

        driver.findElement(By.cssSelector("span[class*='ph-dropdown-icon']")).click();

        driver.findElement(By.cssSelector("div[class*='ph-text'] > div")).click();
        driver.findElement(By.cssSelector("div[data-testid*='whiteline-account-exit'] > div.ph-text")).click();
       }

    @AfterEach
    void tearDawn() {
        driver.quit();
    }
}

