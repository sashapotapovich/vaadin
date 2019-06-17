package com.vaadin.repository;

import com.vaadin.entity.AssignedTestCase;
import com.vaadin.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedTestCaseRepository extends JpaRepository<AssignedTestCase, Long> {
    
    List<AssignedTestCase> findAllByStudent(Student student);
    
}
