package com.mobileprogramming.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mobileprogramming.app.model.Student;
import com.mobileprogramming.app.repository.StudentRepository;

@SpringBootApplication
public class StudentManagementAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementAppApplication.class, args);
	}

	@Autowired
	private StudentRepository studentRepository;
	
	
	  @Override public void run(String... args) throws Exception {
	  
		/*
		 * Student student1 = new Student("ramesh","fadatare","ramesh@gmail.com");
		 * studentRepository.save(student1);
		 * 
		 * Student student2 = new Student("Ashok","jaybhaye","ashok@gmail.com");
		 * studentRepository.save(student2);
		 */
	  
	  }
	 

}
