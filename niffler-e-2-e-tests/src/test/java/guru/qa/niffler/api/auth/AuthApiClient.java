package guru.qa.niffler.api.auth;

import guru.qa.niffler.api.ApiClient;
import guru.qa.niffler.api.CodeInterceptor;
import guru.qa.niffler.api.ThreadSafeCookieStore;
import guru.qa.niffler.jupiter.extension.user.ApiLoginExtension;
import guru.qa.niffler.model.TokenJson;
import guru.qa.niffler.utils.OauthUtils;
import lombok.SneakyThrows;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class AuthApiClient extends ApiClient {

    private final AuthApi authApi;

    public AuthApiClient() {
        super(
                CONFIG.authUrl(),
                true,
                JacksonConverterFactory.create(),
                HttpLoggingInterceptor.Level.BODY,
                new CodeInterceptor()
        );
        this.authApi = retrofit.create(AuthApi.class);
    }

    @SneakyThrows
    public void doLogin(String username, String password) {
        final String codeVerifier = OauthUtils.generateCodeVerifier();
        final String codeChallenge = OauthUtils.generateCodeChallange(codeVerifier);

        authApi.preRequest(
                "code",
                "client",
                "openid",
                CONFIG.frontUrl() + "/authorized",
                codeChallenge,
                "S256"
        ).execute();

        authApi.login(
                ThreadSafeCookieStore.INSTANCE.getCookieValue("XSRF-TOKEN"),
                username,
                password
        ).execute();

        TokenJson response = authApi.token(
                "Basic " + Base64.getEncoder().encodeToString("client:secret".getBytes(StandardCharsets.UTF_8)),
                ApiLoginExtension.getCode(),
                CONFIG.frontUrl() + "/authorized",
                codeVerifier,
                "authorization_code",
                "client"
        ).execute().body();

        ApiLoginExtension.setToken(Objects.requireNonNull(response).idToken());
    }
}
