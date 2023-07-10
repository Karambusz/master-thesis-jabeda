package edu.agh.jabeda.server.domain;

public enum SupportedProblemStatus {
    PENDING(1, "Zgłoszono", "Pending"),
    ACCEPTED(2, "W trakcie", "Accepted"),
    REJECTED(3, "Odrzucono", "Rejected"),
    DONE(4, "Skończono", "Done");

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
