package ch.rishabh.sverwaltung.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import ch.rishabh.sverwaltung.dto.*;
import ch.rishabh.sverwaltung.model.*;
import ch.rishabh.sverwaltung.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void testGetAllStudents() {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(123);
        studentModel.setName("testName");

        ArrayList<StudentModel> studentModelList = new ArrayList<>();
        studentModelList.add(studentModel);
        when(studentRepository.findAll()).thenReturn(studentModelList);
        List<StudentDto> actualAllStudents = studentService.getAllStudents(null);
        assertEquals(1, actualAllStudents.size());
        StudentDto getResult = actualAllStudents.get(0);
        assertEquals(123, getResult.getId());
        assertEquals("testName", getResult.getName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(123);
        studentModel.setName("testName");
        Optional<StudentModel> ofResult = Optional.of(studentModel);
        when(studentRepository.findById(any())).thenReturn(ofResult);
        StudentDto actualStudentById = studentService.getStudentById(123);
        assertEquals(123, actualStudentById.getId());
        assertEquals("testName", actualStudentById.getName());
        verify(studentRepository, times(1)).findById(any());
    }

    @Test
    void testSave() {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(123);
        studentModel.setName("testName");
        when(studentRepository.save(any())).thenReturn(studentModel);

        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        StudentDto actualSaveResult = studentService.save(studentDto);
        assertEquals(123, actualSaveResult.getId());
        assertEquals("testName", actualSaveResult.getName());
        verify(studentRepository, times(1)).save(any());
    }

    @Test
    void testDeleteById() {
        doNothing().when(studentRepository).deleteById(any());
        studentService.deleteById(123);
        verify(studentRepository, times(1)).deleteById(any());
        assertTrue(studentService.getAllStudents(null).isEmpty());
    }

    @Test
    void testDeleteByIdIfStudentNotFoundException() {
        doThrow(new StudentNotFoundException(123)).when(studentRepository).deleteById(any());
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteById(123));
        verify(studentRepository, times(1)).deleteById(any());
    }

    @Test
    void testUpdateStudent() {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(123);
        studentModel.setName("testName");
        Optional<StudentModel> ofResult = Optional.of(studentModel);

        StudentModel studentModel1 = new StudentModel();
        studentModel1.setId(123);
        studentModel1.setName("testName");
        when(studentRepository.save(any())).thenReturn(studentModel1);
        when(studentRepository.findById(any())).thenReturn(ofResult);

        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        StudentDto actualUpdateStudentResult = studentService.updateStudent(123, studentDto);
        assertEquals(123, actualUpdateStudentResult.getId());
        assertEquals("testName", actualUpdateStudentResult.getName());
        verify(studentRepository, times(1)).save(any());
        verify(studentRepository, times(1)).findById(any());
    }

    @Test
    void testUpdateStudentConvertEntityToDto() {
        StudentModel studentModel = new StudentModel();
        studentModel.setId(123);
        studentModel.setName("testName");
        when(studentRepository.save(any())).thenReturn(studentModel);
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        StudentDto actualUpdateStudentResult = studentService.updateStudent(123, studentDto);
        assertEquals(0, actualUpdateStudentResult.getId());
        assertNull(actualUpdateStudentResult.getName());
        verify(studentRepository, times(1)).findById(any());
    }
}

