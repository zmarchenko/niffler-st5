package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StartPage {

    private final SelenideElement loginBtn = $(byText("Login"));

    @Step("Нажать на кнопку \"Login\"")
    public LoginPage clickLogin() {
        loginBtn.click();
        return new LoginPage();
    }

}