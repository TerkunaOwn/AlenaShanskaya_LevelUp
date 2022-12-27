package ru.levelp.at.levelp.at.homework4;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Verify;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import ru.levelp.at.homework4.PageLoginInMail;
import ru.levelp.at.homework4.PageMailAgent;

public class TaskOneObjectTest extends HelperBase {

    @Test
    void taskOneSendMailTest() {

        PageLoginInMail loginInMail = new PageLoginInMail(driver);

        // 1. Войти в почту

        loginInMail.openSite();

        loginInMail.pushLoginButton();
        loginInMail.enterLogin(properties.getProperty("ACCOUNT_LOGIN"));
        loginInMail.enterPassword(properties.getProperty("ACCOUNT_PASSWORD"));

        // 2.Assert, что вход выполнен успешно

        PageMailAgent pageMailAgent = PageFactory.initElements(driver, PageMailAgent.class);

        pageMailAgent.promoClose();
        String actualLogin = pageMailAgent.getLoginInIndexPage();
        assertThat(actualLogin).isEqualTo(properties.getProperty("ACCOUNT_LOGIN")); //сравниваю адреса почты

        // 3.Создать новое письмо (заполнить адресата, тему письма и тело)

        pageMailAgent.openComposeMail();
        pageMailAgent.fillTheFields(properties.getProperty("ACCOUNT_LOGIN"),
            properties.getProperty("SUBJECT"),
            properties.getProperty("BODY_TEXT"));

        // 4. Сохранить его как черновик

        pageMailAgent.buttonSaveMail();
        pageMailAgent.closeComposeLetter();

        // 5. Verify, что письмо сохранено в черновиках
        pageMailAgent.verifySentMail();
        final int sendLetterCountBefore = pageMailAgent.verifySentMail();

        pageMailAgent.openDraftsMail();
        final int draftLetterCountBefore = pageMailAgent.verifyDraftMail();

        // 6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        pageMailAgent.firstLetterInMailbox();

        Verify.verify(pageMailAgent.getActualAddressInMail().equals(properties.getProperty("ACCOUNT_LOGIN")));
        Verify.verify(pageMailAgent.getActualSubjectMail().equals(properties.getProperty("SUBJECT")));
        Verify.verify(pageMailAgent.getActualBodyText().equals(properties.getProperty("BODY_TEXT")));

        // 7. Отправить письмо

        pageMailAgent.sendMail();
        pageMailAgent.closeComposeSendMail();

        // 8. Verify, что письмо исчезло из черновиков

        pageMailAgent.openDraftsMail();
        pageMailAgent.verifyInvisible(draftLetterCountBefore);

        // 9. Verify, что письмо появилось в папке отправленные

        pageMailAgent.openSentMail();
        pageMailAgent.verifyVisible(sendLetterCountBefore);

        // 10. Выйти из учётной записи

        pageMailAgent.clickRightDropdown();
        pageMailAgent.clickButtonExit();
    }

}
