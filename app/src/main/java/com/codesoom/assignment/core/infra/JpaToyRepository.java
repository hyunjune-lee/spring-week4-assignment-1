package com.codesoom.assignment.core.infra;

import com.codesoom.assignment.core.domain.Product;
import com.codesoom.assignment.core.domain.ProductRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 고양이 장난감
 */
@Repository
public interface JpaToyRepository
        extends ProductRepository, CrudRepository<Product, Long> {

    @Override
    public List<Product> findAll();

    @Override
    public Product save(Product product);

}
