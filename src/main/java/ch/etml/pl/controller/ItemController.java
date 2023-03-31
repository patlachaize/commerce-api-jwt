package ch.etml.pl.controller;

import ch.etml.pl.dao.ItemRepository;
import ch.etml.pl.dto.ClientNew;
import ch.etml.pl.entities.ClientEntity;
import ch.etml.pl.entities.ItemEntity;
import ch.etml.pl.dto.Client;
import ch.etml.pl.dto.Item;
import ch.etml.pl.exceptions.ItemNotFoundException;
import ch.etml.pl.service.CommerceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CommerceService commerceService;


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

    @PutMapping(value="/items/{numItem}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Item> achete(@PathVariable int numItem, HttpServletRequest request)
            throws ItemNotFoundException {
        String prenom = (String) request.getAttribute("prenom");
        int age = (Integer) request.getAttribute("age");
        Item item = commerceService.achete(prenom,age,numItem);
        return new ResponseEntity<Item>(item,HttpStatus.CREATED);
    }
}
