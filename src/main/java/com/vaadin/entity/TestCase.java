package com.vaadin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "testcase")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 255)
    private String shortDescription;

    @Min(0)
    @Max(100)
    private Integer passRate;
    
    @NotBlank
    @Size(max = 30000)
    private String questions;

    public TestCase() {
    }

    public TestCase(String shortDescription, Integer passRate, String questions) {
        this.shortDescription = shortDescription;
        this.passRate = passRate;
        this.questions = questions;
    }

}
