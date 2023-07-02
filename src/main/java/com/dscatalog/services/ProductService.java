package com.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dscatalog.dto.ProductDTO;
import com.dscatalog.dto.CategoryDTO;
import com.dscatalog.dto.NewProductDTO;
import com.dscatalog.entities.Category;
import com.dscatalog.entities.Product;
import com.dscatalog.repositories.CategoryRepository;
import com.dscatalog.repositories.ProductRepository;
import com.dscatalog.services.exceptions.DatabaseException;
import com.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
  @Autowired
  private ProductRepository repository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public List<ProductDTO> findAll() {
    List<Product> categories = repository.findAll();

    return categories.stream().map(ProductDTO::new).toList();
  }

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Optional<Product> obj = repository.findById(id);

    Product product = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

    return new ProductDTO(product, product.getCategories());
  }

  @Transactional
  public ProductDTO insert(NewProductDTO request) {
    var product = new Product();

    copyDTOToEntity(request, product);

    var newProduct = repository.save(product);

    return new ProductDTO(newProduct);
  }

  @Transactional
  public ProductDTO update(Long id, NewProductDTO request) {
    try {
      Product product = repository.getReferenceById(id);

      copyDTOToEntity(request, product);

      product.setName(request.name());
      Product updatedCaregory = repository.save(product);

      return new ProductDTO(updatedCaregory);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAllPaged(Pageable pageRequest) {
    Page<Product> categories = repository.findAll(pageRequest);

    return categories.map(ProductDTO::new);
  }

  private void copyDTOToEntity(NewProductDTO productDTO, Product entity) {
    entity.setDescription(productDTO.description());
    entity.setImgUrl(productDTO.imgUrl());
    entity.setName(productDTO.name());
    entity.setPrice(productDTO.price());

    entity.getCategories().clear();

    for (CategoryDTO c : productDTO.categories()) {
      Category category = categoryRepository.getReferenceById(c.id());
      entity.getCategories().add(category);
    }
  }
}
