package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.UiBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.model.CurrencyValues.RUB;


@ExtendWith({CategoryExtension.class, SpendExtension.class})
public class SpendingTest {

    private final UiBot ui = new UiBot();

    private final static String USERNAME = "zhanna1";
    private final static String CATEGORY_NAME = "Обучение";

    static {
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        ui.startPage()
                .clickLogin()
                .setUsername(USERNAME)
                .setPassword("test")
                .clickSignIn();
    }

    @GenerateCategory(
            category = CATEGORY_NAME,
            username = USERNAME
    )
    @GenerateSpend(
            username = USERNAME,
            description = "QA.GURU Advanced 5",
            amount = 65000.00,
            currency = RUB,
            category = CATEGORY_NAME
    )
    @Test
    void spendingShouldBeDeletedAfterTableAction(SpendJson spendJson) {
        SelenideElement spendingRow = ui.mainPage()
                .findRowByText(spendJson.description());

        ui.mainPage()
                .chooseSpending(spendingRow)
                .deleteChosenSpending()
                .assertThatTableContentHasSize(0);
    }

}
