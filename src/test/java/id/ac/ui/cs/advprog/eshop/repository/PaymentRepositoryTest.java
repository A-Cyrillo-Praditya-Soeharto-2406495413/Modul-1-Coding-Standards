package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepositoryImpl();
    }

    @Test
    void testSavePayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(paymentData)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        assertEquals("payment-1", savedPayment.getId());
        assertEquals("VOUCHER", savedPayment.getMethod());
    }

    @Test
    void testFindById() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(paymentData)
                .build();

        paymentRepository.save(payment);
        Payment foundPayment = paymentRepository.findById("payment-1");

        assertNotNull(foundPayment);
        assertEquals("payment-1", foundPayment.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment foundPayment = paymentRepository.findById("non-existent");
        assertNull(foundPayment);
    }

    @Test
    void testFindAll() {
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jl. Merdeka");
        paymentData2.put("deliveryFee", "10000");

        Payment payment1 = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(paymentData1)
                .build();

        Payment payment2 = Payment.builder()
                .id("payment-2")
                .method("CASH_ON_DELIVERY")
                .status("PENDING")
                .paymentData(paymentData2)
                .build();

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();

        assertEquals(2, allPayments.size());
    }
}
