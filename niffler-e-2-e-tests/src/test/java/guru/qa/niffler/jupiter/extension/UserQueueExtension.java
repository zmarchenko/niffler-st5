package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.constant.Friendship;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static guru.qa.niffler.constant.Friendship.DEFAULT;
import static guru.qa.niffler.constant.Friendship.INVITATION_RECEIVED;
import static guru.qa.niffler.constant.Friendship.PENDING_INVITATION;
import static guru.qa.niffler.constant.Friendship.WITH_FRIENDS;
import static guru.qa.niffler.model.UserJson.simpleUser;


public class UserQueueExtension implements ParameterResolver {

    private static final Map<Friendship, UserJson> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put(WITH_FRIENDS, simpleUser("zhanna1", "test"));
        USERS.put(INVITATION_RECEIVED, simpleUser("dima", "test_user1"));
        USERS.put(PENDING_INVITATION, simpleUser("duck", "krya-krya2"));
        USERS.put(DEFAULT, simpleUser("tester", "qa_pass_666"));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User userAnnotation = parameterContext.findAnnotation(User.class).orElseThrow();
        Friendship friendshipType = userAnnotation.friendship();
        return USERS.get(friendshipType);
    }

}