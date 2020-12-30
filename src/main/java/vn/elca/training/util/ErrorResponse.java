package vn.elca.training.util;

public class ErrorResponse {

    // General error message about nature of error
    private String message;

    // Specific errors in API request processing
    private String details;

    public ErrorResponse(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
