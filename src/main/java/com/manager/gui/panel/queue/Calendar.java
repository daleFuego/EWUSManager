package com.manager.gui.panel.queue;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class Calendar extends JFrame {

	/**
	 * @wbp.parser.constructor
	 */
	public Calendar(final VisitDialog addVisit) {
		setResizable(false);
		setSize(new Dimension(200, 495));
		getContentPane().setLayout(null);

		JCalendar calendarFirstMonth = new JCalendar();
		calendarFirstMonth.setLocation(0, 0);
		calendarFirstMonth.setSize(new Dimension(194, 155));
		calendarFirstMonth.getMonthChooser().setMonth(calendarFirstMonth.getMonthChooser().getMonth() - 1);
		getContentPane().add(calendarFirstMonth);

		JCalendar calendarSecondMonth = new JCalendar();
//		calendarSecondMonth.getMonthChooser().setMonth(calendarFirstMonth.getMonthChooser().getMonth());
		calendarSecondMonth.setLocation(0, 155);
		calendarSecondMonth.setSize(new Dimension(194, 155));
		getContentPane().add(calendarSecondMonth);

		JCalendar calendarThirdMonth = new JCalendar();
		calendarThirdMonth.setLocation(0, 310);
		calendarThirdMonth.setSize(new Dimension(194, 155));
		calendarThirdMonth.getMonthChooser().setMonth(calendarSecondMonth.getMonthChooser().getMonth() + 1);
		getContentPane().add(calendarThirdMonth);

		calendarFirstMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					if (addVisit.dayOfSave) {
						addVisit.textFieldDayOfSave.setText(calendarFirstMonth.getDayChooser().getDay() + "/"
								+ (calendarFirstMonth.getMonthChooser().getMonth() + 1) + "/"
								+ calendarFirstMonth.getYearChooser().getYear());

					} else {
						addVisit.textFieldDate.setText(calendarFirstMonth.getDayChooser().getDay() + "/"
								+ (calendarFirstMonth.getMonthChooser().getMonth() + 1) + "/"
								+ calendarFirstMonth.getYearChooser().getYear());

					}
					setVisible(false);
				} catch (Exception ex) {
				}
			}
		});

		calendarSecondMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					if (addVisit.dayOfSave) {
						addVisit.textFieldDayOfSave.setText(calendarSecondMonth.getDayChooser().getDay() + "/"
								+ (calendarSecondMonth.getMonthChooser().getMonth() + 1) + "/"
								+ calendarSecondMonth.getYearChooser().getYear());

					} else {
						addVisit.textFieldDate.setText(calendarSecondMonth.getDayChooser().getDay() + "/"
								+ (calendarSecondMonth.getMonthChooser().getMonth() + 1) + "/"
								+ calendarSecondMonth.getYearChooser().getYear());

					}
					setVisible(false);
				} catch (Exception ex) {
				}
			}
		});

		calendarThirdMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					if (addVisit.dayOfSave) {
						addVisit.textFieldDayOfSave.setText(calendarThirdMonth.getDayChooser().getDay() + "/"
								+ (calendarThirdMonth.getMonthChooser().getMonth() + 1) + "/"
								+ calendarThirdMonth.getYearChooser().getYear());

					} else {
						addVisit.textFieldDate.setText(calendarThirdMonth.getDayChooser().getDay() + "/"
								+ (calendarThirdMonth.getMonthChooser().getMonth() + 1) + "/"
								+ calendarThirdMonth.getYearChooser().getYear());

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

	public Calendar(final DateFrame addDate) {
		setResizable(false);
		setSize(new Dimension(200, 495));
		getContentPane().setLayout(null);

		JCalendar calendarFirstMonth = new JCalendar();
		calendarFirstMonth.setLocation(0, 0);
		calendarFirstMonth.setSize(new Dimension(194, 155));
		calendarFirstMonth.getMonthChooser().setMonth(calendarFirstMonth.getMonthChooser().getMonth() - 1);
		getContentPane().add(calendarFirstMonth);

		JCalendar calendarSecondMonth = new JCalendar();
		calendarSecondMonth.setLocation(0, 155);
		calendarSecondMonth.setSize(new Dimension(194, 155));
		getContentPane().add(calendarSecondMonth);

		JCalendar calendarThirdMonth = new JCalendar();
		calendarThirdMonth.setLocation(0, 310);
		calendarThirdMonth.setSize(new Dimension(194, 155));
		calendarThirdMonth.getMonthChooser().setMonth(calendarSecondMonth.getMonthChooser().getMonth() + 1);
		getContentPane().add(calendarThirdMonth);

		calendarFirstMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					addDate.textField.setText(calendarFirstMonth.getDayChooser().getDay() + "/"
							+ (calendarFirstMonth.getMonthChooser().getMonth() + 1) + "/"
							+ calendarFirstMonth.getYearChooser().getYear());

					setVisible(false);
				} catch (Exception ex) {
				}
			}
		});

		calendarSecondMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					addDate.textField.setText(calendarSecondMonth.getDayChooser().getDay() + "/"
							+ (calendarSecondMonth.getMonthChooser().getMonth() + 1) + "/"
							+ calendarSecondMonth.getYearChooser().getYear());

					setVisible(false);
				} catch (Exception ex) {
				}
			}
		});

		calendarThirdMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					addDate.textField.setText(calendarThirdMonth.getDayChooser().getDay() + "/"
							+ (calendarThirdMonth.getMonthChooser().getMonth() + 1) + "/"
							+ calendarThirdMonth.getYearChooser().getYear());

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
