import java.util.*;

public class Card {
    private double balance;
    private int age;
    private String educationStatus;
    private String cardNumber;

    public Card(double balance, int age, String educationStatus, String cardNumber) {
        setBalance(balance);
        setAge(age);
        setEducationStatus(educationStatus);
        this.cardNumber = cardNumber;

    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public String getcardNumber() {
        return cardNumber;
    }

    public void loadMoney(double amount) {
        if (amount <= 0) {
            return;
        }
        setBalance(amount + getBalance());
    }

    public boolean spendMoney(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            return true;
        }
        return false;
    }
}
