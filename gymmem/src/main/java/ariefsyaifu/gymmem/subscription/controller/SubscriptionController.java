package ariefsyaifu.gymmem.subscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ariefsyaifu.gymmem.subscription.dto.subscription.PatchSubscriptionRequestBody;
import ariefsyaifu.gymmem.subscription.dto.subscription.SubscribeRequestBody;
import ariefsyaifu.gymmem.subscription.service.SubscriptionService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    private SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<Object> getSubscriptions(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        return ResponseEntity.ok(subscriptionService.getSubscriptions(claims));
    }

    @PostMapping
    public ResponseEntity<Object> subscribe(
            @Valid @RequestBody SubscribeRequestBody params, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        subscriptionService.subscribe(params, claims);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchSubscription(
            @PathVariable String id,
            @Valid @RequestBody PatchSubscriptionRequestBody params,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        subscriptionService.patchSubscription(id, params,claims);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> unsubscribe(@PathVariable String id, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        subscriptionService.unsubscribe(id, claims);
        return ResponseEntity.noContent().build();

    }

}
