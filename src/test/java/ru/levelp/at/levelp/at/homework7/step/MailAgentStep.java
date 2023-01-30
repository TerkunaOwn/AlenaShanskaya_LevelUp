package ru.levelp.at.levelp.at.homework7.step;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Verify;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework5.PageMailAgent;

public class MailAgentStep {
    protected final WebDriver driver;
    protected PageMailAgent pageMailAgent;

    public MailAgentStep(WebDriver driver) {
        this.driver = driver;
        pageMailAgent = new PageMailAgent(driver);
    }

    @Step ("Assert, что вход выполнен успешно")
    public void assertLoginInIndexPage(String login) {
        pageMailAgent.promoClose();
        assertThat(pageMailAgent.getLoginInIndexPage()).isEqualTo(login); //сравниваю адреса почты
    }

    @Step("Переход в папку 'Черновики' и подсчет писем")
    public int goToDraft() {
        pageMailAgent.openDraftsMail();
        int draftLetterCountBefore = pageMailAgent.getLetterListItem();
        return draftLetterCountBefore;
    }

    @Step("Переход в папку 'Отправленные' и подсчет писем")
    public int goToSendMail() {
        pageMailAgent.openSentMail();
        int sendLetterCountBefore = pageMailAgent.getLetterListItem();
        return sendLetterCountBefore;
    }

    @Step("Переход в папку 'Тест' и подсчет писем")
    public int goToFolderTest() {
        pageMailAgent.openFolderTest();
        int folderTestLetterCountBefore = pageMailAgent.getLetterListItem();
        return folderTestLetterCountBefore;
    }

    @Step("Переход в папку 'Входящие' и подсчет писем")
    public int goToMySelf() {
        pageMailAgent.openMySelfMail();
        int mySelfLetterCountBefore = pageMailAgent.getLetterListItem();
        return mySelfLetterCountBefore;
    }

    @Step("Переход в папку 'Корзина' и подсчет писем")
    public int goToTrash() {
        pageMailAgent.openTrashMail();
        int trashLetterCountBefore = pageMailAgent.getLetterListItem();
        return trashLetterCountBefore;
    }

    @Step("Создать новое письмо. Заполнить: кому, тема, текст")
    public void createNewMail(String login, String subject, String text) {
        pageMailAgent.openComposeMail();
        pageMailAgent.fillTheFields(login, subject, text);
    }

    @Step("Сохранить письмо")
    public void saveMail() {
        pageMailAgent.buttonSaveMail();
        pageMailAgent.closeComposeLetter();
    }

    @Step("Проверка письма, что контент и адресат совпадает")
    public void assertMail(String login, String subject, String text) {
        pageMailAgent.firstLetterInMailbox();

        Verify.verify(pageMailAgent.getActualAddressInMail().equals(login));
        Verify.verify(pageMailAgent.getActualSubjectMail().equals(subject));
        Verify.verify(pageMailAgent.getActualBodyText().equals(text));
    }

    @Step("Проверка письма, что контент и адресат совпадает")
    public void assertMailMyFolder(String login, String subject, String text) {
        pageMailAgent.openFolderTest();
        pageMailAgent.firstLetterInMyFolder();

        Verify.verify(pageMailAgent.getActualAddressInMailFolderTest().equals(login));
        Verify.verify(pageMailAgent.getActualSubjectMailFolderTest().equals(subject));
        Verify.verify(pageMailAgent.getActualBodyTextFolderTest().equals(text));
    }

    @Step("Проверка письма, что контент и адресат совпадает")
    public void assertMailMySelf(String login, String subject, String text) {
        pageMailAgent.firstLetterInMyFolder();

        Verify.verify(pageMailAgent.getActualAddressInMailFolderTest().equals(login));
        Verify.verify(pageMailAgent.getActualSubjectMailFolderTest().equals(subject));
        Verify.verify(pageMailAgent.getActualBodyTextFolderTest().equals(text));
    }

    @Step("Отправить письмо")
    public void sendMail() {
        pageMailAgent.sendMail();
        pageMailAgent.closeComposeSendMail();
    }

    @Step("Проверка, что количество писем в папке 'Черновики' уменьшилось на 1")
    public void mailInvisibleInDraft(int draftLetterCountBefore) {
        pageMailAgent.openDraftsMail();
        pageMailAgent.verifyInvisible(draftLetterCountBefore);
    }

    @Step("Проверка, что количество писем в папке 'Отправленные' увеличилось на 1")
    public void mailVisibleInSendMail(int sendLetterCountBefore) {
        pageMailAgent.openSentMail();
        pageMailAgent.verifyVisible(sendLetterCountBefore);
    }

    @Step("Проверка, что количество писем в папке 'Тест' увеличилось на 1")
    public void mailVisibleInFolderTest(int folderTestLetterCountBefore) {
        pageMailAgent.openFolderTest();
        pageMailAgent.verifyVisible(folderTestLetterCountBefore);
    }

    @Step("Проверка, что количество писем в папке 'Входящие' увеличилось на 1")
    public void mailVisibleInMySelf(int mySelfLetterCountBefore) {
        pageMailAgent.openMySelfMail();
        pageMailAgent.verifyVisible(mySelfLetterCountBefore);
    }

    @Step("Проверка, что количество писем в папке 'Черновики' увеличилось на 1")
    public void mailVisibleInTrashMail(int trashLetterCountBefore) {
        pageMailAgent.openTrashMail();
        pageMailAgent.verifyVisible(trashLetterCountBefore);
    }

    @Step("Logout user")
    public void deleteMail() {
        pageMailAgent.deleteMail();
    }

    @Step("Выход из аккаунта")
    public void quitMailAgent() {
        pageMailAgent.clickRightDropdown();
        pageMailAgent.clickButtonExit();
    }
}
