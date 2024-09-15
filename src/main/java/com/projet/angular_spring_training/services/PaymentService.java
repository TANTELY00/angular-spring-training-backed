package com.projet.angular_spring_training.services;

import com.projet.angular_spring_training.dto.PaymentDTO;
import com.projet.angular_spring_training.entities.Payment;
import com.projet.angular_spring_training.entities.PaymentStatus;
import com.projet.angular_spring_training.entities.PaymentType;
import com.projet.angular_spring_training.entities.Student;
import com.projet.angular_spring_training.repositories.PayementRepository;
import com.projet.angular_spring_training.repositories.StudentRepositorie;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Transactional
@Service
public class PaymentService {
    private StudentRepositorie studentRepositorie;
    private PayementRepository payementRepository;

    public PaymentService(StudentRepositorie studentRepositorie, PayementRepository payementRepository) {
        this.studentRepositorie = studentRepositorie;
        this.payementRepository = payementRepository;
    }

    public Payment savePayment(MultipartFile files , PaymentDTO paymentDTO) throws IOException {
        // stoker le fichier dans un dossier
        Path path = Paths.get(System.getProperty("user.home"),"angular-spring-training","payment");
        // on va verifier si le dossier exist
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        // besoins du nom du fichier unique
        String fileName = UUID.randomUUID().toString();
        Path filePathDirectory = Paths.get(System.getProperty("user.home"),"angular-spring-training","payment",fileName+".pdf");
        // copier le fichier dans le paramatre ou requete
        Files.copy(files.getInputStream(),filePathDirectory);
        //chercher l'etudiant
        Student student = studentRepositorie.findByCode(paymentDTO.getStudentCode());
        Payment payment = Payment.builder()
                .date(paymentDTO.getDate())
                .type(paymentDTO.getType())
                .amount(paymentDTO.getAmount())
                .file(filePathDirectory.toUri().toString())
                .student(student)
                .build();
        return  payementRepository.save(payment);
    }

    public Payment updatePaymentStatus( PaymentStatus status ,  Long id){
        Payment payment = payementRepository.findById(id).get(); // retourner un type optionel de payement donc on utilise .get()
        payment.setStatus(status);
        return payementRepository.save(payment);
    }

    public byte[] getPaymentFile( Long paymentId) throws IOException {
        Payment payment= payementRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile()))); // parcourir byte[
    }
}
