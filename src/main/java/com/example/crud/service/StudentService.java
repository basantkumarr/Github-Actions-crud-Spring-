package com.example.crud.service;

import com.example.crud.entity.Student;
import com.example.crud.repository.StudentRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudentServ(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentServ(Long id) {

        Optional<Student> studentResp =
                studentRepository.findByIdAndIsDeletedFalse(id);

        if (studentResp.isEmpty()) {
            return null;
        }

        return studentResp.get();
    }
    public List<Student> getAllStudentServ() {

        return studentRepository.findAllByIsDeletedFalse();
    }

    public Student UpdateStudentServ(Long id , Student student) {
        Optional<Student> studentResp=studentRepository.findById(id);

        if(studentResp.isEmpty()){
            return null;
        }

        Student studentToSave = studentResp.get();

        studentToSave.setName(student.getName());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setRollno(student.getRollno());
        studentToSave.setSubject(student.getSubject());
        studentToSave.setAge(student.getAge());
        return studentRepository.save(studentToSave);    }



    public Boolean delStudentServ(Long id) {

        Optional<Student> studentResp = studentRepository.findById(id);

        if (studentResp.isEmpty()) {
            return false;
        }

        Student student = studentResp.get();

        student.setDeleted(true);

        studentRepository.save(student);

        return true;
    }
}