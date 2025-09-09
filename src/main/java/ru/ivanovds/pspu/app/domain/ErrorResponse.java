package ru.ivanovds.pspu.app.domain;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(
        HttpStatus status,
        List<String> details
) {
}
