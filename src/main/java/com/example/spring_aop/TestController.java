package com.example.spring_aop;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/test1")
    public String test1(String name, int age, String email) {
        testService.printSomething();
        return "name: " + name;
    }

    @PostMapping("/test2")
    public String test2(String name, int age, String email) {
        return "age: " + age;
    }

    @PostMapping("/test3")
    public String test3(String name, int age, String email) {
        return "email: " + email;
    }
}
