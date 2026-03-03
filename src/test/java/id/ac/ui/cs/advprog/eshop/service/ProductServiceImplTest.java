package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;
    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl();
        productService = new ProductServiceImpl();

        setInternalState(productService, "productRepository", productRepository);

        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    private void setInternalState(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateAndFindAll() {
        productService.create(product);
        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sampo Cap Bambang", result.get(0).getProductName());
    }

    @Test
    void testFindById() {
        productService.create(product);
        Product found = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertNotNull(found);
        assertEquals("Sampo Cap Bambang", found.getProductName());
    }

    @Test
    void testUpdate() {
        productService.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Baru");
        updatedProduct.setProductQuantity(50);

        productService.update(updatedProduct);

        Product result = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Baru", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    void testDelete() {
        productService.create(product);
        productService.deleteProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        List<Product> result = productService.findAll();
        assertTrue(result.isEmpty());
    }
}