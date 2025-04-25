package com.springproject.myproject.crmproject.repository;
import com.springproject.myproject.crmproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByNameContaining(String name); // Find products by partial name match
}
