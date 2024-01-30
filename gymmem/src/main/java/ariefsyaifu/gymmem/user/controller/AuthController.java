package ariefsyaifu.gymmem.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ariefsyaifu.gymmem.user.dto.auth.RefreshTokenRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.ResetRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.SignInRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.SignInResponse;
import ariefsyaifu.gymmem.user.dto.auth.SignUpRequestBody;
import ariefsyaifu.gymmem.user.dto.auth.VerifyCheckResponse;
import ariefsyaifu.gymmem.user.dto.auth.VerifyEmailRequestBody;
import ariefsyaifu.gymmem.user.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequestBody params) {
        authService.signUp(params);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verification")
    public ResponseEntity<VerifyCheckResponse> verificationCheck(
            @RequestParam String email) {
        VerifyCheckResponse r = authService.verifyCheck(email);
        return ResponseEntity.ok(r);
    }

    @PutMapping("/verification")
    public ResponseEntity<Void> verificationEmail(@Valid @RequestBody VerifyEmailRequestBody params) {
        authService.verifyEmail(params);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequestBody params) {
        SignInResponse r = authService.signIn(params);
        return ResponseEntity.ok(r);
    }

    @PatchMapping("/reset")
    public ResponseEntity<Void> reset(@Valid @RequestBody ResetRequestBody params) {
        authService.reset(params);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<SignInResponse> refresh(@Valid @RequestBody RefreshTokenRequestBody params) {
        return ResponseEntity.ok(authService.refresh(params));
    }

}
