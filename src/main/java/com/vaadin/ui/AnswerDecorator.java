package com.vaadin.ui;

import com.vaadin.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class AnswerDecorator {

    private String label;
    private Boolean isCorrect;

    public AnswerDecorator(Answer answer) {
        this.isCorrect = answer.getIsCorrect();
        this.label = answer.getText();
    }
}
