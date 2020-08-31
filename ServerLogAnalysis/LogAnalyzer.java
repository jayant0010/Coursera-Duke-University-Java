import java.util.*;
import edu.duke.*;

//documentation : ReadingLogFiles.pdf 
//                FindingUniqueIpAddresses.pdf
//                CountingWebsiteVisits.pdf
public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for(String line : fr.lines()){
                records.add(WebLogParser.parseEntry(line));
            }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs(){
         if(records==null){return -1;}
         ArrayList<String> temp = new ArrayList<String>();
         for (LogEntry le : records){
                String ip = le.getIpAddress();
                if(!temp.contains(ip)){
                    temp.add(ip);
                }
            }
         return temp.size();
     }
     
     public void printAllHigherThanNum(int num){
         if(records==null){System.out.println("Records not generated.");}
         for(LogEntry le : records){
                if(le.getStatusCode()>num){
                    System.out.println(le); //just mentioning 'le' calls on the .toString() method by default
                }
            }
         
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String day){
        //date format is "MMM DD"
        if(records==null){return null;}
        ArrayList<String> list = new ArrayList<String>();
        for(LogEntry le : records){
            String temp = le.getAccessTime().toString();
            //System.out.println(temp);
            String date = temp.substring(4,10);
            //System.out.println(date);
            if(day.equals(date)){
                String ip = le.getIpAddress();
                if(!list.contains(ip)){
                    list.add(ip);
                }
            }
        }
        return list;
     }
     public int countUniqueIPsInRange(int low,int high){
         if(records==null){return -1;}
         if(low>high){return -1;}
         
         ArrayList<String> list = new ArrayList<String>();
         for(LogEntry le : records){
             int statusCode = le.getStatusCode();
             if(statusCode>=low && statusCode<=high){
                String ip = le.getIpAddress();
                if(!list.contains(ip)){
                    list.add(ip);
                }
             }
         }
         /*
         //Print Block
         System.out.println("countUniqueIPsInRange : " + low + " to " + high);
         for(int i=0;i<list.size();i++){
             System.out.println(list.get(i));
         }
         */
         return list.size();
     }
     
     public HashMap<String,Integer> countVisitsPerIP(){
         if(records==null){return null;}
         HashMap<String,Integer> map = new HashMap<String,Integer>();
         for(LogEntry le : records){
             String ip = le.getIpAddress();
             if(!map.containsKey(ip)){
                 map.put(ip,1);
             }
             else{
                 map.put(ip,map.get(ip)+1);
             }
         }
         return map;
     }
     
     
     public int mostNumberVisitsByIP(HashMap<String,Integer> map){
         int max = 0;
         String maxIp = ""; //because why not
         for(String ip : map.keySet()){
             if(map.get(ip)>max){
                 max = map.get(ip);
                 maxIp = ip;
             }
         }
         System.out.println("mostNumberVisitsByIP in method print block: maxIP : " + maxIp + " with " + max + " Visits.");
         return max;   
     }
     
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> map){
         if(map==null){return null;}
         ArrayList<String> list = new ArrayList<String>();
         int max = mostNumberVisitsByIP(map);
         for(String ip : map.keySet()){
             int key = map.get(ip);
             if(key==max){
                 list.add(ip);
             }
         }
         
         System.out.println("iPsMostVisits list has " + list.size() + " IPs ");
         
         //print Block
         for(String s : list){
             System.out.println(s);
         }
         
         return list;
     }
     
     public HashMap<String,ArrayList<String>> iPsForDays(){
         if(records==null){return null;}
         HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();
         for(LogEntry le : records){
             String temp = le.getAccessTime().toString();
            //System.out.println(temp);
            String date = temp.substring(4,10);
            //System.out.println(date);
            String ip = le.getIpAddress();
            if(!map.containsKey(date)){
                ArrayList<String> list1 = new ArrayList<String>();
                list1.add(ip);
                map.put(date,list1);
            }
            else{
                ArrayList<String> list2 = map.get(date);
                list2.add(ip);
                map.put(date,list2);
            }
         }
         
         //print block
         for(String s : map.keySet()){
             
             ArrayList<String> temp = map.get(s);
             System.out.println(s + " with " + temp.size() + " IPs listed under this date ");
             
             for(String t : temp){
                 System.out.println(t);
             }
             
         }
         
         return map;
     }
     
     public String dayWithMostIPVisits(HashMap<String,ArrayList<String>> map){
         if(map==null){return "Empty Map Passed.";}
         String maxDate = "";
         int max = 0;
         for(String s : map.keySet()){
             ArrayList<String> list = map.get(s);
             if(list.size()>max){
                 max = list.size();
                 maxDate = s;
             }
         }
         
         //Print Block
         System.out.println("Print block for dayWithMostIPVisits : " + maxDate + " with " + max + " Visits.");
         
         return maxDate;
     }
     
     public ArrayList<String> iPsWithMostvisitsOnDay(HashMap<String,ArrayList<String>> map, String day){
         
         ArrayList<String> ipList = map.get(day);
         HashMap<String,Integer> ipMap = new HashMap<String,Integer>();
         for(String ip : ipList){
             if(!ipMap.containsKey(ip)){
                 ipMap.put(ip,1);
             }
             else{
                 ipMap.put(ip,ipMap.get(ip)+1);
             }
         }
         
         ArrayList<String> listMax = iPsMostVisits(ipMap);
         
         //Print Block
         System.out.println("Print block for iPsWithMostvisitsOnDay. iPsMostVisits() method was called.");
         for(String s : listMax){
             System.out.println(s);
         }
         return listMax;
     }

}
