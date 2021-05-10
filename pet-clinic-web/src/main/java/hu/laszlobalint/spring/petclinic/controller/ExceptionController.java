package hu.laszlobalint.spring.petclinic.controller;

import exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({NotFoundException.class, NumberFormatException.class})
    public ModelAndView exceptionHandler(Exception exception) {
        log.error(exception.getMessage());

        return new ModelAndView("error/exception", new ModelMap("exception", exception));
    }
}
