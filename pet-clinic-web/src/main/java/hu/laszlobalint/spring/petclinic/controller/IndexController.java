package hu.laszlobalint.spring.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    public String index() {

        return "index";
    }

    @GetMapping("/error")
    public String find() {

        return "error/not-implemented";
    }
}
