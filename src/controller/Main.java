package controller;

import controller.KWICSystem.KWIC;
import controller.KWICSystem.Pipeline;
import view.MyWindow;

import javax.swing.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        KWIC pipeline = new Pipeline();
        MyWindow win = new MyWindow();
        win.init(pipeline);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}