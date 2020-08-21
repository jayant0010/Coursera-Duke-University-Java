import edu.duke.*;
import java.io.*;
import java.lang.*;


public class ObOrCaesarCipher {

    private String alphabetBig;
    private String alphabetSmall;
    private String shiftedAlphaBig;
    private String shiftedAlphaSmall;
    private int mainKey;
    
    public ObOrCaesarCipher(int key){
        alphabetBig = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        alphabetSmall = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphaBig = alphabetBig.substring(key) + alphabetBig.substring(0,key);
        shiftedAlphaSmall = alphabetSmall.substring(key) + alphabetSmall.substring(0,key);   
        mainKey = key;
    }
    
    public String encrypt(String input){
        //System.out.println("Original Message : " + input);
        StringBuilder encrypted = new StringBuilder(input);
        for(int i=0;i<encrypted.length();i++){
            if((alphabetBig.indexOf(encrypted.charAt(i))!=-1)||(alphabetSmall.indexOf(encrypted.charAt(i))!=-1)){//if char is an alphabet
                int index;
                char newChar;
                char oldChar;
                oldChar = encrypted.charAt(i);
                if(Character.isUpperCase(oldChar)){
                    index = alphabetBig.indexOf(oldChar);
                    newChar = shiftedAlphaBig.charAt(index);
                    encrypted.setCharAt(i,newChar);
                }
                else if(Character.isLowerCase(oldChar)){
                    index = alphabetSmall.indexOf(oldChar);
                    newChar = shiftedAlphaSmall.charAt(index);
                    encrypted.setCharAt(i,newChar);
                }
                
            }
        }
        
        return encrypted.toString();
    }
    
    
    public String decrypt(String input){
        
        ObOrCaesarCipher cc = new ObOrCaesarCipher(26-mainKey);
        String answer = cc.encrypt(input);
        return answer;
        
    }
    

}



