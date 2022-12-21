package ru.levelp.at.levelp.at.homework3;

import com.google.common.base.Verify;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.levelp.at.homework3.TimeOut;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskOneMailTest extends LocatorsBaseTest {
    String subject = "My beautiful theme";

    @Test
    void testSendMail() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));
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

//-------------------- 2.1. Заходим в черновики и проверяем колличество писем ДО создания нового

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ph-project-promo-close-icon"))).click();//закрыть всплывашку

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_DRAFT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));

        int draftLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

//--------------------- 2.2.Заходим в отправленные и проверяем колличество писем ДО создания нового

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));

        int sendLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();

//---------------------3.Создать новое письмо (заполнить адресата, тему письма и тело)

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_COMPOSE))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[type='text']"))).sendKeys(to);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='Subject']"))).sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cke_editable"))).sendKeys(bodyText);

//---------------------4. Сохранить его как черновик

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='save']"))).click(); //сохраняем письмо в черновике
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[tabindex='700']"))).click();
        TimeOut.sleep(1000); //я не знаю как отловить этот момент

//---------------------5. Verify, что письмо сохранено в черновиках

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_DRAFT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        driver.navigate().refresh();

        List<WebElement> draftLetter = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.js-letter-list-item")));
        int draftLetterCountAfter = draftLetter.size();
        Verify.verify(draftLetterCountBefore+1==draftLetterCountAfter);

        WebElement letterElement = draftLetter.get(0);
        String hrefLastLetter = letterElement.getAttribute("href");

//---------------------6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        driver.navigate().to(hrefLastLetter);
        driver.navigate().refresh();

        String emailAddresLeter = driver.findElement(By.cssSelector("span.letter-contact")).getAttribute("title");
        Verify.verify(emailAddresLeter.equals(to));

        String subjectMail = driver.findElement(By.cssSelector("h2.thread-subject")).getText();
        Verify.verify(subjectMail.equals(subject));

        String emailValue = driver.findElement(By.cssSelector("div.js-readmsg-msg > div > div > div > div")).getText();
        Verify.verify(emailValue.equals(bodyText));

//---------------------7. Отправить письмо

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_DRAFT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        driver.navigate().refresh();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.js-letter-list-item"))).get(0).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id='send']"))).click(); //нажимаем кнопку Отправить
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[class*='button2_close']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class*='button2_close']"))).click();

//---------------------8. Verify, что письмо исчезло из черновиков

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_DRAFT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        driver.navigate().refresh();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), draftLetterCountAfter-1));

//---------------------9. Verify, что письмо появилось в папке отправленные

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), sendLetterCountBefore + 1));

//---------------------10. Выйти из учётной записи

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class*='ph-dropdown-icon']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='whiteline-account-exit']"))).click();
    }
}

