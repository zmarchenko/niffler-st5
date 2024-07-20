package guru.qa.niffler.api.auth;

import guru.qa.niffler.model.TokenJson;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {

    @GET("oauth2/authorize")
    Call<Void> preRequest(
            @Query("response_type") String responseType,
            @Query("client_id") String clientId,
            @Query("scope") String scope,
            @Query(value = "redirect_uri", encoded = true) String redirectUri,
            @Query("code_challenge") String codeChallenge,
            @Query("code_challenge_method") String codeChallengeMethod
    );

    @POST("login")
    @FormUrlEncoded
    Call<Void> login(
            @Field("_csrf") String csrf,
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("oauth2/token")
    @FormUrlEncoded
    Call<TokenJson> token(
            @Header("Authorization") String basicAuthorizationHeader,
            @Field("code") String code,
            @Field(value = "redirect_uri", encoded = true) String redirectUri,
            @Field("code_verifier") String codeVerifier,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId
    );

}
