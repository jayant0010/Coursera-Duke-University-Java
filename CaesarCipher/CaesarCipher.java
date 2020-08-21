import edu.duke.*;
import java.io.*;
import java.lang.*;


public class CaesarCipher {

    public boolean isVowel(char ch){
        char t = Character.toUpperCase(ch);
        if(t=='A'||t=='E'||t=='I'||t=='O'||t=='U'){
            //System.out.println("Called for "+ ch + " : true");
            return true;
        }
        else{
            //System.out.println("Called for "+ ch + " : false");
            return false;
        }
        
    }
    
    public void testIsVowel(){
        System.out.println("A : " + isVowel('A'));
        System.out.println("B : " + isVowel('B'));
        System.out.println("b : " + isVowel('b'));
        System.out.println("! : " + isVowel('!'));
    }
    
    
    public String replaceVowels(String phrase, char ch){
        StringBuilder str = new StringBuilder(phrase);
        for(int i=0;i<str.length();i++){
            char t = str.charAt(i);
            //System.out.println("entered for loop. char = " + t);
            if(isVowel(t)){
                //System.out.println("Innermost loop entered.");
                str.setCharAt(i,ch);
            }
        }
        
        
        return str.toString();
    }
    
    public void testReplaceVowels(){
        String str = "Hello World";
        char ch = '*';
        System.out.println(str + " : " + replaceVowels(str,ch) );
    }
    
    public String emphasize(String phrase, char ch){
        StringBuilder ip = new StringBuilder(phrase);
        for(int i=0;i<ip.length();i++){
            if(ip.charAt(i)==ch){
                if(i%2==0){
                    ip.setCharAt(i,'*');
                }
                else{
                    ip.setCharAt(i,'+');
                }
            }
        }
        
        return ip.toString();
    }
    
    public void testEmphasize(){
        System.out.println("Mary Bella Abracadabra : " + emphasize("Mary Bella Abracadabra",'a'));
    }
    
    
    
    public String encrypt(String input, int key){
        //System.out.println("Original Message : " + input);
        StringBuilder encrypted = new StringBuilder(input);
        String alphabetBig = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetSmall = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphaBig = alphabetBig.substring(key) + alphabetBig.substring(0,key);
        String shiftedAlphaSmall = alphabetSmall.substring(key) + alphabetSmall.substring(0,key);
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
    
    public String decrypt(String input, int key){
        
        String answer = encrypt(input,26-key);
        return answer;
        
    }
    
    public void testEncryptDecrypt(){
        
        //FileResource fr = new FileResource();//to select a .txt file
        //String message = fr.asString();//to select a .txt file
        
        //String message = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        //int key = 15;
        
        String message = "Just a test string with lots of eeeeeeeeeeeeeeeees";
        int key = 15;
        
        System.out.println("Original message  : " + message);
        
        String encr = encrypt(message,key);
        System.out.println("Encrypted message : " + encr);
        
        String decr = decrypt(encr,key);
        System.out.println("Decrypted message : " + decr);
        
    }
    
    public void bruteForceDecrypt(String message){//eyeball decryption
        
        System.out.println("Original message : "+message);
        String temp;
        System.out.println("Starting Brute force Decryption.");
        for(int i=0;i<26;i++){
            temp = decrypt(message,i);
            System.out.println("Decrypted using key " + i + " : " + temp);
        }
    }
    
    
    public void testBruteForceDecrypt(){
        String message = "";
        
        
        message = "Pi cddc qt xc iwt rdcutgtcrt gddb lxiw ndjg wpi dc udg p hjgegxht epgin. NTAA ADJS!";
        // for string "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!"
        // At key 15
        bruteForceDecrypt(message);
    }
    
    public String statisticalAutoDecrypt(String encr){
        
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
        String answer = decrypt(encr,key);
        System.out.println("Key Used : " + key);
        return answer;
    }
    
    public void testStatisticalAutoDecrypt(){
        String test = "Yjhi p ithi higxcv lxiw adih du ttttttttttttttttth";
        // for string "Just a test string with lots of eeeeeeeeeeeeeeeees"
        // encrypted with key 15
        String answer = statisticalAutoDecrypt(test);
        System.out.println("Decrypted text : " + answer);
    }
    
    public String encryptTwoKeys(String input, int k1, int k2){
        String encr = "";
        String encr1 = encrypt(input,k1);
        String encr2 = encrypt(input,k2);
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
    
    public void testEncryptTwoKeys(){
        String encr = "";
        //encr = encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!",8,21);
        encr = encryptTwoKeys("Top ncmy qkff vi vguv vbg ycpx",24,6);
        //encr = encryptTwoKeys("Hfs cpwewloj loks cd Hoto kyg Cyy.",26-14,26-24);//improvised decryption
        
        System.out.println(encr);
    }
    
    public void countWordLengths(FileResource fr, int[] counts){
        for(String word : fr.words()){
            int counter = 0;
            int len = word.length();
            for(int i=0;i<len;i++){
                if(Character.isLetter(word.charAt(i))){
                    counter++;
                }
            }
            
            counts[counter] = counts[counter] + 1;
        }
        int temp = 0;
        int maxIndex = 0;
       
        for(int i=0;i<counts.length;i++){
            if(counts[i]>temp){
                temp = counts[i];
                maxIndex = i;
            }
        }
        
        System.out.println("Highest count is for words for length " + maxIndex + " and the corresponding count is : " + counts[maxIndex]);
        
    }
    
    public void testCountWordLengths(){
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        countWordLengths(fr,counts);
    }
    
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
        String evenDecr = statisticalAutoDecrypt(even);
        String oddDecr = statisticalAutoDecrypt(odd);
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
        System.out.println("Decrypted Text : " + ans);
        
    }
    
    public void testTwoKeyDecrypt(){
        String message = "";
        FileResource fr = new FileResource();
        message = fr.asString();
        
        //message = "Gwpv c vbuq pvokki yfve iqqu qc bgbgbgbgbgbgbgbgbu";
        //For "Just a test string with lots of eeeeeeeeeeeeeeeees" encrypted using keys 23 and 2
        
        //message = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        //for "Eren and Emily have evil eerie green ears" encrypted using keys 22 and 19
        
        twoKeyDecrypt(message);
    }
    
    

}



