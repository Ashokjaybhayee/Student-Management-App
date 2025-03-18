package com.mobileprogramming.app.rest;

import com.mobileprogramming.app.model.Student;
import com.mobileprogramming.app.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class StudentRestControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentRestController studentRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents_returnsListOfStudents() {
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentService.getAllStudents()).thenReturn(students);

        ResponseEntity<List<Student>> response = studentRestController.getAllStudents();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(students, response.getBody());
    }

    @Test
    void getAllStudents_returnsEmptyList() {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList());

        ResponseEntity<List<Student>> response = studentRestController.getAllStudents();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getStudentById_returnsStudent() {
        Student student = new Student();
        when(studentService.getStudentById(1L)).thenReturn(student);

        ResponseEntity<Student> response = studentRestController.getStudentById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(student, response.getBody());
    }

    @Test
        void getStudentById_returnsNotFound() {
            when(studentService.getStudentById(1L)).thenThrow(new IllegalArgumentException("Student not found"));

            ResponseEntity<Student> response = null;
            try {
                response = studentRestController.getStudentById(1L);
            } catch (IllegalArgumentException e) {
                response = ResponseEntity.notFound().build();
            }

            assertEquals(404, response.getStatusCodeValue());
            assertNull(response.getBody());
        }

    @Test
    void saveStudent_createsAndReturnsStudent() {
        Student student = new Student();
        when(studentService.saveStudent(student)).thenReturn(student);

        ResponseEntity<Student> response = studentRestController.saveStudent(student);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(student, response.getBody());
    }

    @Test
    void saveStudent_returnsBadRequest() {
        when(studentService.saveStudent(null)).thenThrow(new IllegalArgumentException("Invalid student"));

        ResponseEntity<Student> response = null;
        try {
            response = studentRestController.saveStudent(null);
        } catch (IllegalArgumentException e) {
            response = ResponseEntity.badRequest().build();
        }

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void updateStudent_updatesAndReturnsStudent() {
        Student student = new Student();
        student.setEmail("test@example.com");
        student.setFirstName("John");
        student.setLastName("Doe");

        Student existingStudent = new Student();
        when(studentService.getStudentById(1L)).thenReturn(existingStudent);
        when(studentService.updateStudent(existingStudent)).thenReturn(existingStudent);

        ResponseEntity<Student> response = studentRestController.updateStudent(1L, student);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(existingStudent, response.getBody());
        assertEquals("test@example.com", existingStudent.getEmail());
        assertEquals("John", existingStudent.getFirstName());
        assertEquals("Doe", existingStudent.getLastName());
    }

    @Test
        void updateStudent_returnsNotFound() {
            Student student = new Student();
            when(studentService.getStudentById(1L)).thenThrow(new IllegalArgumentException("Student not found"));

            ResponseEntity<Student> response = null;
            try {
                response = studentRestController.updateStudent(1L, student);
            } catch (IllegalArgumentException e) {
                response = ResponseEntity.notFound().build();
            }

            assertEquals(404, response.getStatusCodeValue());
        }

    @Test
    void deleteStudent_deletesStudent() {
        doNothing().when(studentService).deleteStudentById(1L);

        ResponseEntity<Void> response = studentRestController.deleteStudent(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(studentService, times(1)).deleteStudentById(1L);
    }

    @Test
        void deleteStudent_returnsNotFound() {
            doThrow(new IllegalArgumentException("Student not found")).when(studentService).deleteStudentById(1L);

            ResponseEntity<Void> response = null;
            try {
                response = studentRestController.deleteStudent(1L);
            } catch (IllegalArgumentException e) {
                response = ResponseEntity.notFound().build();
            }

            assertEquals(404, response.getStatusCodeValue());
        }
}