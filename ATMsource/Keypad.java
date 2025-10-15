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

   /*
   // return an integer value entered by user 
   public int getInput()
   {
      return input.nextInt(); // we assume that user enters an integer  
   } // end method getInput
   
   public float getInputFloat()
   {
      return input.nextFloat(); // we assume that user enters an float  
   } // end method getInputFloat
   */

   private String callInput(){
        Scanner something = new Scanner(System.in);
        return something.next();
   }
   

    private static int dotsCount(String inputstr){
        char[] inputchar = inputstr.toCharArray();
        int count = 0;
        for(int i = 0; i < inputstr.length(); i++){
            if(inputchar[i] == 46){
                count++;
            }
        }
        switch(count){
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 2;
        }
    }

   private static int dotsPosition(String inputstr){
        char[] inputchar = inputstr.toCharArray();
        int count = 0;
        for(int i = 0; i < inputstr.length(); i++){
            if(inputchar[i] == 46){
                count = i;
            }
        }
        if(count == 0){
            return 0;
        }else if(count == inputstr.length() - 1){
            return 2;
        }else{
            return 1;
        }
    }

    private static int checkIntorFloat(int DotsCount, int DotsPosition){
        switch (DotsCount) {
            case 0:
                return 1;
            case 1:
                if(DotsPosition == 1){
                    return 2;
                }else{
                    return 0;
                }
            default:
                return 0;
        }
    }

   public int getInput(){
        String input = callInput();
        if(checkIntorFloat(dotsCount(input), dotsPosition(input)) == 1){
            return Integer.parseInt(input);
        }else{
            return 0;
        }
    }
   public double getInputFloat(){
        String input = callInput();
        if(checkIntorFloat(dotsCount(input), dotsPosition(input)) != 0){
            BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.DOWN);
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