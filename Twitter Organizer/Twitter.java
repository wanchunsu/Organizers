// Wan-Chun Su
// 260729936

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Twitter {
    // declaring the private attribute
    private ArrayList<Tweet> tweets;
    
    // A constructor that initializes the the tweets ArrayList to an empty ArrayList
    public Twitter() {
        this.tweets = new ArrayList<Tweet>(); 
    }
    
    //A method that takes a file name as input and loads the tweets on the file
    public void loadDB(String tweetsFileName) {
        try { //accounting for possible IOExceptions
            // Declare and initialize the objects to read the file
            FileReader fr = new FileReader(tweetsFileName);
            BufferedReader br = new BufferedReader(fr);
            
            // Reading the first line
            String currentLine = br.readLine();
            // continue reading line by line until the next line is blank 
            while (currentLine != null) {
                // Create a String array to store the words of the tweet parts that are split by the tab character
                String[] tweetInfo = currentLine.split("\t"); 
                Tweet newTweet = new Tweet(tweetInfo[0], tweetInfo[1], tweetInfo[2], tweetInfo[3]); 
                // check if the message is valid, if true, add it to the tweets ArrayList
                if (newTweet.checkMessage() == true) {
                    this.tweets.add(newTweet); 
                }
                // read the next line 
                currentLine = br.readLine(); 
            }
            //close the buffered reader and file reader
            br.close();
            fr.close();
            
            // sort the tweets by calling sortTwitter 
            sortTwitter(); 
        } catch (IOException e) {//Catching the IOException if present
            System.out.println("There was a problem loading the datatbase.");
        }
    }
    
    //A method that sorts the tweets from earliest to latest using the selection sort algorithm
    public void sortTwitter() {
        // making sure that the index is with the ArrayList and updating the index after each iteration
        for (int index = 0; index < getSizeTwitter() - 1; index++) {
            //Declaring and initializing the sorting position
            int earlierElement = index;
            // using a for loop to run through each tweet
            for (int i = index + 1; i < getSizeTwitter(); i++) {
                // if there is an earlier tweet, update the earlierElement variable 
                if (getTweet(i).isBefore(getTweet(earlierElement))) {
                    earlierElement = i;
                }
            }
            //Swapping the elements
            //Making a temporary variable to store the tweet at the index position
            Tweet temp = getTweet(index);
            //setting the index postion to equal earlierElement tweet
            this.tweets.set(index, getTweet(earlierElement));
            //setting the earlierElement position to equal the temp variable (index position tweet)
            this.tweets.set(earlierElement, temp); 
        }
    }
    
    // A get method that returns the size of the tweets ArrayList
    public int getSizeTwitter() {
        return this.tweets.size(); 
    }
    
    // A get method that returns the tweet at the specified index
    public Tweet getTweet(int index) {
        return this.tweets.get(index); 
    }
    
    //A method that returns a String storing all the tweets and their parts
    public String printDB() {
        //Creating a new String and initializing it
        String s = "";
        // using a for loop to go through the whole tweets ArrayList
        for (int i = 0; i < getSizeTwitter(); i++) {
            //Using the to String method and a newline character to concatenate the tweets together
            s += getTweet(i).toString() + "\n"; 
        }
        // returning the String
        return s; 
    }
    
    // A method that returns an ArrayList storing the tweets between the input tweets Tweet1 and Tweet2 (inclusive)
    public ArrayList<Tweet> rangeTweets(Tweet tweet1, Tweet tweet2) {
        // Creating a new ArrayList to store the tweets within the specified range
        ArrayList<Tweet> newList = new ArrayList<Tweet>(); 
        // if tweet1 was posted before tweet2
        if (tweet1.isBefore(tweet2)) {
            //Use a for loop to add the tweets between tweet1 and tweet2 (inclusive)
            for(int i=tweets.indexOf(tweet1); i<=tweets.indexOf(tweet2); i++) {
                newList.add(getTweet(i));
            }
        } else {
            //Use a for loop to add the tweets between tweet2 and tweet1 (inclusive)
            for(int i=tweets.indexOf(tweet2); i<=tweets.indexOf(tweet1); i++) {
                newList.add(getTweet(i));
            }
        }
        // return the new ArrayList 
        return newList; 
    }
    
    // A method that saves the tweets database onto a file
    public void saveDB(String fileName) {
        try {//accounting for possible IOExceptions
            // Calling printDB() and storing the tweets database into a String variable
            String s = printDB(); 
            // creating the filewriter and buffered writer needed to write on the file
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            // Writing on the file
            bw.write(s);
            // closing the filewriter and buffered writer
            bw.close();
            fw.close(); 
        } catch (IOException e) {//catching the IOException if present
            System.out.println("There was a problem saving the database");
        }
        
    }
    
    //A helper method that creates a HashMap 
    //The key being the word and the value beign the number of times it appears
    private HashMap<String, Integer> wordFrequency() {
        //Creating a variable to store the HashMap
        HashMap<String, Integer> map =  new HashMap<String,Integer>();
        //Creating a 2D String array to store the messages 
        String[][] messages = new String[getSizeTwitter()][];
        //Storing the words of the message as subelements of the message
        for (int i = 0; i < messages.length; i++) {
            messages[i]=getTweet(i).getMessage().split(" ");
            
        }
        //Using nested for loops to get rid of duplicate words in a message in order to initialize the HashMap
        for (int i = 0; i < messages.length; i++) {
            //Creating a HashSet  to store the words of each message
            //This ensures that there is no duplicate word within a message being store
            HashSet<String> set = new HashSet<String>();
            //going through the words of the message
            for (int j = 0; j < messages[i].length; j++) {
                String lowerCase = messages[i][j].toLowerCase();//setting all the words to lowercase
                messages[i][j] = lowerCase;
                //Checking if the word is a stopword, if not, it stores the word into the HashSet
                if(!Tweet.isStopWord(messages[i][j])) {
                    /*Creating a variable to store the last letter of the word
                     and removing it from the word if it is a ',' ,'.', ';', or ':'*/
                    char c= messages[i][j].charAt(messages[i][j].length()-1);
                    if(c== ',' ||c== '.' || c == ';' || c == ':') {
                        //declaring and initializing a String to store the word that has been modified(punctuation is removed)
                        String modified = "";
                        for(int k=0; k<messages[i][j].length()-1; k++) {
                            modified += messages[i][j].charAt(k);//storing all the characters of the word before the position of the punctuation
                        }
                        messages[i][j] = modified; //changing the String that the variable word is storing
                    }
                    set.add(messages[i][j]);//adding the modified word to the HashSet 
                }
                
            }
            //Adding the words into the HashMap after the HashSet for the message is created
            
            //Using a foreach loop to add the elements of the HashSet into the HashMap
            for(String s : set) {
                if(!map.containsKey(s)) { //the word is not present yet
                    map.put(s,1);
                } else { //the word is present
                    int value = map.get(s);
                    map.put(s,value+1); //updating the value
                }
            }
        }
        //returning the HashMap
        return map;
    }
    
    //A method that returns the word that appears the most in the collection of tweet messages
    public String trendingTopic() {
        //Calling the wordFrequency method and storing the HashMap containing all the words and their frequencies
        HashMap<String,Integer> map = wordFrequency();
        //Creating and initializing a variable to store the most frequent word and the largest word frequency
        String word = "";
        int largestValue=0;
        // Using a foreach loop to find the word in the HashMap that appears the most
        for (String s: map.keySet()) {
            // find a larger value 
            if (largestValue < map.get(s)) {
                // update the largestValue variable as well as the variable word
                largestValue = map.get(s); 
                word = s; 
            }
        }
        // return the word that appears the most 
        return word; 
    }
    
    
    public static void main(String[] args){
        //Ex1:
        /*Twitter example = new Twitter();
         example.loadDB("tweets.txt");*/
        
        //Ex2:
        /*Twitter example = new Twitter();
         Tweet.loadStopWords("stopWords.txt");
         example.loadDB("tweets.txt");
         System.out.println("The number of tweets is: " + example.getSizeTwitter());*/
        
        //Ex3:
        /*Twitter example = new Twitter();
         Tweet.loadStopWords("stopWords.txt");
         example.loadDB("tweets.txt");
         System.out.println(example.printDB());*/
        
        //Ex4:
        /*Twitter example = new Twitter();
         Tweet.loadStopWords("stopWords.txt");
         example.loadDB("tweets.txt");
         System.out.println(example.rangeTweets(example.getTweet(4), example.getTweet(2)));*/
        
        //Ex5:
        /*Twitter example = new Twitter();
         Tweet.loadStopWords("stopWords.txt");
         example.loadDB("tweets.txt");
         System.out.println(example.wordFrequency());
         System.out.println(example.trendingTopic());*/
        
    }
} 