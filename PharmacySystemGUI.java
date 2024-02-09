
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PharmacySystemGUI {
    private JFrame frame;
    JFrame newFrame;
    private JLabel capacityLabel;
    private JTextField capacityTextField;
    private JButton initializeButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton placeOrderButton;
    private JButton getTotalSalesButton;
    private JButton exitButton;

    private JLabel sales_label;



    private JLabel drug_name_label;
    private JTextField drug_name_txt;
    private JLabel drug_id_label;
    private JTextField drug_id_txt;
    private JLabel drug_category_label;
    private JTextField drug_category_txt;
    private JLabel drug_quantity_label;
    private JTextField drug_quantity_txt;
    private JLabel drug_price_label;
    private JTextField drug_price_txt;
    private JButton save;
    private JButton back;

    private JLabel remove_drug_id_label;
    private JTextField remove_drug_id_txt;


    private Map<String, Drug> drugMap;
    private int capacity;
    private double totalSales;

        ImageIcon icon = new ImageIcon("add5.png");
        ImageIcon icon2 = new ImageIcon("remove1.png");
        ImageIcon icon3 = new ImageIcon("place2.png");
        ImageIcon icon5 = new ImageIcon("money2.png");
        ImageIcon icon7 = new ImageIcon("exit.png");


    public PharmacySystemGUI() {

        frame = new JFrame("Pharmacy System");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setPreferredSize(new Dimension(819, 600));
        frame.setLayout(null);
        


        //for add_durg 
        drug_name_label = new JLabel("Name");
        drug_name_txt = new JTextField(40);

        drug_id_label = new JLabel("id");
        drug_id_txt = new JTextField(40);
        
        drug_category_label = new JLabel("category (1 - Cosmetics, 2 - Prescription Drugs, 3 - Other)");
        drug_category_txt = new JTextField(40);
        
        drug_quantity_label = new JLabel("quantity");
        drug_quantity_txt = new JTextField(40);        
        
        drug_price_label = new JLabel("price");
        drug_price_txt = new JTextField(40);
        
        save = new JButton("Save");
        back = new JButton("back");


        remove_drug_id_label = new JLabel("ID of the drug you want to remove");
        remove_drug_id_txt = new JTextField(40);


        //f 
        
        sales_label = new JLabel("Total Sales for the day: $0");
        sales_label.setBounds(600, 30, 300, 30);
        frame.add(sales_label);

        capacityLabel = new JLabel("Pharmacy Capacity: 0");
        capacityLabel.setBounds(600, 0, 200, 30);
        frame.add(capacityLabel);


        initializeButton = new JButton("Click here to Initialize Pharmacy Capacity");
        initializeButton.setOpaque(false);
        initializeButton.setBorderPainted(false);
        initializeButton.setBounds(0, 0, 265, 30);
        frame.add(initializeButton);

        addButton = new JButton();
        addButton.setOpaque(false);
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        
        addButton.setIcon(icon);
        addButton.setBounds(2, 210, 200, 200);
        frame.add(addButton);


        removeButton = new JButton();
        removeButton.setOpaque(false);
        removeButton.setContentAreaFilled(false);
        removeButton.setBorderPainted(false);
        removeButton.setIcon(icon2);
        removeButton.setBounds(200, 200, 200, 200);
        frame.add(removeButton);

        placeOrderButton = new JButton();
        placeOrderButton.setOpaque(false);
        placeOrderButton.setContentAreaFilled(false);
        placeOrderButton.setBorderPainted(false);
        placeOrderButton.setIcon(icon3);
        placeOrderButton.setBounds(400, 200, 200, 200);
        frame.add(placeOrderButton);
        

        getTotalSalesButton = new JButton();
        getTotalSalesButton.setOpaque(false);
        getTotalSalesButton.setContentAreaFilled(false);
        getTotalSalesButton.setBorderPainted(false);
        getTotalSalesButton.setIcon(icon5);
        getTotalSalesButton.setBounds(600, 200, 200, 200);
        frame.add(getTotalSalesButton);

        exitButton = new JButton("Exit");
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setIcon(icon7);
        exitButton.setBounds(362, 500, 95, 50);
        frame.add(exitButton);


        frame.setVisible(true);
        


        initializeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initializePharmacy();
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (capacity!=0){
                openNewWindow();
                }
                else {
                    String errorMessage4="you didn't initialize the pharmacy capacity";
                    JOptionPane.showMessageDialog(frame, errorMessage4, "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                
                
            }
        });
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                frame.setVisible(true);
                newFrame.setVisible(false);
                


            }});




 
save.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        if (drugMap.size() >= capacity) {
            String errorMessage2 = "Pharmacy is full. Cannot add more drugs!";
            JOptionPane.showMessageDialog(newFrame, errorMessage2, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            String name = drug_name_txt.getText();
            String id = drug_id_txt.getText();
            String priceStr = drug_price_txt.getText();
            String categoryStr = drug_category_txt.getText();
            String quantityStr = drug_quantity_txt.getText();

            try {
                if (drugMap.containsKey(id)) {
                    
                    String errorMessage3 = "Error: Drug with ID " + id + " already exists.";
                    JOptionPane.showMessageDialog(newFrame, errorMessage3, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    double price = Double.parseDouble(priceStr);
                    int quantity = Integer.parseInt(quantityStr);

                    int category;
                    switch (categoryStr.toLowerCase()) {
                        case "1":
                        case "cosmetics":
                            category = 1; // cosmetics
                            break;
                        case "2":
                        case "prescription":
                            category = 2; // prescription drugs
                            break;
                        case "3":
                        case "other":
                            category = 3; // other
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid category value. Please enter 1, 2, or 3.");
                    }

                    Drug drug = new Drug(name, id, price, category, quantity);
                    drugMap.put(id, drug);
                    
                    JOptionPane.showMessageDialog(newFrame, "Drug added");
                }
            } catch (NumberFormatException e1) {
                String errorMessage = "Invalid input. Please enter valid numeric values.";
                JOptionPane.showMessageDialog(newFrame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e2) {
                String errorMessage = e2.getMessage();
                JOptionPane.showMessageDialog(newFrame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

                drug_name_txt.setText("");
                drug_id_txt.setText("");
                drug_category_txt.setText("");
                drug_quantity_txt.setText("");
                drug_price_txt.setText("");
    }
});

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (capacity!=0){
                removeDrug();
                }
                else {
                    String errorMessage7="you didn't initialize the pharmacy capacity";
                    JOptionPane.showMessageDialog(frame, errorMessage7, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        placeOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });

        getTotalSalesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getTotalSales();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

        private void openNewWindow() {

        
        JFrame newFrame = new JFrame("ADD DRUG");

        JPanel mainPanel_2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbb = new GridBagConstraints();
        
        gbb.insets = new Insets(5, 5, 5, 5);


        gbb.gridx = 0;
        gbb.gridy = 0;
        mainPanel_2.add(drug_name_label, gbb);

        gbb.gridy = 1;
        mainPanel_2.add(drug_name_txt, gbb);

        gbb.gridy = 2;
        mainPanel_2.add(drug_id_label, gbb);

        gbb.gridy = 3;
        mainPanel_2.add(drug_id_txt, gbb);

        gbb.gridy = 4;
        mainPanel_2.add(drug_category_label, gbb);

        gbb.gridy = 5;
        mainPanel_2.add(drug_category_txt, gbb);

        gbb.gridy = 6;
        mainPanel_2.add(drug_quantity_label, gbb);

        gbb.gridy = 7;
        mainPanel_2.add(drug_quantity_txt, gbb);

        gbb.gridy = 8;
        mainPanel_2.add(drug_price_label, gbb);

        gbb.gridy = 9;
        mainPanel_2.add(drug_price_txt, gbb);

        gbb.gridy = 10;
        
        mainPanel_2.add(save, gbb);

        gbb.gridx = 1;
        mainPanel_2.add(back, gbb);


        newFrame.add(mainPanel_2);
        newFrame.pack();
        newFrame.setVisible(true);
        frame.setVisible(false);


    }

    private void openRemoveWindow() {
    JFrame removeFrame = new JFrame("REMOVE DRUG");

    JPanel mainPanel_3 = new JPanel(new GridBagLayout());
    GridBagConstraints gcc = new GridBagConstraints();
    gcc.insets = new Insets(5, 5, 5, 5);

        gcc.gridx = 0;
        gcc.gridy = 0;
        mainPanel_3.add(remove_drug_id_label, gcc);

        gcc.gridy = 1;
        mainPanel_3.add(remove_drug_id_txt, gcc);
 
        removeFrame.add(mainPanel_3);
        removeFrame.pack();
        removeFrame.setVisible(true);
}


    private void initializePharmacy() {

       String capacitystr = JOptionPane.showInputDialog(frame, "Enter the capacity:");
        capacityLabel.setText("Pharmacy Capacity: "+capacitystr);
        try {
            capacity = Integer.parseInt(capacitystr);
            drugMap = new HashMap<>();
            totalSales = 0;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid capacity. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void addDrug() {

        String name = JOptionPane.showInputDialog(frame, "Enter drug name:");
        String id = JOptionPane.showInputDialog(frame, "Enter drug ID:");
        String priceStr = JOptionPane.showInputDialog(frame, "Enter drug price:");
        String categoryStr = JOptionPane.showInputDialog(frame, "Enter drug category (1 - Cosmetics, 2 - Prescription Drugs, 3 - Other):");
        String quantityStr = JOptionPane.showInputDialog(frame, "Enter drug quantity:");

        try {
            double price = Double.parseDouble(priceStr);
            int category = Integer.parseInt(categoryStr);
            int quantity = Integer.parseInt(quantityStr);

            Drug drug = new Drug(name, id, price, category, quantity);
            drugMap.put(id, drug);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void removeDrug() {
        String id = JOptionPane.showInputDialog(frame, "Enter the ID of the drug to remove:");

        if (drugMap.containsKey(id)) {
            Drug removedDrug = drugMap.remove(id);
            JOptionPane.showMessageDialog(frame,"Drug removed: " + removedDrug.toString());
            
        } else {
            String errorMessage5 = "Drug not found with ID: " + id + "\n";
            JOptionPane.showMessageDialog(frame, errorMessage5, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




private void placeOrder() {
    JFrame orderFrame = new JFrame("Place Order");
    orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    orderFrame.setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    DefaultComboBoxModel<String> idComboBoxModel = new DefaultComboBoxModel<>();
    for (String id : drugMap.keySet()) {
        idComboBoxModel.addElement(id);
    }
    JComboBox<String> idComboBox = new JComboBox<>(idComboBoxModel);

    JTextField quantityField = new JTextField(10);

    JButton addButton = new JButton("Add");
    JButton backButton = new JButton("Back");

    addButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedId = (String) idComboBox.getSelectedItem();
            String quantityText = quantityField.getText();

            if (selectedId != null && drugMap.containsKey(selectedId)) {
                Drug drug = drugMap.get(selectedId);

                double unitPrice = drug.getPrice();
                if (drug.getCategory() == 1) {
                    unitPrice *= 1.2;
                }

                    if (quantityText.isEmpty()) {
                        JOptionPane.showMessageDialog(orderFrame, "Please enter a quantity.");
                        return;
                    }

                    int quantity;
                    try {
                        quantity = Integer.parseInt(quantityText);
                        if (quantity <= 0) {
                            JOptionPane.showMessageDialog(frame, "Invalid quantity. Quantity must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException e11) {
                        JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid integer value.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }   
                        
                if (drug.getQuantity() >= quantity) {
                    double totalPrice = unitPrice * quantity;
                    totalSales += totalPrice;
                    JOptionPane.showMessageDialog(orderFrame, "ADDED");


                    drug.setQuantity(drug.getQuantity() - quantity);
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient quantity available for Drug with ID: ", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    
                }
            } else {
                    JOptionPane.showMessageDialog(frame, "Invalid drug ID: ", "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    });

    backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            orderFrame.dispose();
        }
    });

    JPanel inputPanel = new JPanel(new FlowLayout());
    inputPanel.add(new JLabel("Select Drug ID: "));
    inputPanel.add(idComboBox);
    inputPanel.add(new JLabel("Quantity: "));
    inputPanel.add(quantityField);
    inputPanel.add(addButton);
    inputPanel.add(backButton);

    gbc.gridx = 0;
    gbc.gridy = 0;
    mainPanel.add(inputPanel, gbc);

    orderFrame.add(mainPanel);
    orderFrame.pack();
    orderFrame.setSize(500, 300);
    orderFrame.setVisible(true);
    frame.setVisible(false);
}

    private void getTotalSales() {

        sales_label.setText("Total Sales for the day: $" + totalSales );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PharmacySystemGUI();
            }
        });
    }
}

