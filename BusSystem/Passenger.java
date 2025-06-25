import java.util.*;

public class Passenger {
    private String firstName;
    private String lastName;
    private int age;
    private String educationStatus;;
    private Card card;

    public Passenger(String firstName, String lastName, int age, String educationStatus, Card card) {
        setfirstName(firstName);
        setlastName(lastName);
        setAge(age);
        setEducationStatus(educationStatus);
        setCard(card);
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(String educationStatus) {
        this.educationStatus = educationStatus;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
