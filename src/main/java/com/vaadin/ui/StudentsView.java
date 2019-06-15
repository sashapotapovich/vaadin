package com.vaadin.ui;

import com.vaadin.entity.Student;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.StudentRepository;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "students", layout = MenuView.class)
@Secured("ROLE_ADMIN")
public class StudentsView extends VerticalLayout implements RouterLayout {

    public static final String ID = "students";
    private static TextField groupFilter = new TextField("Group Filter", "");
    private StudentRepository studentRepository;
    private TextField firstNameFilter = new TextField("First Name Filter", "");
    private TextField lastNameFilter = new TextField("Last Name Filter", "");
    private Button clearFilters = new Button("Clear Filters");
    private HorizontalLayout filters = new HorizontalLayout(firstNameFilter, lastNameFilter, groupFilter, clearFilters);
    private VerticalLayout verticalLayout = new VerticalLayout(filters);
    private Grid<Student> grid;

    public StudentsView(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @PostConstruct
    public void init() {
        initFilterListeners();
        filters.setAlignItems(Alignment.BASELINE);
        clearFilters.addClickListener(action -> {
            firstNameFilter.setValue("");
            lastNameFilter.setValue("");
            groupFilter.setValue("");
            grid.setItems(studentRepository.findAll());
        });
        grid = new Grid<>(Student.class, false);
        grid.addColumn(Student::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(Student::getFirstName).setHeader("First Name");
        grid.addColumn(Student::getLastName).setHeader("Last Name");
        grid.addColumn(student -> student.getStudentsGroup().getGroupName()).setHeader("Group");
        add(verticalLayout, grid);
        grid.setItems(studentRepository.findAll());
    }

    public void initFilterListeners() {
        firstNameFilter.addValueChangeListener(listener -> {
            lastNameFilter.setValue("");
            groupFilter.setValue("");
            grid.setItems(studentRepository.findAllByFirstNameContainsOrFirstNameContains(firstNameFilter.getValue().toLowerCase()
                    , firstNameFilter.getValue().toUpperCase()));
        });
        lastNameFilter.addValueChangeListener(listener -> {
            firstNameFilter.setValue("");
            groupFilter.setValue("");
            grid.setItems(studentRepository.findAllByLastNameContains(lastNameFilter.getValue()));
        });
        groupFilter.addValueChangeListener(listener -> {
            firstNameFilter.setValue("");
            lastNameFilter.setValue("");
            grid.setItems(studentRepository.findAllByStudentsGroup_GroupNameLike(groupFilter.getValue()));
        });
    }

    public static void setFilter(String filter) {
        groupFilter.setValue(filter);
    }

}
