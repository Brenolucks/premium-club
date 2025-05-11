package brenolucks.premiumclub.dto;

import brenolucks.premiumclub.model.PlanType;
import jakarta.validation.constraints.*;

public record SubscriptionRequest(
        @Email(message = "Email is not valid!", regexp = "^[a-zA-Z0-9!#$%&'*+\\-=?^_`{|}~]+(?:\\.[a-zA-Z0-9!#$%&'*+\\-=?^_`{|}~]+)*" +
                "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$")
        @NotBlank(message = "This field cannot be empty!")
        @NotNull(message = "This field cannot be null!")
        String email,
        @NotNull(message = "This field cannot be null!")
        @NotBlank(message = "This field cannot be empty!")
        String planType) {
}
