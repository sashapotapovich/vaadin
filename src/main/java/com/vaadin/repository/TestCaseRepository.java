package com.vaadin.repository;

import com.vaadin.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    
    TestCase findByIdLike(Long id);
    
}
