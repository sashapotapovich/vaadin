package com.vaadin.ui;

import com.vaadin.entity.AssignedTestCase;
import com.vaadin.entity.TestCase;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.AssignedTestCaseRepository;
import com.vaadin.repository.TestCaseRepository;
import com.vaadin.ui.editor.TestCaseEditorView;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "tests", layout = MenuView.class)
public class TestDetailsView extends VerticalLayout implements RouterLayout {
    public static final String ID = "tests";
    private TestCaseRepository testCaseRepository;
    private AssignedTestCaseRepository assignedTestCaseRepository;
    private Grid<TestCase> grid = new Grid<>(TestCase.class, false);
    private Binder<TestCase> binder = new Binder<>(TestCase.class);
    private Button addNewTest = new Button("Create new Test", VaadinIcon.ADD_DOCK.create());
    private TestCaseEditorView testCaseEditorView;
    private List<TestCase> allTests;
    private Chart studentsChart = new Chart();
    public TestDetailsView(TestCaseRepository testCaseRepository, TestCaseEditorView testCaseEditorView,
                           AssignedTestCaseRepository assignedTestCaseRepository) {
        this.testCaseRepository = testCaseRepository;
        this.testCaseEditorView = testCaseEditorView;
        this.assignedTestCaseRepository = assignedTestCaseRepository;
    }
    public void initChart(TestCase testCase){
        studentsChart = new Chart();
        Configuration conf = studentsChart.getConfiguration();
        conf.getChart().setType(ChartType.PIE);
        conf.getChart().setBorderRadius(4);
        conf.setTitle("Students Passed Test - " + testCase.getShortDescription());
        List<AssignedTestCase> allByTestCase = assignedTestCaseRepository.findAllByTestCase(testCase);
        AtomicInteger passed = new AtomicInteger(0);
        AtomicInteger pending = new AtomicInteger(0);
        allByTestCase.forEach(assignedTestCase -> {
            if (assignedTestCase.getPassed()){
                passed.incrementAndGet();
            } else {
                pending.incrementAndGet();
            }
        });
        DataSeries deliveriesPerProductSeries = new DataSeries(Arrays.asList(new DataSeriesItem("passed", passed.intValue()),
                                                                             new DataSeriesItem("pending", pending.intValue())));
        PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
        plotOptionsPie.setInnerSize("60%");
        plotOptionsPie.getDataLabels().setCrop(false);
        deliveriesPerProductSeries.setPlotOptions(plotOptionsPie);
        conf.addSeries(deliveriesPerProductSeries);
        addComponentAsFirst(studentsChart);
    }
    @PostConstruct
    public void init() {
        allTests = testCaseRepository.findAll();
        grid.addColumn(TestCase::getId).setHeader("ID").setFlexGrow(0).setSortable(true);
        grid.addColumn(TestCase::getShortDescription).setHeader("Description").setSortable(true);
        grid.addColumn(TestCase::getPassRate).setHeader("Pass Rate").setSortable(true);
        grid.setItems(allTests);
        grid.addItemClickListener(listener ->{
            remove(studentsChart);
            initChart(listener.getItem());
        });
        addNewTest.addClickListener(listener -> {
            testCaseEditorView.open();
        });
        testCaseEditorView.addDetachListener(listener -> {
            allTests = testCaseRepository.findAll();
            grid.setItems(allTests);
        });
        studentsChart.setVisible(false);
        add(studentsChart, addNewTest, grid);
    }
}
