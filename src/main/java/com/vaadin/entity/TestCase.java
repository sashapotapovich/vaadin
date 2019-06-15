package com.vaadin.entity;

import java.util.Map;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Size(max = 3000)
    private String questions;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> customerAttributes;

    public TestCase() {
    }

    public TestCase(String questions) {
        this.questions = questions;
    }

}
