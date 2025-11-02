package br.com.moreproductive.dto;

import java.time.Instant;

public record JWTUserDataDTO(int userID, String email, Instant issuedAtAsInstant) {
}
