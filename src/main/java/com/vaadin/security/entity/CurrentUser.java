package com.vaadin.security.entity;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
