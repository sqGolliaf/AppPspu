package ru.ivanovds.sg.app.domain;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(
        HttpStatus status,
        List<String> details
) {
}
