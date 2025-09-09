package ru.ivanovds.pspu.app.model;

import java.util.regex.Pattern;

public class FieldChecker {
    private final Pattern pattern;

    public FieldChecker(String regex) {
        this.pattern = Pattern.compile(regex, Pattern.UNICODE_CASE);
    }

    public boolean isValid(String input) {
        return pattern.matcher(input.trim()).matches();
    }

    public String sanitizeAuthor(String input) {
        input = input.trim().replaceAll("\\s+", " ");
        input = input.replaceAll("([А-ЯЁ])\\.([А-ЯЁ])\\.", "$1. $2.");
        input = input.substring(0, 1).toUpperCase() + input.substring(1);
        return input;
    }

    public String sanitizePageCount(String input) {
        return input.replaceAll("[^0-9]", "") + " с.";
    }
}
