package com.projet.angular_spring_training;

import com.projet.angular_spring_training.entities.Payment;
import com.projet.angular_spring_training.entities.PaymentStatus;
import com.projet.angular_spring_training.entities.PaymentType;
import com.projet.angular_spring_training.entities.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.projet.angular_spring_training.repositories.PayementRepository;
import com.projet.angular_spring_training.repositories.StudentRepositorie;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class AngularSpringTrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AngularSpringTrainingApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepositorie studentRepositorie,
										PayementRepository payementRepository){
		return args -> {
			studentRepositorie.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Tantely")
					.code("AESS")
					.programId("UX")
					.build());
			studentRepositorie.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Tantely")
					.code("AESSS")
					.programId("UX")
					.build());
			studentRepositorie.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstName("Tantely")
					.code("AESA")
					.programId("UX")
					.build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepositorie.findAll().forEach(student -> {
				for (int i=0 ; i<10 ; i++){
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*20000))
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(student)
							.build();
					payementRepository.save(payment);
				}
			});
		};
	}
}
