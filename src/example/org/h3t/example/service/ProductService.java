package org.h3t.example.service;

import org.h3t.example.entity.Product;
import org.h3t.example.entity.ProductCategory;

public interface ProductService {
	Product findProduct(String name);
	Product createProduct(String name);
	ProductCategory findCategory(String name);
	ProductCategory createCategory(String name);
	void updateCategory(ProductCategory category);
}
