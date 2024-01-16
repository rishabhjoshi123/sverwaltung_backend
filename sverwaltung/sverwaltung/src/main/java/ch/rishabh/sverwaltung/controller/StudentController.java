package ch.rishabh.sverwaltung.controller;

import java.util.List;

import ch.rishabh.sverwaltung.dto.StudentDto;
import ch.rishabh.sverwaltung.service.StudentService;
import ch.rishabh.sverwaltung.dto.*;
import ch.rishabh.sverwaltung.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    StudentService studentService;
    private ResponseEntity<StudentDto> studentDtoResponseEntity;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(studentService.getAllStudents(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") long id) {
        StudentDto studentData = studentService.getStudentById(id);
        return new ResponseEntity<>(studentData, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        try {
            StudentDto studentDto1 = studentService.save(studentDto);
            return studentDtoResponseEntity;
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") long id, @RequestBody StudentDto studentDto) {
        StudentDto studentData = studentService.updateStudent(id, studentDto);
        return new ResponseEntity<>(studentData, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        try {
            studentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
