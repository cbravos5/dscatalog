package com.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {

  @Autowired
  private ProductRepository _repository;

  @Test
  public void deleteShouldDeleteObjectWhenExists() {
    long existingId = 1L;

    _repository.deleteById(existingId);

    Optional<Product> result = _repository.findById(existingId);

    Assertions.assertFalse(result.isPresent());
  }
}
