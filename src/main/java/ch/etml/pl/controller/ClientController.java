package ch.etml.pl.controller;

import ch.etml.pl.dao.ClientRepository;
import ch.etml.pl.dto.Client;
import ch.etml.pl.dto.ClientNew;
import ch.etml.pl.entities.ClientEntity;
import ch.etml.pl.exceptions.ClientNotFoundException;
import ch.etml.pl.exceptions.DuplicateClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping (value="/clients")
    public ResponseEntity<Client> getClientByPrenom(@RequestParam String prenom) throws ClientNotFoundException {
        Optional<ClientEntity> opt = clientRepository.findClientEntitiesByPrenomEquals(prenom);
        if (opt.isEmpty()) {
            throw new ClientNotFoundException(prenom);
        }
        ClientEntity clientEntity = opt.get();
        Client client = new Client(clientEntity.getNum(),
                 clientEntity.getPrenom(), clientEntity.getSolde());
        return new ResponseEntity<Client>(client, HttpStatus.OK );
    }

    @PostMapping(value="/clients")
    public ResponseEntity<Client> insertClient(@RequestBody ClientNew clientNew)  {
        Optional<ClientEntity> opt = clientRepository.findClientEntitiesByPrenomEquals(clientNew.getPrenom());
        if (opt.isPresent()) {
            throw new DuplicateClientException(clientNew.getPrenom());
        }
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setPrenom(clientNew.getPrenom());
        clientEntity.setSolde(new BigDecimal(50));
        ClientEntity clientEntityNew = clientRepository.save(clientEntity);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{num}")
                .buildAndExpand(clientEntityNew.getNum())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
