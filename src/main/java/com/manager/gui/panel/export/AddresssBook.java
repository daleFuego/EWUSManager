package com.manager.gui.panel.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.manager.dao.DBData;
import com.manager.utils.DefineUtils;

@SuppressWarnings("serial")
public class AddresssBook extends JFrame {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AddresssBook(JTextField textFieldReceiver) {
		setResizable(false);
		setSize(416, 168);
		getContentPane().setLayout(null);
		setVisible(true);

		JLabel lblWybierzOdbiorc = new JLabel("Odbiorcy");
		lblWybierzOdbiorc.setBounds(8, 8, 66, 14);
		getContentPane().add(lblWybierzOdbiorc);

		JScrollPane scrollPaneReceivers = new JScrollPane();
		scrollPaneReceivers.setBounds(8, 30, 260, 87);
		getContentPane().add(scrollPaneReceivers);

		JList listReceivers = new JList(DBData.getInstance().getReceivers());
		scrollPaneReceivers.setViewportView(listReceivers);

		JButton btnSelect = new JButton("Wybierz");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textFieldReceiver.setText(listReceivers.getSelectedValue().toString());
				dispose();
			}
		});
		btnSelect.setBounds(276, 28, 109, 20);
		getContentPane().add(btnSelect);

		JButton btnDelete = new JButton("Usuń");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DBData.getInstance().removeFromContacts(listReceivers.getSelectedValue().toString());
					DefaultListModel model = (DefaultListModel) listReceivers.getModel();
					model.remove(listReceivers.getSelectedIndex());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(new JFrame(), "Wystąpił błąd", DefineUtils.APP_TITLE,
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		listReceivers.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					textFieldReceiver.setText(listReceivers.getSelectedValue().toString());
					dispose();
				}
			}
		});

		btnDelete.setBounds(276, 97, 109, 20);
		getContentPane().add(btnDelete);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
