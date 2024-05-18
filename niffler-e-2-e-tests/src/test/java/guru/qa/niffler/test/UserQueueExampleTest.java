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

import static guru.qa.niffler.constant.Friendship.INVITATION_RECEIVED;
import static guru.qa.niffler.constant.Friendship.PENDING_INVITATION;
import static guru.qa.niffler.constant.Friendship.WITH_FRIENDS;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class UserQueueExampleTest {

    private final UiBot ui = new UiBot();

    @BeforeEach
    void login() {
        Selenide.open("http://127.0.0.1:3000/main");
    }

    @Test
    void userWithFriendTest1(@User(friendship = WITH_FRIENDS) UserJson userWithFriend) {
        ui.startPage()
                .clickLogin()
                .setUsername(userWithFriend.username())
                .setPassword(userWithFriend.testData().password())
                .clickSignIn()
                .clickAllPeople()
                .getRowWithUsername("duck")
                .assertThatActionHasStatus("You are friends");
    }

    @Test
    void userWithFriendTest2(@User(friendship = WITH_FRIENDS) UserJson userWithFriend) {
        ui.startPage()
                .clickLogin()
                .setUsername(userWithFriend.username())
                .setPassword(userWithFriend.testData().password())
                .clickSignIn()
                .clickAllPeople()
                .getRowWithUsername("duck")
                .assertThatActionHasStatus("You are friends");
    }

    @Test
    void pendingInvitationTest(
            @User(friendship = PENDING_INVITATION) UserJson sendInviteUser,
            @User(friendship = INVITATION_RECEIVED) UserJson receiveInviteUser) {
        ui.startPage()
                .clickLogin()
                .setUsername(sendInviteUser.username())
                .setPassword(sendInviteUser.testData().password())
                .clickSignIn()
                .clickAllPeople()
                .getRowWithUsername(receiveInviteUser.username())
                .assertThatActionHasStatus("Pending invitation");
    }

    @Test
    void receiveInvitationTest(
            @User(friendship = PENDING_INVITATION) UserJson sendInviteUser,
            @User(friendship = INVITATION_RECEIVED) UserJson receiveInviteUser) {
        ui.startPage()
                .clickLogin()
                .setUsername(receiveInviteUser.username())
                .setPassword(receiveInviteUser.testData().password())
                .clickSignIn()
                .clickAllPeople()
                .getRowWithUsername(sendInviteUser.username())
                .assertThatSubmitActionIsEnabled()
                .assertThatDeclineActionIsEnabled();
    }


    @Test
    void loginTest1(@User UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest2(@User UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest3(@User UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest4(@User UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest5(@User UserJson userJson) {
        ui.startPage()
                .clickLogin()
                .setUsername(userJson.username())
                .setPassword(userJson.testData().password())
                .clickSignIn()
                .assertThatAvatarIsVisible();
    }

}