package com.dscatalog.dto;

import java.util.List;

public record NewProductDTO(
    String name,
    String description,
    Double price,
    String imgUrl,
    List<CategoryDTO> categories) {}
