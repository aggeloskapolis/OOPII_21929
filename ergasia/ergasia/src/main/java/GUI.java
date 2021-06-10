import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

public class GUI  extends JFrame {



    private JButton bbb;
    private JPanel panel1;
    private JButton button2;
    private JTextField textField1;
    private JLabel citylabel;
    private  JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JTextField textField2;
    private JLabel countrylabel;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox8;
    private JComboBox comboBox9;
    private JComboBox comboBox10;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox11;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    public HashMap<String, City> Cities = new HashMap<String, City>();
    final int[] age = new int[1];
    String[] cityto = new String[2];
    String[] countryto = new String[2];
    String name = null;
    String city = null;
    String country = null;
    Vector<Integer> travellersTermsVector = new Vector<>();

    public GUI(String title) throws IOException {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();

        Traveller trv=new Traveller() {
            @Override
            public double calculateSimilarity(City cityObject) {
                return 0;
            }
        };

        bbb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(travellersTermsVector.get(0));
                    JOptionPane.showMessageDialog(null,"Your recommendet country is: "+trv.mainprc(name,age,cityto,countryto,city,country,travellersTermsVector));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                };
                System.out.println(city);
            }
        }
        );

        comboBox1.addItem("0");
        comboBox1.addItem("1");
        comboBox1.addItem("2");
        comboBox1.addItem("3");
        comboBox1.addItem("4");
        comboBox1.addItem("5");
        comboBox1.addItem("6");
        comboBox1.addItem("7");
        comboBox1.addItem("8");
        comboBox1.addItem("9");
        comboBox1.addItem("10");

        comboBox2.addItem("0");
        comboBox2.addItem("1");
        comboBox2.addItem("2");
        comboBox2.addItem("3");
        comboBox2.addItem("4");
        comboBox2.addItem("5");
        comboBox2.addItem("6");
        comboBox2.addItem("7");
        comboBox2.addItem("8");
        comboBox2.addItem("9");
        comboBox2.addItem("10");

        comboBox3.addItem("0");
        comboBox3.addItem("1");
        comboBox3.addItem("2");
        comboBox3.addItem("3");
        comboBox3.addItem("4");
        comboBox3.addItem("5");
        comboBox3.addItem("6");
        comboBox3.addItem("7");
        comboBox3.addItem("8");
        comboBox3.addItem("9");
        comboBox3.addItem("10");

        comboBox4.addItem("0");
        comboBox4.addItem("1");
        comboBox4.addItem("2");
        comboBox4.addItem("3");
        comboBox4.addItem("4");
        comboBox4.addItem("5");
        comboBox4.addItem("6");
        comboBox4.addItem("7");
        comboBox4.addItem("8");
        comboBox4.addItem("9");
        comboBox4.addItem("10");

        comboBox5.addItem("0");
        comboBox5.addItem("1");
        comboBox5.addItem("2");
        comboBox5.addItem("3");
        comboBox5.addItem("4");
        comboBox5.addItem("5");
        comboBox5.addItem("6");
        comboBox5.addItem("7");
        comboBox5.addItem("8");
        comboBox5.addItem("9");
        comboBox5.addItem("10");

        comboBox6.addItem("0");
        comboBox6.addItem("1");
        comboBox6.addItem("2");
        comboBox6.addItem("3");
        comboBox6.addItem("4");
        comboBox6.addItem("5");
        comboBox6.addItem("6");
        comboBox6.addItem("7");
        comboBox6.addItem("8");
        comboBox6.addItem("9");
        comboBox6.addItem("10");

        comboBox7.addItem("0");
        comboBox7.addItem("1");
        comboBox7.addItem("2");
        comboBox7.addItem("3");
        comboBox7.addItem("4");
        comboBox7.addItem("5");
        comboBox7.addItem("6");
        comboBox7.addItem("7");
        comboBox7.addItem("8");
        comboBox7.addItem("9");
        comboBox7.addItem("10");

        comboBox8.addItem("0");
        comboBox8.addItem("1");
        comboBox8.addItem("2");
        comboBox8.addItem("3");
        comboBox8.addItem("4");
        comboBox8.addItem("5");
        comboBox8.addItem("6");
        comboBox8.addItem("7");
        comboBox8.addItem("8");
        comboBox8.addItem("9");
        comboBox8.addItem("10");

        comboBox9.addItem("0");
        comboBox9.addItem("1");
        comboBox9.addItem("2");
        comboBox9.addItem("3");
        comboBox9.addItem("4");
        comboBox9.addItem("5");
        comboBox9.addItem("6");
        comboBox9.addItem("7");
        comboBox9.addItem("8");
        comboBox9.addItem("9");
        comboBox9.addItem("10");

        comboBox10.addItem("0");
        comboBox10.addItem("1");
        comboBox10.addItem("2");
        comboBox10.addItem("3");
        comboBox10.addItem("4");
        comboBox10.addItem("5");
        comboBox10.addItem("6");
        comboBox10.addItem("7");
        comboBox10.addItem("8");
        comboBox10.addItem("9");
        comboBox10.addItem("10");

        comboBox11.addItem(26);
        comboBox11.addItem(61);
        comboBox11.addItem(116);

        textField7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name=textField7.getText();
            }
        });

        for (int i=0;i<10;i++){

            travellersTermsVector.add(0);

        }

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(textField1.getText());
                city =textField1.getText();
            }
        });

        textField2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(textField2.getText());
                country =textField1.getText();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(comboBox1.getSelectedIndex());
                travellersTermsVector.set(0,comboBox1.getSelectedIndex());
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(1,comboBox2.getSelectedIndex());
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(2,comboBox3.getSelectedIndex());
            }
        });
        comboBox4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(3,comboBox4.getSelectedIndex());
            }
        });
        comboBox5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(4,comboBox5.getSelectedIndex());
            }
        });
        comboBox6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(5,comboBox6.getSelectedIndex());
            }
        });
        comboBox7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(6,comboBox7.getSelectedIndex());
            }
        });
        comboBox8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(7,comboBox7.getSelectedIndex());
            }
        });
        comboBox9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(8,comboBox9.getSelectedIndex());
            }
        });
        comboBox10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellersTermsVector.set(9,comboBox10.getSelectedIndex());
            }
        });
        textField3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cityto[0]=textField3.getText();
            }
        });
        textField4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cityto[1]=textField4.getText();
            }
        });
        comboBox11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                age[0] =comboBox11.getSelectedIndex();
            }
        });
        textField5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countryto[0]=textField5.getText();
            }
        });
        textField6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countryto[1]=textField6.getText();
            }
        });

    }

    public GUI() {

    }


    public void mainprc() throws IOException, SQLException {

        JFrame frame=new GUI("GUI");
        
        frame.setVisible(true);

        Traveller trv=new Traveller() {
            @Override
            public double calculateSimilarity(City cityObject) {
                return 0;
            }
        };

        if (cityto[1]!=null) {
            trv.mainprc(name,age, cityto, countryto, city, country, travellersTermsVector);
        }
    }
}


