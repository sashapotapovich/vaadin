package com.vaadin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotBlank
    @Size(max = 255)
    private String lastName;
    
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudentsGroup studentsGroup;

    public Student() {
    }

    public Student(@NotBlank @Size(max = 255) String firstName, @NotBlank @Size(max = 255) String lastName, @NotBlank StudentsGroup studentsGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentsGroup = studentsGroup;
    }
}
