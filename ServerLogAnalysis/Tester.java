import java.util.*;

//documentation : ReadingLogFiles.pdf
//                FindingUniqueIpAddresses.pdf
public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
    }
    
    public void testUniqueIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        System.out.println("Unique IPs in this file : " + la.countUniqueIPs());
    }
    
    public void testPrintAllHigherThanNum(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println("result of Print All Higher Than Num : ");
        la.printAllHigherThanNum(400);
    }
    
    public void testUniqueIPVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        ArrayList<String> list = la.uniqueIPVisitsOnDay("Mar 17");
        System.out.println(list.size());
    }
    
    public void testCountUniqueIPsInRange(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        int num = la.countUniqueIPsInRange(300,399);
        System.out.println("testCountUniqueIPsInRange : "+ num);
    }
    
    public void TestIPsMostVisits(){//houses test cases for all methods after iPsMostVisits
        //all print blocks are inside the method declaration.
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String,Integer> map = la.countVisitsPerIP();
        int mostNumberVisits = la.mostNumberVisitsByIP(map);
        ArrayList<String> list = la.iPsMostVisits(map);
        HashMap<String,ArrayList<String>> map2 = la.iPsForDays();
        String maxDate = la.dayWithMostIPVisits(map2);
        ArrayList<String> list2 = la.iPsWithMostvisitsOnDay(map2,"Mar 17");
    }
}
