import edu.duke.*;
import java.io.*;
import java.lang.*;
import java.util.*;


public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique(){
        myWords.clear();
        myFreqs.clear();
        
        FileResource fr = new FileResource();
        for(String word : fr.words()){
            word = word.toLowerCase();
            int index = myWords.indexOf(word);
            if(index==-1){
                myWords.add(word);
                myFreqs.add(1);
            }
            else{
                int value = myFreqs.get(index);
                myFreqs.set(index,value+1);
            }
            
            
        }
    }
    
    public void tester(){
        findUnique();
        //for(int i=0;i<myWords.size();i++){
        //    System.out.print(myWords.get(i) + "  ");
        //    System.out.println(myFreqs.get(i));
        //}
        System.out.println("Unique words found : " + myWords.size());
        System.out.println("Highest frequency at Index : " + findIndexOfMax() + " At word : " + myWords.get(findIndexOfMax()) + " with freq : " + myFreqs.get(findIndexOfMax()));
    }
    
    public int findIndexOfMax(){
        int maxF = 0;
        int maxI = -1;
        int temp = 0;
        for(int i=0;i<myFreqs.size();i++){
            temp = myFreqs.get(i);
            if(temp>maxF){
                maxF = temp;
                maxI = i;
            }
        }
        return maxI;
    }
    
    
}
