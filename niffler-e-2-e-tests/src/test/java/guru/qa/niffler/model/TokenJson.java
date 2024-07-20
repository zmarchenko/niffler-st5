package guru.qa.niffler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenJson(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("scope") String scope,
        @JsonProperty("id_token") String idToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") String expiresIn) {

}