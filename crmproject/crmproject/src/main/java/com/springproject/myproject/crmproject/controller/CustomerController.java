package com.springproject.myproject.crmproject.controller;
import com.springproject.myproject.crmproject.model.Product;
import com.springproject.myproject.crmproject.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/customer-admin")
public class CustomerController {
    private final ProductRepository productRepository;
    public CustomerController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
