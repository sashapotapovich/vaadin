package com.vaadin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "assignedtests")
@Table(
        name = "assignedtests",
        uniqueConstraints = {@UniqueConstraint(columnNames = { "student_id", "testCase_id"})}
)
public class AssignedTestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "testcase_id", nullable = false)
    private TestCase testCase;
    
    private Boolean passed;

    public AssignedTestCase() {
    }

    public AssignedTestCase(Student student, TestCase testCase, Boolean passed) {
        this.student = student;
        this.testCase = testCase;
        this.passed = passed;
    }
}
