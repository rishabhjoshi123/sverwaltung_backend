package ch.rishabh.sverwaltung.repository;

import ch.rishabh.sverwaltung.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import ch.rishabh.sverwaltung.model.StudentModel;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    List<StudentModel> findByNameContaining(String name);
}
