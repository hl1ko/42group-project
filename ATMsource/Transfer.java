
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
    private boolean firstsub = false;

    public Transfer(int userAccountNumber, Screen atmScreen, //constructor for initializing objects
            BankDatabase atmBankDatabase, Keypad atmKeypad) {
        super(userAccountNumber, atmScreen, atmBankDatabase);  //call superclass's constructor for initializing three of the variables
        keypad = atmKeypad;  //additional constructor implemention to initialize extra object variable
    } // end Transfer constructor

    public class TransferFrame extends JFrame {

        private final Font FONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Font NUMBERFONTSTYLE = new Font("Verdana", Font.PLAIN, 20);
        private final Border FONTBORDER = BorderFactory.createEmptyBorder(5, 3, 5, 0);
        private final Border HEADFOOTBORDER = BorderFactory.createEmptyBorder(0, 5, 0, 0);
        private final int PANELHEIGHT = 80;
        JLabel accountnumber = new JLabel("Target Account Number: ");
        JTextField inputbox1 = new JTextField();
        JLabel transferamount = new JLabel("Transfer amount: ");
        JTextField inputbox2 = new JTextField();
        JButton submitbutton = new JButton("Submit");
        JButton cancelbutton = new JButton("Cancel");

        private TransferFrame() {

            inputbox1.setEditable(true);
            inputbox2.setEditable(false);
            JPanel NPanel = new JPanel();
            JPanel SPanel = new JPanel();
            JPanel CPanel = new JPanel();
            JPanel IPanel = new JPanel();

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

            accountnumber.setPreferredSize(new Dimension(100, 20));
            inputbox1.setPreferredSize(new Dimension(100, 20));
            transferamount.setPreferredSize(new Dimension(100, 20));
            inputbox2.setPreferredSize(new Dimension(100, 20));

            submitbutton.setPreferredSize(new Dimension(100, 20));
            cancelbutton.setPreferredSize(new Dimension(100, 20));

            //----------------------
            IPanel.add(accountnumber);
            IPanel.add(inputbox1);
            IPanel.add(transferamount);
            IPanel.add(inputbox2);
            IPanel.add(submitbutton);
            IPanel.add(cancelbutton);
            //----------------------
            SPanel.add(footer);
            CPanel.add(IPanel);
            add(CPanel, BorderLayout.CENTER);
            add(NPanel, BorderLayout.NORTH);
            add(SPanel, BorderLayout.SOUTH);

            eventhandler handler = new eventhandler();
            inputbox1.addActionListener(handler);
            inputbox2.addActionListener(handler);
            submitbutton.addActionListener(handler);
            cancelbutton.addActionListener(handler);
        }

        private class eventhandler implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == inputbox1) {
                    inputbox1.setEditable(false);
                    inputbox2.setEditable(true);
                    inputbox2.requestFocusInWindow();
                }
                if (event.getSource() == inputbox2) {
                    inputbox2.setEditable(false);
                    submitbutton.requestFocusInWindow();
                }
                if (event.getSource() == submitbutton) {
                    //check content
                    int flag = checkFlag(validation(inputbox1.getText(), inputbox2.getText()));
                    if (flag == 0) {
                        targetAccountNumber = keypad.StringtoInt(inputbox1.getText());
                        amount = keypad.StringtoDouble(inputbox2.getText());
                        System.out.println("target account = " + targetAccountNumber);
                        System.out.println("transfer amount = " + amount);
                        firstsub = true;
                        dispose();
                        callCframe();
                    } else if(flag == 1){
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
                    }
                }
                if (event.getSource() == cancelbutton) {
                    toMainMenu = true;
                    dispose();
                }

            }
        }
    }

    public class Confirmframe extends JFrame {

        JLabel targetaccountBox1 = new JLabel(String.valueOf(targetAccountNumber));
        JLabel amountBox1 = new JLabel(String.valueOf(amount));
        JButton submitbutton = new JButton("Submit");
        JButton cancelbutton = new JButton("Cancel");

        private Confirmframe() {
            JPanel NPanel = new JPanel();
            JPanel SPanel = new JPanel();
            JPanel CPanel = new JPanel();
            JPanel IPanel = new JPanel();

            NPanel.setBackground(Color.red);
            SPanel.setBackground(Color.gray);

            CPanel.setBorder(BorderFactory.createEmptyBorder(95, 240, 0, 240));
            CPanel.setBackground(Color.GRAY);

            IPanel.setLayout(new GridLayout(6, 1));
            IPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE));
            IPanel.setPreferredSize(new Dimension(300, 200));
            IPanel.setBackground(Color.WHITE);

            NPanel.setPreferredSize(new Dimension(800, 80));
            SPanel.setPreferredSize(new Dimension(100, 100));

            IPanel.setLayout(new GridLayout(6, 1));

            //-------------------------------
            IPanel.add(new JLabel("Transfer target:"));
            IPanel.add(targetaccountBox1);
            IPanel.add(new JLabel("Transfer amount:"));
            IPanel.add(amountBox1);
            IPanel.add(submitbutton);
            IPanel.add(cancelbutton);
            //-------------------------------
            eventhandler handler = new eventhandler();
            submitbutton.addActionListener(handler);
            cancelbutton.addActionListener(handler);

            CPanel.add(IPanel);
            add(NPanel, BorderLayout.NORTH);
            add(CPanel, BorderLayout.CENTER);
        }

        private class eventhandler implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (event.getSource() == submitbutton) {

                    screen.waitUntilNotDisplaying(new MessageFrame("Transfer sucess.", "Transfer sucess.", 3));

                    new Timer(3000, e -> {
                        bankDatabase.transfer(getAccountNumber(), targetAccountNumber, amount);
                        toMainMenu = true;
                    }).start();
                    dispose();

                }
                if (event.getSource() == cancelbutton) {
                    firstsub = false;
                    dispose();
                    callTframe();
                }
            }
        }

    }

    private void callTframe() {
        SwingUtilities.invokeLater(() -> {
            TransferFrame frame = new TransferFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            // Add components here if needed
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

    private int[] validation(String inputString1, String inputString2) {
        targetAccountNumber = Integer.parseInt(inputString1);
        amount = keypad.StringtoDouble(inputString2);
        var errorflag = new int[2];
        errorflag[0] = 0;
        errorflag[1] = 0;
        String errormsg = "<html>";
        if (targetAccountNumber == getAccountNumber()) {
            screen.displayMessageLine("\nYou cannot transfer to the same account.");
            screen.displayMessageLine("Transaction cancelled.");
            errormsg = errormsg + "You cannot transfer to the same account.<br><br>" + "";
            //screen.showMessage1("You cannot transfer to the same account.");
            errorflag[0] = 1;
        }
        if (!bankDatabase.checkAccountExist(targetAccountNumber)) {
            screen.displayMessageLine("Target account does not exist.");
            screen.displayMessageLine("Transaction cancelled.");

            errormsg = errormsg + "Target account does not exist.<br><br>" + "";
            //screen.showMessage1("Target account does not exist.");
            errorflag[0] = 1;
        }
        if (bankDatabase.getAvailableBalance(getAccountNumber()) < amount) {
            screen.displayMessageLine("\nInsufficient funds in your account.");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Insufficient funds in your account.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag[1] = 1;
        }

        if (targetAccountNumber == 0) {
            screen.displayMessageLine("\nTransfer target is essential");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Transfer target is essential.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag[0] = 1;
        }

        if (amount <= 0) {
            screen.displayMessageLine("\nTransfer can not be smaller or equals than zero");
            screen.displayMessageLine("\nCanceling transaction...");

            errormsg = errormsg + "Transfer can not be smaller or equals than zero.<br><br>" + "";
            //screen.showMessage1("Insufficient funds in your account.");
            errorflag[1] = 1;
        }
        errormsg = errormsg + "</html>";
        System.out.println("haha: \n" + errormsg);
        if (errorflag[0] == 1 || errorflag[1] == 1) {
            screen.showMessage1(errormsg);
        }
        return errorflag;
    }

    public int checkFlag(int[] errorflag) {
        if (errorflag[0] == 1 && errorflag[1] == 1) {
            return 3;
        }
        if (errorflag[0] == 1) {
            return 1;
        }
        if (errorflag[1] == 1) {
            return 2;
        }
        return 0;
    }

    public void execute() {
        Screen screen = getScreen();
        toMainMenu = false;
        callTframe();
    }

    private double promptForTransferAmount() {
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
    }
}
