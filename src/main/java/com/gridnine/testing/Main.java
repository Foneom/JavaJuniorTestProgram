package com.gridnine.testing;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Input input = new Input() {
            Scanner scanner = new Scanner(System.in);
            @Override
            public String askStr(String question) {
                question = scanner.nextLine();
                return question;
            }
        };
        new StartUI().init(input);
    }

}
