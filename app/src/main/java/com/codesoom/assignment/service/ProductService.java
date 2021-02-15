package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setMaker(productDto.getMaker());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg());
        return productRepository.save(product);
    }
}
