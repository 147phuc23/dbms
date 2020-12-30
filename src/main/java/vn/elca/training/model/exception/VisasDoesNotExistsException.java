package vn.elca.training.model.exception;

import java.util.List;

public class VisasDoesNotExistsException extends RuntimeException {

    List<String> visas;

    public VisasDoesNotExistsException(String exception, List<String> visas){
        super(exception);
        this.visas = visas;
    }

    public List<String> getVisas() {
        return visas;
    }

}