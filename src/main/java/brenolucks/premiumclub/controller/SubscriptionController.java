package brenolucks.premiumclub.controller;

import brenolucks.premiumclub.dto.SubscriptionRequest;
import brenolucks.premiumclub.service.StripeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Logger;

@RestController
public class SubscriptionController {
    @Autowired
    StripeServiceImpl stripeService;

    @PostMapping("/api/subscription")
    public ResponseEntity<Map<String, String>> createSubscription(@RequestBody SubscriptionRequest subscriptionRequest) {
        System.out.println(subscriptionRequest.planType());
        String priceID = stripeService.getPriceByPlanType(subscriptionRequest.planType().toString());
        System.out.println(priceID);
        String checkoutURL = stripeService.createSubscription(subscriptionRequest.email(), priceID);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("checkout_url", checkoutURL));
    }

}
