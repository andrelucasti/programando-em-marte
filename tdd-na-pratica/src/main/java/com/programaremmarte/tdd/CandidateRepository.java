package com.programaremmarte.tdd;

import java.util.List;
import java.util.UUID;

public interface CandidateRepository {
    List<CandidatoConfirmationStatus> fetchAllCandidatesWithStatus(CandidatoConfirmationStatus.Status status);
    Candidate findByMandatoryId(UUID id);
    List<Candidate> fetchAllByIds(List<UUID> uuids);
}
