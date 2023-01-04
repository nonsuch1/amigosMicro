package com.nonsuch1.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
