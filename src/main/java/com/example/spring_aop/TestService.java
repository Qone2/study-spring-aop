package com.example.spring_aop;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void printSomething() {
        System.out.println("Something");
        printSomethingElse();
    }

    public void printSomethingElse() {
        System.out.println("Something else");
    }
}
