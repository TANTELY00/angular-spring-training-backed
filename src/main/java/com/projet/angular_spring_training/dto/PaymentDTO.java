package com.projet.angular_spring_training.dto;

import com.projet.angular_spring_training.entities.PaymentStatus;
import com.projet.angular_spring_training.entities.PaymentType;
import com.projet.angular_spring_training.entities.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter @Builder @ToString
public class PaymentDTO {
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private String studentCode;
}
