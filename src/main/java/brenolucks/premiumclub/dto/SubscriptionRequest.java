package brenolucks.premiumclub.dto;

import brenolucks.premiumclub.model.PlanType;

public record SubscriptionRequest(String email, PlanType planType) {
}
