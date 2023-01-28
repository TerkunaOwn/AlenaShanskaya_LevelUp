package ru.levelp.at.levelp.at.homework7.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.levelp.at.levelp.at.homework7.context.TestContext;
import ru.levelp.at.levelp.at.homework7.listener.AllureAttachmentCallback;
import ru.levelp.at.levelp.at.homework7.step.MailAgentLogin;
import ru.levelp.at.levelp.at.homework7.step.MailAgentStep;

@ExtendWith(AllureAttachmentCallback.class)
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
        properties = new Properties();
        mailAgentStep = new MailAgentStep(driver);
        mailAgentLogin = new MailAgentLogin(driver);
        TestContext.getInstance().putObject("driver", driver);

        properties.load(new FileReader(new File("src/test/resources/user.properties")));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        TestContext.clear();
    }
}
