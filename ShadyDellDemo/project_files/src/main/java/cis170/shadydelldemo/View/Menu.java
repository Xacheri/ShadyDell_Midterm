/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cis170.shadydelldemo.View;

import cis170.shadydelldemo.Utils.CLI;
import cis170.shadydelldemo.Controllers.PersonController;
import cis170.shadydelldemo.Models.Administrator;
import cis170.shadydelldemo.Models.Faculty;
import cis170.shadydelldemo.Models.Person;
import cis170.shadydelldemo.Models.Staff;
import cis170.shadydelldemo.Models.Student;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *  
 * @author Zachery Smith
 */

// name : Menu
// purpose : To provide a console menu for the PersonController functions
public class Menu {
    PersonController controller; // controller to manipulate
    CLI terminal; // input/output methods
    public ArrayList<Student> students;
    public ArrayList<Faculty> faculty;
    public ArrayList<Staff> staff;
    public ArrayList<Administrator> admin;
    
    // constructor for the class
    public Menu(PersonController controller)
    {
        this.controller = controller;
        this.terminal = new CLI();
        String[] type = {"type"}; // preset the fields for the search fuction
        ArrayList<Person> genericPeople; // use this to hold lists before typecasting
        
        // store all people
        genericPeople = controller.searchPeople(type, new String[]{"student"});
        for (Person person : genericPeople)
        {
            saveLocally(person);
        }
        
       
    }
    
    // name : Run
    // purpose : handle the View's resources and initialize the View
    // params : n/a
    // returns : n/a, runs the View
    public void run(){       
        terminal.openScanner(); // open the scanner for terminal input
        MainMenuLoop(); // launches the MainMenuLoop
        terminal.closeScanner(); // close the scanner for terminal input
        controller.savePeople(); // save our people in the controller
    }

    
    // name : MainMenu
    // purpose : Displays the Main Menu
    // params : n/a
    // returns : n/a, Displays the Main Menu
    public void MainMenu(){
        terminal.print("------------------------------------------------------------");
        terminal.print();
        terminal.print("Main");
        terminal.print();
        terminal.print("Select an option:");
        terminal.print(" 1. Add People");
        terminal.print(" 2. Search People");
        terminal.print(" 3. List All People");
        terminal.print(" 4. Exit");
        terminal.print();
        terminal.print("Enter your selection as a numeric input from 1-4");        
    }
    
    // name : MainMenuLoop
    // purpose : Manage the Main Menu control structure
    // params : n/a
    // returns : n/a
    public void MainMenuLoop()
    {
        terminal.print("WELCOME TO SHADY DELL PERSONELL MANAGEMENT");
        boolean isRunning = true; // initialize status     
        // Loops until MainMenuLoop is no longer running
        while (isRunning)
        {
            MainMenu(); // displays menu, prompts for input
            
            int input = terminal.readInt(); // collect input
            
            switch (input) // make a decision based on the input
            {
                
                case 1: // if they choose option 1 (Add People)
                    AddPeopleLoop(); // send them into the Add People process
                    break;
                case 2: // if they chose option 2 (Search People)
                    SearchPeopleLoop(); // send them into the Search People process
                    break;
                case 3:
                    terminal.print("--- ALL PERSONELL ---");
                    terminal.print();
                    DisplaySearchResult(controller.people);
                    terminal.print();
                    terminal.print();
                    break;
                case 4: // if they choose option 4 (Exit)                
                    isRunning = false; // set our sentinel to false
                    break; // the loop will break now   
                 default:
                    break;
            }
        }
    }
    
    // name : Add People Menu
    // purpose : Displays the Add People Menu
    // params : n/a
    // returns : n/a, Displays the Add People Menu
    public void AddPeopleMenu()
    {
        terminal.print("------------------------------------------------------------");
        terminal.print();
        terminal.print("Main / Add People");
        terminal.print();
        terminal.print("How many people would you like to add to Shady Dell?");
        terminal.print();
        terminal.print("Enter your selection as a numeric input, enter -1 to cancel");
    }
    
    // name : AddPeopleLoop
    // purpose : Manage the AddPeople control structure
    // params : n/a
    // returns n/a
    public void AddPeopleLoop()
    {
        boolean isRunning = true; // initialize status     
        // Loops until AddPeopleLoop status is false
        while (isRunning)
        {
            AddPeopleMenu(); // display the menu
            
            int input = terminal.readInt(); // collect input
            if (input == -1) {return;} // if they want to cancel, break out of the AddPeopleLoop
            for(int i = 0; i < input; i++) // loop however many times the user specified
            {
                terminal.print("Creating Person "+ (i+1) +" of "+input);
                CreatePersonLoop();
            }
        }
    }
    
    // name : CreatePersonMenu
    // purpose : Displays the Create Person Menu
    // params : person, Person - the person being created to bind to the display
    // returns : n/a, Displays the Create Person Menu
    public void CreatePersonMenu(Person person)
    {
        terminal.print("------------------------------------------------------------");
        terminal.print();
        terminal.print("Main / Add People / Create Person");
        terminal.print();
        terminal.print("1 - First Name : "+person.fName);
        terminal.print("2 - Last Name : "+person.lName);
        terminal.print("3 - Middle Name : "+person.mName);
        terminal.print("    Middle Initial : "+person.middleI);
        terminal.print("4 - Date of Birth : "+person.DOB.toString());
        terminal.print("5 - Social Security Number : "+person.SSN);
        terminal.print("6 - Phone Number : "+person.phone);
        terminal.print("7 - Address : "+person.address);
        terminal.print("8 - Allergies : ");
        // display each allergy
        for(String allergy : person.allergies)
        {
            terminal.print("               "+allergy);
        }
        
        terminal.print("9 - Type : "+person.type);
        
        // display each course if it is not an administrator or faculty
        if (!"administrator".equals(person.type.toLowerCase()) && !"staff".equals(person.type.toLowerCase()))
        {
            terminal.print("10 - Courses : ");
            for(String course : person.courses)
            {
                terminal.print("              "+course);
            }
        }
        
        terminal.print();
        terminal.print("Please indicate which numbered field you'd like to edit");
        terminal.print("Other commands: q - quit the editor, no save\n"
                     + "                s - save the user\n"
                     + "                build - use the guided builder");
    }
    
    // name : CreatePersonLoop()
    // purpose : Manage the CreatePerson control structure
    // params : n/a
    // returns n/a
    public void CreatePersonLoop()
    {
        boolean isRunning = true; // initialize status   
        Person person = new Person(); // create the person to work with
        // Loops until CreatePerson status is false
        while (isRunning)
        {
            CreatePersonMenu(person); // display the menu
            
            String input = terminal.readString(); // collect input
            
            switch(input) // make a decision based on input
            {
                case "1" -> {
                    // Edit fName
                    terminal.print();
                    terminal.print("What would you like the new first name to be?"); // prompt
                    person.fName = terminal.readString(); // fName is a string, so use readString()
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new first name is: "+person.fName); // confirm result
                }
                case "2" -> {
                    // Edit lName
                    terminal.print();
                    terminal.print("What would you like the new last name to be?"); // prompt
                    person.lName = terminal.readString(); // lName is a string, so use readString()
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new last name is: "+person.lName); // confirm result
                }
                case "3" -> {
                    // Edit mName (and updated middleI)
                    terminal.print();
                    terminal.print("What would you like the new middle name to be?"); // prompt
                    person.mName = terminal.readString(); // mName is a string, so use readString()
                    
                    char empty = 0; // 0 is the null value in ASCII
                    // set middleI to first letter of mName or if no mName, null character
                    person.middleI = person.mName != null && person.mName.length() > 0 ? person.mName.charAt(0) : empty;
                    
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new middle name is: "+person.mName); // confirm result
                }
                case "4" -> {
                    // Edit DOB
                    terminal.print();
                    terminal.print("Editing Date of Birth...");
                    
                    // collect birth info step-by-step
                    terminal.print("What would you like to be the new birth year?");
                    int year = terminal.readInt();
                    terminal.print();
                    terminal.print("What would you like to be the new birth month?");
                    int month = terminal.readInt();
                    terminal.print();
                    terminal.print("What would you like to be the new birth day?");
                    int day = terminal.readInt();
                    terminal.print();
                    
                    // set up to parse date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // set a format to parse out a Date from
                    String dateString = String.format("%02d/%02d/%04d", month, day, year); // parse date from input data

                    // try to parse date
                    try {
                        person.DOB = dateFormat.parse(dateString); // parse the new DOB from the string
                    } catch (ParseException e) {
                        terminal.print("Invalid date input.");
                    }
                }

                case "5" -> {
                    // Edit SSN
                    terminal.print();
                    terminal.print("What would you like the new SSN to be?"); // prompt
                    terminal.print("Enter a 9 digit number");
                    terminal.print();
                    Pattern ssn = Pattern.compile("^[0-9]{9}$"); // regex for 9 digit number
                    // check the regex against user input
                    String ssnInput = terminal.readString();
                    boolean validSSN = ssn.matcher(ssnInput).find();
                    if (validSSN)
                    {
                        person.setSSN(ssnInput);
                        terminal.print("Saving..."); // make it feel important
                        terminal.print("The new SSN is: "+person.SSN); // confirm result
                    }
                    else
                    {
                        terminal.print("Invalid SSN input");
                    }
                }
                case "6" -> {
                    // Edit phone
                    terminal.print();
                    terminal.print("What would you like the new Phone Number to be?"); // prompt
                    terminal.print("Enter a 10 digit number");
                    terminal.print();
                    Pattern phone = Pattern.compile("^[0-9]{10}$"); // regex for 10 digit number
                    // check the regex against user input
                    String phoneInput = terminal.readString();
                    boolean validPhone = phone.matcher(phoneInput).find();
                    if (validPhone)
                    {
                        person.setPhone(phoneInput);
                        terminal.print("Saving..."); // make it feel important
                        terminal.print("The new Phone Number is: "+person.phone); // confirm result
                    }
                    else
                    {
                        terminal.print("Invalid Phone input");
                    }
                }
                case "7" -> {
                    // Edit address
                    terminal.print();
                    terminal.print("What would you like the new address to be?"); // prompt
                    person.address = terminal.readString(); // fName is a string, so use readString()
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new address is: "+person.address); // confirm result
                }
                case "8" -> {
                    // Edit allergies
                    terminal.print();
                    terminal.print("Are there any allergies you'd like to add? y = yes, n = no"); // prompt
                    boolean yes = "y".equals(terminal.readString().toLowerCase());
                    while(yes)
                    {
                        terminal.print("input an allergy");
                        String[] newAllergies = new String[person.allergies.length + 1];
                        for (int i = 0; i < person.allergies.length; i++)
                        {
                            newAllergies[i] = person.allergies[i];
                        }
                        newAllergies[person.allergies.length] = terminal.readString();
                        terminal.print("Saving...");
                        person.allergies = newAllergies;
                        terminal.print();
                        terminal.print("Are there any more allergies you'd like to add? y = yes, n = no"); // prompt
                        yes = "y".equals(terminal.readString().toLowerCase());
                    }
                }
                case "9" -> {
                    // Edit type
                    terminal.print();
                    terminal.print("What would you like the new person type to be?"); // prompt
                    terminal.print("1 - faculty");
                    terminal.print("2 - staff");
                    terminal.print("3 - student");
                    terminal.print("4 - administrator");
                    terminal.print("Enter a number corrosponding to the above");
                    switch(terminal.readInt())
                    {
                        case 1:
                            person.type = "faculty";
                            break;
                        case 2:
                            person.type = "staff";
                            break;
                        case 3:
                            person.type = "student";
                            break;
                        case 4:
                            person.type = "administrator";
                            break;
                        default:
                            terminal.print("invalid type entered...");
                            break;
                    }
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new type is: "+person.type); // confirm result
                }
                case "10" -> {
                    // Edit courses
                    terminal.print();
                    terminal.print("Are there any courses you'd like to add? y = yes, n = no"); // prompt
                    boolean yes = "y".equals(terminal.readString().toLowerCase());
                    while(yes)
                    {
                        terminal.print("input a course");
                        String[] newCourses = new String[person.courses.length + 1]; 
                        for (int i = 0; i < person.courses.length; i++)
                        {
                            newCourses[i] = person.courses[i];
                        }
                        newCourses[person.courses.length] = terminal.readString();
                        terminal.print("Saving...");
                        person.courses = newCourses;
                        terminal.print();
                        terminal.print("Are there any more courses you'd like to add? y = yes, n = no"); // prompt
                        yes = "y".equals(terminal.readString().toLowerCase());
                    }
                }
                case "s" -> {
                    // Save Person
                    terminal.print();
                    terminal.print("Saving User...");
                    controller.people.add(person);
                    saveLocally(person);
                    terminal.print("User Saved!");
                    return; // end the function
                }
                case "q" -> {
                    // Quit
                    terminal.print();
                    terminal.print("Create Person Process Ended");
                    return; // end the function
                }
                case "build" -> {
                    // run the builder
                    person = runBuilder();
                    break;
                }
                default -> // invalid input
                    terminal.print("I'm sorry, "+input+" is not recognized as valid input, try again");
            }
        }         
    }
    
    // name : runBuilder
    // purpose : loop through every field bulding a person
    // params : n/a
    // retirms : person, the person build by the process
    private Person runBuilder(){
        Person person = new Person();
        for (int i = 1; i < 11; i++)
        {
             switch(Integer.toString(i)) // make a decision based on input
            {
                case "1" -> {
                    // Edit fName
                    terminal.print();
                    terminal.print("What would you like the new first name to be?"); // prompt
                    person.fName = terminal.readString(); // fName is a string, so use readString()
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new first name is: "+person.fName); // confirm result
                }
                case "2" -> {
                    // Edit lName
                    terminal.print();
                    terminal.print("What would you like the new last name to be?"); // prompt
                    person.lName = terminal.readString(); // lName is a string, so use readString()
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new last name is: "+person.lName); // confirm result
                }
                case "3" -> {
                    // Edit mName (and updated middleI)
                    terminal.print();
                    terminal.print("What would you like the new middle name to be?"); // prompt
                    person.mName = terminal.readString(); // mName is a string, so use readString()
                    
                    char empty = 0; // 0 is the null value in ASCII
                    // set middleI to first letter of mName or if no mName, null character
                    person.middleI = person.mName != null && person.mName.length() > 0 ? person.mName.charAt(0) : empty;
                    
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new middle name is: "+person.mName); // confirm result
                }
                case "4" -> {
                    // Edit DOB
                    terminal.print();
                    terminal.print("Editing Date of Birth...");
                    
                    // collect birth info step-by-step
                    terminal.print("What would you like to be the new birth year?");
                    int year = terminal.readInt();
                    terminal.print();
                    terminal.print("What would you like to be the new birth month?");
                    int month = terminal.readInt();
                    terminal.print();
                    terminal.print("What would you like to be the new birth day?");
                    int day = terminal.readInt();
                    terminal.print();
                    
                    // set up to parse date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // set a format to parse out a Date from
                    String dateString = String.format("%02d/%02d/%04d", month, day, year); // parse date from input data

                    // try to parse date
                    try {
                        person.DOB = dateFormat.parse(dateString); // parse the new DOB from the string
                    } catch (ParseException e) {
                        terminal.print("Invalid date input.");
                    }
                }

                case "5" -> {
                    // Edit SSN
                    terminal.print();
                    terminal.print("What would you like the new SSN to be?"); // prompt
                    terminal.print("Enter a 9 digit number");
                    terminal.print();
                    Pattern ssn = Pattern.compile("^[0-9]{9}$"); // regex for 9 digit number
                    // check the regex against user input
                    String ssnInput = terminal.readString();
                    boolean validSSN = ssn.matcher(ssnInput).find();
                    if (validSSN)
                    {
                        person.setSSN(ssnInput);
                        terminal.print("Saving..."); // make it feel important
                        terminal.print("The new SSN is: "+person.SSN); // confirm result
                    }
                    else
                    {
                        terminal.print("Invalid SSN input");
                    }
                }
                case "6" -> {
                    // Edit phone
                    terminal.print();
                    terminal.print("What would you like the new Phone Number to be?"); // prompt
                    terminal.print("Enter a 10 digit number");
                    terminal.print();
                    Pattern phone = Pattern.compile("^[0-9]{10}$"); // regex for 10 digit number
                    // check the regex against user input
                    String phoneInput = terminal.readString();
                    boolean validPhone = phone.matcher(phoneInput).find();
                    if (validPhone)
                    {
                        person.setPhone(phoneInput);
                        terminal.print("Saving..."); // make it feel important
                        terminal.print("The new Phone Number is: "+person.SSN); // confirm result
                    }
                    else
                    {
                        terminal.print("Invalid Phone input");
                    }
                }
                case "7" -> {
                    // Edit address
                    terminal.print();
                    terminal.print("What would you like the new address to be?"); // prompt
                    person.address = terminal.readString(); // fName is a string, so use readString()
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new address is: "+person.address); // confirm result
                }
                case "8" -> {
                    // Edit allergies
                    terminal.print();
                    terminal.print("Are there any allergies you'd like to add? y = yes, n = no"); // prompt
                    boolean yes = "y".equals(terminal.readString().toLowerCase());
                    while(yes)
                    {
                        terminal.print("input an allergy");
                        String[] newAllergies = new String[person.allergies.length + 1];
                        for (int j = 0; j < person.allergies.length; j++)
                        {
                            newAllergies[j] = person.allergies[j];
                        }
                        newAllergies[person.allergies.length] = terminal.readString();
                        terminal.print("Saving...");
                        person.allergies = newAllergies;
                        terminal.print();
                        terminal.print("Are there any more allergies you'd like to add? y = yes, n = no"); // prompt
                        yes = "y".equals(terminal.readString().toLowerCase());
                    }
                }
                case "9" -> {
                    // Edit type
                    terminal.print();
                    terminal.print("What would you like the new person type to be?"); // prompt
                    terminal.print("1 - faculty");
                    terminal.print("2 - staff");
                    terminal.print("3 - student");
                    terminal.print("4 - administrator");
                    terminal.print("Enter a number corrosponding to the above");
                    switch(terminal.readInt())
                    {
                        case 1:
                            person.type = "faculty";
                            break;
                        case 2:
                            person.type = "staff";
                            break;
                        case 3:
                            person.type = "student";
                            break;
                        case 4:
                            person.type = "administrator";
                            break;
                        default:
                            terminal.print("invalid type entered...");
                            break;
                    }
                    terminal.print("Saving..."); // make it feel important
                    terminal.print("The new address is: "+person.type); // confirm result
                }
                case "10" -> {
                    // Edit courses
                    terminal.print();
                    terminal.print("Are there any courses you'd like to add? y = yes, n = no"); // prompt
                    boolean yes = "y".equals(terminal.readString().toLowerCase());
                    while(yes)
                    {
                        terminal.print("input a course");
                        String[] newCourses = new String[person.courses.length + 1]; 
                        for (int k = 0; k < person.courses.length; k++)
                        {
                            newCourses[k] = person.courses[k];
                        }
                        newCourses[person.courses.length] = terminal.readString();
                        terminal.print("Saving...");
                        person.courses = newCourses;
                        terminal.print();
                        terminal.print("Are there any more courses you'd like to add? y = yes, n = no"); // prompt
                        yes = "y".equals(terminal.readString().toLowerCase());
                    }
                }
                default -> {
                    terminal.print("BUILD LOOP WENT OUT OF BOUNDS");
                }
            }
        }
        return person;
    }
    
    // name : SearchPeopleMenu
    // purpose : Displays the Search People Menu
    // params : currentQuery, String - a string denoting the current search being built
    // returns : n/a, Displays the Search People Menu
    public void SearchPeopleMenu(String currentQuery){
        
        
        terminal.print("------------------------------------------------------------");
        terminal.print();
        terminal.print("Main / Search People");
        terminal.print();
        terminal.print("Current Query : "+currentQuery);
        terminal.print();
        terminal.print("Do you want to add a query parameter? y = yes, n = no");
        terminal.print();        
    }
    
    // name : SearchPeopleLoop
    // purpose : Manage the CreatePerson control structure
    // params : n/a
    // returns n/a
    public void SearchPeopleLoop(){
        boolean searching = true;
        String query = "";
        String[] fields = new String[0];
        String[] values = new String[0];;
        while (searching)
        {
            SearchPeopleMenu(query);
            String yesInput = terminal.readString().toLowerCase();
            boolean addParameter = "y".equals(yesInput);
            if (addParameter)
            {
                terminal.print();
                terminal.print("What field is this search parameter for?");
                terminal.print("1 - First Name");
                terminal.print("2 - Last Name");
                terminal.print("3 - Middle Name");
                terminal.print("    Middle Initial");
                terminal.print("4 - Date of Birth");
                terminal.print("5 - Social Security Number");
                terminal.print("6 - Phone Number");
                terminal.print("7 - Address");
                terminal.print("8 - Allergies");
                terminal.print("9 - Type");
                terminal.print("10 - Courses");
                terminal.print();
                terminal.print("Enter the number corresponding to the field.");
                
                switch(terminal.readInt())
                {
                    case 1 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "First Name", "fName");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 2 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Last Name", "lName");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 3 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Middle Name", "mName");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 4 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Date of Birth", "DOB");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 5 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Social Security Number", "SSN");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 6 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Phone Number", "phone");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 7 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Address", "address");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 8 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Allergies", "allergies");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 9 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Type", "type");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    case 10 -> {
                        // update our arrays
                        String[][] updates = updateParralels(fields, values, "Courses", "courses");
                        fields = updates[0];
                        values = updates[1];
                        // update the query
                        query += fields[fields.length - 1]+" INCLUDES "+values[values.length - 1]+" \n";
                        break;
                    }
                    default -> terminal.print("Invalid Input for Field Num");
                }
            }
            else
            {
                terminal.print();
                terminal.print("Executing Query");
                ArrayList<Person> matches = controller.searchPeople(fields, values);
                DisplaySearchResult(matches);
                searching = false;
            }
        }
    }
    
    // name : DisplaySearchResult
    // purpose : Display a collection of people
    // params : results, an ArrayList of Person objects
    // returns n/a
    public void DisplaySearchResult(ArrayList<Person> results)
    {
        for (Person person : results)
        {
            DisplayPerson(person);
        }
    }
    
    // name : DisplayPerson
    // purpose : Display a Person
    // params : Person person, a person 
    // returns n/a
    public void DisplayPerson(Person person)
    {
        terminal.print("_________________________________________");
        terminal.print("|   First Name             "+person.fName);
        terminal.print("|   Last Name              "+person.lName);
        terminal.print("|   Middle Name            "+person.mName);
        terminal.print("|   Middle Initial         "+person.middleI);
        terminal.print("|   Date of Birth          "+person.DOB);
        terminal.print("|   Social Security Number "+person.SSN);
        terminal.print("|   Phone Number           "+person.phone);
        terminal.print("|   Address                "+person.address);
        terminal.print("|   Allergies              ");
        for(String allergy : person.allergies)
        {
        terminal.print("|                          "+allergy);
        }
        terminal.print("|   Type                   "+person.type);
        terminal.print("|   Courses                ");
        for(String course : person.courses)
        {
        terminal.print("|                          "+course);
        }
        terminal.print("_________________________________________");
    }
    
    // name : updateParralels
    // purpose : updates the field/values arrays
    // params : String[] fields - fields array, String[] values - values array,
    //          String fieldTitle - title of field adding, String fieldName - field name of field adding
    // returns : fvArray - String[][] - [0] String[] updatedFields, [1] String[] updatedValues, 
    private String[][] updateParralels(String[] fields, String[] values, String fieldTitle, String fieldName)
    {
        terminal.print("Field: "+fieldTitle);
        terminal.print();
        terminal.print("What value would you like to search for?");
        String value = terminal.readString();
        String[] newFields = new String[fields.length + 1];
        String[] newValues = new String[values.length + 1];

        // copy old values
        for (int i = 0; i < fields.length; i++)
        {
            newFields[i] = fields[i];
            newValues[i] = values[i];
        }

        // insert new ones
        newFields[fields.length] = fieldName;
        newValues[fields.length] = value;

        // prepare return object
        String[][] fvArray = new String[2][];
        fvArray[0] = newFields;
        fvArray[1] = newValues;
        return fvArray;
    }
    
    // name : saveLocally
    // purpose : Determines the correct array and stores a person there
    // params : person, the person to store
    // returns : n/a, updates one of our arrays
    private void saveLocally(Person person)
    {
        if (person instanceof Student)
        {
            this.students.add((Student) person);
        }
        if (person instanceof Faculty)
        {
            this.faculty.add((Faculty) person);
        } 
        if (person instanceof Staff)
        {
            this.staff.add((Staff) person);
        }
        if (person instanceof Administrator)
        {
            this.admin.add((Administrator) person);
        }
    }   
}
