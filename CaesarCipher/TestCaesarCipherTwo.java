import edu.duke.*;
import java.io.*;
import java.lang.*;

public class TestCaesarCipherTwo {
    
    public void twoKeyDecrypt(String message){//using statistic breaking
        String even = "";
        String odd = "";
        for(int i=0;i<message.length();i++){//seperate string into even and odd
            char current = message.charAt(i);
            if(Character.isLetter(current)){
                if(i%2==0){
                    even = even + current;
                }
                else{
                    odd = odd + current;
                }
            }
        }
        
        //implement individual string decryption
        TestCaesarCipher t1 = new TestCaesarCipher();
        String evenDecr = t1.breakCaesarCipher(even);
        String oddDecr = t1.breakCaesarCipher(odd);
        String answerTemp = "";
        
        //combine strings
        StringBuilder answer = new StringBuilder(message);
        int countEven = 0;
        int countOdd = 0;
        char t;
        
        for(int i=0;i<answer.length();i++){//modify each char one by one
            
            if(Character.isAlphabetic(answer.charAt(i))){
                if(i%2==0){
                    t = evenDecr.charAt(countEven);
                    countEven++;
                    answer.setCharAt(i,t);
                }
                else{
                    t = oddDecr.charAt(countOdd);
                    countOdd++;
                    answer.setCharAt(i,t);
                }
            }
        }
        String ans = answer.toString();
        System.out.println("Decrypted Text using Ob Or twoKeyDecrypt: " + ans);
        
    }
    
    public void SimpleTests(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        
        System.out.println("original message : " + message);
        CaesarCipherTwo c = new CaesarCipherTwo(21,8);
        String encr = c.encryptTwoKeys(message);
        System.out.println("Encrypted message : " + encr);
        twoKeyDecrypt(encr);
        
    }
}
