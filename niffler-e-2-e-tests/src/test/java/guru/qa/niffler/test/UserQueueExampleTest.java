package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.UiBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.constant.Action.INVITATION_RECEIVED;
import static guru.qa.niffler.constant.Action.INVITATION_SEND;
import static guru.qa.niffler.constant.Action.WITH_FRIENDS;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class UserQueueExampleTest {

    private final UiBot ui = new UiBot();

    @BeforeEach
    void login() {
        Selenide.open("http://127.0.0.1:3000/main");
    }

    @Test
    void test1(@User(action = WITH_FRIENDS) UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .clickAllPeople()
                .getRowWithUsername(userJson.username())
                .assertThatActionHasStatus("You are friends");
    }

    @Test
    void test2(
            @User(action = INVITATION_SEND) UserJson userSend,
            @User(action = INVITATION_RECEIVED) UserJson userReceived) {
        ui.startPage()
                .clickLogin()
                .login(userSend)
                .clickAllPeople()
                .getRowWithUsername(userReceived.username())
                .assertThatActionHasStatus("Pending invitation");
    }

    @Test
    void test3(@User(action = INVITATION_SEND) UserJson userSend,
               @User(action = INVITATION_RECEIVED) UserJson userReceived) {
        ui.startPage()
                .clickLogin()
                .login(userReceived)
                .clickAllPeople()
                .getRowWithUsername(userSend.username())
                .assertThatSubmitActionIsEnabled()
                .assertThatDeclineActionIsEnabled();
    }

    @Test
    void loginTest1(UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest2(UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest3(UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest4(UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest5(UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

}