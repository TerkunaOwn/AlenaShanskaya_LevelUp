package ru.levelp.at.levelp.at.homework3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class LocatorsBaseTest {

    protected WebDriver driver;
    protected final String to = "ashanskaya_spam@mail.ru";
    protected final String bodyText = "My beautiful letter text";
    protected final String USER_LOGIN = "ashanskaya_spam@mail.ru";
    protected final String USER_PASSWORD = "Pavel1508";
    protected final String BUTTON_COMPOSE = "span[class='compose-button__wrapper']";
    protected final String BUTTON_DRAFT = "a[data-folder-link-id='500001']";
    protected static final String PAGE_TITLE_DRAFT = "Черновики - Почта Mail.ru";
    protected final String BUTTON_SENT = "a[data-folder-link-id='500000']";
    protected static final String PAGE_TITLE_SENT = "Отправленные - Почта Mail.ru";
    protected final String BUTTON_TOMYSELF = "a[data-folder-link-id='500015']";
    protected final String URL_PAGE_TOMYSELF = ("https://e.mail.ru/tomyself/");
    protected final String BUTTON_TRASH = "a[data-folder-link-id='500002']";
    protected final String URL_PAGE_TRASH = "https://e.mail.ru/trash/";
    protected final String BUTTON_TEST = "a[data-folder-link-id='1']";
    protected  final String URL_PAGE_TEST = "https://e.mail.ru/1/";

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
