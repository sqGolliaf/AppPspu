package ru.ivanovds.pspu.app.domain;

import java.util.Hashtable;

public record EmployeeResponse(
        Integer id,
        String text,
        Hashtable<String, Object> storage,
        String attribute
) {
}
