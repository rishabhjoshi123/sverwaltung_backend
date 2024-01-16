package ch.rishabh.sverwaltung.service;

import lombok.*;

@Getter
public class StudentNotFoundException extends RuntimeException{

    private long id;
    public StudentNotFoundException(long id){
        this.id = id;
    }

}
