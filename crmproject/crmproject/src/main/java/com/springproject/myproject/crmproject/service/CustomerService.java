package com.springproject.myproject.crmproject.service;
import com.springproject.myproject.crmproject.model.Product;
import com.springproject.myproject.crmproject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {
    private final ProductRepository productRepository;

    public CustomerService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
