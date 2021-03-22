package view;

import controller.KWICSystem.KWIC;
import controller.KWICSystem.Pipeline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MyWindow extends JFrame {

//    private JTextArea inputArea = new JTextArea("Type in your lines here");
    private JTextArea inputArea = new JTextArea("");
    private JTextArea outputArea = new JTextArea();
    private JButton processButton = new JButton("Process");
    private JButton clearButton = new JButton("Clear");
    private JButton loadButton = new JButton("Load");
    private JButton testButton = new JButton("Test");

    public void init(KWIC pipeline) {
        setSize(700, 500);
//        setLocation(200, 100);
        setLocationRelativeTo(null);
        setTitle("KWIC System (Pipes & Filters)");

        Container cp = getContentPane();

        // Add two buttons to the left part of the display window
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(processButton);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(clearButton);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(loadButton);
        leftPanel.add(Box.createRigidArea(new Dimension(10, 20)));
        leftPanel.add(testButton);

        cp.add(BorderLayout.WEST, leftPanel);

        // Add two areas to the center of the display window
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        // Add input field
        JScrollPane inputPane = new JScrollPane(
                inputArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Add output field
        JScrollPane outputPane = new JScrollPane(
                outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(inputPane);
        outputArea.setEditable(false);
        mainPanel.add(outputPane);

        cp.add(BorderLayout.CENTER, mainPanel);

        // processButton handling
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputString = inputArea.getText();
                try {
//                    KWIC pipeline = new Pipeline();
                    String outputString = pipeline.transform(inputString);
                    outputArea.setText(outputString);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // clearButton handling
        clearButton.addActionListener(e -> clearAll());

        // loadButton handling
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testData = "";
                testData = pipeline.loadTestFile();
                inputArea.setText(testData);
            }
        });

        // testButton handling
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Set up test data
                String testData = pipeline.loadTestFile();
                inputArea.setText(testData);

                String inputString = inputArea.getText();

                String[] result = pipeline.runBenchmark(inputString);

                String outputString = result[0];
                String iterations = result[1];
                String timeElapsed = result[2];

                outputArea.setText(outputString);

                // Setup results popup
                String titleBar = "Execution Time";
                String infoMessage = String.valueOf(iterations) + " iterations in " + String.valueOf(timeElapsed) + " ms" ;

//                result = masterControl.runBenchmark();
                JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private  void clearAll() {
        inputArea.setText("");
        outputArea.setText("");
    }
}
