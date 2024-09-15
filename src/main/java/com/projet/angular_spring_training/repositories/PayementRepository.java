package com.projet.angular_spring_training.repositories;

import com.projet.angular_spring_training.entities.Payment;
import com.projet.angular_spring_training.entities.PaymentStatus;
import com.projet.angular_spring_training.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayementRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
