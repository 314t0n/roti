package hu.elte.web.hajnaldavid.roti.graphics.frames;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class BaseFrame extends JFrame {
	
	public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
