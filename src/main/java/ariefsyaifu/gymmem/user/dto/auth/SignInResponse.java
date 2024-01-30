package ariefsyaifu.gymmem.user.dto.auth;

public class SignInResponse {
    public String accessToken;
    public String refreshToken;

    public static SignInResponse valueOf(String accessToken, String refreshToken) {
        SignInResponse r = new SignInResponse();
        r.accessToken = accessToken;
        r.refreshToken = refreshToken;
        return r;
    }
}
