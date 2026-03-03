package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
    @Override
    Product create(Product product);

    @Override
    java.util.Iterator<Product> findAll();

    @Override
    Product findById(String productId);

    @Override
    Product update(Product product);

    @Override
    void delete(String productId);
}
