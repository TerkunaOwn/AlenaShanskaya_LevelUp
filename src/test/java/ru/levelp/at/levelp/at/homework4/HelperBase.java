package ru.levelp.at.levelp.at.homework4;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelperBase {

    public Properties properties;
    protected WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup(); //метод узнает версию нашего браузера и скачивает его и устанавливает
    }

    @BeforeEach
    void setUp() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize(); //разворачиваем на весь экран
      //  driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
        properties = new Properties();

        properties.load(new FileReader(new File("src/test/resources/user.properties")));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
