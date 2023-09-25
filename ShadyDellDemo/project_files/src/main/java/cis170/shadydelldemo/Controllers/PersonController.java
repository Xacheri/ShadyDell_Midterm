/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cis170.shadydelldemo.Controllers;


import cis170.shadydelldemo.DAL.Person_FileAccess;
import cis170.shadydelldemo.Models.Person; // our Model
import java.lang.reflect.Field; // allows us to isolate and dynamically access object fields for search
import java.util.ArrayList; // ArrayList

/**
 *
 * @author Zachery Smith
 */

// name : PersonController
// purpose : Control methods related to manipulating the Person objects
public class PersonController {
    public ArrayList<Person> people; // the arrayList of Person objects
    Person_FileAccess repository; // our data access layer to our json storage system
    
    // constructor for the class
    public PersonController(Person_FileAccess dal)
    {
        this.repository = dal; // set up file access
        this.people = dal.people; // load people from the file
    }
    
    // name : addPeople
    // purpose : merge an ArrayList of Person objects with this.people
    // params : newPeople, ArrayList<Person> - people to add
    // returns : n/a
    public void addPeople(ArrayList<Person> newPeople)
    {
        // merge the array lists
        this.people.addAll(newPeople);
    }
    
    // name : searchPeople
    // purpose : generate ArrayList of Person objs where person[fieldName[i]] includes searchValue[i]
    // params : fieldNames, searchValues, parallel String[] - field names to search through and values to search for
    // returns : matches, ArrayList of matching Person objects
    public ArrayList<Person> searchPeople(String[] fieldNames, String[] searchValues)
    {
        ArrayList<Person> matches = new ArrayList<Person>();
        Class<?> PersonClass = Person.class;
        // iterate over all people in the list...
        for (Person person : this.people)
        {
            // and checking each field value...
            for (int parralelIndex = 0; parralelIndex < fieldNames.length; parralelIndex++)
            {
                // add the person to the array if the field includes the corrosponding value
                try
                {
                    // get the field of the current fieldname
                    Field thisField = PersonClass.getField(fieldNames[parralelIndex]);                    
                    // get the field's value
                    String fieldValue = "";
                    if (thisField.get(person) instanceof String[])
                    {
                        String[] strArr = (String[]) thisField.get(person);
                        for (var str : strArr)
                        {
                            fieldValue += ","+(String)str;
                        }
                    }
                    else
                    {
                        fieldValue = thisField.get(person).toString();
                    }                    
                    // if the field value includes the search term, add the person to the matches
                    if (fieldValue.toLowerCase().contains(searchValues[parralelIndex].toLowerCase()))
                    {
                        matches.add(person);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        // return the matches
        return matches;
    }
    
    // name : savePeople
    // purpose : save the ArrayList of Person objects to JSON
    // params : n/a
    // returns : n/a
    public void savePeople()
    {
        // save the ArrayList
        this.repository.write(this.people);
    }
    
}
