package com.vaadin.repository;

import com.vaadin.entity.AssignedTestCase;
import com.vaadin.entity.Student;
import com.vaadin.entity.TestCase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTestCaseRepository extends JpaRepository<AssignedTestCase, Long> {
    
    List<AssignedTestCase> findAllByStudent(Student student);
    
    Optional<AssignedTestCase> findByStudentAndTestCase(Student student, TestCase testCase);
}
