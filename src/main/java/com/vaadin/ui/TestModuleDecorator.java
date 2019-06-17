package com.vaadin.ui;

import com.vaadin.dto.Answer;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class TestModuleDecorator extends VerticalLayout {
    
    private Label label;
    private List<Answer> answerDecoratorList;
    private RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
    
    public TestModuleDecorator(String question, List<Answer> answerDecoratorList){
        this.label = new Label(question);
        this.answerDecoratorList = answerDecoratorList;
        radioButtonGroup.setItems(
                answerDecoratorList.stream()
                                   .map(Answer::getText)
                                   .collect(Collectors.toList()));
        add(label, radioButtonGroup);
    }
    
}
