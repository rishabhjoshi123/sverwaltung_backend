package ch.rishabh.sverwaltung;

import ch.rishabh.sverwaltung.model.*;
import ch.rishabh.sverwaltung.repository.StudentRepository;
import ch.rishabh.sverwaltung.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import jakarta.annotation.PostConstruct;

@Component
public class DBInit {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {

        StudentModel testStudent = new StudentModel();
        testStudent.setId(20);
        testStudent.setName("testStudentRishabh");

        List<Role> roles = roleRepository.findAll();


        if(!roles.stream().anyMatch(r -> r.getName().equals(ERole.ROLE_ADMIN))) {
            Role rAdmin = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(rAdmin);
        }
        if (!roles.stream().anyMatch(r -> r.getName().equals(ERole.ROLE_USER))) {
            Role rUser = new Role(ERole.ROLE_USER);
            roleRepository.save(rUser);
        }


    }
}
