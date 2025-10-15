import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner; 

public class input_demo {
    public static String getInput(){
        Scanner something = new Scanner(System.in);
        return something.next();
    }

    public static int dotsCount(String inputstr){
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

    public static int dotsPosition(String inputstr){
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

    static int checkIntorFloat(int DotsCount, int DotsPosition){
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

    static int getIntInput(){
        String input = getInput();
        if(checkIntorFloat(dotsCount(input), dotsPosition(input)) == 1){
            return Integer.parseInt(input);
        }else{
            return 0;
        }
    }
    static float getFloatInput(){
        String input = getInput();
        if(checkIntorFloat(dotsCount(input), dotsPosition(input)) != 0){
            BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.DOWN);
            return bd.floatValue();
        }else{
            return 0;
        }
    }

    public static void main(String args[]){
        String inputstr = getInput();
        System.out.println("Received input: " + inputstr);
        System.out.println("String length: " + inputstr.length());
        System.out.println("=========");
        System.out.println("Dots count: " + dotsCount(inputstr));
        System.out.println("Dots position: " + dotsPosition(inputstr));

        if(dotsCount(inputstr) == 1){
            System.out.println("Dots position: " + dotsPosition(inputstr));
        }
        System.out.print("String status: ");
        switch(checkIntorFloat(dotsCount(inputstr), dotsPosition(inputstr))){
            case 1:
                System.out.println("Integer");
                System.out.println(Integer.parseInt(inputstr));
                break;
            case 2:
                System.out.println("Float");
                System.out.println("Float value: " + Float.valueOf(inputstr).floatValue());
                BigDecimal bd = new BigDecimal(inputstr).setScale(2, RoundingMode.DOWN);
                System.out.println("Rounded: " + bd.floatValue());
                break;
            case 0:
                System.out.println("Neither");
                break;
        }
        System.out.println("");
        int k;
        float j;
        /*
        k = getIntInput();
        System.out.println("Integer input: " + k);
        k = getIntInput();
        System.out.println("Integer input: " + k);
        */
        j = getFloatInput();
        System.out.println("Float input: " + j);
        j = getFloatInput();
        System.out.println("Float input: " + j);
    }
}
