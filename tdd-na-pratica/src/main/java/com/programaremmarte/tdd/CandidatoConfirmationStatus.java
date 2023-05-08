package com.programaremmarte.tdd;

import java.util.UUID;

public record CandidatoConfirmationStatus(UUID candidateId, Status status) {

    public enum Status {
        CONFIRMED,
        PENDING,
        REJECTED,
        FAILED
    }
}
