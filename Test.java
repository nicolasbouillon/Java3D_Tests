package fr.nantes1900;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.nantes1900.view.GlobalViewer;

public class Test {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                final GlobalViewer viewer = new GlobalViewer();
                viewer.setVisible(true);
                viewer.setSize(800, 600);
                viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
