package com.vaadin.ui;

import com.vaadin.entity.AssignedTestCase;
import com.vaadin.entity.CurrentUser;
import com.vaadin.entity.Student;
import com.vaadin.entity.TestCase;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.AssignedTestCaseRepository;
import com.vaadin.repository.StudentRepository;
import com.vaadin.repository.TestCaseRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "assignedtests", layout = MenuView.class)
public class AssignedTestCases extends VerticalLayout implements RouterLayout {
    public static final String ID = "assignedtests";

    private TestCaseRepository testCaseRepository;
    private AssignedTestCaseRepository assignedTestCaseRepository;
    private StudentRepository studentRepository;
    private CurrentUser currentUser;
    
    private Grid<TestCase> grid = new Grid<>(TestCase.class, false);
    private List<TestCase> availableTests = new ArrayList<>();
    
    public AssignedTestCases(TestCaseRepository testCaseRepository, AssignedTestCaseRepository assignedTestCaseRepository,
                             StudentRepository studentRepository, CurrentUser currentUser){
        this.testCaseRepository = testCaseRepository;
        this.assignedTestCaseRepository = assignedTestCaseRepository;
        this.studentRepository = studentRepository;
        this.currentUser = currentUser;
    }
    
    @PostConstruct
    public void init(){
        Student currentStudent = studentRepository.findByFirstNameLikeAndLastNameLike(currentUser.getUser().getFirstName(),
                                                                                      currentUser.getUser().getLastName());
        List<AssignedTestCase> allByStudent = assignedTestCaseRepository.findAllByStudent(currentStudent);
        grid.addColumn(TestCase::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(TestCase::getShortDescription).setHeader("Short Description");
        grid.addColumn(TestCase::getPassRate).setHeader("Pass Rate");
        grid.addItemClickListener(listener -> {
            TestView.setCurrent(listener.getItem());
            UI.getCurrent().navigate(TestView.class);
        });
        allByStudent.forEach(assignedTestCase -> {
            availableTests.clear();
            if (!assignedTestCase.getPassed()) {
                availableTests.add(assignedTestCase.getTestCase());
            }
        });
        grid.setItems(availableTests);
        add(grid);
    }
    
}
