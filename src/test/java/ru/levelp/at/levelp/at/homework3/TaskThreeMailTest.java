package ru.levelp.at.levelp.at.homework3;

import com.google.common.base.Verify;
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


public class TaskThreeMailTest extends LocatorsBaseTest{
    String subject = "My beautiful theme";

    @Test
    void testDeleteSendMail() {

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(5000));

        driver.navigate().to("https://mail.ru/");

//---------------------1. Войти в почту

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='enter-mail-primary']"))).click(); //нажатие на КНОПКУ "ВОЙТИ"

        WebElement frame = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("iframe.ag-popup__frame__layout__iframe"))); //нашли фрейм, в котором открылась авторизация
        driver.switchTo().frame(frame); //переключились на него

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='username']"))).sendKeys(USER_LOGIN); //вводим адресс почты уже во фрейме
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='username']"))).sendKeys(Keys.ENTER); //имитируем нажатие клавиши ENTER

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='password']"))).sendKeys(USER_PASSWORD); //вводим пароль
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name='password']"))).sendKeys(Keys.ENTER); //имитируем нажатие клавиши ENTER

        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ph-project__account")));

//---------------------2.Assert, что вход выполнен успешно

        assertThat(USER_LOGIN).isEqualTo(driver.findElement(By.cssSelector("div.ph-project__account")).getAttribute("aria-label")); //сравниваю адреса почты

//--------------------- 2.1.Заходим в Входящие и проверяем колличество писем ДО отправки нового

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon"))).click();//закрыть всплывашку

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_TOMYSELF))).click();
        wait.until(ExpectedConditions.urlToBe(URL_PAGE_TOMYSELF));

        int incomingLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

//-------------------- 2.2. Заходим в папку Корзина и проверяем колличество писем ДО удаления нового

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_TRASH))).click();
        wait.until(ExpectedConditions.urlToBe(URL_PAGE_TRASH));

        int deleteLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

//---------------------3. Создать новое письмо (заполнить адресата (самого себя), тему письма и тело)

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_COMPOSE))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='text']"))).sendKeys(to);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='Subject']"))).sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cke_editable"))).sendKeys(bodyText);

//---------------------4.Отправить письмо

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click(); //нажатие на КНОПКУ "Отправить"
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class*='button2_close']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class*='button2_close']"))).click();

//---------------------5. Verify, что письмо появилось в папке Входящие

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_TOMYSELF))).click();
        wait.until(ExpectedConditions.urlToBe(URL_PAGE_TOMYSELF));
        driver.navigate().refresh();

        List<WebElement> incomingLetter = driver.findElements(By.cssSelector("a.js-letter-list-item"));
        int incomingLetterCountAfter = incomingLetter.size();
        Verify.verify(incomingLetterCountBefore+1==incomingLetterCountAfter);

        WebElement letterElement = incomingLetter.get(0);
        String hrefLastLetter = letterElement.getAttribute("href");

//---------------------6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        driver.navigate().to(hrefLastLetter);
        driver.navigate().refresh();

        String emailAddresLeter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".letter__recipients > .letter-contact"))).getAttribute("title");
        Verify.verify(emailAddresLeter.equals(to));

        String subjectMail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.thread-subject"))).getText();
        Verify.verify(subjectMail.equals(subject));

        String emailValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.js-readmsg-msg > div > div > div > div"))).getText();
        Verify.verify(emailValue.equals(bodyText));

//---------------------7. Удалить письмо

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class*='button2_delete']"))).click();

//---------------------8. Verify что письмо появилось в папке Корзина

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_TRASH))).click();
        wait.until(ExpectedConditions.urlToBe(URL_PAGE_TRASH));
        driver.navigate().refresh();

        List<WebElement> deleteLetter = driver.findElements(By.cssSelector("a.js-letter-list-item"));
        int deleteLetterCountAfter = deleteLetter.size();
        Verify.verify(deleteLetterCountBefore+1==deleteLetterCountAfter);

//---------------------9. Выйти из учётной записи

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class*='ph-dropdown-icon']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='whiteline-account-exit']"))).click();
       }
}

