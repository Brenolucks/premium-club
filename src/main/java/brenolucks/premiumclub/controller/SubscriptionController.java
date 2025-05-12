package brenolucks.premiumclub.controller;

import brenolucks.premiumclub.dto.SubscriptionRequest;
import brenolucks.premiumclub.service.stripe.StripeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SubscriptionController {
    @Autowired
    StripeServiceImpl stripeService;

    @PostMapping("/api/subscription")
    public ResponseEntity<Map<String, String>> createSubscription(@RequestBody @Valid SubscriptionRequest subscriptionRequest) {
        String priceID = stripeService.getPriceByPlanType(subscriptionRequest.planType());

        String checkoutURL = stripeService.createSubscription(subscriptionRequest.email(), priceID, subscriptionRequest.planType());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("checkout_url", checkoutURL));
    }

    @GetMapping("/api/checkout-ok")
    public ResponseEntity<String> checkoutOk() {
        return ResponseEntity.status(HttpStatus.OK).body("PAYMENT SUCCESS ENJOY IT!");
    }
}
