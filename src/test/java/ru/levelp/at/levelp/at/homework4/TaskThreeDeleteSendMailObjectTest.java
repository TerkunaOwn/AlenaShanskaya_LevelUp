package ru.levelp.at.levelp.at.homework4;

import com.google.common.base.Verify;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import ru.levelp.at.homework4.PageLoginInMail;
import ru.levelp.at.homework4.PageMailAgent;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskThreeDeleteSendMailObjectTest extends HelperBase {

    @Test
    void testDeleteSendMail() {

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

//--------------------- 2.1.Заходим в Входящие и проверяем колличество писем ДО отправки нового

        pageMailAgent.openMySelfMail();
        int incomingLetterCountBefore = pageMailAgent.getLetterListItem();

//-------------------- 2.2. Заходим в папку Корзина и проверяем колличество писем ДО удаления нового

        pageMailAgent.openTrashMail();
        int deleteLetterCountBefore = pageMailAgent.getLetterListItem();

//---------------------3.Создать новое письмо (заполнить адресата, тему письма и тело)

        pageMailAgent.openComposeMail();
        pageMailAgent.fillTheFields(properties.getProperty("ACCOUNT_LOGIN"), properties.getProperty("SUBJECT"),  properties.getProperty("BODY_TEXT"));

//---------------------4. Отправить письмо

        pageMailAgent.sendMail();
        pageMailAgent.closeComposeSendMail();

//---------------------5. Verify, что письмо появилось в папке отправленные

        pageMailAgent.openMySelfMail();
        pageMailAgent.verifyVisible(incomingLetterCountBefore);

//---------------------6. Verify контент, адресата и тему письма (должно совпадать с пунктом 3)

        pageMailAgent.openMySelfMail();
        pageMailAgent.firstLetterToMyself();

        Verify.verify(pageMailAgent.getActualAddressInMailFolderMyself().equals(properties.getProperty("ACCOUNT_LOGIN")));
        Verify.verify(pageMailAgent.getActualSubjectMailFolderMyself().equals(properties.getProperty("SUBJECT")));
        Verify.verify(pageMailAgent.getActualBodyText().equals(properties.getProperty("BODY_TEXT")));

//---------------------7. Удалить письмо

        pageMailAgent.deleteMail();

//---------------------8. Verify что письмо появилось в папке Корзина

        pageMailAgent.openTrashMail();
        pageMailAgent.verifyVisible(deleteLetterCountBefore);

//---------------------9. Выйти из учётной записи

        pageMailAgent.clickRightDropdown();
        pageMailAgent.clickButtonExit();
    }
}
