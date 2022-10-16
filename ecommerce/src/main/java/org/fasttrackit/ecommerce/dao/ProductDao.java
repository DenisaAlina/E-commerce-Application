package org.fasttrackit.ecommerce.dao;

import org.fasttrackit.ecommerce.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product, Integer> {
}
