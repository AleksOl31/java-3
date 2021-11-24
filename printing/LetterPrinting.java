package ru.alexanna.lesson_4.printing;

public class LetterPrinting {
    private char currentLetter = 'A';

    public static void main(String[] args) {
        LetterPrinting letterPrinting = new LetterPrinting();
        new Thread(() -> {
            letterPrinting.printA();
        }).start();

        new Thread(() -> {
            letterPrinting.printB();
        }).start();

        new Thread(() -> {
            letterPrinting.printC();
        }).start();
    }

    public synchronized void printA() {
        try {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'A') {
                    wait();
                }
                System.out.print('A');
                currentLetter = 'B';
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void printB() {
        try {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'B') {
                    wait();
                }
                System.out.print('B');
                currentLetter = 'C';
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void printC() {
        try {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'C') {
                    wait();
                }
                System.out.print('C');
                currentLetter = 'A';
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
