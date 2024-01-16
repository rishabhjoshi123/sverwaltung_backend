package ch.rishabh.sverwaltung.service;

import ch.rishabh.sverwaltung.dto.StudentDto;
import ch.rishabh.sverwaltung.model.StudentModel;
import ch.rishabh.sverwaltung.repository.StudentRepository;
import ch.rishabh.sverwaltung.dto.*;
import ch.rishabh.sverwaltung.model.*;
import ch.rishabh.sverwaltung.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDto> getAllStudents(String name){
        if (name == null || name.isBlank()) {
            return studentRepository.findAll()
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } else {
            return studentRepository.findByNameContaining(name)
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        }
    }

    public StudentDto getStudentById(long id){
        return studentRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElseThrow(()->new StudentNotFoundException(id));

    }

    public StudentDto save(StudentDto studentDto){
        StudentModel studentModel = new StudentModel();
        studentModel.setName(studentDto.getName());
        return convertEntityToDto(studentRepository.save(studentModel));
    }

    public void deleteById(long id){
        studentRepository.deleteById(id);
    }

    public StudentDto updateStudent(long id, StudentDto studentDto){
        Optional<StudentModel> byId = studentRepository.findById(studentDto.getId());
        if (byId.isPresent()){
            StudentModel studentModel = byId.get();
            studentModel.setName(studentDto.getName());
            return convertEntityToDto(studentRepository.save(studentModel));
        }
        return convertEntityToDto(new StudentModel());
    }

    private StudentDto convertEntityToDto(StudentModel studentModel){
        StudentDto dto = new StudentDto();
        dto.setId(studentModel.getId());
        dto.setName(studentModel.getName());
        return dto;
    }


}
