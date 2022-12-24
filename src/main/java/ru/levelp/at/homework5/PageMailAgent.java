package ru.levelp.at.homework5;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PageMailAgent extends DriverHelper {

    protected static final String PAGE_TITLE_DRAFT = "Черновики - Почта Mail.ru";
    protected static final String PAGE_TITLE_SENT = "Отправленные - Почта Mail.ru";
    protected static String PAGE_URL_MYSELF = "https://e.mail.ru/tomyself/";
    protected static String PAGE_URL_TRASH = "https://e.mail.ru/trash/";

    @FindBy(css = "span[class='ph-project__user-name svelte-1hiqrvn']")
    private WebElement userName;

    @FindBy(css = "div[class*='ph-project-promo-close-icon__container']")
    private WebElement promoCloseIcon;

    @FindBy(css = "a[data-folder-link-id='500001']")
    private  WebElement BUTTON_DRAFT;
    @FindBy(css = "a[data-folder-link-id='500000']")
    private WebElement BUTTON_SENT;

    @FindBy(css = "span[class='compose-button__wrapper']")
    private WebElement BUTTON_COMPOSE;

    @FindBy(css = "a[data-folder-link-id='1']")
    private WebElement BUTTON_MY_FOLDER;

    @FindBy(css = "a[data-folder-link-id='500015']")
    private WebElement BUTTON_MYSELF;

    @FindBy(css = "a[data-folder-link-id='500002']")
    private WebElement BUTTON_TRASH;

    @FindBy(css = "a.js-letter-list-item")
    private List<WebElement> letterListItem;

    @FindBy(css = "a.js-letter-list-item:nth-of-type(1)")
    private WebElement firstLetterInMailbox;

    @FindBy(css = "input[tabindex='100']")
    private WebElement addressField;

    @FindBy(css = "[name='Subject']")
    private WebElement subjectField;

    @FindBy(css = "div.cke_editable")
    private WebElement bodyTextField;

    @FindBy(css = "span[class='text--1tHKB']")
    private WebElement actualAddressInMail;

    @FindBy(css ="span[class='letter-contact']")
    private WebElement actualAddressInMailFolderTest;

    @FindBy(css ="span[class='letter-contact']")
    private WebElement actualAddressInMailFolderMyself;

    @FindBy(css = "h2[class='thread-subject']")
    private WebElement actualSubjectMailFolderTest;

    @FindBy(css = "h2[class='thread-subject']")
    private WebElement actualSubjectMailFolderMyself;

    @FindBy(css = "[name='Subject']")
    private WebElement actualSubjectMail;

    @FindBy(css = "div.js-readmsg-msg > div > div > div > div")
    private WebElement actualBodyText;

    @FindBy(css = "[data-test-id='save']") //сохранить в черновиках
    private WebElement buttonSaveMail;

    @FindBy(css = "span[class*='button2_delete']") //удалить письмо
    private WebElement buttonDeleteMail;

    @FindBy(css = "button[tabindex='700']")
    private WebElement closeComposeLetter;

    @FindBy(css = "[data-test-id='send']")
    private WebElement buttonSendMail;

    @FindBy(css = "span[tabindex='1000']")
    private WebElement closeComposeSendMail;

    @FindBy(css = "span[class*='ph-dropdown-icon']")
    private WebElement rightDropdown;

    @FindBy(css = "div[data-testid*='whiteline-account-exit']")
    private WebElement buttonExit;


    public PageMailAgent(WebDriver driver) {
        super(driver);
    }

    public String getLoginInIndexPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ph-project__account")));
        return userName.getText();
    } //получаем адресс

    public void promoClose() {
        wait.until(ExpectedConditions.visibilityOf(promoCloseIcon)).click(); //закрываем всплывашку
    }

    public void openDraftsMail() {
        wait.until(ExpectedConditions.visibilityOf(BUTTON_DRAFT)).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
       // driver.navigate().refresh();
    }

    public int verifyDraftMail() {
        wait.until(ExpectedConditions.visibilityOf((BUTTON_DRAFT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_DRAFT));
        int draftLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();
        return draftLetterCountBefore;
    }

    public void verifyInvisible(int count) {
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), count - 1));
    }

    public void verifyVisible(int count) {
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a.js-letter-list-item"), count + 1));
    }

    public int verifySentMail() {
        wait.until(ExpectedConditions.visibilityOf((BUTTON_SENT))).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        int sendLetterCountBefore = driver.findElements(By.cssSelector("a.js-letter-list-item")).size();
        return sendLetterCountBefore;
    }

    public Integer getLetterListItem() {
        return letterListItem.size();
    }

    public void openSentMail() {
        wait.until(ExpectedConditions.visibilityOf(BUTTON_SENT)).click();
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE_SENT));
        driver.navigate().refresh();
    }

    public void openMySelfMail() {
        wait.until(ExpectedConditions.visibilityOf(BUTTON_MYSELF)).click();
        wait.until(ExpectedConditions.urlToBe(PAGE_URL_MYSELF));
    }

    public void openComposeMail() {
        wait.until(ExpectedConditions.visibilityOf(BUTTON_COMPOSE)).click();
    }

    public void openFolderTest() {
        wait.until(ExpectedConditions.visibilityOf(BUTTON_MY_FOLDER)).click();
    }

    public void openTrashMail() {
        wait.until(ExpectedConditions.visibilityOf(BUTTON_TRASH)).click();
        wait.until(ExpectedConditions.urlToBe(PAGE_URL_TRASH));
    }

    public void fillTheFields(String to, String subject, String bodyText) {
        wait.until(ExpectedConditions.visibilityOf(addressField)).sendKeys(to);
        wait.until(ExpectedConditions.visibilityOf(subjectField)).sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOf(bodyTextField)).sendKeys(bodyText);
    }

    public void buttonSaveMail() {
        wait.until(ExpectedConditions.visibilityOf(buttonSaveMail)).click();
    }

    public void closeComposeLetter() {
        wait.until(ExpectedConditions.visibilityOf(closeComposeLetter)).click();
    }

    public void firstLetterInMailbox() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLetterInMailbox)).click();
        wait.until(ExpectedConditions.visibilityOf(actualAddressInMail));
    }

    public void firstLetterInMyFolder() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLetterInMailbox)).click();
        wait.until(ExpectedConditions.visibilityOf(actualAddressInMailFolderTest));
    }

    public void firstLetterToMyself() {
        wait.until(ExpectedConditions.elementToBeClickable(firstLetterInMailbox)).click();
        wait.until(ExpectedConditions.visibilityOf(actualAddressInMailFolderMyself));
    }

    public String getActualAddressInMail() {
        return actualAddressInMail.getText();
    }

    public String getActualAddressInMailFolderTest() {
        return actualAddressInMailFolderTest.getAttribute("title");
    }

    public String getActualAddressInMailFolderMyself() {
        return actualAddressInMailFolderMyself.getAttribute("title");
    }

    public String getActualSubjectMail() {
        return actualSubjectMail.getAttribute("value");
    }

    public String getActualSubjectMailFolderTest() {
        return actualSubjectMailFolderTest.getText();
    }

    public String getActualSubjectMailFolderMyself() {
        return actualSubjectMailFolderMyself.getText();
    }

    public String getActualBodyText() {
        return actualBodyText.getText();
    }

    public String getActualBodyTextFolderTest() {
        return  actualBodyText.getText();
    }

    public void sendMail() {
        wait.until(ExpectedConditions.visibilityOf(buttonSendMail)).click();
    }

    public void deleteMail() {
        wait.until(ExpectedConditions.visibilityOf(buttonDeleteMail)).click();
    }

    public void closeComposeSendMail() {
        wait.until(ExpectedConditions.visibilityOf(closeComposeSendMail)).click();
    }

    public void clickRightDropdown() {
        wait.until(ExpectedConditions.visibilityOf(rightDropdown)).click();
    }

    public void clickButtonExit() {
        wait.until(ExpectedConditions.visibilityOf(buttonExit)).click();
    }

}
