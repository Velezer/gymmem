package ariefsyaifu.gymmem.user.dto.auth;

public class VerifyCheckResponse {

    public String status;

    public static VerifyCheckResponse valueOf(String status) {
        VerifyCheckResponse r = new VerifyCheckResponse();
        r.status = status;
        return r;
    }
    
}
