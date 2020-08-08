import edu.duke.*;
import org.apache.commons.csv.*;
/**
 * Write a description of csvcountries here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class csvcountries {
    public void listExporters(CSVParser csv, String s){
        for (CSVRecord record : csv){
            String export = record.get("Exports");
            if(export.indexOf(s)!=-1){
                System.out.println("Country that exports " + s + "is :" + record.get("Country"));
            }
        }
        
    }
    
    public int numberOfExporters(CSVParser csv, String item){
        int count = 0;
        for(CSVRecord record : csv){
            String export = record.get("Exports");
            if(export.indexOf(item)!=-1){
                count++;
            }
        }
        return count;
    }
    
    
    public void listExportersTwoProducts(CSVParser csv, String item1, String item2){
        for (CSVRecord record : csv){
            String products = record.get("Exports");
            if(products.indexOf(item1)!=-1 && products.indexOf(item2)!=-1){
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public String countryInfo(CSVParser csv, String s){
        String fin = "";
        String country = "";
        for (CSVRecord record : csv){
            country = record.get("Country");
            //System.out.println(country);
            if(country.compareTo(s)==0){
                //System.out.println("Loop Entered");
                String exports = record.get("Exports");
                String value = record.get("Value (dollars)");
                fin = country + ": " + exports + " : " + value;
                return fin;
                //System.out.println(fin);
            }
        }
        return "NOT FOUND";
    
    }
    
    public void bigExporters(CSVParser csv, String price){
        for(CSVRecord record : csv){
            String value = record.get("Value (dollars)");
            if(value.length()>price.length()){
                System.out.println(record.get("Country") + " " + value);
            }
            
        }
    }
    
    public void testFunctions(){
        FileResource fr = new FileResource();
        CSVParser csv = fr.getCSVParser();
        
        //listExporters(csv,"coffee");
        
        //System.out.println(countryInfo(csv,"Germany"));
        //System.out.println(countryInfo(csv,"Nauru"));
        
        //listExportersTwoProducts(csv,"fish","nuts");
        
        //System.out.println(numberOfExporters(csv,"sugar"));
        
        //bigExporters(csv, "$999,999,999,999");
    }

}
