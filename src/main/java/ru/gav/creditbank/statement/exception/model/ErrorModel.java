package ru.gav.creditbank.statement.exception.model;

public record ErrorModel(String message, String... additionalInfo) {
}
