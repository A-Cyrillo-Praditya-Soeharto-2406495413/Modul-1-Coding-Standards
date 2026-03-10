package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

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
        Payment payment = Payment.builder()
                .id(order.getId())
                .method(method)
                .status("PENDING")
                .paymentData(paymentData)
                .build();

        return paymentRepository.save(payment);
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
