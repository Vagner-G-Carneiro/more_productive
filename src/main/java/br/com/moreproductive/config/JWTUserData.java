package br.com.moreproductive.config;

import java.time.Instant;

public record JWTUserData(int userID, String email, Instant issuedAtAsInstant) {
}
