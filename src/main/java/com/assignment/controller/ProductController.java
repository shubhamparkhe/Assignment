package com.assignment.controller;

import com.assignment.model.Product;
import com.assignment.model.ProductFilter;
import com.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;



    @PostMapping("customer/_search/")
    public List<Product> searchProduct(@RequestBody ProductFilter productFilter) {
        return productService.getProduct(productFilter);
    }

    @PostMapping("admin/add-product/")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProducts(product);
    }

    @PutMapping ("admin/add-product/{id}")
    public Product updateProduct(@RequestBody Product product,@PathVariable  String id) {
        return productService.updateProduct(product);
    }

    @GetMapping("/admin/product/")
    public List<Product> getProduct() {
        return productService.getProduct();
    }

    @DeleteMapping("admin/product/{id}")
    public boolean addProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
    @PostMapping("seller/add-product/")
    public boolean addSellerProduct(@RequestBody List<Product> products) {
        return productService.addSellerProducts(products);
    }

    @PutMapping ("seller/add-product/")
    public boolean updateSellerProduct(@RequestBody Product product) {
        return productService.updateSellerProducts(product);
    }

    @DeleteMapping("seller/product/{id}")
    public boolean deleteSellerProduct(@PathVariable String id) {
        return productService.deleteSellerProduct(id);
    }

    @GetMapping("/seller/_search/")
    public List<Product> getSellerProduct() {
        return productService.getSellerProduct();
    }


}
