package com.example.crud.service;

import com.example.crud.annotation.ExecutionTime;
import com.example.crud.entity.StudenReqDTO;
import com.example.crud.entity.Student;
import com.example.crud.entity.StudentRespDTO;
import com.example.crud.exception.ResourceNotFoundException;
import com.example.crud.repository.StudentRepository;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    @ExecutionTime
    public StudentRespDTO createStudentServ(StudenReqDTO studentReq) {

        Student student = mapToEntity(studentReq);
        studentRepository.save(student);
        return maptodata(student);

    }




    @ExecutionTime
    public StudentRespDTO getStudentServ(Long id) {

        Student studentResp =
                studentRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()-> new ResourceNotFoundException("Student with this ID not found"));

             return maptodata(studentResp);
       
    }
    
    
    
    
    

    
    public List<StudentRespDTO> getAllStudentServ() {

        List<Student>  ls=  studentRepository.findAllByIsDeletedFalse();
        return ls.stream()
                .map(this::maptodata)
                .toList();
    }



    @ExecutionTime

    public Student UpdateStudentServ(Long id, StudenReqDTO student) {

        Student s1 = studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ID not found "));



        s1.setName(student.getName());
        s1.setAge(student.getAge());
        s1.setEmail(student.getEmail());
        s1.setSubject(student.getSubject());
        s1.setRollno(student.getRollno());

        return studentRepository.save(s1);
    }



    public Boolean delStudentServ(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ID not found to delete"));


        student.setDeleted(true);

        studentRepository.save(student);

        return true;
    }



    private Student mapToEntity(StudenReqDTO studentReq) {
        Student s1= new Student();

        s1.setName(studentReq.getName());
        s1.setAge(studentReq.getAge());
        s1.setEmail(studentReq.getEmail());
        s1.setSubject(studentReq.getSubject());
        s1.setRollno(studentReq.getRollno());
        s1.setDeleted(false);

        s1.setCreatedAt(LocalDateTime.now());
        s1.setUpdatedAt(LocalDateTime.now());


        return s1;


    }


    private StudentRespDTO maptodata(Student student) {

        StudentRespDTO s1= new StudentRespDTO();

        s1.setName(student.getName());
        s1.setAge(student.getAge());
        s1.setEmail(student.getEmail());
        s1.setSubject(student.getSubject());
        s1.setRollno(student.getRollno());
        s1.setMessage("Hello you did it ");

        return s1;

    }

}