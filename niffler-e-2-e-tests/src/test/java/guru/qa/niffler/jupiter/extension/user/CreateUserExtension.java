package guru.qa.niffler.jupiter.extension.user;

import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.TestUser;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Method;

public abstract class CreateUserExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreateUserExtension.class);

    abstract UserJson createUser(UserJson user);

    @Override
    public void beforeEach(ExtensionContext context) {
        Method method = context.getRequiredTestMethod();

        if (method.getAnnotation(TestUser.class) != null ||
                method.getAnnotation(ApiLogin.class).user() != null) {
            UserJson userJson = UserJson.randomUser();
            context.getStore(NAMESPACE).put(context.getUniqueId(), createUser(userJson));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class) && extensionContext.getTestMethod().orElseThrow().isAnnotationPresent(TestUser.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserJson.class);
    }

}