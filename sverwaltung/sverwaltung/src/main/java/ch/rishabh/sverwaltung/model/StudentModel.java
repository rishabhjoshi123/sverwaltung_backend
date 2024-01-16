package ch.rishabh.sverwaltung.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor()
public class StudentModel {

    public StudentModel(StudentModel studentModel){
        this.name = studentModel.name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;
}
