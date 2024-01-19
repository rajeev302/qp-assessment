package org.example.repository;

import org.example.entity.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GroceryRepository extends JpaRepository<Grocery, Long> {
}
