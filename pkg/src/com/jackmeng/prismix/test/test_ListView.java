
package com.jackmeng.prismix.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class test_ListView extends JFrame
{
    private DefaultListModel< String > listModel;
    private JList< String > list;
    private JTextField textField;
    private JPanel componentPanel;
    private List< JComponent > componentList;

    public test_ListView()
    {
        super("List Component Example");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        textField = new JTextField();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)
            {
                String text = textField.getText();
                if (!text.isEmpty())
                {
                    listModel.addElement(text);
                    textField.setText("");
                }
            }
        });
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
        componentList = new ArrayList<>();

        JButton addComponentButton = new JButton("Add Component");
        addComponentButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)
            {
                JTextField newTextField = new JTextField();
                componentList.add(newTextField);
                componentPanel.add(newTextField);
                componentPanel.revalidate();
                componentPanel.repaint();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(componentPanel, BorderLayout.CENTER);
        mainPanel.add(addComponentButton, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run()
            {
                new test_ListView();
            }
        });
    }
}
