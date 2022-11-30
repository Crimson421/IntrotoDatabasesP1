import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Main {
    private static void initGui() {
        JFrame frame = new JFrame("Frontend GUI");
        int width = 900;
        int height = 600;
        frame.pack();
        frame.setSize(width, height);

        createOptionMenu(frame);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
    }

    private static void createOptionMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        Border emptyBorder = BorderFactory.createEmptyBorder();

        JButton menuItem1 = new JButton(
                "<html>Add a new <br>" +
                        "entity type to the <br>" +
                        "database</html>");
        menuItem1.setBorder(emptyBorder);

        JButton menuItem2 = new JButton(
                "<html>Add a student <br>" +
                        "to specified <br>" +
                        "course or <br>section</html>");
        menuItem2.setBorder(emptyBorder);
        JButton menuItem3 = new JButton(
                "<html>Calculate a <br>" +
                        "student's GPA</html>");
        menuItem3.setBorder(emptyBorder);
        JButton menuItem4 = new JButton(
                "<html>Search <br>" +
                        "for courses <br>" +
                        "offered by a department</html>");
        menuItem4.setBorder(emptyBorder);
        JButton menuItem5 = new JButton(
                "<html>List all <br>" +
                        "course sections<br>" +
                        "previously taught<br>" +
                        "by an instructor</html>");
        menuItem5.setBorder(emptyBorder);
        JButton menuItem6 = new JButton(
                "<html>Add/update <br>" +
                        "a grade for a <br> " +
                        "student in a course</html>");
        menuItem6.setBorder(emptyBorder);

        menuItem1.addActionListener((ActionEvent e) -> {
            createInputPane(frame, "1");
        });
        menuItem2.addActionListener((ActionEvent e) -> {
            createInputPane(frame, "2");

        });
        menuItem3.addActionListener((ActionEvent e) -> {
            createInputPane(frame, "3");

        });
        menuItem4.addActionListener((ActionEvent e) -> {
            createInputPane(frame, "4");

        });
        menuItem5.addActionListener((ActionEvent e) -> {
            createInputPane(frame, "5");

        });
        menuItem6.addActionListener((ActionEvent e) -> {
            createInputPane(frame, "6");

        });

        menuBar.add(menuItem1);
        menuBar.add(menuItem2);
        menuBar.add(menuItem3);
        menuBar.add(menuItem4);
        menuBar.add(menuItem5);
        menuBar.add(menuItem6);

        frame.setJMenuBar(menuBar);
    }

    public static void createInputPane(JFrame frame, String option) {

        JPanel inputPane = new JPanel();

        switch (option) {
            case "1":
                JLabel label = new JLabel("Enter the name of the entity you want to add");
                JTextField textField = new JTextField(20);
                JButton button = new JButton("Submit");

                addTuple(inputPane, option);
                break;
            case "2":
                JLabel label2 = new JLabel("");
                JTextField textField2 = new JTextField(20);
                JButton button2 = new JButton("Submit");
                button2.addActionListener((ActionEvent e) -> {
                    String input = textField2.getText();
                    List<String> inputList = new ArrayList<String>();

                });

                break;
            case "3":
                JLabel label3 = new JLabel("");
                JTextField textField3 = new JTextField(20);
                JButton button3 = new JButton("Submit");

                break;
            case "4":
                JLabel label4 = new JLabel("");
                JTextField textField4 = new JTextField(20);
                JButton button4 = new JButton("Submit");

                break;
            case "5":
                JLabel label5 = new JLabel("");
                JTextField textField5 = new JTextField(20);
                JButton button5 = new JButton("Submit");

                break;
            case "6":
                JLabel label6 = new JLabel("");
                JTextField textField6 = new JTextField(20);
                JButton button6 = new JButton("Submit");

                break;
            default:
                break;
        }
        frame.add(inputPane);
        frame.revalidate();
        frame.repaint();
    }

    private static void addTuple(JPanel inputPane, String entityType) {
        System.out.print(entityType);
        List<String> attribs = new ArrayList<>();

        switch (entityType.toLowerCase()) {
            case "person":
                JLabel name = new JLabel("Name");
                JLabel birthDate = new JLabel("Birth date");

                JTextField textField = new JTextField(20);
                JButton button = new JButton("Submit");
                button.addActionListener((ActionEvent e) -> {
                    attribs.add(textField.getText());
                });
                break;
        }
    }

    public static void main(String[] args) {
        initGui();
    }
}
