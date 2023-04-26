package edu.agh.jabeda.server.domain;

public enum SupportedProblemStatus {
    PENDING(1, "Pending"),
    ACCEPTED(2, "Accepted"),
    REJECTED(3, "Rejected"),
    DONE(4, "Done"),
    REPORTED(5, "Reported");

    private final int id;
    private final String code;

    SupportedProblemStatus(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

}
