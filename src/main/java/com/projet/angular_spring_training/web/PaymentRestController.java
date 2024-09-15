package com.projet.angular_spring_training.web;

import com.projet.angular_spring_training.dto.PaymentDTO;
import com.projet.angular_spring_training.entities.Payment;
import com.projet.angular_spring_training.entities.PaymentStatus;
import com.projet.angular_spring_training.entities.PaymentType;
import com.projet.angular_spring_training.entities.Student;
import com.projet.angular_spring_training.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.projet.angular_spring_training.repositories.PayementRepository;
import com.projet.angular_spring_training.repositories.StudentRepositorie;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class PaymentRestController {
    private final StudentRepositorie studentRepositorie;
    private final PayementRepository payementRepository;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepositorie studentRepositorie, PayementRepository payementRepository, PaymentService paymentService) {
        this.studentRepositorie = studentRepositorie;
        this.payementRepository = payementRepository;
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments(){
        return payementRepository.findAll();
    }

    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentByStudent(@PathVariable String code){
        return payementRepository.findByStudentCode(code);
    }

    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentByStatus(@RequestParam PaymentStatus status){
        return payementRepository.findByStatus(status);
    }

    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentByStatus(@RequestParam PaymentType type){
        return payementRepository.findByType(type);
    }

    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return payementRepository.findById(id).orElse(null); // Utilisation de orElse(null) pour éviter NoSuchElementException
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents(){
        return studentRepositorie.findAll();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepositorie.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
        return studentRepositorie.findByProgramId(programId);
    }

    @PutMapping(path = "/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status ,@PathVariable Long id){
        return paymentService.updatePaymentStatus(status,id);
    }

    @PostMapping(path = "/payments" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //indique que la requete contient plusieurs formes
    public  Payment savePayment(@RequestParam("files") MultipartFile files , PaymentDTO paymentDTO) throws IOException { // => changer dans le DTO : LocalDate date , double amount , PaymentType type , String studentCode  et preciser le noms de la champ dans la requette comme @RequestParam("file")
        return this.paymentService.savePayment(files,paymentDTO);
    }

    @GetMapping(path = "/payments/{paymentId}/file" , produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
       return this.paymentService.getPaymentFile(paymentId);
    }
}
