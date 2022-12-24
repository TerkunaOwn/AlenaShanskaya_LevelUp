package ru.levelp.at.levelp.at.homework4;

import com.google.common.base.Verify;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import ru.levelp.at.homework4.PageLoginInMail;
import ru.levelp.at.homework4.PageMailAgent;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskTwoUsingFilterObjectTest extends HelperBase {

    @Test
    void testUsingFilterToSendEmail() {

        PageLoginInMail loginInMail = new PageLoginInMail(driver);

//---------------------1. Войти в почту

        loginInMail.openSite();

        loginInMail.pushLoginButton();
        loginInMail.enterLogin(properties.getProperty("ACCOUNT_LOGIN"));
        loginInMail.enterPassword(properties.getProperty("ACCOUNT_PASSWORD"));

//---------------------2.Assert, что вход выполнен успешно

        PageMailAgent pageMailAgent = PageFactory.initElements(driver, PageMailAgent.class);

        pageMailAgent.promoClose();
        String actualLogin = pageMailAgent.getLoginInIndexPage();
        assertThat(actualLogin).isEqualTo(properties.getProperty("ACCOUNT_LOGIN")); //сравниваю адреса почты

//--------------------- 2.1.Заходим в отправленные и проверяем колличество писем ДО создания нового

        pageMailAgent.openSentMail();
        int sendLetterCountBefore = pageMailAgent.getLetterListItem();

//-------------------- 2.2. Заходим в папку Тест и проверяем колличество писем ДО создания нового

        pageMailAgent.openFolderTest();
        int folderTestLetterCountBefore = pageMailAgent.getLetterListItem();

//---------------------3.Создать новое письмо (заполнить адресата (самого себя), тему письма (должно содержать слово Тест) и тело)

        pageMailAgent.openComposeMail();
        pageMailAgent.fillTheFields(properties.getProperty("ACCOUNT_LOGIN"), properties.getProperty("SUBJECT_TEST"),  properties.getProperty("BODY_TEXT"));

//---------------------4.Отправить письмо

        pageMailAgent.sendMail();
        pageMailAgent.closeComposeSendMail();

//---------------------5. Verify, что письмо появилось в папке отправленные

        pageMailAgent.openSentMail();
        pageMailAgent.verifyVisible(sendLetterCountBefore);

//---------------------6. Verify, что письмо появилось в папке Тест

        pageMailAgent.openFolderTest();
        pageMailAgent.verifyVisible(folderTestLetterCountBefore);

//---------------------7. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        pageMailAgent.openFolderTest();
        pageMailAgent.firstLetterInMyFolder();

        Verify.verify(pageMailAgent.getActualAddressInMailFolderTest().equals(properties.getProperty("ACCOUNT_LOGIN")));
        Verify.verify(pageMailAgent.getActualSubjectMailFolderTest().equals(properties.getProperty("SUBJECT_TEST")));
        Verify.verify(pageMailAgent.getActualBodyTextFolderTest().equals(properties.getProperty("BODY_TEXT")));

//---------------------8. Выйти из учётной записи

        pageMailAgent.clickRightDropdown();
        pageMailAgent.clickButtonExit();
    }
}
