package com.example.demo.Student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("admin/api/v1/student")
public class AdminController{

    private static List<Student> STUDENTS = Arrays.asList(

            new Student(1, "Kiran Paudyal"),
            new Student(2, "Fernando Torres"),
            new Student(3, "Anna Gray")

    );


    // hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    @GetMapping (path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_ADMINTRAINEE')")
    public Student getStudent(@PathVariable("studentId") Integer id){
        return STUDENTS.get(id-1);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    public void deleteStudent(@PathVariable("studentId") Integer id) {
        System.out.println("Deleted");
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("Deleted");

    }

    @PutMapping (path = "studentId")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("Deleted");

    }

}
