package com;

public class Son extends Father{

    protected void say(){
        System.out.println("son");
    }

    public static void main(String[] args) {
        new Son().say();
    }
}
