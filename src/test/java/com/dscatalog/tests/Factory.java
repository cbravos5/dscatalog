package com.dscatalog.tests;

import java.time.Instant;

import com.dscatalog.dto.ProductDTO;
import com.dscatalog.entities.Category;
import com.dscatalog.entities.Product;

public class Factory {

  public static Product createProduct() {
    Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.now());

    product.getCategories().add(new Category(2L, "Electronics"));

    return product;
  }

  public static ProductDTO creatoProductDTO() {
    Product product = createProduct();

    return new ProductDTO(product, product.getCategories());
  }

}
