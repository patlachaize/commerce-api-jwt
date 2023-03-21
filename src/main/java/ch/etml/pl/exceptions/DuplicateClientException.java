package ch.etml.pl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateClientException extends RuntimeException {
    public DuplicateClientException(String prenom) {
        super("Client "+prenom+" déjà existant");
    }
}
