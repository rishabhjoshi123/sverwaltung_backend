package ch.rishabh.sverwaltung.controller;

import ch.rishabh.sverwaltung.dto.*;
import ch.rishabh.sverwaltung.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testCreateStudent() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        when(studentService.save(any())).thenReturn(studentDto);

        StudentDto studentDto1 = new StudentDto();
        studentDto1.setId(123);
        studentDto1.setName("testName");
        String content = (new ObjectMapper()).writeValueAsString(studentDto1);
        MockHttpServletRequestBuilder requestBuilder = post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents(null)).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = get("/api/students");
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    @Test
    void testGetAllStudentsNotFound() throws Exception {
        when(studentService.getAllStudents(null)).thenThrow(new StudentNotFoundException(123));
        MockHttpServletRequestBuilder getResult = get("/api/students");
        this.mockMvc
                .perform(getResult)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("123")));
    }

    @Test
    void testGetStudentById() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        when(studentService.getStudentById(anyLong())).thenReturn(studentDto);
        MockHttpServletRequestBuilder requestBuilder = get("/api/students/{id}", 123);
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void testGetStudentByIdNotFound() throws Exception {
        when(studentService.getStudentById(anyLong())).thenThrow(new StudentNotFoundException(123));
        MockHttpServletRequestBuilder requestBuilder = get("/api/students/{id}", 123);
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("123")));
    }

    @Test
    void testUpdateStudent() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        when(studentService.updateStudent(anyLong(), any())).thenReturn(studentDto);

        StudentDto studentDto1 = new StudentDto();
        studentDto1.setId(123);
        studentDto1.setName("testName");
        String content = (new ObjectMapper()).writeValueAsString(studentDto1);
        MockHttpServletRequestBuilder requestBuilder = put("/api/students/{id}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("testName")))
                .andExpect(jsonPath("$.id", is(123)));
    }

    @Test
    void testUpdateStudentNotFound() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(123);
        studentDto.setName("testName");
        String content = (new ObjectMapper()).writeValueAsString(studentDto);
        when(studentService.updateStudent(anyLong(), any())).thenThrow(new StudentNotFoundException(123));
        MockHttpServletRequestBuilder requestBuilder = put("/api/students/{id}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        this.mockMvc
                .perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("123")));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteById(anyLong());
        this.mockMvc
                .perform(delete("/api/students/{id}", 123))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteStudent2() throws Exception {
        doNothing().when(studentService).deleteById(anyLong());
        this.mockMvc
                .perform(delete("/api/students/{id}", 123))
                .andExpect(status().isNoContent());
    }
}
