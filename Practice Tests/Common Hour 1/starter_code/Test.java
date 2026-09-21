import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Test{
    public static void main(String[] args){
        Animal[] animals = new Animal[30];
        int count = readAnimals(animals, "animals.txt");
        if(count == 0){
            System.out.println("No data read from the file animals.txt");
        }
        else{
            System.out.println("File animals.txt read successfully. " + count + " animals read from the file");
        }
        // cloning some animals
        animals[count++] = cloneAnimal(animals, count);
        animals[count++] = cloneAnimal(animals, count);
        animals[count++] = cloneAnimal(animals, count);
        animals[count++] = cloneAnimal(animals, count);
        
        // Display the list of animals
        System.out.println("List of Animals");
        System.out.printf("%-15s\t%-10s\t%-20s\t%10s\n", "Type", "Tag", "Name", "Weight");
        for(int i=0; i<count; i++){
            System.out.println(animals[i]);
        }

        // Display the animals that can fly
        System.out.println("\nList of Flying Animals");
        printFlying(animals, count);
       
        // Display the list of animals sorted by weight
        System.out.println("\nList of Animals sorted by weight");
        sortAnimals(animals, count);
       

        // find an animal with a specific tag
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a tag:");
        String tag = keyboard.next();
        if(checkTag(tag)){
            int index = findAnimal(animals, count, tag);
            if(index == -1){
                System.out.println("No animal found with tag: " + tag);
            }
            else{
                System.out.println("Animal found: " + animals[index]);
            }
        }
        keyboard.close();
        
    }
    /**
     * Search method
     * @param list the list of animal to search in
     * @param size the actual number of animals in the array list
     * @param tag the tag the method is looking for
     * @return the index of the animal with the given tag if found, -1 otherwise
     */
    public static int findAnimal(Animal[] list, int size, String tag){
        for(int i=0; i<size; i++){
            if(list[i].getTag().equals(tag)){
                return i;
            }
        }
        return -1;
    }
    /**
     * Method to check if a tag is valid
     * @param tag the tag being checked
     * @return true if the tag matches the regex "[A-Z]{2}-\\d{4}", false otherwise
     * if the tag does not match the regex, throw Exception, handle the exception, and return false
     */
    public static boolean checkTag(String tag) {
        try {
            if (tag.matches("[A-Z]{2}-\\d{4}")) {return true;}
            else {throw new Exception("Invalid TAG: " + tag + ". Must have 2 upper-case letters followed by 4 digits");}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * reads the attributes of the animal from filename, 
     * creates animal concrete objects, and 
     * store them in the array list
     * @param list the list where the constructed animals will be stored
     * @param filename the filename of the file to read from
     * @return the number of animals read and stored in list
     */
    public static int readAnimals(Animal[] list, String filename){
        int number_of_animals = 0;
        try {
            Scanner scnr = new Scanner(new File(filename));
            // if (scnr.hasNextLine()) scnr.nextLine();
            while (number_of_animals < list.length && scnr.hasNextLine()) {
                String line = scnr.nextLine().trim();
                if (line.isEmpty()) continue;

                // string, string, double, int, int
                String[] attributes = line.split(":");
                if (attributes.length < 6) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }
                String type = attributes[0];

                String[] possibleClasses = {"Bat","Bear","Blue Jay","Humming Bird"};
                Boolean validType = false;
                for (String s : possibleClasses) {
                    if (s.equals(type)) {
                        validType = true;
                        break;
                    }
                }

                if (! validType) {
                    System.out.println("Invalid type of account: " + type
                            + ", should be {\"Bat\",\"Bear\",\"Blue Jay\",\"Humming Bird\"}}");
                    continue;
                }

                String tag = attributes[1];
                if (! checkTag(tag)) {
                    continue;
                }


                double w;
                try {
                    w = Double.parseDouble(attributes[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for the weight \"" + attributes[3]
                            + "\", must be a double");
                    continue;
                }

                int inc;
                try {
                    inc = Integer.parseInt(attributes[4]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for the incubation period \"" + attributes[4]
                            + "\", must be a Integer");
                    continue;
                }

                int fs;

                try {
                    fs = Integer.parseInt(attributes[5]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for the flying speed\\inc period \"" + attributes[5]
                            + "\", must be a Integer");
                    continue;
                }

                try {
                    Animal a;
                    // String[] possibleClasses = {"Bat","Bear","Blue Jay","Humming Bird"};

                    if (type.equals("Bat")) {
                        a = new Bat(tag,attributes[2], w,inc,fs);
                    }
                    else if (type.equals("Bear")) {
                        a = new Bear(tag,attributes[2], w,inc,fs);
                    }
                    else if (type.equals("Blue Jay")) {
                        a = new BlueJay(tag,attributes[2], w,inc,fs);
                    }
                    else {
                        a = new HummingBird(tag,attributes[2], w,inc,fs);
                    }

                    list[number_of_animals] = a;
                    number_of_animals++;
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }


            scnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + filename);
        }
        return number_of_animals;
    }

    /**
     * prints the value returned by flies() for each animal of type CanFly
     * @param list the list of all the animals
     * @param size the actual number of animals in the array list
     */
    public static void printFlyingAnimals(Animal[] list, int size) {
        for (int i = 0; i < size; i++) {
            if (list[i] instanceof CanFly) {
                System.out.println(((CanFly) list[i]).flies());
            }
        }
    }

    /**
     * prints the list of animals sorted by weight
     * @param list the list of animals to be sorted
     * @param size the actual number of animals in the array list
     */
    public static void sortAnimals(Animal[] list, int size){
        System.out.printf("%-15s\t%-10s\t%-20s\t%10s\n", "Type", "Tag", "Name", "Weight");
        java.util.Arrays.sort(list, 0, size);
        for(int i=0; i<size; i++){
            System.out.println(list[i]);
        }
    }
    /**
     * prints only the animals that can fly
     * @param list the list of all the animals
     * @param size the actual number of animals in the array list
     */
    public static void printFlying(Animal[] list, int size){
        System.out.printf("%-15s\t%-20s\t%s\n", "Type", "Name", "Flying Speed (mph)");
        printFlyingAnimals(list, size);
    }
    /**
     * Method to clone 4 randomly selected animals and change some of their attributes
     * @param list the list from where animals will be selected for cloning
     * @param size the actual number of animals in the array list
     * @return the cloned animal
     */
    public static Animal cloneAnimal(Animal[] list, int size){
        int index = (int)(Math.random() * (size-1));
        Animal a = null;
        String newTag = list[index].getTag().substring(0,2) + "-" + (int)(Math.random() * 9999 + 1111);
        if(list[index] instanceof BlueJay){
            a = (BlueJay) (list[index].clone());
            ((BlueJay)a).setFlyingSpeed(((Bird)a).getFlyingSpeed() * 2);
            a.setTag(newTag);
        }
        else if(list[index] instanceof Bat){
            a = (Bat) (list[index].clone());
            a.setName("Natalidae");
            a.setTag(newTag);
        }
        else if(list[index] instanceof Bear){
            a = (Bear) (list[index].clone());
            ((Bear)a).setHibernation(6);
            a.setTag(newTag);
        }
        else{
            a = (Animal) (list[index].clone());
            a.setWeight(a.getWeight() + 2);
            a.setTag(newTag);
        }
        return a;
    }
}