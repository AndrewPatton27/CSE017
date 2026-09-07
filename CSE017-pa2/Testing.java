import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
public class Testing{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static void main(String[] args){
        int tests = Integer.parseInt(args[0]);
        String refFile = args[1];
        String outFile = args[2];
        File outRef = new File(refFile);
        File out = new File(outFile);
        ArrayList<Entry<String,ArrayList<String>>> testsRef = new ArrayList<>();
        ArrayList<Entry<String,ArrayList<String>>> testsOut = new ArrayList<>();
        readTests(testsRef, refFile);
        readTests(testsOut, outFile);
        int count = 0;
        for(int i=0; i<tests; i++){
            Entry<String,ArrayList<String>> refPair = testsRef.get(i);
            Entry<String, ArrayList<String>> outPair = testsOut.get(i);
            ArrayList<String> refTest = refPair.getValue();
            ArrayList<String> outTest = outPair.getValue();
            
            if(compare(refTest, outTest)){ // test passed
                System.out.printf("\n%-100s\t%s[OK]%s\n", refPair.getKey(), ANSI_GREEN, ANSI_RESET);
                for(int j=0; j<120; j++)
                    System.out.printf("=");
                System.out.println();
                count++;
            }
            else{ // test not passed
                System.out.printf("\n%-100s\t%s[Fail]%s\n", outPair.getKey(),ANSI_RED, ANSI_RESET);
                for(int j=0; j<120; j++)
                    System.out.printf("=");
                System.out.println();
                System.out.printf("Expected ouput\n--------------\n");
                for(String s:refTest){
                    System.out.println(s);
                }
                System.out.printf("Actual output\n--------------\n");
                for(String s:outTest){
                    System.out.println(s);
                }
            }
        }
        if(count == tests){
            System.out.printf("%s\nAll %d tests passed.%s\n", ANSI_GREEN, tests, ANSI_RESET);
        }
        else{
            System.out.printf("%s\n%d tests passed and %d failed%s\n", ANSI_PURPLE, count,(tests-count), ANSI_RESET);
        }
        
    }
    public static boolean compare(ArrayList<String> ref, ArrayList<String> out){
        if(ref.size() != out.size()){
            return false;
        }
        for(int i=0; i<ref.size(); i++){
            String[] refItems = ref.get(i).split("\\s+");
            String[] outItems = out.get(i).split("\\s+");
            if (refItems.length != outItems.length){
                return false;
            }
            for(int j=0; j<refItems.length; j++){
                if(!refItems[j].trim().equals(outItems[j].trim())){
                    return false;
                }
            }
        }
        return true;
    }
    public static void readTests(ArrayList<Entry<String,ArrayList<String>>> tests, String filename){
        try{
            Scanner read = new Scanner(new File(filename));
            int index = 0;
            ArrayList<String> test = new ArrayList<>();
            String oldLine = "";
            while (read.hasNextLine()){
                String line = read.nextLine();
                if(line.isBlank()){
                    continue;
                }
                line = line.trim();
                if(line.startsWith("Test case")){
                    if(oldLine == "")
                        oldLine = line;
                    if(test.size() != 0){
                        tests.add(new Entry<String,ArrayList<String>>(oldLine, test));
                        test = new ArrayList<>();
                        oldLine = line;
                    }
                }
                else{
                    //if(!line.isEmpty()){
                    test.add(line);
                   // }
                }
            }
            if(test.size()!= 0){
                tests.add(new Entry<String,ArrayList<String>>(oldLine, test));
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File " + filename + " not found.");
            System.exit(0);
        }
    }
}
class Entry<K, V>{
    private K key;
    private V value;
    public Entry(K key, V value){
        this.key = key;
        this.value = value;
    }
    public K getKey(){ return key;}
    public V getValue(){ return value;}

    public void setKey(K k) { key = k;}
    public void setValue(V v){ value = v;}

    public String toString(){
        return "(" + key.toString() + ", " + value.toString() + ")";
    }
}
