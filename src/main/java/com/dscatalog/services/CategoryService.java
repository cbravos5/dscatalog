package com.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dto.CategoryDTO;
import com.dscatalog.entities.Category;
import com.dscatalog.repositories.CategoryRepository;
import com.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public List<CategoryDTO> findAll() {
    List<Category> categories = repository.findAll();

    return categories.stream().map(CategoryDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    Optional<Category> obj = repository.findById(id);

    Category category = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

    return new CategoryDTO(category);
  }
}
