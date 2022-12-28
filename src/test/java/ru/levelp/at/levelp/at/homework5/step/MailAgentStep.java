package ru.levelp.at.levelp.at.homework5.step;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Verify;
import org.openqa.selenium.WebDriver;
import ru.levelp.at.homework5.PageMailAgent;

public class MailAgentStep {
    protected final WebDriver driver;
    protected PageMailAgent pageMailAgent;

    public MailAgentStep(WebDriver driver) {
        this.driver = driver;
        pageMailAgent = new PageMailAgent(driver);
    }

    public void assertLoginInIndexPage(String login) {
        pageMailAgent.promoClose();
        assertThat(pageMailAgent.getLoginInIndexPage()).isEqualTo(login); //сравниваю адреса почты
    }

    public int goToDraft() {
        pageMailAgent.openDraftsMail();
        int draftLetterCountBefore = pageMailAgent.getLetterListItem();
        return draftLetterCountBefore;
    }

    public int goToSendMail() {
        pageMailAgent.openSentMail();
        int sendLetterCountBefore = pageMailAgent.getLetterListItem();
        return sendLetterCountBefore;
    }

    public int goToFolderTest() {
        pageMailAgent.openFolderTest();
        int folderTestLetterCountBefore = pageMailAgent.getLetterListItem();
        return folderTestLetterCountBefore;
    }

    public int goToMySelf() {
        pageMailAgent.openMySelfMail();
        int mySelfLetterCountBefore = pageMailAgent.getLetterListItem();
        return mySelfLetterCountBefore;
    }

    public int goToTrash() {
        pageMailAgent.openTrashMail();
        int trashLetterCountBefore = pageMailAgent.getLetterListItem();
        return trashLetterCountBefore;
    }

    public void createNewMail(String login, String subject, String text) {
        pageMailAgent.openComposeMail();
        pageMailAgent.fillTheFields(login, subject, text);
    }

    public void saveMail() {
        pageMailAgent.buttonSaveMail();
        pageMailAgent.closeComposeLetter();
    }

    public void assertMail(String login, String subject, String text) {
        pageMailAgent.firstLetterInMailbox();

        Verify.verify(pageMailAgent.getActualAddressInMail().equals(login));
        Verify.verify(pageMailAgent.getActualSubjectMail().equals(subject));
        Verify.verify(pageMailAgent.getActualBodyText().equals(text));
    }

    public void assertMailMyFolder(String login, String subject, String text) {
        pageMailAgent.openFolderTest();
        pageMailAgent.firstLetterInMyFolder();

        Verify.verify(pageMailAgent.getActualAddressInMailFolderTest().equals(login));
        Verify.verify(pageMailAgent.getActualSubjectMailFolderTest().equals(subject));
        Verify.verify(pageMailAgent.getActualBodyTextFolderTest().equals(text));
    }

    public void assertMailMySelf(String login, String subject, String text) {
        pageMailAgent.firstLetterInMyFolder();

        Verify.verify(pageMailAgent.getActualAddressInMailFolderTest().equals(login));
        Verify.verify(pageMailAgent.getActualSubjectMailFolderTest().equals(subject));
        Verify.verify(pageMailAgent.getActualBodyTextFolderTest().equals(text));
    }

    public void sendMail() {
        pageMailAgent.sendMail();
        pageMailAgent.closeComposeSendMail();
    }

    public void mailInvisibleInDraft(int draftLetterCountBefore) {
        pageMailAgent.openDraftsMail();
        pageMailAgent.verifyInvisible(draftLetterCountBefore);
    }

    public void mailVisibleInSendMail(int sendLetterCountBefore) {
        pageMailAgent.openSentMail();
        pageMailAgent.verifyVisible(sendLetterCountBefore);
    }

    public void mailVisibleInFolderTest(int folderTestLetterCountBefore) {
        pageMailAgent.openFolderTest();
        pageMailAgent.verifyVisible(folderTestLetterCountBefore);
    }

    public void mailVisibleInMySelf(int mySelfLetterCountBefore) {
        pageMailAgent.openMySelfMail();
        pageMailAgent.verifyVisible(mySelfLetterCountBefore);
    }

    public void mailVisibleInTrashMail(int trashLetterCountBefore) {
        pageMailAgent.openTrashMail();
        pageMailAgent.verifyVisible(trashLetterCountBefore);
    }

    public void deleteMail() {
        pageMailAgent.deleteMail();
    }

    public void quitMailAgent() {
        pageMailAgent.clickRightDropdown();
        pageMailAgent.clickButtonExit();
    }
}
