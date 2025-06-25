import java.util.*;

public class Quiz01_20220808704 {
    public static void main(String[] args) {
        try {
            Customer burcu = new Customer("Burcu");

            CreditCard card = new CreditCard(1234567812345678L, "Burcu Yılmaz", new Date(), 123);
            burcu.setPaymentMethod(card);

            Item normalProduct = new Product("Laptop", 550) {
            };

            burcu.addItem(normalProduct);

            System.out.println("Cart Contents:");
            burcu.display();

            System.out.println("\nPurchase Summary:");
            burcu.purchase();

        } catch (CreditCardException | InvalidPriceException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }
}

interface Item extends Comparable<Item> {
    public double getPrice();

    public String getName();
}

interface PaymentMethod {
    public boolean pay(List<Item> cart);
}

interface Colorable {
    public void paint(String color);
}

abstract class Product implements Item {
    private String name;
    private double price;

    Product(String name, double price) throws InvalidPriceException {
        SetName(name);
        setPrice(price);
    }

    public void setPrice(double price) throws InvalidPriceException {
        if (price < 0) {
            throw new InvalidPriceException(price);
        } else {
            this.price = price;
        }
    }

    public void SetName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Item other) {
        return Double.compare(this.price, other.getPrice());
    }
}

class Tax implements Item {
    private double taxRate;
    private Item item;

    public Tax(int taxRatePercent, Item item) {
        if (taxRatePercent < 0 || taxRatePercent > 100) {
            throw new RuntimeException("Invalid tax rate: " + taxRatePercent);

        }
        this.taxRate = taxRatePercent / 100.0;
        SetItem(item);

    }

    public void SetItem(Item item) {
        this.item = item;
    }

    @Override
    public double getPrice() {
        return item.getPrice() + item.getPrice() * taxRate;
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public int compareTo(Item other) {
        return Double.compare(this.getPrice(), other.getPrice());
    }
}

class Discount implements Item {
    private double percent;
    private Item item;

    public Discount(int percentInt, Item item) {
        if (percentInt < 0 || percentInt > 100) {
            throw new RuntimeException("Invalid discount: " + percentInt);
        }
        this.percent = percentInt / 100.0;
        this.item = item;
    }

    @Override
    public double getPrice() {
        return item.getPrice() - item.getPrice() * percent;
    }

    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public int compareTo(Item other) {
        return Double.compare(this.getPrice(), other.getPrice());
    }
}

class CreditCard implements PaymentMethod {
    private long cardNumber;
    private String holderName;
    private Date expirationDate;
    private int cvv;

    public CreditCard(long cardNumber, String holderName, Date expirationDate, int cvv) throws CreditCardException {
        this.holderName = holderName;
        this.expirationDate = expirationDate;
        setCardNumber(cardNumber);
        setCvv(cvv);

    }

    public void setCardNumber(long cardNumber) throws CreditCardException {
        String cardnS = String.valueOf(cardNumber);
        if (cardnS.length() != 16) {
            throw new CreditCardException(cardNumber);
        } else {
            this.cardNumber = cardNumber;
        }
    }

    public void setCvv(int cvv) throws CreditCardException {
        String cvvS = String.valueOf(cvv);
        if (cvvS.length() != 3) {
            throw new CreditCardException(cvv);
        } else {
            this.cvv = cvv;
        }
    }

    @Override
    public boolean pay(List<Item> cart) {
        List<Item> taxedList = new ArrayList<>();
        double total = 0;
        for (Item item : cart) {
            Item taxedItem = new Tax(4, item);
            taxedList.add(taxedItem);
            total += taxedItem.getPrice();
        }
        System.out.println("Paid: " + total + " with " + getClass().getSimpleName() + " " + holderName);

        cart.clear();
        return true;
    }
}

class Paypal implements PaymentMethod {
    private String userName;
    private String password;

    public Paypal(String userName, String password) {
        setUserName(userName);
        SetPassword(password);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void SetPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean pay(List<Item> cart) {
        List<Item> discountdList = new ArrayList<>();
        double total = 0;
        for (Item item : cart) {
            Item discountitem = new Discount(6, item);
            discountdList.add(discountitem);
            total += discountitem.getPrice();
        }
        System.out.println("Paid: " + total + " with " + getClass().getSimpleName());

        cart.clear();

        return true;
    }
}

class Customer {
    private String name;
    private List<Item> cart;
    private PaymentMethod paymentMethod;

    public Customer(String name) {
        setName(name);
        this.cart = new ArrayList<>();

    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean addItem(Item item) {
        if (item instanceof Colorable) {
            item = new Discount(8, item);
        }
        if (item.getPrice() > 500) {
            item = new Tax(20, item);
        }
        item = new Tax(12, item);
        return cart.add(item);
    }

    public boolean removeItem(Item item) {
        return cart.remove(item);
    }

    public boolean purchase() {
        if (paymentMethod == null) {
            System.out.println("No payment method selected.");
            return false;
        }
        return paymentMethod.pay(cart);
    }

    public void display() {
        Collections.sort(cart);
        for (Item sorrt : cart) {
            System.out.println(sorrt.getName() + "-" + sorrt.getPrice());
        }
    }

}

class InvalidPriceException extends RuntimeException {
    private double price;

    public InvalidPriceException(double price) {
        System.out.println("ERROR: Invalid price: " + price);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

class CreditCardException extends Exception {
    private long cardNumber;
    private int cvv;

    public CreditCardException(int cvv) {
        super("ERROR: Invalid cvv" + cvv);
        this.cvv = cvv;
        ;
    }

    public CreditCardException(long cardNumber) {
        super("ERROR: Invalid card number" + cardNumber);
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getCvv() {
        return cvv;
    }
}
