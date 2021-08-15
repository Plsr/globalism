package de.christianpoplawski.globalism;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class App extends JFrame {
	private final int WIDTH = 660;
	private final int HEIGHT = 660;
	
    public App() {
        initUI();
    }

    private void initUI() {
        add(new Board(WIDTH, HEIGHT));

        setResizable(false);
        pack();

        setSize(WIDTH, HEIGHT);
        setTitle("animation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        EventQueue.invokeLater(() -> {
            App ex = new App();
            ex.setVisible(true);
        });
    }
}
