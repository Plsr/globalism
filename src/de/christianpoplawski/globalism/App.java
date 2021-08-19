package de.christianpoplawski.globalism;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class App extends JFrame {
	private final int WIDTH = 30;
	private final int HEIGHT = 30;
	
    public App() {
        initUI();
    }

    private void initUI() {
        add(new Board(WIDTH, HEIGHT));

        setResizable(false);
        pack();
        setTitle("animation");
        System.out.println(getPreferredSize());
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
