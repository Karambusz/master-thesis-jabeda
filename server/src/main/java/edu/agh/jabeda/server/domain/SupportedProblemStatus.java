package edu.agh.jabeda.server.domain;

public enum SupportedProblemStatus {
    PENDING(1, "Pending", "Pending"),
    ACCEPTED(2, "Accepted", "Accepted"),
    REJECTED(3, "Rejected", "Rejected"),
    DONE(4, "Done", "Done"),
    REPORTED(5, "Reported", "Reported");

    private final int id;
    private final String code;
    private final String name;

    SupportedProblemStatus(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
