package org.example.controller;

import org.example.entity.Grocery;
import org.example.service.GroceryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
public class GroceryController {
    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    /**
     * test api
     */

    @GetMapping("test")
    public ResponseEntity<String> getTestResult(){
        return ResponseEntity.ok("server working");
    }

    /**
     * Below are the APIs exposed for the admin
     */

    @PostMapping("admin/addGrocery")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Grocery> addNewGroceryItem(@RequestBody Grocery grocery) {
        System.out.println("grocery is " + grocery);
        return groceryService.createGrocery(grocery);
    }

    @GetMapping("admin/getAllGrocery")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Grocery>> getAllAdminGroceryItems() {
        return ResponseEntity.ok(groceryService.getAllGrocery());
    }

    @DeleteMapping("admin/{groceryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> removeGroceryItem(@PathVariable(name = "groceryId") Long groceryId) {
        return ResponseEntity.ok(groceryService.deleteGrocery(groceryId));
    }

    @PutMapping("admin/update/{groceryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Grocery> updateGroceryDetails(@PathVariable(name = "groceryId") Long groceryId, @RequestBody Grocery newGroceryData) {
        return ResponseEntity.ok(groceryService.updateGrocery(groceryId, newGroceryData));
    }

    @GetMapping("admin/{groceryId}/getInventoryLevel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> getInventoryLevel(@PathVariable(name = "groceryId") Long groceryId) {
        Integer inventoryLevel = groceryService.getInventoryLevel(groceryId);
        return ResponseEntity.ok(inventoryLevel);
    }

    @PutMapping("admin/{groceryId}/updateInventoryLevel/{inventoryLevel}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Integer> updateInventoryLevel(@PathVariable(name = "groceryId") Long groceryId, @PathVariable(name = "inventoryLevel") Integer inventoryLevel) {
        Integer updatedInventoryLevel = groceryService.updateInventoryLevel(groceryId, inventoryLevel);
        return ResponseEntity.ok(updatedInventoryLevel);
    }


    /**
     * Below are the APIs exposed for the user
     */

    @GetMapping("user/getAllGrocery")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Grocery>> getAllConsumerGroceryItems() {
        return ResponseEntity.ok(groceryService.getAllGrocery());
    }

    @PutMapping("user/{userId}/bookGrocery/{groceryId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Optional<Long>> bookGrocery(@PathVariable(name = "userId") Long userId, @PathVariable(name = "groceryId") Long groceryId) {
        return ResponseEntity.ok(groceryService.bookGrocery(userId, groceryId));
    }

    @PutMapping("user/{userId}/bookMultipleGrocery")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> bookMultipleGrocery(@PathVariable(name = "userId") Long userId, @RequestBody List<Long> groceryList) {
        return ResponseEntity.ok(groceryService.bookMultipleGrocery(userId, groceryList));
    }
}
