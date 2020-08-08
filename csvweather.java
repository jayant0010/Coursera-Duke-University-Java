import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;


//all modules used in the 'Weather' set of examples from week 3.
//data is not pre loaded
//Data can be found at https://www.dukelearntoprogram.com//course2/files.php through the "All Example Country Export CSV Data" link.
//test functions for fundamental modules have been clubbed under the testFunctions() modules.

public class csvweather {

    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestHour = null;
        double coldTemp;
        double temp;
        for(CSVRecord record : parser){
            temp = Double.parseDouble(record.get("TemperatureF"));
            if(temp!=-9999.0){
                if(coldestHour==null){
                    coldestHour=record;
                }
                else{
                    coldTemp = Double.parseDouble(coldestHour.get("TemperatureF"));
                    if(temp<coldTemp){
                        coldestHour=record;
                    }
                    
                }
                    
            }
            
            
        }
        
        return coldestHour;
        
    }
    
    public String fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource();
        File t = null;
        CSVRecord temp = null;
        for(File f : dr.selectedFiles()){
            //System.out.println(f);
            FileResource fr = new FileResource(f);
            CSVParser csv = fr.getCSVParser();
            CSVRecord hour = coldestHourInFile(csv);
            if(temp==null){
                temp = hour;
            }
            
            if(Double.parseDouble(hour.get("TemperatureF"))<Double.parseDouble(temp.get("TemperatureF"))){
                temp = hour;
                t = f;
            }
     
        }
        
        return t.getName();
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord driestHour = null;
        double humidTemp;
        double temp;
        for(CSVRecord record : parser){
            String strTemp = record.get("Humidity");
            
            if(isNumeric(strTemp)){
                temp = Double.parseDouble(record.get("Humidity"));
                if(driestHour==null){
                    driestHour=record;
                }
                else{
                    humidTemp = Double.parseDouble(driestHour.get("Humidity"));
                    if(temp<humidTemp){
                        driestHour=record;
                    }
                    
                }
                    
            }
            
            
        }
        
        return driestHour;
        
    }
    
    
    public CSVRecord lowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource();
        File t = null;
        CSVRecord temp = null;
        for(File f : dr.selectedFiles()){
            //System.out.println(f);
            FileResource fr = new FileResource(f);
            CSVParser csv = fr.getCSVParser();
            CSVRecord humidityRec = lowestHumidityInFile(csv);
            if(temp==null){
                temp = humidityRec;
            }
            
            if(Double.parseDouble(humidityRec.get("Humidity"))<Double.parseDouble(temp.get("Humidity"))){
                temp = humidityRec;
                t = f;
            }
     
        }
        System.out.println("Lowest Humidity in many files : " + t);
        return temp;
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        CSVRecord hour = null;
        double temp = 0;
        double tempt = 0;
        double count = 0;
        for(CSVRecord record : parser){
            temp = Double.parseDouble(record.get("TemperatureF"));
            count++;//move count inside the if -9999 loop if the instances of invalid(-9999) temperatures is to be ignored
            if(temp!=-9999.0){
                tempt = temp + tempt;
            }
            
            
        }
        tempt = tempt/count;
        return tempt;
        
    }
    
    public Double averageTemperatureWithHighHumidityInFile(CSVParser parser, Integer value){
        CSVRecord hour = null;
        double temp = 0;
        double tempt = 0;
        double count = 0;
        double humid = 0;
        double realValue = value;
        String strHumid = "";
        for(CSVRecord record : parser){
            temp = Double.parseDouble(record.get("TemperatureF"));
            strHumid = record.get("Humidity");
            
            if(isNumeric(strHumid)&&(Double.parseDouble(strHumid)>=realValue)){
                count++;//move count inside the if -9999 loop if the instances of invalid(-9999) temperatures is to be ignored
                if(temp!=-9999.0){
                    tempt = temp + tempt;
                }
            }
            
            
        }
        tempt = tempt/count;
        return tempt;
        
    }
    
    public void testLowestHumidityInManyFiles(){
        CSVRecord res = lowestHumidityInManyFiles();
        System.out.println("Lowest humidity was : " + res.get("Humidity") + " at : " + res.get("DateUTC"));
    }
    
    public void testFileWithColdestTemperature(){
        String str = fileWithColdestTemperature();
        System.out.println("File with Coldest Temp : " + str);
    }
    
    public void testFunctions(){
    
        System.out.println("Testing Block");
        FileResource fr = new FileResource();
        CSVParser csv = fr.getCSVParser();
        
        //CSVRecord hour = coldestHourInFile(csv);
        //System.out.println(hour.get("TemperatureF"));
        
        //CSVRecord humid = lowestHumidityInFile(csv);
        //System.out.println("Lowest Humidity :" + humid.get("Humidity") + " on instance : " + humid.get("DateUTC"));
        
        //Double avg = averageTemperatureInFile(csv);
        //System.out.println("Average Temperature : " + avg);
        
        //int value = 80;
        //Double avg = averageTemperatureWithHighHumidityInFile(csv,value);
        //System.out.println("Average Temperature is " + avg + " at least Humidity : " + value);

    }
    
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
}
}
