package com.assignment.service;

import com.assignment.model.Product;
import com.assignment.model.ProductFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {   
    List<Product> getProduct(ProductFilter productFilter);
    List<Product> getProduct();
    Product addProducts(Product product);
    boolean deleteProduct(String id);
    boolean deleteSellerProduct(String id);
    Product updateProduct(Product product);
    Boolean addSellerProducts(List<Product> products);
    boolean updateSellerProducts(Product product);

    List<Product> getSellerProduct();
}

