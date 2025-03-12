package edu.bbte.idde.krim2244.dataaccess.exception;

public class RepoNotFoundException extends RuntimeException {

    public RepoNotFoundException() {
        super("Repository not found.");
    }

    public RepoNotFoundException(String message) {
        super(message);
    }

    public RepoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepoNotFoundException(Throwable cause) {
        super("Repository not found due to an underlying cause.", cause);
    }
}