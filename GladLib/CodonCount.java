import edu.duke.*;
import java.util.*;


public class CodonCount {

    private HashMap<String,Integer> map;
    
    public CodonCount(){
        map = new HashMap<String,Integer>();
    }
    
    private void buildCodonMap(int start,String input){
        //build a new map of codons mapped to their counts from the string dna
        //make sure map is empty before this process starts
        map.clear();
        int count=0;
        String dna = input.toUpperCase();
        for(int i=start;i<dna.length()-2;i=i+3){
            String codon = dna.substring(i,i+3);
            if(!map.containsKey(codon)){
                map.put(codon,1);
                count++;
            }
            else{
                map.put(codon,map.get(codon)+1);
            }
        }
        
        System.out.println("Reading  frame starting at "+start+" with "+count+" unique codons.");
    }
    
    public String getMostCommonCodon(){
        if(map==null){
            return "Map not generated yet";
        }
        int max = 0;
        String best = "";
        for(String s : map.keySet()){
            if(map.get(s)>max){
                best = s;
                max = map.get(s);
            }
        }
        
        return best;
    }
    
    public void printCodonCounts(int start, int end){
        //print all the codon in the HashMap along with counts with counts b/w start and end
        for(String s : map.keySet()){
            if(map.get(s)>=start&&map.get(s)<=end){
                System.out.println(s + "  " + map.get(s));
            }
        }
    }
    
    public int printUniqueCodons(){
        if(map==null){return -1;}
        else{return map.size();}
    }
    
    public void tester(){
        FileResource fr = new FileResource();
        String temp = fr.asString();
        String dna = temp.trim();
        
        String common = "";
        
        buildCodonMap(0,dna);
        System.out.println("No. of Unique Codons : " + printUniqueCodons());
        common = getMostCommonCodon();
        System.out.println("Common codon : " + common + " with count " + map.get(common));
        printCodonCounts(7,7);
      
    }
    
}
