package ariefsyaifu.gymmem.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ariefsyaifu.gymmem.payment.dto.CreatePaymentRequestBody;
import ariefsyaifu.gymmem.payment.dto.CreatePaymentResponse;
import ariefsyaifu.gymmem.payment.dto.PatchPaymentRequestBody;
import ariefsyaifu.gymmem.payment.service.TransactionHistoryService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payment/transaction/history")
public class TransactionHistoryController {

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    private TransactionHistoryService transactionHistoryService;

    @GetMapping
    public ResponseEntity<Object> getPayments(
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return ResponseEntity.ok(transactionHistoryService.getPayments(claims));
    }

    @PostMapping
    public ResponseEntity<Object> createPayment(
            @Valid @RequestBody CreatePaymentRequestBody params,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        CreatePaymentResponse r = transactionHistoryService.createPayment(params, claims);
        return ResponseEntity.ok(r);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchPayment(
            @PathVariable String id,
            @Valid @RequestBody PatchPaymentRequestBody params,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        transactionHistoryService.patchPayment(id, params, claims);
        return ResponseEntity.noContent().build();
    }

}
