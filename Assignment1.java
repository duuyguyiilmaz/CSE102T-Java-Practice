import java.util.*;

public class Assignment1 {
    public static void main(String[] args) {
        Warrior w = new Warrior("DUYGU", 100, 20, 6, 3, 60);
        Mage m = new Mage("GANDALF", 70, 15, 2, 1, 30);
        Archer a = new Archer("Robin", 80, 18, 4, 2, 75);
        Item sword = new Item("Sword of Might", "ATTACK", 5);
        w.addItemToInventory(sword);
        w.equipItemFromInventory(0);
        w.warriorAttack(m);

    }
}

class Item {
    private String name;
    private String bonusType;
    private int bonusValue;

    public Item(String name, String bonusType, int bonusValue) {
        this.name = name;
        this.bonusType = bonusType;
        this.bonusValue = bonusValue;
    }

    public String getName() {
        return name;
    }

    public String getBonusType() {
        return bonusType;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    public String toString() {
        return getName() + " [" + getBonusType() + "Bonus:" + getBonusValue() + "]";
    }
}

class Character {
    protected String name;
    private int health;
    protected int attackPower;
    Item[] inventory;
    Item[] equipment;
    protected int inventoryCapacity;
    protected int equipmentCapacity;
    int bonushealth = 0;
    int bonusaccuracy = 0;
    int bonusmana = 0;
    int bonusAttackPower = 0;

    public Character(String name, int health, int attackPower, int inventoryCapacity, int equipmentCapacity) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.inventoryCapacity = inventoryCapacity;
        this.equipmentCapacity = equipmentCapacity;
        this.equipment = new Item[equipmentCapacity];
        this.inventory = new Item[inventoryCapacity];
    }

    public void takeDamage(int damage) {
        health = (health + bonushealth) - damage;
        if (health < 0) {
            health = 0;
        }
        System.out.println(name + " takes " + damage + " damage. (Current Health: " + health + ")");

    }

    public String toString() {
        return name + " [Current Health: " + (health) + ", Total Attack: " + (attackPower + bonusAttackPower) + ", ";
    }

    public void addItemToInventory(Item ıtem) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = ıtem;
                break;
            }
        }
    }

    public void removeItemFromInventory(int index) {
        if (index < 0 || index >= inventory.length || inventory[index] == null) {
            System.out.println("Invalid index.");
            return;
        }
        for (int i = index; i < inventory.length - 1; i++) {
            inventory[i] = inventory[i + 1];
        }
        inventory[inventory.length - 1] = null;
    }

    public void equipItemFromInventory(int invIndex) {
        if (invIndex < 0 || invIndex >= inventory.length || inventory[invIndex] == null) {
            System.out.println("Invalid index");
            return;
        }
        for (int j = 0; j < equipment.length; j++) {
            if (equipment[j] == null) {
                equipment[j] = inventory[invIndex];

                System.out.println(name + " equips " + equipment[j].getName() + " from inventory." + " (Bonus "
                        + equipment[j].getBonusType() + ": " + equipment[j].getBonusValue() + ")");
                if (equipment[j].getBonusType().equals("HEALTH")) {
                    bonushealth += equipment[j].getBonusValue();
                }
                if (equipment[j].getBonusType().equals("ATTACK")) {
                    bonusAttackPower += equipment[j].getBonusValue();
                }
                if (equipment[j].getBonusType().equals("MANA") && this instanceof Mage) {
                    bonusmana += equipment[j].getBonusValue();
                }
                if (equipment[j].getBonusType().equals("ACCURACY") && this instanceof Archer) {
                    bonusaccuracy += equipment[j].getBonusValue();
                }
                removeItemFromInventory(invIndex);
                return;
            }
        }
    }

    public void unequipItem(int equipIndex) {
        if (equipIndex < 0 || equipIndex >= equipment.length || equipment[equipIndex] == null) {
            System.out.println("Invalid index.");
            return;
        }
        Item removed = equipment[equipIndex];
        addItemToInventory(removed);
        System.out.println(name + " removes " + equipment[equipIndex].getName() + " from equipment. (BONUS "
                + equipment[equipIndex].getBonusType() + ": 0)");
        switch (removed.getBonusType()) {
            case "HEALTH":
                bonushealth -= removed.getBonusValue();
                health -= bonushealth;
                break;
            case "ATTACK":
                bonusAttackPower -= removed.getBonusValue();
                attackPower -= bonusAttackPower;
                break;
            case "MANA":
                if (this instanceof Mage) {
                    bonusmana -= removed.getBonusValue();
                }
                break;
            case "ACCURACY":
                if (this instanceof Archer) {
                    bonusaccuracy -= removed.getBonusValue();
                }
                break;
        }
        equipment[equipIndex] = null;
        for (int i = equipIndex; i < equipment.length - 1; i++) {
            equipment[i] = equipment[i + 1];
        }
        equipment[equipment.length - 1] = null;
    }

}

class Warrior extends Character {
    private double armor;

    public Warrior(String name, int health, int attackPower, int inventoryCapacity, int equipmentCapacity,
            double armor) {
        super(name, health, attackPower, 6, 3);
        this.armor = armor;
    }

    public void warriorTakeDamage(int damage, boolean IgnoreArmor) {
        if (IgnoreArmor == true || armor == 0) {
            takeDamage(damage);
        } else {
            System.out.println(this.name + "'s damage reduced.");
            if (armor >= 50) {
                int damagereduction = (int) Math.round(damage * 90.0 / 100.0);
                takeDamage(damagereduction);
            } else {
                int damagereduction2 = (int) Math.round(damage * 95.0 / 100.0);
                takeDamage(damagereduction2);
            }
        }
        armor -= 10;
        System.out.println(name + " Remaning Armor: " + armor);
    }

    public void warriorAttack(Character target) {
        int totalAttackPower = attackPower + bonusAttackPower;
        System.out.println(name + " attacks " + target.name + " with total power " + totalAttackPower);
        if (target instanceof Warrior) {
            ((Warrior) target).warriorTakeDamage(attackPower, false);
        } else {
            target.takeDamage(totalAttackPower);
        }
    }

    @Override
    public String toString() {

        return "Warrior " + super.toString() + "Current Armor: " + armor + ", " + "Bonus Health: " + bonushealth
                + ", Bonus AttackPower: " + bonusAttackPower + "]";
    }
}

class Mage extends Character {
    private int mana;

    public Mage(String name, int health, int attackPower, int inventoryCapacity, int equipmentCapacity, int mana) {
        super(name, health, attackPower, 2, 1); // inventory = 2, equipment = 1
        this.mana = mana;
    }

    public void mageAttack(Character target) {
        int totalAttackPower = attackPower + bonusAttackPower;
        int totalmana = mana + bonusmana;

        if (mana >= 10) {
            System.out.println(
                    this.name + " casts a spell on " + target.name + " dealing " + (totalAttackPower + 5) + " damage!");
            if (target instanceof Warrior) {
                ((Warrior) target).warriorTakeDamage(totalAttackPower + 5, false);
            } else {
                target.takeDamage(totalAttackPower + 5);
            }
            if (mana > 10) {
                mana -= 10;
            } else {
                mana = 0;
            }
            System.out.println(this.name + "'s Remaning Mana: " + mana);
        } else {
            System.out.println(
                    this.name + " casts a spell on " + target.name + " dealing " + totalAttackPower + " damage!");
            if (target instanceof Warrior) {
                ((Warrior) target).warriorTakeDamage(totalAttackPower, false);
            } else {
                target.takeDamage(totalAttackPower);
            }
        }
    }

    public String toString() {
        return "Mage " + super.toString() + "Current mana: " + mana + ", Bonus Health: " + bonushealth
                + ", Bonus AttackPower: " + bonusAttackPower + ", Bonus MANA: " + bonusmana + "]";
    }
}

class Archer extends Character {
    private int accuracy;

    public Archer(String name, int health, int attackPower, int inventoryCapacity, int equipmentCapacity,
            int accuracy) {
        super(name, health, attackPower, 4, 2);
        this.accuracy = accuracy;
    }

    public void archerAttack(Character target) {
        int totalAttackPower = attackPower + bonusAttackPower;
        int totalAccuracy = accuracy + bonusaccuracy;
        if (totalAccuracy >= 80) {
            totalAttackPower = totalAttackPower + 5;
        }
        System.out.println(name + " attacks " + target.name + " with total power " + totalAttackPower);
        if (target instanceof Warrior) {
            ((Warrior) target).warriorTakeDamage(totalAttackPower, false);

        } else {
            target.takeDamage(totalAttackPower);
        }

    }

    public String toString() {

        return "Archer " + super.toString() + "Accuracy: " + accuracy + ", Bonus Health: " + bonushealth
                + ", Bonus AttackPower: " + bonusAttackPower + ", Bonus ACCURACY: " + bonusaccuracy + "]";
    }
}
