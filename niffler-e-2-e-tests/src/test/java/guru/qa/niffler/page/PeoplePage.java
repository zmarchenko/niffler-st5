package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class PeoplePage {

    private final ElementsCollection tableRows = $("table.abstract-table").$("tbody").$$("tr");
    private SelenideElement userRow;

    public PeoplePage getRowWithUsername(String username) {
        userRow = tableRows.find(text(username));
        return this;
    }

    public void assertThatActionHasStatus(String expectedStatus) {
        userRow.shouldHave(text(expectedStatus));
    }

    public PeoplePage assertThatSubmitActionIsEnabled() {
        userRow.$("button.button-icon_type_submit").shouldBe(Condition.interactable);
        return this;
    }

    public void assertThatDeclineActionIsEnabled() {
        userRow.$("button.button-icon_type_close").shouldBe(Condition.interactable);
    }


}
