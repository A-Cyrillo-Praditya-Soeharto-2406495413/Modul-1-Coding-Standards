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
class PaymentServiceImplTest {

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
    void testAddPayment() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(result);
        assertEquals("order-1", result.getId());
        assertEquals("VOUCHER", result.getMethod());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Payment payment = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(new HashMap<>())
                .build();

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("payment-1")).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        products.add(product);

        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Payment payment = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("PENDING")
                .paymentData(new HashMap<>())
                .build();

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderRepository.findById("payment-1")).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testGetPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(paymentData)
                .build();

        when(paymentRepository.findById("payment-1")).thenReturn(payment);

        Payment result = paymentService.getPayment("payment-1");

        assertNotNull(result);
        assertEquals("payment-1", result.getId());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        payments.add(Payment.builder().id("payment-1").method("VOUCHER").status("SUCCESS").paymentData(new HashMap<>()).build());
        payments.add(Payment.builder().id("payment-2").method("CASH_ON_DELIVERY").status("PENDING").paymentData(new HashMap<>()).build());

        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }
}
