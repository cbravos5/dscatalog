package com.dscatalog.resources;

import com.dscatalog.entities.Category;
import com.dscatalog.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;


    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        var list = service.findAll();

        return ResponseEntity.ok().body(list);
    }

}
