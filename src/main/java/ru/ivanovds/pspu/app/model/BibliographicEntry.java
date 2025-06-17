package ru.ivanovds.pspu.app.model;

public record BibliographicEntry(
    String authors,
    String title,
    String edition,
    String city,
    String publisher,
    String year,
    String pages
) {
    public String buildEntry() {
        return String.format("%s %s. — %s. — %s: %s, %s. — %s.",
                authors, title, edition == null || edition.isEmpty() ? "—" : edition,
                city, publisher, year, pages);
    }
}
