package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.UiBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.constant.Friendship.INVITATION_RECEIVED;
import static guru.qa.niffler.constant.Friendship.PENDING_INVITATION;
import static guru.qa.niffler.constant.Friendship.WITH_FRIENDS;

@ExtendWith(UserQueueExtension.class)
public class UserQueueExampleTest {

    private final UiBot ui = new UiBot();

    @BeforeEach
    void login() {
        Selenide.open("http://127.0.0.1:3000/main");
    }

    @Test
    void userWithFriendTest(@User(friendship = WITH_FRIENDS) UserJson userWithFriend,
                            @User() UserJson secondUserWithFriend) {
        ui.startPage()
                .login(userWithFriend)
                .clickAllPeople()
                .getRowWithUsername(secondUserWithFriend.username())
                .assertThatActionHasStatus(WITH_FRIENDS.getMessage());
    }

    @Test
    void invitationReceivedTest(
            @User(friendship = PENDING_INVITATION) UserJson requester,
            @User(friendship = INVITATION_RECEIVED) UserJson addressee) {
        ui.startPage()
                .login(addressee)
                .clickAllPeople()
                .getRowWithUsername(requester.username())
                .assertThatSubmitActionIsEnabled()
                .assertThatDeclineActionIsEnabled();
    }

    @Test
    void invitationSentTest(
            @User(friendship = PENDING_INVITATION) UserJson requester,
            @User(friendship = INVITATION_RECEIVED) UserJson addressee) {
        ui.startPage()
                .login(requester)
                .clickAllPeople()
                .getRowWithUsername(addressee.username())
                .assertThatActionHasStatus(PENDING_INVITATION.getMessage());
    }


    @Test
    void loginTest1(@User UserJson userJson) {
        ui.startPage()
                .login(userJson)
                .assertThatAvatarIsVisible();
    }

    @Test
    void loginTest2(@User UserJson userJson) {
        ui.startPage()
                .login(userJson)
                .assertThatAvatarIsVisible();
    }

}