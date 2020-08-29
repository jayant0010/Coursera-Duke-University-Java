import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles {
    //a program to determine which words occur in the greatest number of files,
    //and for each word, which files they occur in.
    private HashMap<String,ArrayList<String>> map;
    
    public WordsInFiles(){
        map = new HashMap<String,ArrayList<String>>();
    }
    
    private void addWordsFromFile(File f){
        FileResource fr = new FileResource(f);
        for(String word : fr.words()){
            //does key exis?
            if(!map.containsKey(word)){//if no, create key, create list, add new updated list
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(f.getName());
                map.put(word,temp);  
            }
            else{//if yes, get list, update list, add new updated list
                ArrayList<String> list = map.get(word);
                String fileName = f.getName();
                if(!list.contains(fileName)){
                    list.add(fileName);
                }
                map.put(word,list);
            }
        }
        
        
    }
    
    private void buildWordFileMap(){
        map.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
            System.out.println(f.getName() + " Processed.");
        }
        
    }
    
    public int maxNumber(){
        if(map==null){return -1;}//map not generated yet
        int maxSize = 0;
        String maxWord = "";
        for(String s : map.keySet()){
            ArrayList<String> list = map.get(s);
            int size = list.size();
            if(size>maxSize){
                maxWord = s;
                maxSize = size;
            }
            
        }
        System.out.println("Word that appears in max files is " + maxWord + " with frequency " + maxSize);
        return maxSize;
    }
    
    public ArrayList<String> wordsInNumFiles(int num){
        if(map==null){return null;}
        ArrayList<String> list = new ArrayList<String>();
        for(String s : map.keySet()){
            int size = map.get(s).size();
            if(size==num){
                list.add(s);
            }
        }
        return list;
    }
    
    public void printFilesIn(String word){
        if(map==null){System.out.println("Map not generated.");}
        ArrayList<String> list = map.get(word);
        if(list==null){System.out.println("Word does not exist in map.");}
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
    
    public void tester(){
        buildWordFileMap();
        int temp = maxNumber();
        ArrayList<String> list = wordsInNumFiles(4);
        System.out.println(list.size());
        //for(int i=0;i<list.size();i++){
        //    System.out.println(list.get(i));
        //}
        printFilesIn("tree");
    }
    
}
