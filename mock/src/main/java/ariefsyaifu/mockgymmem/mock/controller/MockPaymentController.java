package ariefsyaifu.mockgymmem.mock.controller;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mock/payment")
public class MockPaymentController {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    @PostMapping("/r3")
    public ResponseEntity<Map<String, Object>> r3(
            @RequestBody Map<String, Object> params) throws InterruptedException {
        Thread.sleep(1000);
        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "timestamp", formatter.format(Instant.now()),
                "params", params));
    }

    @PostMapping("/r4")
    public ResponseEntity<Map<String, Object>> r4(
            @RequestBody Map<String, Object> params) throws InterruptedException {
        Thread.sleep(2000);
        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "timestamp", formatter.format(Instant.now()),
                "params", params));
    }

    @PostMapping("/r5")
    public ResponseEntity<Map<String, Object>> r5(
            @RequestBody Map<String, Object> params) throws InterruptedException {
        Thread.sleep(3000);
        return ResponseEntity.ok(Map.of(
                "status", "SUCCESS",
                "timestamp", formatter.format(Instant.now()),
                "params", params));
    }

}
