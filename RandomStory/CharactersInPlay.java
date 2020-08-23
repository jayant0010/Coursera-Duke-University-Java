import edu.duke.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class CharactersInPlay {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public CharactersInPlay() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    private void update(String person){
        int index = myWords.indexOf(person);
        if(index==-1){
            myWords.add(person);
            myFreqs.add(1);
        }
        else{
            int value = myFreqs.get(index);
            myFreqs.set(index,value+1);
        }
    }
    
    public void findAllCharacters(){
        FileResource fr = new FileResource();
        myWords.clear();
        myFreqs.clear();
        for(String line : fr.lines()){
            int index = line.indexOf('.');
            String person = line.substring(0,index+1);
            update(person);
        }
        
        
    }
    
    public void printCharacters(int limit){
        System.out.println("printCharacters");
        for(int i=0;i<myWords.size();i++){
            int value = myFreqs.get(i);
            if(value>limit){
                System.out.println("Character : " + myWords.get(i) + "   :   " + myFreqs.get(i));
            }
        }
    }
    
    public void charactersWithNumParts(int num1,int num2){
        System.out.println("charactersWithNumParts");
        for(int i=0;i<myWords.size();i++){
            int value = myFreqs.get(i);
            if((num1<=value)&&(value<=num2)){
                System.out.println("Character : " + myWords.get(i) + "   :   " + myFreqs.get(i));
            }
        }
    }
    
    public void tester(){
        findAllCharacters();
        printCharacters(1);
        charactersWithNumParts(10,15);
    }
 }
