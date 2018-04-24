package com.company;

import java.io.*;
import java.util.*;

public class Main
{
    static Random rand = new Random();
    static Scanner scan = new Scanner(System.in);


    //Checks if player wants to play again
    static boolean quit()
    {
        System.out.print("\nDo you want to play again? [Press Y to play again or any other key to exit]: ");
        if (scan.nextLine().equals("y"))
        {
            System.out.println();
            return false;
        }
        else
        {
            System.out.println("Goodbye!");
            return true;
        }

    }

    public static void main (String[] args) throws Exception
    {
        boolean quitGame = false;
        Game game = new Game();
        File file = new File("text.txt");
        Scanner scanFile = new Scanner(file);
        int wordCount = 0;
        int randnum;
        ArrayList<String> nameArray = new ArrayList<>();

        //Check how many words are in document and initialize nameArray with the words
        while (scanFile.hasNextLine())
        {
            nameArray.add(wordCount, scanFile.nextLine());
            wordCount++;
        }

        //Start of the game
        while (!quitGame)
        {
            //generate random number based on total word count minus 1 since arrays start at 0
            randnum = rand.nextInt(wordCount - 1);

            //setting the randomly generated name
            game.setName(nameArray.get(randnum));

            //Start of game
            game.gameStart();

            //Checks if player wants to continue
            quitGame = quit();
        }
    }
}

class Game
{
    Scanner scan = new Scanner(System.in);
    private String name;
    private char[] hiddenName;
    private char userInput;
    private char userGuess;
    private boolean isMatch;
    private ArrayList<String> wrongLetter = new ArrayList<>();
    private int lives;

    void gameStart()
    {
        setHiddenName();

        //These two variables are initialized every time a new game is started
        isMatch = false;
        lives = 10;

        System.out.println("I'm thinking of a K-pop group. Can you guess who they are? (One letter/number at a time.)");

        while(!isMatch)
        {
            printHiddenName();
            System.out.print("\nYour guess: ");

            //converts user inputs to uppercase to compare to name String
            userInput = scan.next().charAt(0);
            userGuess = Character.toUpperCase(userInput);

            containLetterUpdate(userGuess);
            System.out.print("Remaining tries: " + lives + ", Wrong guesses: ");
            printWrongLetter();

            isMatch = wordMatch();

            //Player loses game if "lives" = 0
            if (lives == 0)
            {
                System.out.println("\nGame over! I was thinking of " + name + "!");
                break;
            }
        }
    }

    //Setting the randomly picked name
    void setName(String name)
    {
        //entire name string is converted to uppercase
        this.name = name.toUpperCase();
    }

    //initializes hiddenName array with underscores
    void setHiddenName()
    {

        hiddenName = name.toCharArray();
        for (int i = 0; i < name.length(); i++)
        {
            hiddenName[i] = '_';
        }
    }

    //A method to print out the hiddenName array with spaces between the letters/underscores
    void printHiddenName()
    {
        for (int i = 0; i < name.length(); i++)
        {
            System.out.print(hiddenName[i] + " ");
        }
    }

    //Checks if userGuess is in name String and updates hiddenName array if it is true
    void containLetterUpdate(char userGuess)
    {
        //Runs if userGuess is incorrect or user guessed the same letter/number
        if (name.indexOf(userGuess) == -1 || hiddenName[name.indexOf(userGuess)] == userGuess)
        {
            lives--;
            System.out.println("\nWrong letter or letter already used!");

            //Converting char to String to add to arraylist wrongletter
            String temp = Character.toString(userGuess);
            if (!wrongLetter.contains(temp))
            {
                wrongLetter.add(temp);
            }
        }
        else //replaces underscore with correct letter for all instances of letter
        {
            System.out.println("\nCorrect!");
            for (int i = 0; i < name.length(); i++)
            {
                if (name.charAt(i) == userGuess)
                {
                    hiddenName[i] = userGuess;
                }
            }
        }
    }

    //Prints a list of incorrect guesses (letters/numbers)
    void printWrongLetter()
    {
        for (int i =0; i < wrongLetter.size(); i++)
        {
            System.out.print(wrongLetter.get(i) + ", ");
        }
        System.out.println();
    }

    //Checks if the hiddenName array and name String matches
    boolean wordMatch()
    {
        if (new String(hiddenName).equals(name))
        {
            printHiddenName();
            System.out.println("\nYou got it right! I was thinking of " + name + "!");
            return true;
        }
        else
        {
            return false;
        }
    }
}
