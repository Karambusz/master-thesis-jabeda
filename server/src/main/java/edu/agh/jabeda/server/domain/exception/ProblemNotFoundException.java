package edu.agh.jabeda.server.domain.exception;

public class ProblemNotFoundException extends JabedaNotFoundException {

    public ProblemNotFoundException(int id) {
        super(String.format("Problem with id=<%d> not found in the category!", id),
                JabedaErrorCode.PROBLEM_NOT_FOUND);
    }
}