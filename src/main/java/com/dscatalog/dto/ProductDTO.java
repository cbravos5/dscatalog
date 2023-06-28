package com.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dscatalog.entities.Category;
import com.dscatalog.entities.Product;

public record ProductDTO(
    Long id,
    String name,
    String description,
    Double price,
    String imgUrl,
    Instant date,
    List<CategoryDTO> categories) {

  public ProductDTO(
      Long id,
      String name,
      String description,
      Double price,
      String imgUrl,
      Instant date) {
    this(id, name, description, price, imgUrl, date, new ArrayList<>());
  }

  public ProductDTO(Product entity) {
    this(
        entity.getId(),
        entity.getName(),
        entity.getDescription(),
        entity.getPrice(),
        entity.getImgUrl(),
        entity.getDate(), new ArrayList<>());
  }

  public ProductDTO(Product entity, Set<Category> categories) {
    this(entity);
    categories.forEach(c -> this.categories.add(new CategoryDTO(c)));
  }
}
