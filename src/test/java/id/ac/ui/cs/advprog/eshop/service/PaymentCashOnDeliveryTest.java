package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentCashOnDeliveryTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentServiceImpl(paymentRepository, orderRepository);
    }

    @Test
    void testCashOnDeliveryValid() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No. 10");
        paymentData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertNotEquals("REJECTED", result.getStatus());
    }

    @Test
    void testCashOnDeliveryEmptyAddress() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testCashOnDeliveryNullAddress() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testCashOnDeliveryEmptyDeliveryFee() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No. 10");
        paymentData.put("deliveryFee", "");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testCashOnDeliveryNullDeliveryFee() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No. 10");
        paymentData.put("deliveryFee", null);

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", result.getStatus());
    }
}
