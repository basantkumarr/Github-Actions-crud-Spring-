package com.example.crud.controller;

import com.example.crud.entity.StudenReqDTO;
import com.example.crud.entity.Student;
import com.example.crud.entity.StudentRespDTO;
import com.example.crud.service.StudentService;
import jakarta.validation.Valid;
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



    @PostMapping("/create")
    public ResponseEntity<StudentRespDTO> createStudent(@Valid @RequestBody StudenReqDTO student) {
        StudentRespDTO createdStudent =studentService.createStudentServ(student);
         return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentRespDTO> getStudent(@PathVariable Long id) {
        StudentRespDTO getStudent =studentService.getStudentServ(id);



        return ResponseEntity.status(HttpStatus.OK).body(getStudent);

    }

    @GetMapping("/getall")
    public ResponseEntity<List<StudentRespDTO>> getallStudent() {
        List<StudentRespDTO> getallStudent =studentService.getAllStudentServ();
        return ResponseEntity.status(HttpStatus.OK).body(getallStudent);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody StudenReqDTO studentreq ) {
         Student  UpdatedStudent =studentService.UpdateStudentServ(id, studentreq);
        return ResponseEntity.status(HttpStatus.OK).body(UpdatedStudent);

    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> delStudent(@PathVariable Long id) {

        Boolean deleted = studentService.delStudentServ(id);


        return ResponseEntity.noContent().build();
    }

}