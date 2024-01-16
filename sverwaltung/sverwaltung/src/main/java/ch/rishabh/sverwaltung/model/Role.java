package ch.rishabh.sverwaltung.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ch.rishabh.sverwaltung.model.ERole name;

    public Role() {

    }

    public Role(ch.rishabh.sverwaltung.model.ERole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ch.rishabh.sverwaltung.model.ERole getName() {
        return name;
    }

    public void setName(ch.rishabh.sverwaltung.model.ERole name) {
        this.name = name;
    }
}
