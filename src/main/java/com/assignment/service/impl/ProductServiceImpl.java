package com.assignment.service.impl;

import com.assignment.model.Product;
import com.assignment.model.ProductFilter;
import com.assignment.service.ProductService;
import com.assignment.utils.ProjectUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public List<Product> getProduct(ProductFilter productFilter) {

        Query query=new Query(Criteria.where("name").is(productFilter.getProductName())
                .and("category").is(productFilter.getCategory())
                .and("type").is(productFilter.getProductType())
                .and("price").lte(productFilter.getMaxPrice()).gte(productFilter.getMinPrice()));

        return mongoTemplate.find(query,Product.class);
    }

    @Override
    public List<Product> getProduct() {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        return mongoTemplate.findAll(Product.class);
    }

    @Override
    public Product addProducts(Product product) {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        product.setUser(userName);
        return  mongoTemplate.save(product);
    }

    @Override
    public boolean deleteProduct(String id) {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        Query query=new Query(Criteria.where("_id").is(new ObjectId(id)));
        return mongoTemplate.remove(query, Product.class).getDeletedCount()>0;
    }

    @Override
    public boolean deleteSellerProduct(String id) {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        Query query=new Query(Criteria.where("_id").is(new ObjectId(id)).and("user").is(userName));
        return mongoTemplate.remove(query, Product.class).getDeletedCount()>0;
    }

    @Override
    public Product updateProduct(Product product) {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        return mongoTemplate.save(product);
    }

    @Override
    public Boolean addSellerProducts(List<Product> products) {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        List<Product> UpdatedProduct =  products.stream().map(a-> {a.setUser(userName); return a;
        }).collect(Collectors.toList());
        try {
              mongoTemplate.insert(UpdatedProduct,Product.class);
              return true;
        }catch (Exception ex){
            return false;
        }

    }

    @Override
    public boolean updateSellerProducts(Product product) {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        if(userName.equalsIgnoreCase(product.getUser())){
            mongoTemplate.save(product);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getSellerProduct() {
        String userName = ProjectUtils.getCurrentUser(SecurityContextHolder.getContext());
        Query query=new Query(Criteria.where("user").is(userName));
        return mongoTemplate.find(query,Product.class);
    }
}
