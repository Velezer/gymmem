package ariefsyaifu.gymmem.user.dto.auth;

public class SignInResponse {
    public String token;
    public String refreshToken;

    public static SignInResponse valueOf(String token, String refreshToken) {
        SignInResponse r = new SignInResponse();
        r.token = token;
        r.refreshToken = refreshToken;
        return r;
    }
}
