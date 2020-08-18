package dev.codesoapbox.dummy4j.exceptions;

public class UrlCouldNotBeCreatedException extends RuntimeException {

    public UrlCouldNotBeCreatedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Url could not be created due to the following error: [ " + getCause().getMessage() + " ]";
    }
}
