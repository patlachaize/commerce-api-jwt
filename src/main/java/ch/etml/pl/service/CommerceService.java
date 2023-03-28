package ch.etml.pl.service;

import ch.etml.pl.dao.ClientRepository;
import ch.etml.pl.dao.ItemRepository;
import ch.etml.pl.dto.Client;
import ch.etml.pl.dto.Item;
import ch.etml.pl.entities.ClientEntity;
import ch.etml.pl.entities.ItemEntity;
import ch.etml.pl.exceptions.ItemNotFoundException;
import ch.etml.pl.exceptions.SoldeInsuffisantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CommerceService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Item achete(String prenom, int numItem) throws ItemNotFoundException {
        Optional<ItemEntity> optItem = itemRepository.findItemEntityByNumAndClientIsNull(numItem);
        if (optItem.isEmpty()) {
            throw new ItemNotFoundException(numItem);
        }
        ItemEntity itemEntity = optItem.get();
        Optional<ClientEntity> optClient = clientRepository.findClientEntitiesByPrenomEquals(prenom);
        ClientEntity clientEntity = null;
        if (optClient.isPresent()) {
            clientEntity = optClient.get();
        } else {
            clientEntity = new ClientEntity();
            clientEntity.setPrenom(prenom);
            clientEntity.setSolde(new BigDecimal(50));
            clientEntity = clientRepository.save(clientEntity);
        }
        BigDecimal soldeNew = clientEntity.getSolde().subtract(itemEntity.getPrix());
        if (soldeNew.intValue() < 0) {
            throw new SoldeInsuffisantException(new Client(
                    clientEntity.getNum(), clientEntity.getPrenom(), clientEntity.getSolde()
            ));
        }
        clientEntity.setSolde(soldeNew);
        itemEntity.setClient(clientEntity);
        Client client = new Client(clientEntity.getNum(), clientEntity.getPrenom(), clientEntity.getSolde());
        Item item = new Item(itemEntity.getNum(), itemEntity.getDescription(), itemEntity.getPrix(),client);
        return item;
    }

}
