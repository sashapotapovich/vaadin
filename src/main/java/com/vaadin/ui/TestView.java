package com.vaadin.ui;

import com.google.gson.Gson;
import com.vaadin.entity.Answer;
import com.vaadin.entity.AssignedTestCase;
import com.vaadin.entity.CurrentUser;
import com.vaadin.entity.Student;
import com.vaadin.entity.TestCase;
import com.vaadin.entity.TestModule;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.AssignedTestCaseRepository;
import com.vaadin.repository.StudentRepository;
import com.vaadin.repository.TestCaseRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "testExecution", layout = MenuView.class)
public class TestView extends VerticalLayout implements RouterLayout {
    public static final String ID = "testExecution";

    private TestCaseRepository testCaseRepository;
    private StudentRepository studentRepository;
    private AssignedTestCaseRepository assignedTestCaseRepository;
    private CurrentUser currentUser;
    private List<TestModuleDecorator> allAnswers = new ArrayList<>();
    private Button finish = new Button("Finish Test Execution", VaadinIcon.DIPLOMA.create());
    private BigDecimal mark;
    private Label finishText = new Label();
    private Integer passRate;
    private Label passRateMessage = new Label();
    private Button closeDialog = new Button("Close", VaadinIcon.CLOSE.create());
    private Button saveResults = new Button("Save", VaadinIcon.DISC.create());
    private HorizontalLayout actions = new HorizontalLayout(saveResults, closeDialog);
    private VerticalLayout dialogLayout = new VerticalLayout(finishText, passRateMessage, actions);
    private Dialog dialog = new Dialog(dialogLayout);
    private static TestCase current;

    public TestView(TestCaseRepository testCaseRepository, AssignedTestCaseRepository assignedTestCaseRepository,
                    StudentRepository studentRepository, CurrentUser currentUser) {
        this.testCaseRepository = testCaseRepository;
        this.assignedTestCaseRepository = assignedTestCaseRepository;
        this.studentRepository = studentRepository;
        this.currentUser = currentUser;
    }

    @PostConstruct
    public void init() {
        closeDialog.addClickListener((listener) -> dialog.close());
        saveResults.setEnabled(false);
        finish.addClickListener(listener -> {
            mark  = BigDecimal.valueOf(0);
            allAnswers.forEach(testModuleDecorator -> {
                Optional<Answer> first = testModuleDecorator.getAnswerDecoratorList().stream().filter(answer -> answer.getIsCorrect()).findFirst();
                if (testModuleDecorator.getRadioButtonGroup().getValue().equals(first.get().getText())){
                    mark = mark.add(BigDecimal.valueOf(1));
                }
            });
            mark = mark.multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(allAnswers.size()), 2, RoundingMode.HALF_UP);
            finishText.setText("Final Mark - " + mark.doubleValue() + "%");
            if (mark.doubleValue() >= Double.valueOf(passRate)){
                saveResults.setEnabled(true);
                saveResults.getElement().getThemeList().add("success");
            }
            dialog.open();
        });
        saveResults.addClickListener(listener -> {
            Student currentStudent = studentRepository.findByFirstNameLikeAndLastNameLike(currentUser.getUser().getFirstName(),
                                                                                                          currentUser.getUser().getLastName());
            AssignedTestCase assignedTestCase = new AssignedTestCase(currentStudent, current, true);
            assignedTestCaseRepository.saveAndFlush(assignedTestCase);
            dialog.close();
        });
        //List<TestCase> all = testCaseRepository.findAll();
        //current = all.get(0);
        String questions = current.getQuestions();
        passRate = current.getPassRate();
        passRateMessage.setText("Pass Rate - " + passRate + "%");
        VerticalLayout layout = new VerticalLayout();
        TestModule[] module = new Gson().fromJson(questions, TestModule[].class);
        List<TestModule> testModules = Arrays.asList(module);
        AtomicInteger i = new AtomicInteger(1);
        testModules.forEach(testModule -> {
            H2 textArea = new H2("Question №" + i.getAndIncrement());
            TestModuleDecorator testModuleDecorator = new TestModuleDecorator(testModule.getQuestion(), testModule.getAnswers());
            allAnswers.add(testModuleDecorator);
            add(textArea, testModuleDecorator);
        });
        layout.setAlignItems(Alignment.BASELINE);
        layout.setSizeFull();
        add(layout, finish);
    }
    
    public static void setCurrent(TestCase testCase){
        current = testCase;
    }

}
