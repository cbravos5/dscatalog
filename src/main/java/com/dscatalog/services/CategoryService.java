package com.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dto.CategoryDTO;
import com.dscatalog.dto.NewCategoryDTO;
import com.dscatalog.entities.Category;
import com.dscatalog.repositories.CategoryRepository;
import com.dscatalog.services.exceptions.DatabaseException;
import com.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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

    Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

    return new CategoryDTO(category);
  }

  @Transactional
  public CategoryDTO insert(NewCategoryDTO request) {
    var category = new Category();

    category.setName(request.name());

    var newCategory = repository.save(category);

    return new CategoryDTO(newCategory);
  }

  @Transactional
  public CategoryDTO update(Long id, NewCategoryDTO request) {
    try {
      Category category = repository.getReferenceById(id);
  
      category.setName(request.name());
      Category updatedCaregory = repository.save(category);
  
      return new CategoryDTO(updatedCaregory);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    } catch(DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }

  @Transactional(readOnly = true)
  public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
    Page<Category> categories = repository.findAll(pageRequest);

    return categories.map(CategoryDTO::new);
  }
}
