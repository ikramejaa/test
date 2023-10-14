package com.example.GestionStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {



        @Autowired
        private ProductRepo productRepo;

        @GetMapping("/all")
        public List<Product> getAllProducts() {
            return productRepo.findAll();
        }

        @PostMapping("/add")
        public Product createProduct(@RequestBody Product product) {
            return productRepo.save(product);
        }

        @GetMapping("/find/{id}")
        public Product getProductById(@PathVariable Long id) {
            return productRepo.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
        }

        @PutMapping("update/{id}")
        public Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
            return productRepo.findById(id)
                    .map(product ->{product.setName(newProduct.getName());
                        product.setPrice(newProduct.getPrice());
                        product.setDescription(newProduct.getDescription());
                        product.setQuantity(newProduct.getQuantity());

                        return productRepo.save(product);
                    })
                    .orElseThrow(() -> new ProductNotFoundException(id));
        }

        @DeleteMapping("/delete/{id}")
        public String deleteProduct(@PathVariable Long id) {
            if (!productRepo.existsById(id)) {
                throw new ProductNotFoundException(id);
            }
            productRepo.deleteById(id);
            return "Product with ID " + id + " has been deleted successfully.";
        }
    }


