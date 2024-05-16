package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.model.UserJson.simpleUser;
import static io.qameta.allure.Allure.getLifecycle;


//любой тест проходит через него
public class UserQueueExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static final Queue<UserJson> USERS = new ConcurrentLinkedDeque<>();
    private static final Queue<UserJson> INVITATION_SEND = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> INVITATION_RECEIVED = new ConcurrentLinkedQueue<>();
    private static final Queue<UserJson> WITH_FRIENDS = new ConcurrentLinkedQueue<>();


    //TODO: подготовить тестовых пользователей
    //TODO: пользователей сложить в очереди
    //TODO: нужно научить этот класс получать параметр аннотации
    //TODO: нужно складывать в стор юзера из соответствующей очереди
    //TODO: можно ли в аннотации задать параметру значение по умолчанию?
    //TODO: один парень оптимизировал очереди - может, и у меня получится?


    static {
        USERS.add(simpleUser("zhanna1", "test"));
        USERS.add(simpleUser("dima", "test"));
        USERS.add(simpleUser("duck", "test"));

        INVITATION_SEND.add();
        INVITATION_SEND.add();
        INVITATION_SEND.add();

        INVITATION_RECEIVED.add();
        INVITATION_RECEIVED.add();
        INVITATION_RECEIVED.add();

        WITH_FRIENDS.add();
        WITH_FRIENDS.add();
        WITH_FRIENDS.add();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        UserJson userForTest = null;

        while (userForTest == null) {
            userForTest = USERS.poll();
        }
        getLifecycle().updateTestCase(
                testCase -> {
                    testCase.setStart(new Date().getTime());
                }
        );
        context.getStore(NAMESPACE).put(context.getUniqueId(), userForTest);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        UserJson userFromTest = context.getStore(NAMESPACE).get(context.getUniqueId(), UserJson.class);
        USERS.add(userFromTest);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserJson.class);
    }

}