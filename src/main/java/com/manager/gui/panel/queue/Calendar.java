package com.manager.gui.panel.queue;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.manager.utils.DefineUtils;
import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class Calendar extends JFrame {

	/**
	 * @wbp.parser.constructor
	 */
	public Calendar(final VisitDialog addVisit) {
		setResizable(false);
		setSize(new Dimension(302, 606));
		getContentPane().setLayout(null);

		final JCalendar calendarFirstMonth = new JCalendar();
		calendarFirstMonth.setFont(DefineUtils.FONT);
		calendarFirstMonth.setLocation(0, 0);
		calendarFirstMonth.setSize(new Dimension(300, 200));
		calendarFirstMonth.getDayChooser().getDayPanel().setFont(DefineUtils.FONT);
		calendarFirstMonth.getMonthChooser().setMonth(calendarFirstMonth.getMonthChooser().getMonth() - 1);
		getContentPane().add(calendarFirstMonth);

		final JCalendar calendarSecondMonth = new JCalendar();
		calendarSecondMonth.setFont(DefineUtils.FONT);
		calendarSecondMonth.setLocation(0, 201);
		calendarSecondMonth.setSize(new Dimension(300, 200));
		getContentPane().add(calendarSecondMonth);

		final JCalendar calendarThirdMonth = new JCalendar();
		calendarThirdMonth.setLocation(0, 402);
		calendarThirdMonth.setSize(new Dimension(300, 200));
		calendarThirdMonth.setFont(DefineUtils.FONT);
		calendarThirdMonth.getMonthChooser().setMonth(calendarSecondMonth.getMonthChooser().getMonth() + 1);
		getContentPane().add(calendarThirdMonth);

		calendarFirstMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

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

		final JCalendar calendarFirstMonth = new JCalendar();
		calendarFirstMonth.setFont(DefineUtils.FONT);
		calendarFirstMonth.setLocation(0, 0);
		calendarFirstMonth.setSize(new Dimension(194, 155));
		calendarFirstMonth.getMonthChooser().setMonth(calendarFirstMonth.getMonthChooser().getMonth() - 1);
		getContentPane().add(calendarFirstMonth);

		final JCalendar calendarSecondMonth = new JCalendar();
		calendarSecondMonth.setFont(DefineUtils.FONT);
		calendarSecondMonth.setLocation(0, 155);
		calendarSecondMonth.setSize(new Dimension(194, 155));
		getContentPane().add(calendarSecondMonth);

		final JCalendar calendarThirdMonth = new JCalendar();
		calendarThirdMonth.setLocation(0, 310);
		calendarThirdMonth.setSize(new Dimension(194, 155));
		calendarThirdMonth.setFont(DefineUtils.FONT);
		calendarThirdMonth.getMonthChooser().setMonth(calendarSecondMonth.getMonthChooser().getMonth() + 1);
		getContentPane().add(calendarThirdMonth);

		calendarFirstMonth.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {

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
