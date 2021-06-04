package de.christianpoplawski.globalism;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class App extends JFrame {
    public App() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setSize(330, 330);
        setTitle("Donut");
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
