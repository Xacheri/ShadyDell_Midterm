package cis170.shadydelldemo.Models;
import java.io.Serializable;
import java.util.*; // For Date and Calender to manipulate Date objects
import com.google.gson.annotations.SerializedName; /* external library class providing annotations
                                                    to use for generating json keys in FileAccess */
/**
 *
 * @author Zachery Smith
 */

// name: Person
// purpose: Represents an individual Person at Shady Dell
public class Person { // implements Serializable {
    // private static final long serialVersionUID = 1127L; // a serialization ID, just to be safe when serializing
  
    @SerializedName("fName")
    public String fName;
    @SerializedName("mName")
    public String mName;
    @SerializedName("middleI")
    public char middleI;
    @SerializedName("lName")
    public String lName;
    @SerializedName("DOB")
    public Date DOB;
    @SerializedName("SSN")
    public String SSN; // String - we don't do math to social security numbers
    @SerializedName("phone")
    public String phone; // String - we don't do math to phone numbers
    @SerializedName("address")
    public String address;
    @SerializedName("allergies")
    public String[] allergies;
    @SerializedName("type")
    public String type;
    @SerializedName("courses")
    public String[] courses;
    
    // specific constructor for the class
    public Person(String fName, String mName, String lName, Date DOB, String SSN, String phone, String address, String[] allergies, String type, String[] courses) {
        this.fName = fName;
        this.mName = mName;
        char empty = 0; // 0 is the null value in ASCII
        this.middleI = mName != null && mName.length() > 0 ? mName.charAt(0) : empty;
        this.lName = lName;
        this.DOB = DOB;
        this.SSN = SSN;
        this.phone = phone;
        this.address = address;
        this.allergies = allergies;
        this.type = type;
        this.courses = courses;
    }
    
    // empty constructor for this class
    public Person()
    {
        this(
            "",
            "",
            "",
            new Date(), // the null person was born when you constructed it
            "000-00-0000",
            "000-000-0000",
            "",
            new String[0],
            "",
            new String[0]
        );
    }
    
    // name : setSSN
    // purpose : format and set the SSN
    // params : nineDigit, a string of nine digit numbers
    // returns : void, sets SSN
    public void setSSN(String nineDigits){
        String formattedSSN = new String();
        
        for(int i = 0; i < 9; i++)
        {
            formattedSSN += nineDigits.charAt(i);
            if(i == 2 || i == 4) // on the 3rd and 5th iterations
            {
                formattedSSN += '-';
            }
        }
        
        this.SSN = formattedSSN;        
    }
    
    // name : setPhone
    // purpose : format and set the SSN
    // params : nineDigit, a string of 10 digits
    // returns : void, sets phone
    public void setPhone(String tenDigits){
        String formattedPhone = new String();
        
        for(int i = 0; i < 10; i++)
        {
            formattedPhone += tenDigits.charAt(i);
            if(i == 2 || i == 5) // on the 3rd and 6th iterations
            {
                formattedPhone += '-';
            }
        }
        
        this.phone = formattedPhone;        
    }
}
