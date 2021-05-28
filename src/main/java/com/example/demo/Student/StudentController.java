package com.example.demo.Student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private static final List< Student> STUDENTS = Arrays.asList(
            new Student(1, "Kiran Paudyal"),
            new Student(2, "Fernando Torres"),
            new Student(3, "Anna Gray")

    );
    @GetMapping(path = "{studentId}")
    public Student getStudent( @PathVariable("studentId") Integer studentId){

        return STUDENTS.stream()
                .filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student" + studentId + "does not exist"));

    };

}
