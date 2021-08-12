package com.mobileprogramming.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobileprogramming.app.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
