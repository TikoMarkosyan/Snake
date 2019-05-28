package com.company;

import javax.swing.*;
import java.awt.*;

public class Main  extends JFrame {

    public Main(){
        Snake();
    }
    public void Snake(){
        add(new Board());
        setVisible(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}
