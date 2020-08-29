import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String,ArrayList<String>> myMap;
    private ArrayList<String> usedList;
    private int countConsidered;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "datalong";//directory pointer
    
    public GladLibMap(){
        initializeFromSource(dataSourceDirectory);
        usedList = new ArrayList<String>();
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
private void initializeFromSource(String source) {
        //System.out.println("Source : "+source);
        ArrayList<String> adjectiveList= readIt(source+"/adjective.txt");
        ArrayList<String> nounList = readIt(source+"/noun.txt");
        ArrayList<String> colorList = readIt(source+"/color.txt");
        ArrayList<String> countryList = readIt(source+"/country.txt");
        ArrayList<String> nameList = readIt(source+"/name.txt");      
        ArrayList<String> animalList = readIt(source+"/animal.txt");
        ArrayList<String> timeList = readIt(source+"/timeframe.txt");
        ArrayList<String> verbList = readIt(source+"/verb.txt");
        ArrayList<String> fruitList = readIt(source+"/fruit.txt");
        
        myMap = new HashMap<String,ArrayList<String>>();
        myMap.put("adjective",adjectiveList);
        myMap.put("noun",nounList);
        myMap.put("color",colorList);
        myMap.put("country",countryList);
        myMap.put("name",nameList);
        myMap.put("animal",animalList);
        myMap.put("time",timeList);
        myMap.put("verb",verbList);
        myMap.put("fruit",fruitList);
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("country")) {
            return randomFrom(myMap.get("country"));
        }
        if (label.equals("color")){
            return randomFrom(myMap.get("color"));
        }
        if (label.equals("noun")){
            return randomFrom(myMap.get("noun"));
        }
        if (label.equals("name")){
            return randomFrom(myMap.get("name"));
        }
        if (label.equals("adjective")){
            return randomFrom(myMap.get("adjective"));
        }
        if (label.equals("animal")){
            return randomFrom(myMap.get("animal"));
        }
        if (label.equals("timeframe")){
            return randomFrom(myMap.get("time"));
        }
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (label.equals("verb")){
            return randomFrom(myMap.get("verb"));
        }
        if (label.equals("fruit")){
            return randomFrom(myMap.get("fruit"));
        }
        return "**UNKNOWN**";
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        int idx = usedList.indexOf(sub);
        while(idx!=-1){//check for first occurence, only print if first
            sub = getSubstitute(w.substring(first+1,last));
            idx = usedList.indexOf(sub);
        }
        usedList.add(sub);
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
                countConsidered++;
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        //System.out.println(source);
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public int totalWordsInMap(){
        if(myMap==null){return -1;}
        int count = 0;
        for(String s : myMap.keySet()){
            count = count + myMap.get(s).size();
        }
        return count;
    }
    
    public int totalWordsConsidered(){
        return countConsidered;
    }
    
    public void makeStory(){
        System.out.println("\n");
        usedList.clear();
        countConsidered = 0;
        
        int totalWords = totalWordsInMap();
        System.out.println("Total Words to choose from : " + totalWords);
        
        String story = fromTemplate("data/madtemplate.txt");
        printOut(story,60);
        
        System.out.println("Total words considered : " + totalWordsConsidered());
    }
    


}
