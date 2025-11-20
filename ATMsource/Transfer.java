
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Transfer extends Transaction {

    BankDatabase bankDatabase = getBankDatabase();
    Screen screen = getScreen();

    private int targetAccountNumber;
    private double amount; // amount to transfer
    private Keypad keypad; // reference to keypad
    private final static int CANCELED = 0; // constant for cancel option

    public Transfer(int userAccountNumber, Screen atmScreen, //constructor for initializing objects
            BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);  //call superclass's constructor for initializing three of the variables
        keypad = atmKeypad;  //additional constructor implemention to initialize extra object variable
    } // end Transfer constructor

    public class AmountFrame extends JFrame {

        private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Border FONTBORDER = BorderFactory.createEmptyBorder(5, 3, 5, 0);
        private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        private final int PANELHEIGHT = 80;
        JLabel transferamount = new JLabel("Transfer amount: ");
        JTextField inputbox2 = new JTextField();
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        private AmountFrame() {
            inputbox2.setEditable(true);
            JPanel NPanel = new JPanel();
            JPanel SPanel = new JPanel();
            JPanel CPanel = new JPanel();
            JPanel IPanel = new JPanel();
            JPanel BPanel = new JPanel();

            NPanel.setPreferredSize(new Dimension(800, PANELHEIGHT));

            JLabel footer = new JLabel("For further assistance, please contact customer support.");
            footer.setFont(FONTSTYLE);
            footer.setBorder(HEADFOOTBORDER);
            footer.setPreferredSize(new Dimension(800, PANELHEIGHT));

            //SPanel.setLayout(new BoxLayout(NPanel, BoxLayout.Y_AXIS));
            SPanel.setPreferredSize(new Dimension(800, PANELHEIGHT));

            CPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
            CPanel.setBackground(Color.GRAY);

            IPanel.setLayout(new GridLayout(6, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(300, 200));
            IPanel.setBackground(Color.WHITE);

            IPanel.setLayout(new GridLayout(6, 1));
            BPanel.setLayout(new GridLayout(1, 2));

            transferamount.setPreferredSize(new Dimension(100, 20));
            inputbox2.setPreferredSize(new Dimension(100, 20));

            submitButton.setPreferredSize(new Dimension(100, 20));
            cancelButton.setPreferredSize(new Dimension(100, 20));
            //---------------------------
            BPanel.add(cancelButton);
            BPanel.add(submitButton);
            //---------------------------
            IPanel.add(transferamount);
            IPanel.add(inputbox2);
            IPanel.add(new JLabel());
            IPanel.add(BPanel);
            //---------------------------
            CPanel.add(IPanel);
            //---------------------------
            add(NPanel);
            add(SPanel);
            add(CPanel);
            inputbox2.requestFocusInWindow();
            eventhandler handler = new eventhandler();
            inputbox2.addActionListener(handler);
            submitButton.addActionListener(handler);
            cancelButton.addActionListener(handler);

            keylisten keyhandler = new keylisten();
            submitButton.addKeyListener(keyhandler);
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource() == submitButton) {
                    if (e.getSource() == submitButton) {
                        submit();
                    }
                    if (e.getSource() == cancelButton) {
                        cancel();
                    }
                }
            }
        }

        private class eventhandler implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == inputbox2) {
                    inputbox2.setEditable(false);
                    submitButton.requestFocusInWindow();
                }
                if (event.getSource() == submitButton) {
                    submit();
                }
                if (event.getSource() == cancelButton) {
                    cancel();
                }

            }
        }

        public void submit() {
            amount = keypad.StringtoDouble(inputbox2.getText());
            if (validateAmount(inputbox2.getText()) == 0) {
                amount = keypad.StringtoDouble(inputbox2.getText());
                System.out.println("transfer amount = " + amount);
                callCframe();
                dispose();
            } else {
                reset();
            }

        }

        public void cancel() {
            toMainMenu = true;
            dispose();
        }

        public void reset() {
            inputbox2.setText("");
            inputbox2.setEditable(true);
            inputbox2.requestFocusInWindow();
        }
    }

    public class TransferFrame extends JFrame {

        private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Border FONTBORDER = BorderFactory.createEmptyBorder(5, 3, 5, 0);
        private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        private final int PANELHEIGHT = 80;
        JLabel accountnumber = new JLabel("Target Account Number: ");
        JTextField inputbox1 = new JTextField();
        JButton submitbutton = new JButton("Transfer");
        JButton cancelbutton = new JButton("Back to Main Menu");

        private TransferFrame() {

            inputbox1.setEditable(true);
            JPanel NPanel = new JPanel();
            JPanel SPanel = new JPanel();
            JPanel CPanel = new JPanel();
            JPanel IPanel = new JPanel();
            JPanel BPanel = new JPanel();
            //NPanel.setLayout(new BoxLayout(NPanel, BoxLayout.Y_AXIS));
            NPanel.setPreferredSize(new Dimension(800, PANELHEIGHT));

            JLabel footer = new JLabel("For further assistance, please contact customer support.");
            footer.setFont(FONTSTYLE);
            footer.setBorder(HEADFOOTBORDER);
            footer.setPreferredSize(new Dimension(800, PANELHEIGHT));

            //SPanel.setLayout(new BoxLayout(NPanel, BoxLayout.Y_AXIS));
            SPanel.setPreferredSize(new Dimension(800, PANELHEIGHT));

            CPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
            CPanel.setBackground(Color.GRAY);

            IPanel.setLayout(new GridLayout(6, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(300, 200));
            IPanel.setBackground(Color.WHITE);

            IPanel.setLayout(new GridLayout(6, 1));

            BPanel.setLayout(new GridLayout(1, 2));

            accountnumber.setPreferredSize(new Dimension(100, 20));
            inputbox1.setPreferredSize(new Dimension(100, 20));

            submitbutton.setPreferredSize(new Dimension(100, 20));
            cancelbutton.setPreferredSize(new Dimension(100, 20));

            //----------------------
            IPanel.add(accountnumber);
            IPanel.add(inputbox1);
            BPanel.add(cancelbutton);
            BPanel.add(submitbutton);
            IPanel.add(BPanel);
            //----------------------
            SPanel.add(footer);
            CPanel.add(IPanel);
            //-----------------------------------
            add(CPanel, BorderLayout.CENTER);
            add(NPanel, BorderLayout.NORTH);
            add(SPanel, BorderLayout.SOUTH);

            eventhandler handler = new eventhandler();
            inputbox1.addActionListener(handler);
            submitbutton.addActionListener(handler);
            cancelbutton.addActionListener(handler);

            keylisten keyhandler = new keylisten();
            submitbutton.addKeyListener(keyhandler);
            cancelbutton.addKeyListener(keyhandler);
        }

        private class keylisten implements  KeyListener{
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

            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == inputbox1) {
                    inputbox1.setEditable(false);
                    submitbutton.requestFocusInWindow();
                }
                if (event.getSource() == submitbutton) {
                    submit();

                    /*else if(flag == 1){
                        inputbox1.setEditable(true);
                        inputbox2.setEditable(false);
                        inputbox1.requestFocusInWindow();
                    }else if(flag == 2){
                        inputbox1.setEditable(false);
                        inputbox2.setEditable(true);
                        inputbox2.requestFocusInWindow();
                    }else if(flag == 3){
                        inputbox1.setEditable(true);
                        inputbox2.setEditable(true);
                        inputbox1.requestFocusInWindow();
                    } */
                }
                if (event.getSource() == cancelbutton) {
                    cancel();
                }

            }
        }

        private void submit() {
            if (validateTarget(inputbox1.getText()) == 0) {
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

    public class Confirmframe extends JFrame {

        JLabel targetaccountBox1 = new JLabel(String.valueOf(targetAccountNumber));
        JLabel amountBox1 = new JLabel(screen.dollarAmountToString(amount));
        JButton submitbutton = new JButton("Submit");
        JButton cancelbutton = new JButton("Cancel");

        private Confirmframe() {
            JPanel NPanel = new JPanel();
            JPanel SPanel = new JPanel();
            JPanel CPanel = new JPanel();
            JPanel IPanel = new JPanel();
            JPanel BPanel = new JPanel();

            NPanel.setBackground(Color.white);
            SPanel.setBackground(Color.white);

            CPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
            CPanel.setBackground(Color.GRAY);

            IPanel.setLayout(new GridLayout(6, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(300, 200));
            IPanel.setBackground(Color.WHITE);

            NPanel.setPreferredSize(new Dimension(800, 80));
            SPanel.setPreferredSize(new Dimension(100, 100));

            IPanel.setLayout(new GridLayout(6, 1));
            BPanel.setLayout(new GridLayout(1, 2));

            //-------------------------------
            IPanel.add(new JLabel("Transfer target:"));
            IPanel.add(targetaccountBox1);
            IPanel.add(new JLabel("Transfer amount:"));
            IPanel.add(amountBox1);
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
            add(NPanel, BorderLayout.NORTH);
            add(CPanel, BorderLayout.CENTER);
        }

        private class eventhandler implements ActionListener {

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

            screen.waitUntilNotDisplaying(new MessageFrame("Transfer sucess.", "Transfer sucess.", 3));

            new Timer(3000, e -> {
                bankDatabase.transfer(getAccountNumber(), targetAccountNumber, amount);
                toMainMenu = true;
            }).start();
            dispose();

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
            errorflag = 1;
        }

        if (amount <= 0) {
            screen.displayMessageLine("\nTransfer can not be smaller or equals than zero");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Transfer can not be smaller or equals than zero.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag = 2;
        }
        errormsg = errormsg + "</html>";
        if (errorflag != 0) {
            screen.showMessage1(errormsg);
        }
        return errorflag;
    }

    private int validateTarget(String inputString1) {
        targetAccountNumber = Integer.parseInt(inputString1);

        int errorflag = 0;
        String errormsg = "<html>";
        if (targetAccountNumber == getAccountNumber()) {
            screen.displayMessageLine("\nYou cannot transfer to the same account.");
            screen.displayMessageLine("Transaction cancelled.");
            errormsg = errormsg + "You cannot transfer to the same account.<br><br>" + "";
            //screen.showMessage1("You cannot transfer to the same account.");
            errorflag = 1;
        }
        if (!bankDatabase.checkAccountExist(targetAccountNumber)) {
            screen.displayMessageLine("Target account does not exist.");
            screen.displayMessageLine("Transaction cancelled.");

            errormsg = errormsg + "Target account does not exist.<br><br>" + "";
            //screen.showMessage1("Target account does not exist.");
            errorflag = 2;
        }

        if (targetAccountNumber == 0) {
            screen.displayMessageLine("\nTransfer target is essential");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Transfer target is essential.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag = 3;
        }

        errormsg = errormsg + "</html>";
        System.out.println("haha: \n" + errormsg);
        if (errorflag != 0) {
            screen.showMessage1(errormsg);
        }
        return errorflag;
    }

    public void execute() {
        toMainMenu = false;
        callTframe();
    }

    /*private double promptForTransferAmount() {
        Screen screen = getScreen(); // get reference to screen

        // prompt user to enter the first entry
        screen.displayMessage("\nPlease enter a transfer amount in dollars up to maximum of two digits (.00): ");
        double input = keypad.getInputFloat();

        if (input <= 0) {
            screen.displayMessageLine("\nThe amount must be greater than 0.");
            return CANCELED;
        }

        // receive for the second entry for data vaildation
        screen.displayMessage("\nPlease re-enter the transfer amount: ");
        double input1 = keypad.getInputFloat();

        if (input1 != input) {   //check if the second entry is equal to the first entry, if not the transaction would be cancelled
            screen.displayMessageLine("\nThe transfer amount did not match.");
            return CANCELED;
        }

        return (double) input; // return dollar amount 
    } */
}
