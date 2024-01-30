package ariefsyaifu.gymmem.otp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ariefsyaifu.gymmem.otp.dto.GenerateOtpRequestBody;
import ariefsyaifu.gymmem.otp.service.OtpService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    private OtpService otpService;

    @PostMapping
    public ResponseEntity<Object> generate(@Valid @RequestBody GenerateOtpRequestBody params) {
        return ResponseEntity.ok(otpService.generate(params));
    }

}
