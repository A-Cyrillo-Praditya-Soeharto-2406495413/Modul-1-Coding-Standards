package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product saveProduct = productIterator.next();
        assertEquals(product.getProductId(), saveProduct.getProductId());
        assertEquals(product.getProductName(), saveProduct.getProductName());
        assertEquals(product.getProductQuantity(), saveProduct.getProductQuantity());
    }

    @Test
    void tesFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository. findAll() ;
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next() ;
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next() ;
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductTrue() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        productRepository.update(updatedProduct);

        Product result = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("Sampo Cap Usep", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    void testEditProductFalse() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Existing Product");
        productRepository.create(product);

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("random-id");
        nonExistentProduct.setProductName("Ghost Product");

        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);

        Product original = productRepository.findById("existing-id");
        assertEquals("Existing Product", original.getProductName());
    }

    @Test
    void testDeleteProductTrue() {
        Product product = new Product();
        product.setProductId("delete-me-id");
        product.setProductName("To Be Deleted");
        productRepository.create(product);

        productRepository.delete("delete-me-id");
        assertNull(productRepository.findById("delete-me-id"));

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProductFalse() {
        productRepository.delete("random-id");
        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }
}