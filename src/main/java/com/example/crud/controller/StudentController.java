package com.example.crud.controller;

import com.example.crud.entity.Student;
import com.example.crud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/hello")
    public String hello() {
        return "Hello from CRUD Application!";
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
         Student createdStudent =studentService.createStudentServ(student);
         return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student getStudent =studentService.getStudentServ(id);
        return ResponseEntity.status(HttpStatus.OK).body(getStudent);

    }

    @GetMapping("/getall")
    public ResponseEntity<List<Student>> getallStudent() {
        List<Student> getallStudent =studentService.getAllStudentServ();
        return ResponseEntity.status(HttpStatus.OK).body(getallStudent);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentreq ) {
         Student  UpdatedStudent =studentService.UpdateStudentServ(id, studentreq);
        return ResponseEntity.status(HttpStatus.OK).body(UpdatedStudent);

    }
    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> delStudent(@PathVariable Long id) {

        Boolean deleted = studentService.delStudentServ(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok("Student soft deleted successfully");
    }

}