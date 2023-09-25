package cis170.shadydelldemo;

import cis170.shadydelldemo.Controllers.PersonController;
import cis170.shadydelldemo.DAL.Person_FileAccess;
import cis170.shadydelldemo.View.Menu;

/**
 *
 * @author Zachery Smith
 */
public class ShadyDellDemo {

    // entry point for the program
    public static void main(String[] args) {
        // Initialize our Data Access Layer
        Person_FileAccess fileAccess = new Person_FileAccess();
        // Initialize our Controller
        PersonController controller = new PersonController(fileAccess);
        // Pass the controller to the view
        Menu menu = new Menu(controller);
        // run the view
        menu.run();
    }
}
