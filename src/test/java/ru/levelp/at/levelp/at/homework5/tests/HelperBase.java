package ru.levelp.at.levelp.at.homework5.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.levelp.at.levelp.at.homework5.step.MailAgentLogin;
import ru.levelp.at.levelp.at.homework5.step.MailAgentStep;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelperBase {

    public Properties properties;
    protected WebDriver driver;
    protected MailAgentStep mailAgentStep;
    protected MailAgentLogin mailAgentLogin;

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
        mailAgentStep = new MailAgentStep(driver);
        mailAgentLogin = new MailAgentLogin(driver);

        properties.load(new FileReader(new File("src/test/resources/user.properties")));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
