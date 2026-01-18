import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple test application that demonstrates the usage of JDatePanel and JDatePicker.
 * 
 * This application creates a Swing GUI with:
 * - A JDatePanel (embedded calendar component)
 * - A JDatePicker (popup calendar with text field)
 * - Buttons to read and print the selected dates
 */
public class SimpleTestApp {

    public static void main(String[] args) {
        // Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("JDatePicker Test Application");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center on screen

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create components panel with GridBagLayout for better control
        JPanel componentsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add JDatePanel section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel panelLabel = new JLabel("JDatePanel (Embedded):");
        panelLabel.setFont(panelLabel.getFont().deriveFont(Font.BOLD));
        componentsPanel.add(panelLabel, gbc);

        gbc.gridy = 1;
        final JDatePanel datePanel = new JDatePanel();
        componentsPanel.add(datePanel, gbc);

        // Add some spacing
        gbc.gridy = 2;
        componentsPanel.add(Box.createVerticalStrut(20), gbc);

        // Add JDatePicker section
        gbc.gridy = 3;
        JLabel pickerLabel = new JLabel("JDatePicker (Popup):");
        pickerLabel.setFont(pickerLabel.getFont().deriveFont(Font.BOLD));
        componentsPanel.add(pickerLabel, gbc);

        gbc.gridy = 4;
        final JDatePicker datePicker = new JDatePicker();
        componentsPanel.add(datePicker, gbc);

        mainPanel.add(componentsPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Add button to read values
        JButton readButton = new JButton("Read Selected Dates");
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read JDatePanel value
                Calendar panelDate = (Calendar) datePanel.getModel().getValue();
                String panelDateStr = panelDate != null 
                    ? dateFormat.format(panelDate.getTime()) 
                    : "No date selected";

                // Read JDatePicker value
                Calendar pickerDate = (Calendar) datePicker.getModel().getValue();
                String pickerDateStr = pickerDate != null 
                    ? dateFormat.format(pickerDate.getTime()) 
                    : "No date selected";

                // Display results
                String message = String.format(
                    "JDatePanel: %s%nJDatePicker: %s",
                    panelDateStr,
                    pickerDateStr
                );
                JOptionPane.showMessageDialog(frame, message, "Selected Dates", 
                    JOptionPane.INFORMATION_MESSAGE);

                // Also print to console
                System.out.println("=== Selected Dates ===");
                System.out.println("JDatePanel:  " + panelDateStr);
                System.out.println("JDatePicker: " + pickerDateStr);
                System.out.println();
            }
        });
        buttonPanel.add(readButton);

        // Add button to set today's date
        JButton setTodayButton = new JButton("Set Today's Date");
        setTodayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar today = Calendar.getInstance();
                datePanel.getModel().setDate(
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                );
                datePanel.getModel().setSelected(true);

                datePicker.getModel().setDate(
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
                );
                datePicker.getModel().setSelected(true);

                System.out.println("Set both components to today's date");
            }
        });
        buttonPanel.add(setTodayButton);

        // Add button to clear dates
        JButton clearButton = new JButton("Clear Dates");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePanel.getModel().setSelected(false);
                datePicker.getModel().setSelected(false);
                System.out.println("Cleared both date selections");
            }
        });
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to frame
        frame.setContentPane(mainPanel);

        // Display the frame
        frame.setVisible(true);

        System.out.println("JDatePicker Test Application started");
        System.out.println("Use the buttons to interact with the date components");
    }
}
