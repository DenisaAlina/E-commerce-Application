package org.fasttrackit.ecommerce.controller;

import org.fasttrackit.ecommerce.entity.ImageModel;
import org.fasttrackit.ecommerce.entity.Product;
import org.fasttrackit.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasRole('Admin')")
    @PostMapping( consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart Product product,
                                 @RequestPart ("imageFile") MultipartFile[] file){
        try{
            Set<ImageModel> images=uploadImage(file);
            product.setProductImages(images);
            return  productService.addNewProduct(product);
        }catch(Exception e){
            System.out.println((e.getMessage()));
            return null;
        }
    }

    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels=new HashSet<>();
        for(MultipartFile file :multipartFiles){
            ImageModel imageModel= new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            imageModels.add(imageModel);
        }
        return imageModels;
    }


    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();

    }

    @GetMapping({"{productId}"})
    public Product getProductDetailsById(@PathVariable Integer productId){
        return productService.getProductDetailsById(productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping({"/{productId}"})
    public void deleteProductDetails(@PathVariable Integer productId){
        productService.deleteProductDetails(productId);
    }
}
