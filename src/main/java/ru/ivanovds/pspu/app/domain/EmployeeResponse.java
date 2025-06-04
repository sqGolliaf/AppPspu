package ru.ivanovds.pspu.app.domain;

import lombok.Builder;

import java.util.Hashtable;

@Builder
public record EmployeeResponse(
        Integer id,
        String text,
        Hashtable<String, Object> storage,
        String attribute
) {
}
