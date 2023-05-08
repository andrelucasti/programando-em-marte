package com.programaremmarte.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ResendEmailToCandidateTest {
    @Test
    void shouldResendEmailToCandidateWhenStatusIsFailed() {
        var candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        var candidateRepository = mock(CandidateRepository.class);
        var emailService = mock(EmailService.class);

        var failedStatuses = List.of(
                new CandidatoConfirmationStatus(UUID.randomUUID(), CandidatoConfirmationStatus.Status.FAILED),
                new CandidatoConfirmationStatus(UUID.randomUUID(), CandidatoConfirmationStatus.Status.FAILED),
                new CandidatoConfirmationStatus(UUID.randomUUID(), CandidatoConfirmationStatus.Status.FAILED));

        when(candidateRepository.fetchAllCandidatesWithStatus(CandidatoConfirmationStatus.Status.FAILED))
                .thenReturn(failedStatuses);

        var ids = failedStatuses.stream().map(CandidatoConfirmationStatus::candidateId).toList();

        var candidateList = ids.stream().map(
                id -> new Candidate(id, "Name-" + id, "name" + id + "@gmail.com")).toList();

        when(candidateRepository.fetchAllByIds(Mockito.eq(ids))).thenReturn(candidateList);

        var subject = new ResendEmailToCandidate(candidateRepository, emailService);
        subject.execute();

        verify(emailService, times(3)).sendEmailToCandidate(candidateArgumentCaptor.capture());

        var candidates = candidateArgumentCaptor.getAllValues();
        Assertions.assertArrayEquals(candidateList.toArray(), candidates.toArray());
    }
}