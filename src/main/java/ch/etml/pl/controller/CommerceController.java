package ch.etml.pl.controller;

import ch.etml.pl.dao.ItemRepository;
import ch.etml.pl.entities.ClientEntity;
import ch.etml.pl.entities.ItemEntity;
import ch.etml.pl.dto.Client;
import ch.etml.pl.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommerceController {

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping(value="/items")
    public ResponseEntity<List<Item>> getItems() {
        List<ItemEntity> itemEntities = itemRepository.findAll();
        ArrayList<Item> items = new ArrayList<>();
        for (ItemEntity itemEntity : itemEntities) {
            Client client = null;
            ClientEntity clientEntity = itemEntity.getClient();
            if (clientEntity != null) {
                client = new Client(
                        clientEntity.getNum(), clientEntity.getPrenom(), clientEntity.getSolde());
            }
            Item item = new Item(itemEntity.getNum(), itemEntity.getDescription(),
                    itemEntity.getPrix(), client);
            items.add(item);
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK );
    }

}
