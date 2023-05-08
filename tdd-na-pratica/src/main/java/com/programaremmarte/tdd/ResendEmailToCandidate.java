package com.programaremmarte.tdd;

public class ResendEmailToCandidate {

    private final CandidateRepository candidateRepository;
    private final EmailService emailService;

    public ResendEmailToCandidate(CandidateRepository candidateRepository,
                                  EmailService emailService) {
        this.candidateRepository = candidateRepository;
        this.emailService = emailService;
    }

    public void execute() {
        var ids = candidateRepository.fetchAllCandidatesWithStatus(CandidatoConfirmationStatus.Status.FAILED)
                .stream()
                .map(CandidatoConfirmationStatus::candidateId)
                .toList();

       var candidates = candidateRepository.fetchAllByIds(ids);

        candidates.forEach(emailService::sendEmailToCandidate);

    }
}
