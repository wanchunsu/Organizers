
import java.util.HashSet;
import java.util.ArrayList;
import java.io.*;
public class Tweet {
    //Declaring the private attributes of this class
    private String userAccount;
    private String date;
    private String time;
    private String message;
    private static HashSet<String> stopWords;
    
    //Constructor that takes the user account, date, time and message as input and intializes them
    public Tweet(String userAccount, String date, String time, String message) {
        this.userAccount = userAccount;
        this.date = date;
        this.time = time;
        this.message = message;
    }
    //A method that checks if the message is valid
    public boolean checkMessage() {
        //throws an exception if the HashSet is null
        if(this.stopWords==null) {
            throw new NullPointerException("The Hashset stopWords has not been initialized");
        }
        //splitting the message by whitespace and storing the elements into a String array
        String[] splitMessage = this.message.split(" ");
        int numStopWords = 0;
        //Using a for loop to count the amount of stop words that are in the array
        for(int i=0; i<splitMessage.length; i++) {
            if(isStopWord(splitMessage[i])) { //Using isStopWord to find out if the array elements are stop words
                numStopWords++;//updating the number of stop words if one is found
            }
        }
        int numWords = splitMessage.length-numStopWords;/*updating the total number of words in the 
        message by subtracting the number of stopwords */
        if(numWords > 0 && numWords < 16) {
            return true; //returning true if the message is of valid length
        }
        return false;//returning false otherwise
    }
    
    //A helper method that checks if the word is a stop word
    public static boolean isStopWord(String word) {
        //controlling cases where there is punctuation appended at the end of the word
        if(word.charAt(word.length()-1)== ',' ||word.charAt(word.length()-1)== '.' || 
           word.charAt(word.length()-1)== ';' ||word.charAt(word.length()-1)== ':') {
            //declaring and initializing a String to store the word that has been modified(punctuation is removed)
            String modified = "";
            for(int i=0; i<word.length()-1; i++) {
                modified += word.charAt(i);//storing all the characters of the word before the position of the punctuation
            }
            word = modified; //changing the String that the variable word is storing
        }
        for(String s : stopWords) { //checking to see if the word is a stop word
            if(word.equalsIgnoreCase(s)) {
                return true; //returning true if the word is a stop word
            }
        }
        return false;//otherwise returning false  
    }
    
    //Get method that returns the value of the date attribute
    public String getDate() {
        return this.date;
    }
    //Get method that returns the value of the time attribute
    public String getTime() {
        return this.time;
    }
    //Get method that returns the value of the message attribute
    public String getMessage() {
        return this.message;
    }
    //Get method that returns the value of the userAccount attribute
    public String getUserAccount() {
        return this.userAccount;
    }
    
    // A toString method that returns information about the tweet as well as the tweet itself
    public String toString() {
        String s = getUserAccount() + "\t" + getDate() + "\t" + getTime() + "\t" + getMessage();
        return s;
    }
    //A helper method that places the elements of a String array containing integers into an ArrayList of type Integer
    private static ArrayList<Integer> convert(String[] s) {
        ArrayList<Integer> converted = new ArrayList<Integer>();
        //Using a for loop to initialize the ArrayList with the String array elements
        for(int i=0; i<s.length; i++) {
            //Converting the String to an integer and adding it to the ArrayList
            converted.add(Integer.parseInt(s[i]));
        }
        //returning the converted ArrayList
        return converted;
    }
    //A helper method that finds if the date/time of the attribute tweet is before the date/time of the input tweet
    //It returns a String: "true" if the date/time is before, "false" if the date/time is after, and "equal" if the date/time is equal
    private static String dateOrTimeIsBefore(ArrayList<Integer> attribute, ArrayList<Integer> input) {
        
        if(attribute.get(0)<input.get(0)) { //attribute year/hour is before the input year/hour
            return "true";
        } else if (attribute.get(0)>input.get(0)) { //attribute year/hour is greater than input year/hour
            return "false";
        } else { //case where the years/hours are equal
            if(attribute.get(1)<input.get(1)) { /*years/hours are equal but attribute 
                month/minute is less than input month/minute*/
                return "true";
            } else if (attribute.get(1)>input.get(1)) { //years/hours are equal but attribute 
                //month/minute is greater than input month/minute
                return "false";
            } else { //years/hours and month/minute are equal
                if(attribute.get(2)<input.get(2)) {/*years/hours and months/minutes are equal 
                    but attribute day/seconds is earlier than input day/seconds*/
                    return "true";
                } else if(attribute.get(2)>input.get(2)){/*years/hours and months/minutes are equal but 
                    attribute day/seconds is greater than input day/seconds*/
                    return "false";
                } else {//all aspects of the date/time are equal
                    return "equal";
                  
                }
            }
        }     
    }
 
    //A method that returns true if the tweet was posted at an earlier time than the input parameter tweet
    public boolean isBefore(Tweet t) {
        //Splitting the dates and times by their respective characters and storing the individual elements into String arrays
        //inputTweetDate & inputTweetTime corresponds to the date and time of the input parameter, respectively
        String[] inputTweetDate = t.date.split("-");
        String[] inputTweetTime = t.time.split(":");
        //attributeDate & attributeTime corresponds to the date and time of the attribute, respectively
        String[] attributeDate = getDate().split("-");
        String[] attributeTime = getTime().split(":");
        
        //Using the convert method to convert the String array elements(which are actually numbers) into ArrayLists of Integers
        //inTweetDate & inTweetTime corresponds to the date and time of the input parameter, respectively
        ArrayList<Integer> inTweetDate = convert(inputTweetDate);
        ArrayList<Integer> inTweetTime = convert(inputTweetTime);
        //attTweetDate & attTweetTime corresponds to the date and time of the attribute, respectively
        ArrayList<Integer> attTweetDate = convert(attributeDate);
        ArrayList<Integer> attTweetTime = convert(attributeTime);
        
        //Calling dateOrTimeIsBefore method to check whether the attribute tweet was before the input tweet
        //First checking if the date is before and returning true if that is the case
        if(dateOrTimeIsBefore(attTweetDate,inTweetDate).equals("true")) {
            return true;
        } else if (dateOrTimeIsBefore(attTweetDate,inTweetDate).equals("equal")) {//dates are equal, need to check the time
            if(dateOrTimeIsBefore(attTweetTime,inTweetTime).equals("true")) { 
            //checking if the time of the attribute tweet is before the input tweet time and returning true if it is the case
                return true;
            } else { //if the time of the attribute tweet is equal to or after that of the input, return false
                return false;
            }
        } else { //if the date of the attribute tweet is after that of the input tweet, return false
            return false;
        }   
    }
    
    /*A method that takes as input the name of a file containing stop words and uses the 
    content to initialize the stopWords attribute*/
    public static void loadStopWords(String fileName){ 
        try { //accounting for possible IOExceptions
            //declaring and initializing the FileReader and BufferedReader 
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            //Creating a variable to store the current line that is being read
            String currentLine = br.readLine();
            //Creating a new HashSet to store the lines of the file
            HashSet<String> words = new HashSet<String>();
            while(currentLine != null) {//reading each line of the file until a blank line is encountered
                words.add(currentLine); //initializing the words variable to store the current line of the file
                currentLine = br.readLine(); //updating the variable to store the next line of the file
            }
            br.close();//closing the BufferedReader
            fr.close();//closing the FileReader
            stopWords = words;//initializing the stopWords attribute
        } catch(IOException e) { //Catching the IOException if there is one
            System.out.println("The was a problem loading the file");
        }
    }
 
}
