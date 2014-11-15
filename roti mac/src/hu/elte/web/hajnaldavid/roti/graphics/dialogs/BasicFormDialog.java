package hu.elte.web.hajnaldavid.roti.graphics.dialogs;

import hu.elte.web.hajnaldavid.roti.graphics.frames.MainFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BasicFormDialog extends JDialog {
	
	protected JPanel formPanel;	
	protected final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;

	public BasicFormDialog(Window owner) {
		super(owner);
		initGui();
		setModal(true);		
		setVisible(false);
	}

	public BasicFormDialog() {	
		initGui();
		setModal(true);	
		setVisible(false);
	}
	
	protected void refresh(){		
		this.revalidate();
		this.repaint();
	}

	protected void initGui() {	
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(MainFrame.DEFAULT_BG);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(MainFrame.DEFAULT_BG);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Mentés");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Mégse");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setButtons();

	}
	
	private void setButtons(){		
		okButton.setFont(new Font("Kartika", Font.PLAIN, 11));
		okButton.setBackground(MainFrame.LIGHT_BTN);
		cancelButton.setFont(new Font("Kartika", Font.PLAIN, 11));
		cancelButton.setBackground(MainFrame.LIGHT_BTN);
	}
	

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}	


}
