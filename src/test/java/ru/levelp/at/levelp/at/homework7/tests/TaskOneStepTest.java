package ru.levelp.at.levelp.at.homework7.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("Работа с черновиками")
@DisplayName("Домашняя работа №7.")
public class TaskOneStepTest extends HelperBase {

    @Test
    @Story("Создать письмо. Сохранить письмо в черновике. Отправить письмо из черновика")
    @Description("Создане письма, сохраниение в черновики и отправка письма")
    @DisplayName("Задание 1")
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

    @Test
    @Story("Упавший тест")
    @Description("Создане письма, сохраниение в черновики и отправка письма")
    @DisplayName("Задание 1")
    void taskOneSendMailTestBadTest() {

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

        // 2.Assert, что вход выполнен успешно
        mailAgentStep.assertLoginInIndexPage(properties.getProperty("ACCOUNT_LOGIN"));
    }
}
