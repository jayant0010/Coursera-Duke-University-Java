import edu.duke.*;
import java.io.*;
import java.lang.*;
public class CaesarCipherTwo {

    private int mainKey1;
    private int mainKey2;
    
    public CaesarCipherTwo(int k1, int k2){
        mainKey1 = k1;
        mainKey2 = k2;
    }
    
    public String encryptTwoKeys(String input){
        String encr = "";
        ObOrCaesarCipher c1 = new ObOrCaesarCipher(mainKey1);
        ObOrCaesarCipher c2 = new ObOrCaesarCipher(mainKey2);
        String encr1 = c1.encrypt(input);
        String encr2 = c2.encrypt(input);
        for(int i=0;i<input.length();i++){
            if(i%2==0){
                encr = encr + encr1.charAt(i);
            }
            else{
                encr = encr + encr2.charAt(i);
            }
        }
        return encr;
    }
    
    
    
}
