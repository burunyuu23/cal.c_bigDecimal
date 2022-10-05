package ru.vsu.edu.shlyikov_d_g;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.edu.shlyikov_d_g.util.SwingUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;


public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTextField textInput1;
    private JTextField textInput2;
    private JButton buttonDiv;
    private JButton buttonMultiply;
    private JButton buttonMod;
    private JButton buttonPlus;
    private JButton buttonMinus;
    private JLabel textEqual;
    private JTextArea textAnswer;
    private JLabel textSign;
    private JButton testButton;


    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    private boolean test = false;


    public FrameMain() {
        $$$setupUI$$$();
        this.setTitle("task1_1");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        this.pack();


        buttonPlus.addActionListener(e -> {
            textSign.setText("+");
            textInput1.setText(new BigDecimal(textInput1.getText()).toString());
            textInput2.setText(new BigDecimal(textInput2.getText()).toString());
            String ans = new BigDecimal(textInput1.getText()).plus(textInput2.getText()).toString();;
            textAnswer.setText(ans);
            textInput1.setText(ans);
        });
        buttonMinus.addActionListener(e -> {
            textSign.setText("-");
            textInput1.setText(new BigDecimal(textInput1.getText()).toString());
            textInput2.setText(new BigDecimal(textInput2.getText()).toString());
            String ans = new BigDecimal(textInput1.getText()).minus(textInput2.getText()).toString();;
            textAnswer.setText(ans);
            textInput1.setText(ans);
        });
        buttonMultiply.addActionListener(e -> {
            textSign.setText("*");
            textInput1.setText(new BigDecimal(textInput1.getText()).toString());
            textInput2.setText(new BigDecimal(textInput2.getText()).toString());
            String ans = new BigDecimal(textInput1.getText()).multiply(textInput2.getText()).toString();;
            textAnswer.setText(ans);
            textInput1.setText(ans);
        });
        buttonDiv.addActionListener(e -> {
            textSign.setText("/");
            textInput1.setText(new BigDecimal(textInput1.getText()).toString());
            textInput2.setText(new BigDecimal(textInput2.getText()).toString());
            String ans = new BigDecimal(textInput1.getText()).div(textInput2.getText()).toString();;
            textAnswer.setText(ans);
            textInput1.setText(ans);
        });
        buttonMod.addActionListener(e -> {
            textSign.setText("%");
            textInput1.setText(new BigDecimal(textInput1.getText()).toString());
            textInput2.setText(new BigDecimal(textInput2.getText()).toString());
            String ans = new BigDecimal(textInput1.getText()).mod(textInput2.getText()).toString();;
            textAnswer.setText(ans);
            textInput1.setText(ans);
        });

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(4, 3, new Insets(10, 10, 10, 10), 10, 10));
        panelMain.setBackground(new Color(-4137515));
        panelMain.setForeground(new Color(-4477440));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(1, 2, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Output:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(1, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(null, "Input:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelMain.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        testButton = new JButton();
        testButton.setText("Activate/Deactivate tests");
        panelMain.add(testButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
