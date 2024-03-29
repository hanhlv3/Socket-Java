package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI {
    private JFrame frame;
    private JTextField numberField1;
    private JTextField numberField2;
    private JTextField operatorField;
    private JTextArea resultArea;
    private JButton calculateButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientGUI() {
        frame = new JFrame("Calculator Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(308, 292);

        numberField1 = new JTextField();
        numberField1.setBounds(97, 0, 128, 31);
        numberField2 = new JTextField();
        numberField2.setBounds(97, 37, 128, 20);
        operatorField = new JTextField();
        operatorField.setBounds(97, 73, 128, 20);
        resultArea = new JTextArea();
        resultArea.setBounds(97, 159, 128, 20);
        resultArea.setEditable(false);
        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(1, 108, 128, 37);
        frame.getContentPane().setLayout(null);

        JLabel label = new JLabel("Number 1:");
        label.setBounds(1, 0, 97, 31);
        frame.getContentPane().add(label);
        frame.getContentPane().add(numberField1);
        JLabel label_1 = new JLabel("Operator:");
        label_1.setBounds(1, 68, 97, 31);
        frame.getContentPane().add(label_1);
        frame.getContentPane().add(operatorField);
        JLabel label_2 = new JLabel("Number 2:");
        label_2.setBounds(1, 42, 97, 31);
        frame.getContentPane().add(label_2);
        frame.getContentPane().add(numberField2);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        frame.getContentPane().add(calculateButton);
        JLabel label_3 = new JLabel("Result:");
        label_3.setBounds(11, 156, 105, 31);
        frame.getContentPane().add(label_3);
        frame.getContentPane().add(resultArea);
        frame.setVisible(true);

        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calculate() {
        String number1 = numberField1.getText();
        String number2 = numberField2.getText();
        String operator = operatorField.getText();

        if (number1.isEmpty() || number2.isEmpty() || operator.isEmpty()) {
            resultArea.setText("Nhập đủ số và toán tử");
            return;
        }

        out.println(number1 + " " + operator + " " + number2);
        out.flush();

        try {
            String response = in.readLine();
            resultArea.setText("Kết quả: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI();
            }
        });
    }
}
