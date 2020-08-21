import edu.duke.*;
import java.io.*;
import java.lang.*;
//this class file needs ObOrCaesarCipher.java in the same directory to work.


public class TestCaesarCipher {
    
    public String breakCaesarCipher(String encr){
        
        String alphaLower = "abcdefghijklmnopqrstuvwxyz";
        String alphaUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int[] ar = new int[26];
        int index = 0;
        for(int i=0;i<encr.length();i++){//frequency counter loop
            if(Character.isAlphabetic(encr.charAt(i))){
                char ch = encr.charAt(i);
                if(Character.isUpperCase(ch)){
                    index = alphaUpper.indexOf(ch);
                    ar[index] = ar[index] + 1;
                }
                if(Character.isLowerCase(ch)){
                    index = alphaLower.indexOf(ch);
                    ar[index] = ar[index] + 1;
                }
            }
        }
        int temp = 0;
        int highest = 0;
        for (int i=0;i<ar.length;i++){
            if(ar[i]>temp){
                highest = i;
                temp = ar[i];
            }
        }
        
        //System.out.println("Largest frequency is of letter at pos " + highest + " i.e. letter " + alphaLower.charAt(highest));
        //System.out.println("But largest frequency should belong to 'e' i.e. of letter at pos " + alphaLower.indexOf('e'));
        
        int key = highest - 4;
        if(highest<4){
            key = 26 - (4-highest);
        }
        ObOrCaesarCipher c2 = new ObOrCaesarCipher(key);
        String answer = c2.decrypt(encr);
        return answer;
       
    }

    public void simpleTests(){
        
        String message = ""; 
        
        FileResource fr = new FileResource();
        message = fr.asString();//read message from a text file
        
        //message = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        
        ObOrCaesarCipher c1 = new ObOrCaesarCipher(15);
        System.out.println("Original message: " + message);
        String encr = c1.encrypt(message);
        System.out.println("Encrypted message: " + encr);
        String decr = c1.decrypt(encr);
        System.out.println("Decypted message: " + decr);
        String decr2 = breakCaesarCipher(encr);
        System.out.println("Decypted message using statistics: " + decr2);
        
    }
    
    
}
