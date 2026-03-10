package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testPaymentCreation() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = Payment.builder()
                .id("payment-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(paymentData)
                .build();

        assertEquals("payment-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testPaymentWithEmptyPaymentData() {
        Payment payment = Payment.builder()
                .id("payment-2")
                .method("CASH_ON_DELIVERY")
                .status("PENDING")
                .paymentData(new HashMap<>())
                .build();

        assertNotNull(payment.getPaymentData());
        assertTrue(payment.getPaymentData().isEmpty());
    }
}
