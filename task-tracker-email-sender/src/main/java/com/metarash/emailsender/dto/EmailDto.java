package com.metarash.emailsender.dto;

public record EmailDto (
    String recipient,
    String subject,
    String text
) {}