package ru.ivanovds.sg.app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotFoundException extends RuntimeException {

    public String message;
}
