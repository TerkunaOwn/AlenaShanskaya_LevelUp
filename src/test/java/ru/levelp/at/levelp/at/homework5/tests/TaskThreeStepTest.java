package ru.levelp.at.levelp.at.homework5.tests;

import org.junit.jupiter.api.Test;

public class TaskThreeStepTest extends HelperBase {
    @Test
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
