
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Transfer extends Transaction {

    BankDatabase bankDatabase = getBankDatabase();
    Screen screen = getScreen();

    private int targetAccountNumber;
    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad

    public Transfer(int userAccountNumber, Screen atmScreen, //constructor for initializing objects
            BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);  //call superclass's constructor for initializing three of the variables
        keypad = atmKeypad;  //additional constructor implemention to initialize extra object variable
    } // end Transfer constructor

    private class AmountFrame extends Universial_Frame {

        Universial_Label transferamount = new Universial_Label("Transfer amount: ");
        Universial_Textfield inputbox1 = new Universial_Textfield();
        Universial_Label doubleinput = new Universial_Label("Transfer amount: ");
        Universial_Textfield inputbox2 = new Universial_Textfield();
        Universial_Button submitButton = new Universial_Button("Submit");
        Universial_Button cancelButton = new Universial_Button("Cancel");

        private AmountFrame() {

            inputbox1.setEditable(true);
            JPanel IPanel = new JPanel();
            JPanel BPanel = new JPanel();

            CPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 0, 80));
            CPanel.setBackground(Color.GRAY);
            CPanel.setPreferredSize(new Dimension(800, 400));

            IPanel.setLayout(new GridLayout(7, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(400, 300));
            IPanel.setBackground(Color.WHITE);

            BPanel.setLayout(new GridLayout(1, 2));

            transferamount.setPreferredSize(new Dimension(100, 20));
            inputbox1.setPreferredSize(new Dimension(100, 20));

            doubleinput.setVisible(false);
            inputbox2.setVisible(false);
            //---------------------------
            BPanel.add(cancelButton);
            BPanel.add(submitButton);
            //---------------------------
            IPanel.add(new Universial_Label("Transfering to: " + targetAccountNumber));
            IPanel.add(transferamount);
            IPanel.add(inputbox1);
            IPanel.add(doubleinput);
            IPanel.add(inputbox2);
            IPanel.add(new Universial_Label("Avaliabe Balance: " + screen.dollarAmountToString(bankDatabase.getAvailableBalance(getAccountNumber()))));
            IPanel.add(BPanel);
            //---------------------------
            CPanel.add(IPanel);
            //---------------------------

            add(CPanel, BorderLayout.CENTER);

            inputbox1.requestFocusInWindow();

            eventhandler handler = new eventhandler();
            inputbox1.addActionListener(handler);
            inputbox2.addActionListener(handler);
            submitButton.addActionListener(handler);
            cancelButton.addActionListener(handler);

            keylisten keyhandler = new keylisten();
            submitButton.addKeyListener(keyhandler);
            cancelButton.addKeyListener(keyhandler);
        }

        private class keylisten implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == submitButton) {
                        dios();
                    }
                    if (e.getSource() == cancelButton) {
                        cancel();
                    }
                }
            }
        }

        private class eventhandler implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == inputbox1) {
                    inputbox1.setEditable(false);
                    submitButton.requestFocusInWindow();
                }
                if (event.getSource() == inputbox2) {
                    inputbox2.setEditable(false);
                    submitButton.requestFocusInWindow();
                }
                if (event.getSource() == submitButton) {
                    dios();
                }
                if (event.getSource() == cancelButton) {
                    cancel();
                }

            }
        }

        private void dios() {
            if (inputbox2.isVisible() && doubleinput.isVisible()) {
                if (checkEmptyField(inputbox2)) {
                    inputbox2.setEditable(true);
                    inputbox2.requestFocusInWindow();
                    return;
                }
                submit();
            } else {
                if (checkEmptyField(inputbox1)) {
                    reset();
                    return;
                }
                if (validateAmount(inputbox1.getText()) == 0) {
                    inputbox1.setEditable(false);
                    doubleinput.setVisible(true);
                    inputbox2.setEditable(true);
                    inputbox2.setVisible(true);
                    inputbox2.requestFocusInWindow();
                } else {
                    reset();
                }
            }
        }

        private void submit() {
            if (!doubleCheckAmount(inputbox1, inputbox2)) {
                screen.showMessage1("Failed double check.", "Unexpected Input");
                reset();
                return;
            }
            if (validateAmount(inputbox1.getText()) == 0) {
                amount = keypad.StringtoDouble(inputbox2.getText());
                System.out.println("transfer amount = " + amount);
                callCframe();
                dispose();
            } else {
                reset();
            }

        }

        private void cancel() {
            toMainMenu = true;
            dispose();
        }

        private void reset() {
            inputbox1.setText("");
            inputbox1.setEditable(true);
            inputbox2.setText("");
            inputbox2.setEditable(true);
            doubleinput.setVisible(false);
            inputbox2.setVisible(false);
            inputbox1.requestFocusInWindow();
        }
    }

    private class TransferFrame extends Universial_Frame {

        Universial_Label accountnumber = new Universial_Label("Target Account Number: ");
        Universial_Textfield inputbox1 = new Universial_Textfield();

        Universial_Button submitbutton = new Universial_Button("Transfer!");
        Universial_Button cancelbutton = new Universial_Button("Back to Main Menu!");

        private TransferFrame() {
            inputbox1.setEditable(true);
            JPanel IPanel = new JPanel();
            JPanel BPanel = new JPanel();

            CPanel.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));
            CPanel.setBackground(Color.GRAY);

            IPanel.setLayout(new GridLayout(6, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(500, 300));
            IPanel.setBackground(Color.WHITE);

            IPanel.setLayout(new GridLayout(6, 1));

            BPanel.setLayout(new GridLayout(1, 2));

            accountnumber.setPreferredSize(new Dimension(100, 20));
            inputbox1.setPreferredSize(new Dimension(100, 20));

            //----------------------
            IPanel.add(accountnumber);
            IPanel.add(inputbox1);
            BPanel.add(cancelbutton);
            BPanel.add(submitbutton);
            IPanel.add(new JLabel());
            IPanel.add(new JLabel());
            IPanel.add(new JLabel());
            IPanel.add(BPanel);
            //----------------------
            CPanel.add(IPanel);
            //-----------------------------------
            add(CPanel, BorderLayout.CENTER);

            eventhandler handler = new eventhandler();
            inputbox1.addActionListener(handler);
            submitbutton.addActionListener(handler);
            cancelbutton.addActionListener(handler);

            keylisten keyhandler = new keylisten();
            submitbutton.addKeyListener(keyhandler);
            cancelbutton.addKeyListener(keyhandler);
        }

        private class keylisten implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == submitbutton) {
                        submit();
                    }
                    if (e.getSource() == cancelbutton) {
                        cancel();
                    }
                }
            }
        }

        private class eventhandler implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == inputbox1) {
                    inputbox1.setEditable(false);
                    submitbutton.requestFocusInWindow();
                }
                if (event.getSource() == submitbutton) {
                    submit();
                }
                if (event.getSource() == cancelbutton) {
                    cancel();
                }

            }
        }

        private void submit() {
            if (checkEmptyField(inputbox1)) {
                System.out.println(inputbox1.getText());
                reset();
                return;
            }
            if (!validateTarget(inputbox1)) {
                targetAccountNumber = keypad.StringtoInt(inputbox1.getText());
                System.out.println("target account = " + targetAccountNumber);
                dispose();
                callAframe();
            } else {
                reset();
            }
        }

        private void cancel() {
            toMainMenu = true;
            dispose();
        }

        private void reset() {
            inputbox1.setText("");
            inputbox1.setEditable(true);
            inputbox1.requestFocusInWindow();
        }
    }

    private class Confirmframe extends Universial_Frame {

        Universial_Label targetaccountBox1 = new Universial_Label(String.valueOf(targetAccountNumber));
        Universial_Label amountBox1 = new Universial_Label(screen.dollarAmountToString(amount));

        Universial_Button submitbutton = new Universial_Button("Submit");
        Universial_Button cancelbutton = new Universial_Button("Cancel");

        private Confirmframe() {
            JPanel IPanel = new JPanel();
            JPanel BPanel = new JPanel();

            CPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
            CPanel.setBackground(Color.GRAY);

            IPanel.setLayout(new GridLayout(6, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(300, 200));
            IPanel.setBackground(Color.WHITE);

            IPanel.setLayout(new GridLayout(6, 1));
            BPanel.setLayout(new GridLayout(1, 2));

            //-------------------------------
            IPanel.add(new Universial_Label("Transfer target:"));
            IPanel.add(targetaccountBox1);
            IPanel.add(new Universial_Label("Transfer amount:"));
            IPanel.add(amountBox1);
            IPanel.add(new JLabel());
            BPanel.add(submitbutton);
            BPanel.add(cancelbutton);
            //-------------------------------
            IPanel.add(BPanel);
            eventhandler handler = new eventhandler();
            submitbutton.addActionListener(handler);
            cancelbutton.addActionListener(handler);

            keylisten keyhandler = new keylisten();
            submitbutton.addKeyListener(keyhandler);
            cancelbutton.addKeyListener(keyhandler);

            CPanel.add(IPanel);
            add(CPanel, BorderLayout.CENTER);
        }

        private class eventhandler implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == submitbutton) {
                    submit();
                }
                if (event.getSource() == cancelbutton) {
                    cancel();
                }
            }
        }

        private class keylisten implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getSource() == submitbutton) {
                        submit();
                    }
                    if (e.getSource() == cancelbutton) {
                        cancel();
                    }
                }
            }
        }

        private void submit() {
            dispose();
            screen.waitUntilNotDisplaying(new MessageFrame("Transfer sucess.", "Transfer sucess.", 3));
            System.out.println(getAccountNumber() + " -> " + targetAccountNumber + ": " + amount);

            new Timer(3000, e -> {
                logout = true;
            }).start();
            SwingUtilities.invokeLater(() -> {

                bankDatabase.transfer(getAccountNumber(), targetAccountNumber, amount);
            });
        }

        private void cancel() {
            dispose();
            callTframe();
        }
    }

    private void callAframe() {
        SwingUtilities.invokeLater(() -> {
            AmountFrame Aframe = new AmountFrame();
            Aframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Aframe.setSize(800, 600);
            Aframe.setLocationRelativeTo(null);
            Aframe.setVisible(true);
        });
    }

    private void callTframe() {
        SwingUtilities.invokeLater(() -> {
            TransferFrame frame = new TransferFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void callCframe() {
        Confirmframe Cframe = new Confirmframe();
        Cframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Cframe.setSize(800, 600); // set frame size
        Cframe.setLocationRelativeTo(null);
        Cframe.setVisible(true); // display frame
    }

    private boolean doubleCheckAmount(JTextField field1, JTextField field2) {
        return (field1.getText()).equals(field2.getText());
    }

    private int validateAmount(String inputString1) {
        int errorflag = 0;
        String errormsg = "";
        errormsg = errormsg + "<html>";
        amount = keypad.StringtoDouble(inputString1);

        if (bankDatabase.getAvailableBalance(getAccountNumber()) < amount) {
            screen.displayMessageLine("\nInsufficient funds in your account.");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Insufficient funds in your account.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag = 2;
        }

        if (amount <= 0) {
            screen.displayMessageLine("\nTransfer can not be smaller or equals than zero");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Transfer can not be smaller or equals than zero.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag = 3;
        }
        errormsg = errormsg + "</html>";
        if (errorflag != 0) {
            screen.showMessage1(errormsg, "Unexpected Input");
        }
        return errorflag;
    }

    private boolean validateTarget(JTextField inputbox1) {
        String inputString1 = inputbox1.getText();
        targetAccountNumber = Integer.parseInt(inputString1);

        boolean errorflag = false;
        String errormsg = "<html>";
        if (targetAccountNumber == getAccountNumber()) {
            errormsg = errormsg + "You cannot transfer to the same account.<br><br>" + "";
            errorflag = true;
        }
        if (!bankDatabase.checkAccountExist(targetAccountNumber)) {
            screen.displayMessageLine("Target account does not exist.");
            screen.displayMessageLine("Transaction cancelled.");

            errormsg = errormsg + "Target account does not exist.<br><br>" + "";
            //screen.showMessage1("Target account does not exist.");
            errorflag = true;
        }

        if (inputbox1.getText().isEmpty()) {
            screen.displayMessageLine("Target account does not exist.");
            screen.displayMessageLine("Transaction cancelled.");

            errormsg = errormsg + "Target account is essential<br><br>" + "";
            //screen.showMessage1("Target account does not exist.");
            errorflag = true;
        }

        errormsg = errormsg + "</html>";
        System.out.println("haha: \n" + errormsg);
        if (errorflag) {
            screen.showMessage1(errormsg, "Unexpected Input");
        }
        return errorflag;
    }

    public void execute() {
        toMainMenu = false;
        callTframe();
    }

    private boolean checkEmptyField(Universial_Textfield inputbo1) {
        if (inputbo1.getText().length() == 0) {
            screen.showMessage1("<html>Missing essential information.<br><br></html>", "Unexpected Input");
            return true;
        }
        return false;
    }
}
