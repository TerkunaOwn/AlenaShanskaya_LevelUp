package ru.levelp.at.levelp.at.homework3;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LocatorsBaseTest {

    protected WebDriver driver;
    protected final String to = "ashanskaya_spam@mail.ru";
    protected final String bodyText = "My beautiful letter text";
    protected final String userLogin = "ashanskaya_spam@mail.ru";
    protected final String userPassword = "Pavel1508";
    protected final String buttonCompose = "span[class='compose-button__wrapper']";
    protected final String buttonDraft = "a[data-folder-link-id='500001']";
    protected static final String pageTitleDraft = "Черновики - Почта Mail.ru";
    protected final String buttonSent = "a[data-folder-link-id='500000']";
    protected static final String pageTitleSent = "Отправленные - Почта Mail.ru";
    protected final String buttonMySelf = "a[data-folder-link-id='500015']";
    protected final String urlPageToMySelf = ("https://e.mail.ru/tomyself/");
    protected final String buttonTrash = "a[data-folder-link-id='500002']";
    protected final String urlPageTrash = "https://e.mail.ru/trash/";
    protected final String buttonTest = "a[data-folder-link-id='1']";
    protected  final String urlPageTest = "https://e.mail.ru/1/";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup(); //метод узнает версию нашего браузера и скачивает его и устанавливает
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); //разворачиваем на весь экран
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
