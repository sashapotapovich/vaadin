package com.vaadin.ui.editor;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class TestModuleEditor extends Div {

    private TextArea question = new TextArea("Question");
    private TextField answer1 = new TextField("Answer");
    private TextField answer2 = new TextField("Answer");
    private TextField answer3 = new TextField("Answer");
    private TextField answer4 = new TextField("Answer");
    private Checkbox checkbox1 = new Checkbox("Is Correct?");
    private Checkbox checkbox2 = new Checkbox("Is Correct?");
    private Checkbox checkbox3 = new Checkbox("Is Correct?");
    private Checkbox checkbox4 = new Checkbox("Is Correct?");
    private VerticalLayout answers1 = new VerticalLayout(answer1, checkbox1);
    private VerticalLayout answers2 = new VerticalLayout(answer2, checkbox2);
    private VerticalLayout answers3 = new VerticalLayout(answer3, checkbox3);
    private VerticalLayout answers4 = new VerticalLayout(answer4, checkbox4);
    private HorizontalLayout horizontalLayout = new HorizontalLayout(answers1, answers2, answers3, answers4);
    private VerticalLayout verticalLayout = new VerticalLayout(question, horizontalLayout);

    
    public TestModuleEditor() {
        question.setSizeFull();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(verticalLayout);
    }

}
