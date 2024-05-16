package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final ElementsCollection tableRows = $(".spendings-table").$(" tbody").$$("tr");
    private final SelenideElement deleteSpendingBtn = $(".spendings__table-controls").find(byText("Delete selected"));
    private final SelenideElement avatar = $(".header__avatar");
    private final SelenideElement allPeople = $("[href='/people']");

    public SelenideElement findRowByText(String text) {
        return tableRows.find(text(text));
    }

    public MainPage chooseSpending(SelenideElement spending) {
        spending.$$("td").first().scrollTo().click();
        return this;
    }

    public MainPage deleteChosenSpending() {
        deleteSpendingBtn.click();
        return this;
    }

    public PeoplePage clickAllPeople() {
        allPeople.click();
        return new PeoplePage();
    }

    public void assertThatTableContentHasSize(int expectedSize) {
        tableRows.shouldHave(size(expectedSize));
    }

    public void assertThatAvatarIsVisible() {
        avatar.shouldBe(visible);
    }

}