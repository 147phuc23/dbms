package vn.elca.training.model.exception;

public class ConcurrentUpdateException extends RuntimeException {

    public ConcurrentUpdateException(String exception){
        super(exception);
    }

}
