package ru.levelp.at.levelp.at.homework7.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Работа с фильтрами")
@DisplayName("Домашняя работа №7.")
public class TaskTwoAllureStepTest extends HelperBase {

    @Test
    @Story("Создать письмо. Отправить письмо. Работа фильтра папок")
    @Description("Создане письма, отправка письма и проверка работы фильтра")
    @DisplayName("Задание 2")
    void testUsingFilterToSendEmail() {

        // 1.Войти в почту
        mailAgentLogin.loginToMailAgent(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("ACCOUNT_PASSWORD"));
        // 2.Assert, что вход выполнен успешно
        mailAgentStep.assertLoginInIndexPage(properties.getProperty("ACCOUNT_LOGIN"));
        // 2.1.Заходим в отправленные и проверяем колличество писем ДО создания нового
        int sendLetterCountBefore = mailAgentStep.goToSendMail();
        // 2.2. Заходим в папку Тест и проверяем колличество писем ДО создания нового
        int folderTestLetterCountBefore = mailAgentStep.goToFolderTest();
        // 3.Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)
        mailAgentStep.createNewMail(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT_TEST"),
            properties.getProperty("BODY_TEXT"));
        // 4.Отправить письмо
        mailAgentStep.sendMail();
        // 5. Verify, что письмо появилось в папке отправленные
        mailAgentStep.mailVisibleInSendMail(sendLetterCountBefore);
        // 6. Verify, что письмо появилось в папке Тест
        mailAgentStep.mailVisibleInFolderTest(folderTestLetterCountBefore);
        // 7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mailAgentStep.assertMailMyFolder(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT_TEST"),
            properties.getProperty("BODY_TEXT"));
        // 8. Выйти из учётной записи
        mailAgentStep.quitMailAgent();
    }
}
