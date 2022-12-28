package ru.levelp.at.levelp.at.homework5.tests;

import org.junit.jupiter.api.Test;

public class TaskOneStepTest extends HelperBase {

    @Test
    void taskOneSendMailTest() {

        // 1. Войти в почту
        mailAgentLogin.loginToMailAgent(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("ACCOUNT_PASSWORD"));
        // 2.Assert, что вход выполнен успешно
        mailAgentStep.assertLoginInIndexPage(properties.getProperty("ACCOUNT_LOGIN"));
        // 3.Создать новое письмо (заполнить адресата, тему письма и тело)
        mailAgentStep.createNewMail(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT"),
            properties.getProperty("BODY_TEXT"));
        // 4. Сохранить его как черновик
        mailAgentStep.saveMail();
        // 5. Verify, что письмо сохранено в черновиках
        int sendLetterCountBefore = mailAgentStep.goToSendMail();
        int draftLetterCountBefore = mailAgentStep.goToDraft();
        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)
        mailAgentStep.assertMail(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT"),
            properties.getProperty("BODY_TEXT"));
        // 7. Отправить письмо
        mailAgentStep.sendMail();
        // 8. Verify, что письмо исчезло из черновиков
        mailAgentStep.mailInvisibleInDraft(draftLetterCountBefore);
        // 9. Verify, что письмо появилось в папке отправленные
        mailAgentStep.mailVisibleInSendMail(sendLetterCountBefore);
        // 10. Выйти из учётной записи
        mailAgentStep.quitMailAgent();
    }
}
