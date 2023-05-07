package com.dscatalog.dto;

import com.dscatalog.entities.Category;

public record CategoryDTO(Long id, String name) {
  public CategoryDTO(Category entity) {
    this(entity.getId(), entity.getName());
  }
}
