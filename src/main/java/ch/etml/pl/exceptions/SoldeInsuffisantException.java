package ch.etml.pl.exceptions;

import ch.etml.pl.dto.Client;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class SoldeInsuffisantException extends RuntimeException {
    public SoldeInsuffisantException(Client client) {
        super(client.getPrenom() + " n'a pas assez avec le solde " + client.getSolde());
    }
}
