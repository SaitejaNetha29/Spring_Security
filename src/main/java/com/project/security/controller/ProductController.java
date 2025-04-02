package com.project.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	List<String> products = List.of("Fair&lovely","Reginald","Sunscreen");
	
	@GetMapping
	public List<String> getProduct() {
		return products;
	}
	
	@PostMapping
	public List<String> saveProduct(@PathVariable String product) {
		products.add(product);
		return products;
	}
}
