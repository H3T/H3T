package org.h3t.example.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.h3t.example.entity.Product;
import org.h3t.example.entity.ProductCategory;

@Stateless
@Remote(ProductService.class)
public class ProductServiceBean implements ProductService {

	@PersistenceContext
	private EntityManager em;

	public Product findProduct(String name) {
		return (Product) em.createQuery("from Product where name = :name")
				.setParameter("name", name).getSingleResult();
	}

	public ProductCategory findCategory(String name) {
		return (ProductCategory) em.createQuery(
				"from ProductCategory where name = :name").setParameter("name", name)
				.getSingleResult();
	}

	public Product createProduct(String name) {
		Product ret = new Product(name);
		em.persist(ret);
		return ret;
	}

	public ProductCategory createCategory(String name) {
		ProductCategory ret = new ProductCategory(name);
		em.persist(ret);
		return ret;
	}

	public void updateCategory(ProductCategory category) {
		em.merge(category);
	}

}
