package edu.agh.jabeda.server.domain.exception;

public class ReportedProblemNotFoundException extends JabedaNotFoundException{

    public ReportedProblemNotFoundException(int id) {
        super(String.format("No problem reported with id=<%d>!", id), JabedaErrorCode.REPORTED_PROBLEM_NOT_FOUND);
    }
}
