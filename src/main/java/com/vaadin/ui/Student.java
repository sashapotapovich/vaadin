package com.vaadin.ui;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "students")
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

    public Student() {
    }

    public Student(@NotBlank @Size(max = 255) String firstName, @NotBlank @Size(max = 255) String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
