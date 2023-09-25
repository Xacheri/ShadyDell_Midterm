/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cis170.shadydelldemo.DAL;
import cis170.shadydelldemo.Models.Person; // our person model
import java.io.File; // used to generate a filepath 
import java.io.FileWriter; // used to write to files
import java.io.FileReader; // used to read to files
import java.io.BufferedReader; // to provide extra reading methods
import java.io.IOException; // used to create i/o exceptions in the case of read/write errors

// used for TypeTokens for proper deserialization
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import com.google.gson.Gson; // the Gson object builds Json from Java objects

import java.util.ArrayList; // ArrayList

/**
 *
 * @author Zachery Smith
 */

// name : Person_FileAccess
// purpose : Handle all file manipulationg for the person data
public class Person_FileAccess {
    public ArrayList<Person> people; // the array list of people
    private String filePath; // filepath to JSON
    
    // constructor for the class
    // params: people, ArrayList<Person> - the person ArrayList you want to perform Person_FileAccess methods on
    public Person_FileAccess(ArrayList<Person> people){
        
        // set our people list to the one submitted in the params
        this.people = people;
        
        // build the filepath to our json file
        // home directory of the user
        String homeDir = System.getProperty("user.home");
        
        // append a cute recognizable folder name
        String jsonDir = homeDir + File.separator + "shady-dell-smithz";
        
        // append a simple filename
        String jsonFilePath = jsonDir + File.separator + "mydata.json";
        
        // set our filepath to our constructed json filepath
        this.filePath = jsonFilePath;
    }
    
    // null constructor for the class
    // useful for filling Person_FileAccess from json
    public Person_FileAccess(){        
        // build the filepath to our json file
        // home directory of the user
        String homeDir = System.getProperty("user.home");
        
        // append a cute recognizable folder name
        String jsonDir = homeDir + File.separator + "shady-dell-smithz";
        
        // append a simple filename
        String jsonFilePath = jsonDir + File.separator + "mydata.json";
        
        // set our filepath to our constructed json filepath
        this.filePath = jsonFilePath;
        
        // set our people list with the read() method;
        read();
        
        // if there was nothing / an empty file 
        if (this.people == null)
        {
            // intialize an empty array
            this.people = new ArrayList<Person>();
        }
    }
    
    // name : json (private)
    // purpose : convert the people to json
    // params : people, the ArrayList of Person objects to become json
    // returns : peopleJson, String - json string of the people
    private String json(ArrayList<Person> people) {
        // initialize google's json tool
        Gson gson = new Gson();
        
        // serialize the people to json
        String peopleJson = gson.toJson(people);
        
        // return our json string
        return peopleJson;
    }
    
    // name : touch
    // purpose : checks if the json file is present and creates it if not
    // params : n/a, uses this.filePath
    // returns : status, boolean - wether the file exists after execution
    private boolean touch() {
        try {
            // create a file object from our filepath to check
            File file = new File(this.filePath);
            // recursively make missing directories jic this is the first time this has been run
            file.getParentFile().mkdirs();
            
            // Check if the file exists
            if (!file.exists()) {
                // if not, create the file
                file.createNewFile();
            }
            
            return file.exists(); // return current statuc of the file
        }
        catch (IOException e)
        {
            e.printStackTrace(); // print what's going on
            return false; // the status is false, something funky happened
            
        }
    }
    
    // name : write
    // purpose : write the people array to the json file
    // params : people, the people to write to the file
    // returns : n/a
    public void write(ArrayList<Person> people) {
        // make sure our file exists, if it doesnt..
        if (!touch())
        {
            // print a message and return
            System.out.println("Oops, the file couldn't be created");
            return;
        }
        // in java, you can initialize something to be scoped for the try block only
        // this way, as soon as you leave the try block (concluding/catching an I/O exception)
        // the object you initialized is deconstructed (abstract away memory management!)
        try (FileWriter writer = new FileWriter(this.filePath)) {

            writer.write(json(people));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   // name : read
   // purpose : write the people array to the json file
   // params : n/a, uses this.filepath
   // returns: n/a, updates this.people
    public void read() {
        // make sure our file exists, if it doesnt, create an empty file
        touch();
        
        
        // in java, you can initialize something to be scoped for the try block only
        // this way, as soon as you leave the try block (concluding/catching an I/O exception)
        // the object you initialized is deconstructed (abstract away memory management!)
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // read our json
            String jsonString = "";
            // this for loop iterates logically, not arithmetically through the file
            // it starts by creating the line variable
            // if line is null, it wont execute and try to append it
            // at the end of every iteration, line is replaced with the next line
            for (String line = reader.readLine(); line != null; line = reader.readLine())
            {
                jsonString += line;
            }
            // deserialize our json and update our object
            deserialize(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // name : deserialize (private)
    // purpose : deserializes json and updates this.people
    // params : json, json serialized from an ArrayList<Person>
    // returns : status, boolean - wether the ArrayList was updated
    private boolean deserialize(String json){
        try
        {
            Gson gson = new Gson(); // grab our google json helper
            Type ArrayList_Person = new TypeToken<ArrayList<Person>>(){}.getType(); // define the ArrayList<Person> type token for gson          
            ArrayList<Person> newPeople = gson.fromJson(json, ArrayList_Person); // deserialize Persons            
            this.people = newPeople; // set people to our newly built array and return
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
