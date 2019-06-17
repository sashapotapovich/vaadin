package com.vaadin.ui.editor;

import com.google.gson.Gson;
import com.vaadin.dto.Answer;
import com.vaadin.entity.TestCase;
import com.vaadin.dto.TestModule;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.repository.TestCaseRepository;
import com.vaadin.ui.MenuView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "createTest", layout = MenuView.class)
public class TestCaseEditorView extends Dialog implements RouterLayout {
    
    private Button create = new Button("Create", VaadinIcon.DISC.create());
    private Button addQuestion = new Button("Add Question", VaadinIcon.ADD_DOCK.create());
    private TextField passRate = new TextField("Pass Rate");
    private TextField shortDecription = new TextField("Short Description");
    private HorizontalLayout actions = new HorizontalLayout(addQuestion, create);
    private List<TestModuleEditor> questions = new ArrayList<>();
    private TestCaseRepository testCaseRepository;
    private VerticalLayout layout = new VerticalLayout();

    public TestCaseEditorView(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @PostConstruct
    public void init() {
        questions.add(addQuestionSection());
        HorizontalLayout horizontalLayout = new HorizontalLayout(shortDecription, passRate);
        horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.END, passRate);
        layout.add(horizontalLayout);
        //add();
        questions.forEach(layout::add);
        layout.add(actions);
        addQuestion.addClickListener(listener -> {
            layout.remove(actions);
            questions.add(addQuestionSection());
            layout.add(questions.get(questions.size() - 1));
            layout.add(actions);
        });
        create.addClickListener(listener -> {
            TestCase newTest = new TestCase();
            newTest.setShortDescription(shortDecription.getValue());
            newTest.setPassRate(Integer.valueOf(passRate.getValue()));
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            questions.forEach(testModuleEditor -> {
                TestModule testModule = new TestModule();
                testModule.setQuestion(testModuleEditor.getQuestion().getValue());
                testModule.setAnswers(Arrays.asList(
                        new Answer(testModuleEditor.getAnswer1().getValue(), testModuleEditor.getCheckbox1().getValue()),
                        new Answer(testModuleEditor.getAnswer2().getValue(), testModuleEditor.getCheckbox2().getValue()),
                        new Answer(testModuleEditor.getAnswer3().getValue(), testModuleEditor.getCheckbox3().getValue()),
                        new Answer(testModuleEditor.getAnswer4().getValue(), testModuleEditor.getCheckbox4().getValue())
                ));
                String jsonModule = new Gson().toJson(testModule, TestModule.class);
                sb.append(jsonModule).append(",");
            });
            sb.append("]");
            newTest.setQuestions(sb.toString());
            testCaseRepository.saveAndFlush(newTest);
            this.close();
        });
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(layout);
    }

    public TestModuleEditor addQuestionSection(){
        TestModuleEditor testModuleEditor = new TestModuleEditor();
        return testModuleEditor;
    }
    
    
}
