/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cis170.shadydelldemo.Utils;

import java.util.Scanner;

/**
 *
 * @author Zachery Smith
 */

// name : CLI
// purpose : provides static methods for command line input and output
public class CLI {
    public Scanner inputScanner; // console input scanner
    
    // constructor for the class
    public CLI(){
        this.inputScanner = null; // initialize to null so developer manages open/closing of stream
    }
    
    // name : readInt
    // purpose : collect integer input from console.
    // params : n/a, uses User input
    // returns : number, user-input integer
    public int readInt()
    {
        String input = readString();
        try {
            int number = Integer.parseInt(input);
            return number;
        } catch (NumberFormatException e) {
            print("Invalid number, try again");
            return readInt();
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    
    // name : readString
    // purpose : collect string input from console.
    // params : n/a, uses User input
    // returns : input , user-input string
    public String readString()
    {
        String input = this.inputScanner.nextLine();  
        return input;
    }
    
    // name : print
    // purpose : outputs a string on a new line in the console.
    // params : str, a string to output
    // returns : n/a
    public void print(String str)
    {
        System.out.println(str);
    }
    
    // name : print
    // purpose : outputs a blank line in the console.
    // params : n/a
    // returns : n/a
    public void print()
    {
        System.out.println();
    }

    // name : openScanner
    // purpose : opens the input scanner object for the class
    // params : n/a
    // returns : n/a, opens input scanner
    // note : must openScanner() before conducting any input methods
    public void openScanner()
    {
        this.inputScanner = new Scanner(System.in);
    }
    
    // name : closeScanner
    // purpose : closes the input scanner object for the class
    // params : n/a
    // returns : n/a, closes input scanner
    // note : must closeScanner() when you are done with all input methods
    public void closeScanner()
    {
        this.inputScanner.close();
    }
}