package ch.etml.pl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(int numItem) {
        super("Item "+numItem+" inexistant ou déjà vendu");
    }
}
