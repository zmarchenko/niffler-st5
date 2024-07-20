package guru.qa.niffler.jupiter.extension.user;

import guru.qa.niffler.api.ThreadSafeCookieStore;
import guru.qa.niffler.api.auth.AuthApiClient;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import static guru.qa.niffler.jupiter.extension.ContextHolderExtension.context;

public class ApiLoginExtension implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiLoginExtension.class);
    private static final String CODE = "code";
    private static final String TOKEN = "token";
    private static final String CODE_CHALLENGE = "codeChallenge";
    private static final String CODE_VERIFIER = "codeVerifier";

    private final AuthApiClient authApiClient = new AuthApiClient();

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                ApiLogin.class
        ).ifPresent(annotation -> {
            if (!annotation.username().isEmpty() && !annotation.password().isEmpty()) {
                authApiClient.doLogin(annotation.username(), annotation.password());
            } else if (annotation.user() != null) {
                UserJson testUser = context.getStore(CreateUserExtension.NAMESPACE)
                        .get(context.getUniqueId(), UserJson.class);
                authApiClient.doLogin(testUser.username(), testUser.testData().password());
            } else {
                throw new NullPointerException("Не указаны данные для аутентификации");
            }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        ThreadSafeCookieStore.INSTANCE.removeAll();
    }

    public static void setToken(String token) {
        context().getStore(NAMESPACE).put(TOKEN, token);
    }

    public static String getToken() {
        return context().getStore(NAMESPACE).get(TOKEN, String.class);
    }

    public static void setCodeChallenge(String codeChallenge) {
        context().getStore(NAMESPACE).put(CODE_CHALLENGE, codeChallenge);
    }

    public static String getCodeChallenge() {
        return context().getStore(NAMESPACE).get(CODE_CHALLENGE, String.class);
    }

    public static void setCodeVerifier(String codeVerifier) {
        context().getStore(NAMESPACE).put(CODE_VERIFIER, codeVerifier);
    }

    public static String getCodeVerifier() {
        return context().getStore(NAMESPACE).get(CODE_VERIFIER, String.class);
    }

    public static void setCode(String code) {
        context().getStore(NAMESPACE).put(CODE, code);
    }

    public static String getCode() {
        return context().getStore(NAMESPACE).get(CODE, String.class);
    }

}