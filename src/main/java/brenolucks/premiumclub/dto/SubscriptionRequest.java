package brenolucks.premiumclub.dto;

import brenolucks.premiumclub.model.PlanType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
        @Email(message = "Email is not valid!", regexp = "^[a-zA-Z0-9!#$%&'*+\\-=?^_`{|}~]+(?:\\.[a-zA-Z0-9!#$%&'*+\\-=?^_`{|}~]+)*" +
                "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$")
        @NotBlank(message = "This field cannot be empty!")
        @NotNull
        String email,
        @NotBlank(message = "This field cannot be empty!")
        @NotNull(message = "This field cannot be null!")
        PlanType planType) {
}
