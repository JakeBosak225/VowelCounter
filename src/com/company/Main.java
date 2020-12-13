package com.company;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        MainMenu();
    }

    public static void MainMenu() throws IOException {
        //Scanner for keyboard input
        Scanner userInputScanner = new Scanner(System.in);

        System.out.println("**********************************");
        System.out.println("**** Welcome To Vowel Counter ****");
        System.out.println("**********************************");

        while (true) {
            System.out.println("");
            System.out.println("Please make a selection: ");
            System.out.println("1. Run Vowel Counter");
            System.out.println("2. View The Log In The Console");
            System.out.println("3. Exit");

            int menuSelection = 0;
            try {
                String userInput = userInputScanner.nextLine();
                menuSelection = Integer.parseInt(userInput);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number input to continue...");
                System.out.println("");
                continue;
            }

            switch (menuSelection) {
                case 1:
                    vowelCounterSetUp();
                    break;

                case 2:
                    viewLog();
                    break;

                case 3:
                    exitApp();
                    break;

                default:
                    System.out.println("Please make valid selection");
            }
        }
    }

    public static void vowelCounterSetUp() throws IOException {
        //Scanner for keyboard input
        Scanner userInputScanner = new Scanner(System.in);
        boolean includeY = false;

        System.out.println("Before we start we need to set up a few preferences....");
        System.out.println("");
        System.out.println("Would you like to include the letter 'Y' as a vowel?");
        System.out.print("Enter 'Y' -OR- press ENTER for no.... ");
        String userInputForUseY = userInputScanner.nextLine();

        //Check if the userInput was Y to include Y in the vowel counter
        if (userInputForUseY.equalsIgnoreCase("Y")) {
            includeY = true;
        }
        System.out.println("");
        System.out.println("Would you like to use the default file or enter in your own?");
        System.out.print("Enter 'Y' to use your own -OR- press ENTER to use the default...");
        String userInputForFileSelection = userInputScanner.nextLine();
        System.out.println("");

        String filePath;
        //Check if userInput was Y to use custom File, if not use default passage file
        if (userInputForFileSelection.equalsIgnoreCase("Y")) {
            System.out.println("Enter your file path");
            filePath = userInputScanner.nextLine();
        } else {
            filePath = "passage.txt";
        }

        File fileToUse = new File(filePath);

        //Check if the file exists or is a valid file, if not loop back to main menu
        if (!fileToUse.exists() || !fileToUse.isFile()) {
            System.out.println("Path Specified is not an existing File, Returning to Main Menu");
            return;
        }

        //Run the vowel counter with the users preferences
        runVowelCounter(includeY, fileToUse);
    }

    public static void runVowelCounter(boolean includeY, File file) throws IOException {
        //Scanner for keyboard input
        Scanner userInputScanner = new Scanner(System.in);

        //Hold each vowel, Key being the Vowel, Value Amount of times is showed up.
        HashMap<Character, Integer> vowels = new HashMap<>();

        //List to hold each line for displaying the
        // last word in the file that contains the most used vowel
        List<String> lines = new ArrayList<>();

        Scanner fileScanner = new Scanner(file);

        //Loop through each line in the file specified
        while (fileScanner.hasNextLine()) {
            //set the current line of the file
            String currentLine = fileScanner.nextLine();

            //Add Current Line to the lines list
            lines.add(currentLine);

            //Loop through each character of the current line
            for (int i = 0; i < currentLine.length(); i++) {
                //Get the Character At index I
                switch (Character.toUpperCase(currentLine.charAt(i))) {

                    //Check for the vowel,
                    //if the vowel is already in the map increase the count by 1
                    //if not add it to the map with a value of 1
                    case 'A':
                        if (vowels.containsKey('A')) {
                            vowels.put('A', vowels.get('A') + 1);
                        } else {
                            vowels.put('A', 1);
                        }
                        break;

                    case 'E':
                        if (vowels.containsKey('E')) {
                            vowels.put('E', vowels.get('E') + 1);
                        } else {
                            vowels.put('E', 1);
                        }
                        break;

                    case 'I':
                        if (vowels.containsKey('I')) {
                            vowels.put('I', vowels.get('I') + 1);
                        } else {
                            vowels.put('I', 1);
                        }
                        break;

                    case 'O':
                        if (vowels.containsKey('O')) {
                            vowels.put('O', vowels.get('O') + 1);
                        } else {
                            vowels.put('O', 1);
                        }
                        break;

                    case 'U':
                        if (vowels.containsKey('U')) {
                            vowels.put('U', vowels.get('U') + 1);
                        } else {
                            vowels.put('U', 1);
                        }
                        break;

                    case 'Y':
                        //Check if the user wanted to include Y before manipulating the map
                        if (includeY) {
                            if (vowels.containsKey('Y')) {
                                vowels.put('Y', vowels.get('Y') + 1);
                            } else {
                                vowels.put('Y', 1);
                            }
                        }
                        break;
                }
            }
        }

        //Sort the map by values in Descending order
        vowels = sortMap(vowels);


        System.out.println("**********************************");
        System.out.println("****         Results:         ****");
        System.out.println("**********************************");

        System.out.println("Vowels by appearance: ");
        //Display each vowel and the number of times it appeared
        for (Map.Entry<Character, Integer> vowel : vowels.entrySet()) {
            System.out.println(vowel.getKey() + " appears " + vowel.getValue() + " times.");
        }

        //Set the line list to reverse order
        Collections.reverse(lines);

        //Get the most used vowel
        char mostUsedVowel = (char) vowels.keySet().toArray()[0];
        String lastWordWithMostUsedVowel = getLastWordWithMostUsedVowel(lines, mostUsedVowel);

        //Print the last word with the most used vowel
        System.out.println("-----------------------------------------------");
        System.out.println("Most common vowel: '" + mostUsedVowel + "'");
        System.out.println(
                "Last word used with most common vowel: " +
                lastWordWithMostUsedVowel);
        System.out.println("-----------------------------------------------");

        System.out.println("Would you like to save these results to the log?");
        System.out.print("Enter 'Y' to do so -OR- press ENTER to not save it...");
        String saveToLogInput = userInputScanner.nextLine();

        //Check if user wants to save to the log, if so run saveToLog Method.
        if(saveToLogInput.equalsIgnoreCase("Y")){
            saveToLog(file, vowels, mostUsedVowel, lastWordWithMostUsedVowel);
        }

        System.out.println("");
        System.out.println("Would you like to count the vowels of another(or the same) file?");
        System.out.print("Enter 'Y to go to the Main Menu -OR- press ENTER to exit...");
        String goAgainInput = userInputScanner.nextLine();
        System.out.println("");

        //Check if user wants to go again, if so go back to MainMenu
        if(goAgainInput.equalsIgnoreCase("Y")){
            return;
        }else {
            exitApp();
        }
    }

    //Method for sorting the map by vowel count in descending order
    public static HashMap<Character, Integer> sortMap(HashMap<Character, Integer> mapToSort) {
        //Set up a new Linked map to store the new order in
        LinkedHashMap<Character, Integer> sortedMap = new LinkedHashMap<>();

        //Get the mapToSorts entry set and use a stream to sort by the values descending
        mapToSort.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(index -> sortedMap.put(index.getKey(), index.getValue()));

        return sortedMap;
    }

    public static String getLastWordWithMostUsedVowel(List<String> lines, char vowel) {
        //Loop through the lines(last to first)
        for (String line : lines) {
            //split line into words by spaces
            String[] words = line.split(" ");

            //Loop through the words array backwards
            for (int i = words.length - 1; i >= 0; i--) {
                //check if current index of word(to upper case) has the vowel
                //by checking if the value of indexOf is not -1
                //will return -1 if vowel is not in word
                if (words[i].toUpperCase().indexOf(vowel) != -1) {
                    //If word has punctuation at the end, remove it and
                    //only return the base word
                    String wordWithoutPunctuation = checkForAndRemovePunctuation(words[i]);

                    //return the last word to use most used vowel
                    return wordWithoutPunctuation;
                }
            }
        }
        //If vowel not found return null
        return null;
    }

    public static String checkForAndRemovePunctuation(String word) {
        String[] punctuation = {".", "!", "?"};
        for (int i = 0; i < punctuation.length - 1; i++) {
            if (word.endsWith(punctuation[i])) {
                return word.substring(0, word.length() - 1);
            }
        }

        return word;
    }

    public static void saveToLog(File fileUsed, HashMap<Character, Integer> vowels, char mostUsedVowel, String topWord) throws IOException {
        //Get the date and time of when this log was taken
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        String formattedDateTime = now.format(formatter);

        File logFile = new File("log.txt");

        //the file exists add to it
        if(!logFile.exists()){
            logFile.createNewFile();
        }

        //Set up a file writer with the logFile and set it able to append
        FileWriter fileWriter = new FileWriter(logFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        //Write the info to the file(log)
        bufferedWriter.write("********************************************** \n");
        bufferedWriter.write("Logged on: " + formattedDateTime + "\n");
        bufferedWriter.write("File Used: " + fileUsed.getName() + "\n");
        bufferedWriter.write("Vowel Count: \n");

        for (Map.Entry<Character, Integer> vowel : vowels.entrySet()) {
            bufferedWriter.write(vowel.getKey() + " appears " + vowel.getValue() + " times.\n");
        }

        bufferedWriter.write("Most common vowel: '" + mostUsedVowel + "'\n");
        bufferedWriter.write(
                "Last word used with most common vowel: " +
                        topWord + "\n");
        bufferedWriter.write("********************************************** \n\n");

        //Close the buffered Writer to save memory
        bufferedWriter.close();
    }

    public static void viewLog() throws FileNotFoundException {
        File logFile = new File("log.txt");

        //Check if the log exists
        if(!logFile.exists()){
            System.out.println("Sorry, no log exists yet, please run the program and save to the log to see results in the console");
            return;
        }

        //Set up the file scanner
        Scanner logScanner = new Scanner(logFile);

        //Loop through the file and print to the console
        while(logScanner.hasNext()){
            System.out.println(logScanner.nextLine());
        }
    }

    public static void exitApp() {
        System.out.println("Thank you for using VowelCounter!");
        System.exit(1);
    }
}
