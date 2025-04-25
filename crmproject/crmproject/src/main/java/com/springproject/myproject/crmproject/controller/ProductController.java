
package com.springproject.myproject.crmproject.controller;

import com.springproject.myproject.crmproject.model.Product;
import com.springproject.myproject.crmproject.model.User;
import com.springproject.myproject.crmproject.repository.UserRepository;
import com.springproject.myproject.crmproject.service.ProductService;
import com.springproject.myproject.crmproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService,   UserService userService) {
        this.productService = productService;
        this.userService=userService;
    }

    // Superadmins can view & manage products
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProductsForAdmins() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Optional<Map<String, Object>> result = productService.updateProduct(id, updatedProduct);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        Optional<Map<String, Object>> result = productService.deleteProduct(id);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/role-counts")
        public ResponseEntity<?> getUserCountByRole() {
            List<Map<String, Object>> result = userService.getUserCountByRole();
            return ResponseEntity.ok(result);
        }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
