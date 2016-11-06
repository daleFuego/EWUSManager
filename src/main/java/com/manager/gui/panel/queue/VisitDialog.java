package com.manager.gui.panel.queue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.manager.data.PeselValidator;
import com.manager.logic.QueueManager;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class VisitDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTextField textFieldName;
	public JTextField textFieldPesel;
	public JTextField textFieldSurname;
	public JTextField textFieldDate;
	public JTextField textFieldDayOfSave;
	public boolean dayOfSave = false;
	private String currentDate = new SimpleDateFormat("dd/MM/yyyy").format((new Date())).toString();

	public VisitDialog(final QueueManager queueManager) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(false);
		final Calendar calendar = new Calendar(this);
		setTitle("Dodaj pacjenta");
		setBounds(100, 100, 350, 211);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblDate = new JLabel("Data wizyty");
			lblDate.setFont(DefineUtils.FONT);
			lblDate.setBounds(21, 111, 86, 14);
			contentPanel.add(lblDate);
		}
		{
			textFieldSurname = new JTextField();
			textFieldSurname.setFont(DefineUtils.FONT);
			textFieldSurname.setBounds(128, 58, 171, 20);
			contentPanel.add(textFieldSurname);
			textFieldSurname.setColumns(10);
		}
		{
			textFieldPesel = new JTextField();
			textFieldPesel.setFont(DefineUtils.FONT);
			textFieldPesel.setBounds(128, 83, 171, 20);

			textFieldPesel.addCaretListener(new CaretListener() {

				public void caretUpdate(CaretEvent e) {
					if (new PeselValidator(textFieldPesel.getText()).isValid()) {
						textFieldPesel.setForeground(Color.BLACK);
					} else {
						textFieldPesel.setForeground(Color.RED);
					}
				}
			});
			contentPanel.add(textFieldPesel);
			textFieldPesel.setColumns(10);
		}
		{
			textFieldName = new JTextField();
			textFieldName.setFont(DefineUtils.FONT);
			textFieldName.setBounds(128, 33, 171, 20);
			contentPanel.add(textFieldName);
			textFieldName.setColumns(10);
		}
		{
			JLabel lblPesel = new JLabel("PESEL");
			lblPesel.setFont(DefineUtils.FONT);
			lblPesel.setBounds(21, 86, 86, 14);
			contentPanel.add(lblPesel);
		}
		{
			JLabel lblSurname = new JLabel("Nazwisko");
			lblSurname.setFont(DefineUtils.FONT);
			lblSurname.setBounds(21, 61, 86, 14);
			contentPanel.add(lblSurname);
		}
		{
			textFieldDate = new JTextField(currentDate);
			textFieldDate.setFont(DefineUtils.FONT);
			textFieldDate.setEditable(false);
			textFieldDate.setBounds(128, 108, 99, 20);
			contentPanel.add(textFieldDate);
			textFieldDate.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Imię");
			lblName.setBounds(21, 36, 86, 14);
			lblName.setFont(DefineUtils.FONT);
			contentPanel.add(lblName);
		}

		JButton btnDate = new JButton("Data");
		btnDate.setFont(DefineUtils.FONT);
		btnDate.setBounds(237, 107, 62, 23);
		btnDate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dayOfSave = false;
				calendar.setVisible(true);
			}
		});
		contentPanel.add(btnDate);
		{
			JLabel lblDayOfSave = new JLabel("Zapisany dnia");
			lblDayOfSave.setFont(DefineUtils.FONT);
			lblDayOfSave.setBounds(21, 11, 86, 14);
			contentPanel.add(lblDayOfSave);
		}
		{
			textFieldDayOfSave = new JTextField(currentDate);
			textFieldDayOfSave.setEditable(false);
			textFieldDayOfSave.setColumns(10);
			textFieldDayOfSave.setBounds(128, 8, 99, 20);
			textFieldDayOfSave.setFont(DefineUtils.FONT);
			contentPanel.add(textFieldDayOfSave);
		}
		{
			JButton buttonDateOfSave = new JButton("Data");
			buttonDateOfSave.setBounds(237, 7, 62, 23);
			buttonDateOfSave.setFont(DefineUtils.FONT);
			buttonDateOfSave.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					dayOfSave = true;
					calendar.setVisible(true);
				}
			});
			contentPanel.add(buttonDateOfSave);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setFont(DefineUtils.FONT);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						calendar.setVisible(false);
						queueManager.deliverData(textFieldDayOfSave.getText() + "\t" + textFieldName.getText() + "\t"
								+ textFieldSurname.getText() + "\t" + textFieldPesel.getText() + "\t"
								+ textFieldDate.getText() + System.lineSeparator());
					}

				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.setFont(DefineUtils.FONT);
				cancelButton.setSize(89, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				SwingUtilities.updateComponentTreeUI(this);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
