package com.springproject.myproject.crmproject.service;
//package com.springproject.myproject.crmproject.service;

import com.springproject.myproject.crmproject.model.Product;
import com.springproject.myproject.crmproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Map<String, Object>> updateProduct(Long id, Product updatedProduct) {
        if (!id.equals(updatedProduct.getId())) {
            return Optional.of(Map.of("message", "ID in URL and request body do not match"));
        }

        return productRepository.findById(id).map(existing -> {
            existing.setName(updatedProduct.getName());
            existing.setPrice(updatedProduct.getPrice());
            existing.setDescription(updatedProduct.getDescription());
            Product savedProduct = productRepository.save(existing);
            return Map.of("message", "Updated successfully", "data", savedProduct);
        });
    }

    public Optional<Map<String, Object>> deleteProduct(Long id) {
        return productRepository.findById(id).map(existing -> {
            productRepository.deleteById(id);
            return Map.of("message", "Deletion successful", "data", existing);
        });
    }
}
