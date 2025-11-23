// Withdrawal.java
// Represents a withdrawal ATM transaction

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Withdrawal extends Transaction {

    protected int amount; // amount to withdraw
    private Keypad keypad; // reference to keypad
    private CashDispenser cashDispenser; // reference to cash dispenser
    private BankDatabase bankDatabase;
    // constant corresponding to menu option to cancel
    private final static int CANCELED = 0;

    public int stateNum;

    // Withdrawal constructor
    public Withdrawal(int userAccountNumber, Screen atmScreen,
            BankDatabase atmBankDatabase, Keypad atmKeypad,
            CashDispenser atmCashDispenser) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad and cash dispenser
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    } // end Withdrawal constructor

    // perform transaction
    public void execute() {
        // get references to bank database and screen
        bankDatabase = getBankDatabase();
        if (cashDispenser.AnyBillsAvaliable()) {
            callWFrame();
        } else {
             stateNum = 5;
            logout = true;
        }

    }

    private void callWFrame() {
        WithdrawalFrame withdrawalFrame = new WithdrawalFrame();
        withdrawalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        withdrawalFrame.setSize(800, 600); // set frame size
        withdrawalFrame.setLocationRelativeTo(null);
        withdrawalFrame.setVisible(true); // display frame
    }


    public int getStateNum() {
        return stateNum;
    }

    private void performWithdrawal() {
        double availableBalance = bankDatabase.getAvailableBalance(getAccountNumber()); // amount available for withdrawal
        if (amount > availableBalance) {
            stateNum = 4;
            return;
        }
        if (!cashDispenser.canBillsHandleTheJob(amount)) {
            stateNum = 1;
            return;
        }
        // cashDispenser.canBillsHandleTheJob === cashDispenser.isSufficientCashAvailable. but incase of accident. we decided to keep it.
        if (!cashDispenser.isSufficientCashAvailable(amount)) {
            stateNum = 3;
            return;
        }

        bankDatabase.debit(getAccountNumber(), amount);
        cashDispenser.dispenseCash(amount); // dispense cash
        // instruct user to take cash
        stateNum = 2;
        logout = true;
    }

    // display a menu of withdrawal amounts and the option to cancel;
    // return the chosen amount or 0 if the user chooses to cancel
    private class WithdrawalFrame extends Universal_Frame {

        private Universal_Button confirmButton;
        private Universal_Button cancelButton;
        private Universal_Button resetButton;

        private WithdrawOButton withdrawJOption1;
        private WithdrawOButton withdrawJOption2;
        private WithdrawOButton withdrawJOption3;
        private WithdrawOButton withdrawJOption4;
        private WithdrawOButton withdrawJOption5;
        private WithdrawOButton withdrawJOption6;
        private Universal_Textfield customAmount;
        private Universal_Label showWithdrawAmount;

        private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Border FONTBORDER = BorderFactory.createEmptyBorder(5, 3, 5, 0);
        private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        private final int PANELHEIGHT = 80;

        private WithdrawalFrame() {

            JPanel northPanel = new JPanel();
            northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
            northPanel.setPreferredSize(new Dimension(800, 50));

            JLabel title = new JLabel("Withdrawal", SwingConstants.CENTER);
            title.setFont(FONTSTYLE);
            title.setBorder(HEADFOOTBORDER);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setPreferredSize(new Dimension(800, 25));

            JLabel availableNotesMsg = new JLabel(cashDispenser.showAvaliableBills(), SwingConstants.CENTER);
            availableNotesMsg.setFont(FONTSTYLE);
            availableNotesMsg.setBorder(HEADFOOTBORDER);
            availableNotesMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
            availableNotesMsg.setPreferredSize(new Dimension(800, 25));

            JPanel withdrawalPanel = new JPanel();
            withdrawalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            withdrawalPanel.setPreferredSize(new Dimension(720, 350));
            withdrawalPanel.setBackground(Color.WHITE);

            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
            centerPanel.setBackground(Color.GRAY);

            JPanel optionPanel = new JPanel();

            optionPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns, gaps
            optionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            optionPanel.setPreferredSize(new Dimension(710, 150));

            Universal_Label withdrawalMenu = new Universal_Label("Withdrawal Menu:");

            withdrawJOption1 = new WithdrawOButton("1 - $100", 100);
            withdrawJOption2 = new WithdrawOButton("2 - $200", 200);
            withdrawJOption3 = new WithdrawOButton("3 - $500", 500);
            withdrawJOption4 = new WithdrawOButton("4 - $800", 800);
            withdrawJOption5 = new WithdrawOButton("5 - $1000", 1000);
            withdrawJOption6 = new WithdrawOButton("6 - $2000", 2000);

            optionPanel.add(withdrawJOption1);
            optionPanel.add(withdrawJOption2);
            optionPanel.add(withdrawJOption3);
            optionPanel.add(withdrawJOption4);
            optionPanel.add(withdrawJOption5);
            optionPanel.add(withdrawJOption6);

            JPanel customPanel = new JPanel();
            customPanel.setLayout(new GridLayout(1, 2));
            customPanel.setBackground(Color.WHITE);
            customPanel.setPreferredSize(new Dimension(710, 40));

            Universal_Label withdrawJOption0 = new Universal_Label("or enter a custom amount (hkd):", NUMBERFONTSTYLE);
            withdrawJOption0.setBorder(FONTBORDER);

            //
            customAmount = new Universal_Textfield();
            customAmount.setFont(NUMBERFONTSTYLE);
            customAmount.requestFocusInWindow();

            customPanel.add(withdrawJOption0);
            customPanel.add(customAmount);

            Universal_Label withdrawAmount = new Universal_Label("withdraw amount (hkd):", NUMBERFONTSTYLE);
            // withdrawAmount.setBorder(FONTBORDER);
            // withdrawAmount.setPreferredSize(buttonSize);

            showWithdrawAmount = new Universal_Label("0");
            showWithdrawAmount.setFont(NUMBERFONTSTYLE);
            // showWithdrawAmount.setBorder(FONTBORDER);
            showWithdrawAmount.setPreferredSize(new Dimension(120, 35));
            showWithdrawAmount.setBackground(new Color(65, 125, 128));
            showWithdrawAmount.setBackground(Color.WHITE);

            resetButton = new Universal_Button("Reset Amount");
            resetButton.setPreferredSize(new Dimension(185, 35));
            JPanel showAmountPanel = new JPanel();
            showAmountPanel.setLayout(new GridLayout(1, 2));
            showAmountPanel.setBackground(Color.WHITE);
            // showAmountPanel.setPreferredSize(new Dimension(515,35));

            JPanel groupPanel = new JPanel();
            groupPanel.setLayout(new GridLayout(1, 2));

            showAmountPanel.add(withdrawAmount);
            // showAmountPanel.add(new JLabel());
            showAmountPanel.add(showWithdrawAmount);
            // showAmountPanel.add(resetButton);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 3));
            buttonPanel.setPreferredSize(new Dimension(700, 35));

            confirmButton = new Universal_Button("Confirm Withdrawal");
            confirmButton.setPreferredSize(new Dimension(250, 35));
            cancelButton = new Universal_Button("Back to Main Menu");
            cancelButton.setPreferredSize(new Dimension(250, 35));
            buttonPanel.add(cancelButton);
            buttonPanel.add(new JLabel());

            buttonPanel.add(confirmButton);

            optionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            customPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            showAmountPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel space = new JLabel();
            space.setPreferredSize(new Dimension(200, 35));

            northPanel.add(title);
            northPanel.add(availableNotesMsg);

            withdrawalPanel.add(withdrawalMenu);
            withdrawalPanel.add(optionPanel);
            withdrawalPanel.add(customPanel);
            withdrawalPanel.add(showAmountPanel);
            withdrawalPanel.add(resetButton);
            withdrawalPanel.add(cancelButton);
            withdrawalPanel.add(space);
            withdrawalPanel.add(confirmButton);

            centerPanel.add(withdrawalPanel);

            add(northPanel, BorderLayout.NORTH);
            add(centerPanel, BorderLayout.CENTER);

            // event handel...
            WithdrawalHandler handler = new WithdrawalHandler();
            cancelButton.addActionListener(handler);
            confirmButton.addActionListener(handler);
            resetButton.addActionListener(handler);
            customAmount.addActionListener(handler);

        }

        public void addNotify() {
            super.addNotify();
            customAmount.requestFocusInWindow();
        }

        private class WithdrawalHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == customAmount) {
                    if (customAmount.getText().contains(".")) {
                        JOptionPane.showMessageDialog(null, "Detected invalid character.",
                                "Invalid value", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if (keypad.StringtoDouble(customAmount.getText()) % 100 != 0) {
                        JOptionPane.showMessageDialog(null, "Make sure the custom amount is divisible by 100 and try again.",
                                "Invalid value", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    if (customAmount.getText().isEmpty()) {
                        showWithdrawAmount.setText("0");
                    } else {
                        showWithdrawAmount.setText(customAmount.getText());
                    }
                    customAmount.setText("");
                }

                if (event.getSource() == resetButton) {
                    showWithdrawAmount.setText("0");
                    customAmount.setText("");
                }

                if (event.getSource() == confirmButton) {
                    amount = Integer.parseInt(showWithdrawAmount.getText());
                    if(amount>=100){
                        dispose(); // close the GUI window
                        //performWithdrawal();
                        performWithdrawal();
                        logout = true;
                    }else{
                        showWithdrawAmount.setText("0");
                        customAmount.setText("");
                        JOptionPane.showMessageDialog(null, "The custom amount should be at least 100.\nOr you can press \"Back to Main Menu\" to cancel withdrawal.",
                                "Invalid value", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    
                }

                if (event.getSource() == cancelButton) {
                    amount = CANCELED;
                    dispose(); // close the GUI window
                    toMainMenu = true;
                }
            }
        }

        private class WithdrawOButton extends Universal_Button {

            int Denomination = 0;
            handler handler = new handler();

            public WithdrawOButton(String label, Color ShowColor, Color HoverColor, int Deno) {
                super(label, ShowColor, HoverColor);
                Denomination = Deno;
                addActionListener(handler);
            }

            public WithdrawOButton(String label, int Deno) {
                super(label);
                Denomination = Deno;
                addActionListener(handler);
            }

            public class handler implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {
                    amount = returnDeno();
                    System.out.println(amount);
                    showWithdrawAmount.setText(String.valueOf(amount));
                    customAmount.setText("");
                }
            }

            private int returnDeno() {
                return Denomination;
            }
        }
    }
} // end class Withdrawal

/**
 * ************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 * ***********************************************************************
 */
