package com.manager.gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.toedter.calendar.JCalendar;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("serial")
public class Calendar extends JFrame {

	public Calendar(final AddVisit addVisit) {
		setResizable(false);
		setSize(new Dimension(200, 200));
		final JCalendar calendar = new JCalendar();
		calendar.setSize(new Dimension(100, 100));
		getContentPane().add(calendar, BorderLayout.CENTER);

		calendar.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					if (!addVisit.dayOfSave) {
						addVisit.textFieldDate.setText(
								calendar.getDayChooser().getDay() + "/" + (calendar.getMonthChooser().getMonth() + 1)
										+ "/" + calendar.getYearChooser().getYear());
					} else{
						addVisit.textFieldDayOfSave.setText(
								calendar.getDayChooser().getDay() + "/" + (calendar.getMonthChooser().getMonth() + 1)
										+ "/" + calendar.getYearChooser().getYear());
					}
					setVisible(false);
				} catch (Exception ex) {
				}
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Calendar(final AddDate addDate) {
		setResizable(false);
		setSize(new Dimension(200, 200));
		final JCalendar calendar = new JCalendar();
		calendar.setSize(new Dimension(100, 100));
		getContentPane().add(calendar, BorderLayout.CENTER);

		calendar.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					addDate.textField.setText(calendar.getDayChooser().getDay() + "/"
							+ (calendar.getMonthChooser().getMonth() + 1) + "/" + calendar.getYearChooser().getYear());
					setVisible(false);
				} catch (Exception ex) {
				}
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
