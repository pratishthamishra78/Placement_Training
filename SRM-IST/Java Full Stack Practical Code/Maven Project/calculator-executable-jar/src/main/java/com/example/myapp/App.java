package com.example.myapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class App implements ActionListener {

    JFrame frame;
    JTextField display;

    String currentInput = "";
    double firstNumber = 0;
    String operator = "";
    boolean newNumber = true;

    JButton[] numberButtons = new JButton[10];

    public App() {

        // Create Frame
        frame = new JFrame("Java Calculator");
        frame.setSize(400, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("JAVA CALCULATOR", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        mainPanel.add(title, BorderLayout.NORTH);

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setText("0");

        mainPanel.add(display, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 3, 8, 8));

        // Number buttons
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            numberButtons[i].addActionListener(this);
        }

        // Other buttons
        JButton decimalButton = createButton(".");
        JButton addButton = createButton("+");
        JButton subtractButton = createButton("-");
        JButton multiplyButton = createButton("×");
        JButton divideButton = createButton("÷");
        JButton equalsButton = createButton("=");
        JButton clearButton = createButton("C");
        JButton deleteButton = createButton("⌫");
        JButton percentButton = createButton("%");

        // Add buttons
        buttonPanel.add(clearButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(percentButton);

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);

        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(addButton);

        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Separate panel for equals button
        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.add(equalsButton, BorderLayout.CENTER);

        // Combine button panel and equals
        JPanel container = new JPanel(new BorderLayout(8, 8));
        container.add(buttonPanel, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.remove(buttonPanel);
        mainPanel.add(container, BorderLayout.SOUTH);

        // Frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Create Button
    private JButton createButton(String text) {

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.addActionListener(this);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        // Number buttons
        if (command.matches("[0-9]")) {

            if (newNumber) {
                currentInput = command;
                newNumber = false;
            } else {
                currentInput += command;
            }

            display.setText(currentInput);
        }

        // Decimal
        else if (command.equals(".")) {

            if (newNumber) {
                currentInput = "0.";
                newNumber = false;
            } else if (!currentInput.contains(".")) {
                currentInput += ".";
            }

            display.setText(currentInput);
        }

        // Operators
        else if (command.equals("+")
                || command.equals("-")
                || command.equals("×")
                || command.equals("÷")) {

            if (!currentInput.isEmpty()) {

                firstNumber = Double.parseDouble(currentInput);
                operator = command;
                newNumber = true;
            }
        }

        // Equals
        else if (command.equals("=")) {

            calculateResult();
        }

        // Clear
        else if (command.equals("C")) {

            currentInput = "";
            firstNumber = 0;
            operator = "";
            newNumber = true;

            display.setText("0");
        }

        // Delete
        else if (command.equals("⌫")) {

            if (!currentInput.isEmpty()) {

                currentInput =
                        currentInput.substring(0, currentInput.length() - 1);

                if (currentInput.isEmpty()) {
                    display.setText("0");
                } else {
                    display.setText(currentInput);
                }
            }
        }

        // Percentage
        else if (command.equals("%")) {

            if (!currentInput.isEmpty()) {

                double number = Double.parseDouble(currentInput);
                number = number / 100;

                currentInput = String.valueOf(number);
                display.setText(currentInput);
            }
        }
    }

    // Calculation method
    private void calculateResult() {

        if (currentInput.isEmpty() || operator.isEmpty()) {
            return;
        }

        double secondNumber = Double.parseDouble(currentInput);
        double result = 0;

        try {

            switch (operator) {

                case "+":
                    result = firstNumber + secondNumber;
                    break;

                case "-":
                    result = firstNumber - secondNumber;
                    break;

                case "×":
                    result = firstNumber * secondNumber;
                    break;

                case "÷":

                    if (secondNumber == 0) {
                        display.setText("Cannot divide by 0");
                        currentInput = "";
                        operator = "";
                        newNumber = true;
                        return;
                    }

                    result = firstNumber / secondNumber;
                    break;
            }

            // Remove unnecessary .0
            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                display.setText(String.valueOf(result));
            }

            currentInput = String.valueOf(result);
            operator = "";
            newNumber = true;

        } catch (Exception ex) {

            display.setText("Error");
            currentInput = "";
            operator = "";
            newNumber = true;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new App());
    }
}