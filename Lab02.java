
public class Lab02 {

    public static void main(String[] args) {
        Car car1 = new Car("RANGE OVER", "BLACK", 4);
        car1.start();
        Item item1 = new Item("Toy", 55);
        Wallet wallet1 = new Wallet("Purple", 50);
        Person person = new Person("Duygu", car1, wallet1);
        Brain brain = new Brain(person, "medium");
        System.out.println("Person is: " + person.getName());
        System.out.println("Persons car is: " + car1.toString());
        System.out.println("ıtem's price is " + item1.getValue());
        System.out.println("Duygu's wallet filled : " + wallet1.getAmount());
        System.out.println("Does Duygu buy this item ?");
        person.buy(item1);
        System.out.println(brain.getSize());
        System.out.println(brain.remember());

    }
}

class Car {
    private String model;
    private String color;
    private int speed;
    private boolean isOn;
    private int acceralation;

    Car(String model, int par, String color, int par1, int par2) {
        this.model = model;
        this.color = color;
    }

    Car(String model, String color, int acceralation) {
        this(model, 2020, color, 15000, 300);
        this.acceralation = acceralation;
    }

    public void setAccarelation(int acceralation) {
        this.acceralation = acceralation;
    }

    public void acceralate() {
        if (isOn) {
            speed += acceralation;
        }
    }

    public void acceralate(int acceralation) {
        this.acceralation = acceralation;
        acceralate();
    }

    public void start() {
        if (!isOn) {
            isOn = true;
            acceralate();
            System.out.println("The car started");
        } else {
            System.out.println("The car alderay started");
        }
    }

    public void stop() {
        if (isOn) {
            speed = 0;
            isOn = false;
            System.out.println("Car has stopped");
        } else {
            System.out.println("Car has alderay stopped");
        }
    }

    public String toString() {
        if (!isOn) {
            return String.format("Color: %s", "Model:%s", color, model);
        } else {
            return String.format("Color: %s, Model:%s, Current Speed: %d, Acceleration:%d", color, model, speed,
                    acceralation);
        }
    }
}

class Item {
    private String name;
    private int value;

    Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return String.format("%s (%d)", name, value);
    }
}

class Wallet {
    private String color;
    private int amount;

    Wallet(String color) {
        this.color = color;
    }

    Wallet(String color, int amount) {
        this(color);
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public int getAmount() {
        return amount;
    }

    public void addMoney(int money) {
        if (money > 0) {
            amount += money;
        } else {
            throw new ArithmeticException("The Money cannot be negative");
        }
    }

    public void removeMoney(int money) {
        if (money <= amount) {
            amount -= money;
        } else {
            throw new IllegalArgumentException("The money cannot bigger than amount");

        }
    }

    @Override
    public String toString() {
        return String.format("Wallet", color);
    }
}

class Brain {
    private String size;
    private Person person;

    Brain(Person person) {
        this.person = person;
        size = "Medium";
    }

    Brain(Person person, String size) {
        Setperson(person);
        Setsize(size);
    }

    public void Setperson(Person person) {
        this.person = person;
    }

    public void Setsize(String size) {
        this.size = size;
    }

    public Person getPedrson() {
        return person;
    }

    public String getSize() {
        return size;
    }

    public int chance() {
        if (size.equalsIgnoreCase("small")) {
            return 1;
        } else if (size.equalsIgnoreCase("medium")) {
            return 2;
        } else {
            return 3;
        }
    }

    public boolean remember() {
        double chance = Math.random() * 4;
        if (chance < chance()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return person.getName() + " " + size;
    }
}

class Person {

    private String name;
    private Car car;
    private Wallet wallet;
    private Brain brain;

    Person(String name) {
        this.name = name;
        this.brain = new Brain(null);
    }

    Person(String name, Car car, Wallet wallet) {
        this.name = name;
        this.car = car;
        this.wallet = wallet;
        this.brain = new Brain(null);
    }

    public boolean think() {
        return brain.remember();
    }

    public String getName() {
        return name;
    }

    public void drive() {
        if (car != null) {
            car.start();
            if (think()) {
                car.acceralate();
            }
        }
    }

    public void buy(Item item) {
        if (wallet != null) {
            if (wallet.getAmount() < item.getValue()) {
                System.out.println("you don't have enough money.");
            } else {
                wallet.removeMoney(item.getValue());
                System.out.println("You bought the item!");
            }
        } else {
            System.out.println("You dont even have a wallet.");
        }
    }

}
