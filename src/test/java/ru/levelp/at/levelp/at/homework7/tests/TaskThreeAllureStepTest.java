package ru.levelp.at.levelp.at.homework7.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Работа с входящими письмами")
@DisplayName("Домашняя работа №7.")
public class TaskThreeAllureStepTest extends HelperBase {

    @Test
    @Story("Создать письмо. Отправить письмо. удалить письмо")
    @Description("Создане письма, отправка письма и удаление письма")
    @DisplayName("Задание 3")
    void testDeleteSendMail() {

        // 1. Войти в почту
        mailAgentLogin.loginToMailAgent(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("ACCOUNT_PASSWORD"));
        // 2.Assert, что вход выполнен успешно
        mailAgentStep.assertLoginInIndexPage(properties.getProperty("ACCOUNT_LOGIN"));
        // 2.1.Заходим в Входящие и проверяем колличество писем ДО отправки нового
        int mySelfLetterCountBefore = mailAgentStep.goToMySelf();
        // 2.2. Заходим в папку Корзина и проверяем колличество писем ДО удаления нового
        int trashLetterCountBefore = mailAgentStep.goToTrash();
        // 3.Создать новое письмо (заполнить адресата, тему письма и тело)
        mailAgentStep.createNewMail(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT"),
            properties.getProperty("BODY_TEXT"));
        // 4. Отправить письмо
        mailAgentStep.sendMail();
        // 5. Verify, что письмо появилось в папке отправленные
        mailAgentStep.mailVisibleInMySelf(mySelfLetterCountBefore);
        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mailAgentStep.assertMailMySelf(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT"),
            properties.getProperty("BODY_TEXT"));
        // 7. Удалить письмо
        mailAgentStep.deleteMail();
        // 8. Verify что письмо появилось в папке Корзина
        mailAgentStep.mailVisibleInTrashMail(trashLetterCountBefore);
        // 9. Выйти из учётной записи
        mailAgentStep.quitMailAgent();
    }
}
