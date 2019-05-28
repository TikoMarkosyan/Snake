package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private final int W_Height = 300;
    private final int W_Weight = 300;
    private int snakelenght = 3;
    private int all_dots = 900;
    private int dot_size = 10;

    private int[] snakeX = new int[all_dots];
    private int[] snakeY = new int[all_dots];

    private int appleX;
    private int appleY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    private Image apple;
    private Image dots;
    private Timer timer;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(W_Height, W_Weight));
        loadimg();
        initGame();
    }

    private void initGame() {
        for (int i = snakelenght; i > 0; i--) {
            snakeX[i] = 50 - i * 10;
            snakeY[i] = 50;
        }
        applelocation();
        timer = new Timer(140, this);
        timer.start();
    }

    private void loadimg() {
        ImageIcon iid = new ImageIcon("dot.png");
        dots = iid.getImage();
        ImageIcon iia = new ImageIcon("head.png");
        apple = iia.getImage();
    }
    private void GameOver(Graphics g){
        String msg = "Game Over  your sours id "+snakelenght;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (W_Height - metr.stringWidth(msg)) / 2, W_Weight / 2);
    }
    private void snkakeliveorno() {
        for (int i = snakelenght; i > 0; i--) {
            if (snakelenght == 4 && snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                inGame = false;
            }
        }
        if (snakeX[0] > W_Weight) {
            inGame = false;
        } else if (snakeX[0] < 0) {
            inGame = false;
        } else if (snakeY[0] > W_Height) {
            inGame = false;
        } else if (snakeY[0] < 0) {
            inGame = false;
        }
    }

    private void applelocation() {
        int r = (int) (Math.random() * 29);
        appleX = ((r * dot_size));

        r = (int) (Math.random() * 29);
        appleY= ((r * dot_size));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        onDraw(g);
        if(!inGame) {
            GameOver(g);
        }
    }

    private void onDraw(Graphics g) {
        g.drawImage(apple, appleX, appleY, this);
        for (int i = snakelenght; i > 0; i--) {
            g.drawImage(dots, snakeX[i], snakeY[i], this);
        }
    }

    private void muve() {
        for (int i = snakelenght; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (left) {
            snakeX[0] -= dot_size;
        } else if (right) {
            snakeX[0] += dot_size;
        } else if (up) {
            snakeY[0] -= dot_size;
        } else if (down) {
            snakeY[0] += dot_size;
        }
    }

    private void snakeeatorno() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            snakelenght++;
            applelocation();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            snakeeatorno();
            snkakeliveorno();
            muve();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}