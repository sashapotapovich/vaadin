package com.vaadin.ui;

import com.vaadin.entity.TestCase;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.TestCaseRepository;
import com.vaadin.ui.editor.TestCaseEditorView;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "tests", layout = MenuView.class)
public class TestDetailsView extends VerticalLayout implements RouterLayout {
    public static final String ID = "tests";

    private TestCaseRepository testCaseRepository;
    private Grid<TestCase> grid = new Grid<>(TestCase.class, false);
    private Binder<TestCase> binder = new Binder<>(TestCase.class);
    private Button addNewTest = new Button("Create new Test", VaadinIcon.ADD_DOCK.create());
    private TestCaseEditorView testCaseEditorView;
    private List<TestCase> allTests;

    public TestDetailsView(TestCaseRepository testCaseRepository, TestCaseEditorView testCaseEditorView) {
        this.testCaseRepository = testCaseRepository;
        this.testCaseEditorView = testCaseEditorView;
    }

    @PostConstruct
    public void init() {
        allTests = testCaseRepository.findAll();
        grid.addColumn(TestCase::getId).setHeader("ID").setFlexGrow(0);
        grid.addColumn(TestCase::getShortDescription).setHeader("Description");
        grid.addColumn(TestCase::getPassRate).setHeader("Pass Rate");
        grid.setItems(allTests);
        addNewTest.addClickListener(listener -> {
            testCaseEditorView.open();
        });
        testCaseEditorView.addDetachListener(listener -> {
            allTests = testCaseRepository.findAll();
            grid.setItems(allTests);
        });
        add(addNewTest, grid);
    }

}
