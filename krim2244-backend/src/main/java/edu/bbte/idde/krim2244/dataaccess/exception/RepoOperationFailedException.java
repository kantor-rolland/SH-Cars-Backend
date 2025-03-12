package edu.bbte.idde.krim2244.dataaccess.exception;

public class RepoOperationFailedException extends RuntimeException {

    public RepoOperationFailedException() {
        super("Repository operation failed.");
    }

    public RepoOperationFailedException(String message) {
        super(message);
    }

    public RepoOperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepoOperationFailedException(Throwable cause) {
        super("Repository operation failed due to an underlying cause.", cause);
    }
}
