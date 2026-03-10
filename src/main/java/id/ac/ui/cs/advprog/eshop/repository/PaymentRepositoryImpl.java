package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private List<Payment> paymentData = new ArrayList<>();

    @Override
    public Payment save(Payment payment) {
        return null;
    }

    @Override
    public Payment findById(String paymentId) {
        return null;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }
}
