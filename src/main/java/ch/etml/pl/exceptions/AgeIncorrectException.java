package ch.etml.pl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AgeIncorrectException extends RuntimeException {
    public AgeIncorrectException(String description) {
        super ("trop jeune pour acheter " + description);
    }
}
