package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = determinePaymentStatus(method, paymentData);

        Payment payment = Payment.builder()
                .id(order.getId())
                .method(method)
                .status(status)
                .paymentData(paymentData)
                .build();

        return paymentRepository.save(payment);
    }

    private String determinePaymentStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucher(voucherCode)) {
                return "SUCCESS";
            } else {
                return "REJECTED";
            }
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            String address = paymentData.get("address");
            String deliveryFee = paymentData.get("deliveryFee");
            if (isValidCashOnDelivery(address, deliveryFee)) {
                return "PENDING";
            } else {
                return "REJECTED";
            }
        }
        return "PENDING";
    }

    private boolean isValidCashOnDelivery(String address, String deliveryFee) {
        if (address == null || address.isEmpty()) {
            return false;
        }
        if (deliveryFee == null || deliveryFee.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidVoucher(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        Pattern numericPattern = Pattern.compile("\\d");
        Matcher matcher = numericPattern.matcher(voucherCode);
        int digitCount = 0;
        while (matcher.find()) {
            digitCount++;
        }

        return digitCount == 8;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        Payment updatedPayment = Payment.builder()
                .id(payment.getId())
                .method(payment.getMethod())
                .status(status)
                .paymentData(payment.getPaymentData())
                .build();

        paymentRepository.save(updatedPayment);

        Order order = orderRepository.findById(payment.getId());
        if (order != null) {
            String orderStatus = "SUCCESS".equals(status) ? "SUCCESS" : "FAILED";
            Order updatedOrder = new Order(order.getId(), order.getProducts(),
                    order.getOrderTime(), order.getAuthor(), orderStatus);
            orderRepository.save(updatedOrder);
        }

        return updatedPayment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
