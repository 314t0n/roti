package hu.elte.web.hajnaldavid.roti.graphics.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
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

	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}	


}
