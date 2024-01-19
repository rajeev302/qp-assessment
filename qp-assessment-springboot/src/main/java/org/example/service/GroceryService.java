package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.entity.Grocery;
import org.example.repository.GroceryRepository;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryRepository groceryRepository){
        this.groceryRepository = groceryRepository;
    }

    public ResponseEntity<Grocery> createGrocery(Grocery groceryData){
        Grocery newGrocery = groceryRepository.save(groceryData);
        return ResponseEntity.ok(newGrocery);
    }

    public List<Grocery> getAllGrocery(){
        return groceryRepository.findAll();
    }

    public Optional<Grocery> getGroceryById(Long groceryId){
        return groceryRepository.findById(groceryId);
    }

    public Grocery updateGrocery(Long id, Grocery groceryData){
        if (id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        Grocery existingGrocery = groceryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );
        existingGrocery.setName(groceryData.getName());
        existingGrocery.setPrice(groceryData.getPrice());
        existingGrocery.setQuantity(groceryData.getQuantity());
        return groceryRepository.save(existingGrocery);
    }

    public String deleteGrocery(Long groceryId){
        groceryRepository.deleteById(groceryId);
        return "grocery deleted successfully";
    }

    public Integer getInventoryLevel(Long groceryId){
        if (groceryId == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        Grocery grocery = groceryRepository.findById(groceryId).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(groceryId))
        );
        return grocery.getInventoryLevel();
    }

    public Integer updateInventoryLevel(Long groceryId, Integer inventoryLevel){
        if (groceryId == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        Grocery existingGrocery = groceryRepository.findById(groceryId).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(groceryId))
        );
        existingGrocery.setInventoryLevel(inventoryLevel);
        Grocery savedGrocery = groceryRepository.save(existingGrocery);
        return savedGrocery.getInventoryLevel();
    }

    public Optional<Long> bookGrocery(Long userId, Long groceryId){
        Optional<Grocery> grocery = getGroceryById(groceryId);
        if (grocery.isPresent()){
            grocery.get().setUserBookedId(userId);
            updateGrocery(grocery.get().getId(), grocery.get());
            return Optional.of(grocery.get().getId());
        } else {
            return Optional.empty();
        }
    }

    public String bookMultipleGrocery(Long userId, List<Long> groceryList){
        for (Long id: groceryList){
            Optional<Grocery> grocery = getGroceryById(id);
            if (grocery.isPresent()){
                grocery.get().setUserBookedId(userId);
                updateGrocery(grocery.get().getId(), grocery.get());
            }
        }
        return "multiple booking success";
    }

}
