package ru.vsu.cs.p_p_v.GUI;

import ru.vsu.cs.p_p_v.RectangleFile;
import ru.vsu.cs.p_p_v.RectangleFiller;
import ru.vsu.cs.p_p_v.RectangleFiller.Rectangle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class FormMain extends JFrame {
    private JTable tableInput;
    private JTable tableOutput;
    private JButton buttonLoad;
    private JButton buttonSave;
    private JButton buttonProcess;
    private JPanel panelMain;
    private JButton buttonIncreaseSize;
    private JButton buttonDecreaseSize;
    private JButton buttonUpdate;

    public FormMain() {
        this.setTitle("Arithmetic progression creator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.setSize(600, 300);

        buttonIncreaseSize.setMargin(new Insets(0, 0, 0, 0));
        buttonDecreaseSize.setMargin(new Insets(0, 0, 0, 0));

        DefaultTableModel model = new DefaultTableModel();
        model.setRowCount(2);
        model.setColumnCount(4);
        tableInput.setModel(model);

        buttonLoad.addActionListener(e -> buttonLoadPressed());
        buttonProcess.addActionListener(e -> buttonProcessPressed());
        buttonSave.addActionListener(e -> buttonSavePressed());
        buttonIncreaseSize.addActionListener(e -> buttonIncreaseSizePressed());
        buttonDecreaseSize.addActionListener(e -> buttonDecreaseSizePressed());
        buttonUpdate.addActionListener(e -> updateInputVisualization());
    }

    private void buttonLoadPressed() {
        try {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnCount(4);

                File file = fc.getSelectedFile();

                Scanner fileScan = new Scanner(file);

                Scanner lineScan = new Scanner(fileScan.nextLine());
                model.addRow(new Object[]{lineScan.nextInt(), lineScan.nextInt()});

                while (fileScan.hasNextLine()) {
                    Scanner rectangleScan = new Scanner(fileScan.nextLine());
                    model.addRow(new Object[]{rectangleScan.nextInt(), rectangleScan.nextInt(), rectangleScan.nextInt(), rectangleScan.nextInt()});
                }

                tableInput.setModel(model);

                updateInputVisualization();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    private void buttonProcessPressed() {
        try {
            String tableData = getTableData(tableInput);
            RectangleFiller filler = RectangleFile.rectangleFillerFromString(tableData);

            List<Rectangle> newRectangles = filler.fillByRectangles();

            DefaultTableModel model = (DefaultTableModel) tableOutput.getModel();

            for (Rectangle rect : newRectangles) {
                for (int y = rect.pointTopLeft.y; y > rect.pointBottomRight.y; y--) {
                    for (int x = rect.pointTopLeft.x; x < rect.pointBottomRight.x; x++) {
                        model.setValueAt("+", filler.sizeY - y, x);
                    }
                }
            }

            tableOutput.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    private void buttonSavePressed() {
        try {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            int returnVal = fc.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                String tableData = getTableData(tableInput);
                RectangleFiller filler = RectangleFile.rectangleFillerFromString(tableData);

                List<Rectangle> newRectangles = filler.fillByRectangles();

                RectangleFile.RectanglesToFile(file.getPath(), filler.sizeX, filler.sizeY, newRectangles);

                JOptionPane.showMessageDialog(null, "Saved!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public void buttonIncreaseSizePressed() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableInput.getModel();
            model.addRow((Object[]) null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public void buttonDecreaseSizePressed() {
        try {
            DefaultTableModel model = (DefaultTableModel) tableInput.getModel();

            if (model.getRowCount() > 2) {
                model.setRowCount(model.getRowCount() - 1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    public String getTableData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowNum = model.getRowCount();

        StringBuilder builder = new StringBuilder();

        builder.append(String.valueOf(model.getValueAt(0, 0)) + " " + String.valueOf(model.getValueAt(0, 1)) + "\n");

        for (int i = 1; i < rowNum; i++) {
            for (int j = 0; j < 4; j++) {
                builder.append(String.valueOf(model.getValueAt(i, j)) + " ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private void updateInputVisualization() {
        try {
            RectangleFiller filler = RectangleFile.rectangleFillerFromString(getTableData(tableInput));
            List<Rectangle> rectangles = filler.rectangles;

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnCount(filler.sizeX);
            model.setRowCount(filler.sizeY);

            for (Rectangle rect : rectangles) {
                for (int y = rect.pointTopLeft.y; y > rect.pointBottomRight.y; y--) {
                    for (int x = rect.pointTopLeft.x; x < rect.pointBottomRight.x; x++) {
                        model.setValueAt("#", filler.sizeY - y, x);
                    }
                }
            }

            tableOutput.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
}
