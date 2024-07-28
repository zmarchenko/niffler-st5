package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.ui.page.MainPage;
import guru.qa.niffler.ui.page.StartPage;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class LoginTest extends BaseWebTest {

    @Test
    @ApiLogin(user = @TestUser)
    void calendarTest() {
        Selenide.open(StartPage.URL, MainPage.class)
                .setDate(new Date());
    }
}