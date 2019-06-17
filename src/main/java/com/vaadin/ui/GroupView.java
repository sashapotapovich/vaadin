package com.vaadin.ui;

import com.vaadin.entity.AssignedTestCase;
import com.vaadin.entity.Student;
import com.vaadin.entity.StudentsGroup;
import com.vaadin.entity.TestCase;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.AssignedTestCaseRepository;
import com.vaadin.repository.StudentGroupRepository;
import com.vaadin.repository.StudentRepository;
import com.vaadin.repository.TestCaseRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "group", layout = MenuView.class)
public class GroupView extends VerticalLayout implements RouterLayout {

    public static final String ID = "group";

    private StudentGroupRepository studentGroupRepository;
    private TestCaseRepository testCaseRepository;
    private AssignedTestCaseRepository assignedTestCaseRepository;
    private StudentRepository studentRepository;
    
    private Grid<StudentsGroup> grid;

    public GroupView(StudentGroupRepository studentGroupRepository, TestCaseRepository testCaseRepository,
                     AssignedTestCaseRepository assignedTestCaseRepository, StudentRepository studentRepository) {
        this.studentGroupRepository = studentGroupRepository;
        this.testCaseRepository = testCaseRepository;
        this.assignedTestCaseRepository = assignedTestCaseRepository;
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void init() {
        grid = new Grid<>(StudentsGroup.class, false);
        grid.addColumn(StudentsGroup::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(StudentsGroup::getGroupName).setHeader("Group Name");
        add(grid);
        grid.addItemClickListener(listener -> {
            Dialog dialog = new Dialog();
            Label groupName = new Label(listener.getItem().getGroupName());
            VerticalLayout verticalLayout = new VerticalLayout();
            HorizontalLayout actions = new HorizontalLayout();
            Button close = new Button("Close", VaadinIcon.CLOSE.create(), (action) -> dialog.close());
            close.getElement().getThemeList().add("error");
            Button assignTest = new Button("Assign Test", VaadinIcon.PLUS.create());
            assignTest.addClickListener(listener2 -> {
                dialog.close();
                HorizontalLayout layout = new HorizontalLayout();
                ComboBox<String> comboBox = new ComboBox<>();
                Label label = new Label("Test case description");
                layout.add(comboBox, label);
                Dialog dialog1 = new Dialog();
                Button assign = new Button("Assign");
                dialog1.add(layout, assign);
                List<TestCase> all = testCaseRepository.findAll();
                comboBox.setItems(all.stream().map(testCase -> testCase.getId().toString()));
                comboBox.addValueChangeListener(action -> {
                    TestCase chosenTest = testCaseRepository.findById(Long.valueOf(comboBox.getValue())).get();
                    label.setText(chosenTest.getShortDescription());
                });
                assign.addClickListener(click -> {
                    List<Student> studentsToassignTest = studentRepository.findAllByStudentsGroup_GroupNameLike(groupName.getText());
                    studentsToassignTest.forEach(student -> {
                        TestCase byIdLike = testCaseRepository.findById(Long.valueOf(comboBox.getValue())).get();
                        assignedTestCaseRepository.saveAndFlush(new AssignedTestCase(student, byIdLike, false));
                        dialog1.close();
                    });
                });
                dialog1.open();
            });
            Button openStudents = new Button("Open Students", VaadinIcon.GROUP.create());
            actions.add(openStudents, assignTest, close);
            actions.setVerticalComponentAlignment(Alignment.START, openStudents);
            actions.setVerticalComponentAlignment(Alignment.END, assignTest, close);
            openStudents.addClickListener(action -> {
                dialog.close();
                StudentsView.setFilter(groupName.getText());
                UI.getCurrent().navigate(StudentsView.class);
            });
            verticalLayout.add(groupName, actions);
            dialog.add(verticalLayout);
            dialog.open();
        });
        grid.setItems(studentGroupRepository.findAll());
    }

}
