package ru.levelp.at.levelp.at.homework4;

import com.google.common.base.Verify;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import ru.levelp.at.homework4.PageMailAgent;
import ru.levelp.at.homework4.PageLoginInMail;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskOneObjectTest extends HelperBase {

    @Test
    void TaskOneSendMailTest(){

        PageLoginInMail loginInMail = new PageLoginInMail(driver);

//---------------------1. Войти в почту

        loginInMail.openSite();

        loginInMail.pushLoginButton();
        loginInMail.enterLogin(properties.getProperty("ACCOUNT_LOGIN"));
        loginInMail.enterPassword(properties.getProperty("ACCOUNT_PASSWORD"));
        driver.navigate().refresh();

//---------------------2.Assert, что вход выполнен успешно

        PageMailAgent pageMailAgent = PageFactory.initElements(driver, PageMailAgent.class);

        String actualLogin = pageMailAgent.getLoginInIndexPage();
        assertThat(actualLogin).isEqualTo(properties.getProperty("ACCOUNT_LOGIN")); //сравниваю адреса почты
        pageMailAgent.promoClose();

//-------------------- 2.1. Заходим в черновики и проверяем колличество писем ДО создания нового

        pageMailAgent.openDraftsMail();
        int draftLetterCountBefore = pageMailAgent.getLetterListItem();

//--------------------- 2.2.Заходим в отправленные и проверяем колличество писем ДО создания нового

        pageMailAgent.openSentMail();
        int sendLetterCountBefore = pageMailAgent.getLetterListItem();

//---------------------3.Создать новое письмо (заполнить адресата, тему письма и тело)

        pageMailAgent.openComposeMail();
        pageMailAgent.fillTheFields(properties.getProperty("ACCOUNT_LOGIN"), properties.getProperty("SUBJECT"),  properties.getProperty("BODY_TEXT"));

//---------------------4. Сохранить его как черновик

        pageMailAgent.buttonSaveMail();
        pageMailAgent.closeComposeLetter();

//---------------------5. Verify, что письмо сохранено в черновиках

        pageMailAgent.openDraftsMail();
        int draftLetterCountAfter = pageMailAgent.getLetterListItem();
        Verify.verify(draftLetterCountBefore+1==draftLetterCountAfter);

//---------------------6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        pageMailAgent.firstLetterInMailbox();

        Verify.verify(pageMailAgent.getActualAddressInMail().equals(properties.getProperty("ACCOUNT_LOGIN")));
        Verify.verify(pageMailAgent.getActualSubjectMail().equals(properties.getProperty("SUBJECT")));
        Verify.verify(pageMailAgent.getActualBodyText().equals(properties.getProperty("BODY_TEXT")));

//---------------------7. Отправить письмо

        pageMailAgent.sendMail();
        pageMailAgent.closeComposeSendMail();

//---------------------8. Verify, что письмо исчезло из черновиков

        pageMailAgent.openDraftsMail();
        driver.navigate().refresh();

        int draftLetterCountAfter1 = pageMailAgent.getLetterListItem();
        Verify.verify(draftLetterCountAfter-1 == draftLetterCountAfter1);

//---------------------9. Verify, что письмо появилось в папке отправленные

        pageMailAgent.openSentMail();

        int sendLetterCountAfter = pageMailAgent.getLetterListItem();
        Verify.verify(sendLetterCountBefore+1 == sendLetterCountAfter);

//---------------------10. Выйти из учётной записи

        pageMailAgent.clickRightDropdown();
        pageMailAgent.clickButtonExit();
    }

}
