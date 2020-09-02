import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        String slice = "";
        for(int i=whichSlice;i<message.length();i=i+totalSlices){
            slice = slice + message.charAt(i);
        }
        return slice;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        ArrayList<String> sliceList = new ArrayList<String>();
        for(int i=0;i<klength;i++){
            String slice = sliceString(encrypted,i,klength);
            sliceList.add(slice);
        }
        CaesarCracker  cr = new CaesarCracker(mostCommon);
        for(int i=0;i<klength;i++){
            key[i] = cr.getKey(sliceList.get(i));
        }
        /*
        //Print block
        for(int i=0;i<klength;i++){
            System.out.println(key[i]);
        }
        // */
        return key;
    }

    public void breakVigenere () {//Known language, Known Key-Length
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[] key = tryKeyLength(encrypted,4,'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        System.out.println("Decrypted message : " + decrypted);
        //System.out.println("Decrypted message : " + decrypted.substring(0,300));
        //string this big exceeds the scope of bluej output window,
        //result would appear to be incomplete,
        //print only the initial lines to counter that.
    }
    
    public void breakVigenereWithoutKey () {//Known language, UnKnown Key-Length
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        FileResource dictFile = new FileResource("dictionaries/English");
        HashSet<String> dictionary = readDictionary(dictFile);
        String decrypted = breakForLanguage(encrypted,dictionary,'e');
        //System.out.println("Decrypted message : " + decrypted);
        System.out.println("Decrypted message : " + decrypted.substring(0,300));
        //very large strings exceed the scope of bluej output window,
        //result would appear to be incomplete,
        //print only the initial lines to counter that.
    }
    
    
    public void breakVigenereWithoutKeyAndLanguage () {//UnKnown language, UnKnown Key-Length
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        String source = "dictionaries/";
        //generate multi-Language Dictionary
        HashMap<String,HashSet<String>> languages = new HashMap<String,HashSet<String>>();
        
        ArrayList<String> langList = new ArrayList<String>(Arrays.asList("Danish","Dutch","English","French","German","Italian","Portuguese","Spanish"));
        
        for(String lang : langList){
            String fileSource = source + lang;
            FileResource f = new FileResource(fileSource);
            HashSet<String> dictionary = readDictionary(f);
            languages.put(lang,dictionary);
            System.out.println(lang + " Dictionary file processed.");
            
        }
        System.out.println("Multi-Language Dictionary generation complete");
        System.out.println();
        System.out.println();
        
        String decrypted = breakForAllLangs(encrypted,languages);
        
        System.out.println("Decrypted message upto 300 char: ");
        System.out.println(decrypted.substring(0,300));
        
        //System.out.println("Complete Decrypted message : ");
        //System.out.println(decrypted);
        //very large strings exceed the scope of bluej output window,
        //result would appear to be incomplete,
        //print only the initial lines to counter that.
    }
    
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();
        for(String word : fr.lines()){
            String temp = word.toLowerCase();
            dictionary.add(temp);
        }
        return dictionary;
    }
    
    public int countWords(String message,HashSet<String> dictionary){
        int count = 0;
        for(String word : message.split("\\W+")){
            String temp  = word.toLowerCase();
            if(dictionary.contains(temp)){
                count++;
            }
        }
        /*
        //Print Block
        System.out.println("countWords print block : number of matches : " + count);
        // */
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary, char mostCommon){
        
        int maxCount = 0;
        String decrypted = "";
        int len = 0;
        int bestCount = 0;
        for(int i=1;i<=100;i++){
        
            int[] key = tryKeyLength(encrypted,i,mostCommon);
            VigenereCipher vc = new VigenereCipher(key);
            String message = vc.decrypt(encrypted);
            int countOfWords = countWords(message,dictionary);
            if(countOfWords>maxCount){
                maxCount = countOfWords;
                int[] bestKey = key;
                len = bestKey.length;
                decrypted = message;
            }
            
        }
        
        //print block
        System.out.println("Key Length used : " + len);
        System.out.println("Number of Valid Words : " + maxCount);
        System.out.println();
        //*/
        return decrypted;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
        for(String word : dictionary){
            for(int i=0;i<word.length();i++){//create frequency hash map
                char temp = word.charAt(i);
                if(!map.containsKey(temp)){
                    map.put(temp,1);
                }
                else{
                    map.put(temp,map.get(temp)+1);
                }
            }
        }
        
        int max = 0;
        char freqChar = 'e';
        for(Character letter : map.keySet()){//iterate over hash map
            if(map.get(letter)>max){
                max = map.get(letter);
                freqChar = letter;
            }
        }
        return freqChar;
    }
    
    public String breakForAllLangs(String encrypted, HashMap<String,HashSet<String>> languages){
        int max = 0;
        String decrypted = "";
        String decrLang = "";
        for(String lang : languages.keySet()){
            HashSet<String> dictionary = languages.get(lang);
            char mostCommon = mostCommonCharIn(dictionary);
            String intermediate = breakForLanguage(encrypted,dictionary,mostCommon);
            int countGoodWords = countWords(intermediate,dictionary);
            if(countGoodWords>max){
                max = countGoodWords;
                decrypted = intermediate;
                decrLang = lang;
            }
        }
        
        //Print Block
        System.out.println();
        System.out.println("breakForAllLangs print block : Decryption Language : " + decrLang);
        System.out.println("breakForAllLangs print block : Word Match count    : " + max);
        System.out.println();
        //System.out.println("Decrypted Message : " + decrypted.substring(0,300));
        //very large strings exceed the scope of bluej output window,
        //result would appear to be incomplete,
        //print only the initial lines to counter that.
        // */
        return decrypted;
    }
   
    
    public void testSliceString(){
        System.out.println(sliceString("abcdefghijklm", 0, 3));
        System.out.println(sliceString("abcdefghijklm", 1, 3));
        System.out.println(sliceString("abcdefghijklm", 2, 3));
        System.out.println(sliceString("abcdefghijklm", 0, 4));
    }
    
    public void TestTryKeyLength(){
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        int[] key = tryKeyLength(encrypted,4,'e');
        
    }
}
