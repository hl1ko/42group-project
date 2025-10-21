// Keypad.java
// Represents the keypad of the ATM
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner; // program uses Scanner to obtain user input

public class Keypad
{
    private Scanner input; // reads data from the command line
                            
    // no-argument constructor initializes the Scanner
    public Keypad()
    {
        input = new Scanner( System.in );    
    } // end no-argument Keypad constructor

    private String callInput(){
        return input.next();
    }

    private static int dotsCount(String inputString){
        char[] inputChar = inputString.toCharArray();
        int count = 0;
        for(int i = 0; i < inputString.length(); i++){
            if ( inputChar[i] == 46 )   // 
            {
                count++;
            }
        }
        return switch (count) {
            case 0 -> 0;  // no dots
            case 1 -> 1;  // exactly 1 dot
            default -> 2; // more than 1 dot
        };
    }

    private static int dotsPosition(String inputString){
        char[] inputChar = inputString.toCharArray();
        int position = 0;

        for(int i = 0; i < inputString.length(); i++){
            if ( inputChar[i] == 46 )
                position = i;
        }

        if ( position == 0 ) {
            return 0; //the only dots at start position
        } else if (position == inputString.length() - 1){
            return 2; //the only dots at end position
        } else {
            return 1; //the only dots at middle
        }
    }

    private static int IntegerOrDouble(String inputString){
        switch (dotsCount(inputString)) {
            case 0: // no dots -> integer
                return 1;
            case 1: // 1 dots -> 
                return switch (dotsPosition(inputString)) {
                    case 1 -> 2; //dot locate between start and end -> float
                    case 2 -> 2; //dot locate at end -> no decimal stuff -> float to avoid error
                    default -> 0; //dot locate at start -> not numeric -> invalid
                };
            default: // more than one dots -> invalid
                return 0;
        }
    }

    public int getInput(){
        String inputString = callInput();
        if (IntegerOrDouble(inputString) == 1){ //if 
            return Integer.parseInt(inputString);
        } else {
            return 0;
        }
    }

    public int getInput_customcancel(int CANCELED){
        String inputString = callInput();
        if (IntegerOrDouble(inputString) == 1){ //if 
            return Integer.parseInt(inputString);
        } else {
            return CANCELED;
        }
    }

    public double getInputFloat(){
        String inputString = callInput();
        if(IntegerOrDouble(inputString) != 0){
            BigDecimal bd = new BigDecimal(inputString).setScale(2, RoundingMode.DOWN);
            return bd.doubleValue();
        }else{
            return 0;
        }
    }
} // end class Keypad  



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/