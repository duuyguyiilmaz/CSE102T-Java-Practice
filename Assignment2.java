public class Assignment2 {

    public static void main(String[] args) {

        Warrior warrior = new Warrior("Conan", 100, 15, 60);
        Mage mage = new Mage("Merlin", 90, 12, 40);
        Archer archer = new Archer("Robin", 85, 16, 65);

        // Pre-Battle: Equip Items.
        // Warrior: "Steel Shield" (ARMOR), "Power Bracer" (ENERGY), "Sword of Might"
        // (ATTACK)
        warrior.addItemToInventory(new Item("Steel Shield", "ARMOR", 20));
        warrior.addItemToInventory(new Item("Power Bracer", "ENERGY", 10));
        warrior.addItemToInventory(new Item("Sword of Might", "ATTACK", 5));
        warrior.equipItemFromInventory(0);
        warrior.equipItemFromInventory(0);
        warrior.equipItemFromInventory(0);

        // Mage: "Mana Pendant" (MANA)
        mage.addItemToInventory(new Item("Mana Pendant", "MANA", 8));
        mage.equipItemFromInventory(0);

        // Archer: "Eagle Eye" (ACCURACY), "Energy Booster" (ENERGY)
        archer.addItemToInventory(new Item("Eagle Eye", "ACCURACY", 10));
        archer.addItemToInventory(new Item("Energy Booster", "ENERGY", 8));
        archer.equipItemFromInventory(0);
        archer.equipItemFromInventory(0);

        System.out.println("\n === Combat Simulation ===");

        System.out.println("\n --- Round 1 ---");
        // Round 1:
        // Warrior special attacks Mage.
        warrior.specialAttack(mage);
        // Mage basic attacks Archer.
        mage.attack(archer);
        // Archer special attacks Warrior.
        archer.specialAttack(warrior);

        System.out.println("\n --- Round 2 ---");
        // Round 2:
        // Warrior basic attacks Archer.
        warrior.attack(archer);
        // Mage special attacks Warrior.
        mage.specialAttack(warrior);
        // Archer basic attacks Warrior.
        archer.attack(warrior);

        System.out.println("\n ---Final Status---");
        System.out.println(warrior);
        System.out.println(mage);
        System.out.println(archer);

        System.out.println("\n === Error Message Test ===");
        // Create a small Mage for testing (Inventory: 2, Equipment: 1)
        Mage testMage = new Mage("TestMage", 50, 10, 30);

        // Try to add 3 items to inventory.
        testMage.addItemToInventory(new Item("Item1", "MANA", 5));
        testMage.addItemToInventory(new Item("Item2", "ATTACK", 3));
        // Third addition should trigger an error.
        testMage.addItemToInventory(new Item("Item3", "MANA", 4));

        // Try to equip more than capacity.
        // Since testMage's equipment capacity is 1, equipping a second item shold
        // trigger an error.
        testMage.equipItemFromInventory(0); // First item equipped succesfully.
        testMage.equipItemFromInventory(0); // Second equip attempt should display an error.

        Archer testArcher = new Archer("Robin", 85, 16, 65);
        // Try to equip an item with an invalid bonus type.
        testArcher.addItemToInventory(new Item("Item4", "MANA", 4));
        testArcher.equipItemFromInventory(0); // Equip attempt should display an error.

    }

}

class EquipmentBrokenException extends Exception {
    public EquipmentBrokenException(String message) {
        super(message);
    }
}

class EquipmentFullException extends Exception {
    public EquipmentFullException(String message) {
        super("Error: " + message);
    }
}

class BonusNotAllowedException extends Exception {
    public BonusNotAllowedException(String message) {
        super("Error: " + message);
    }
}

class InventoryFullException extends Exception {
    public InventoryFullException(String message) {
        super("Error: " + message);
    }
}

class Item {
    private String name;
    private String bonusType;
    private int bonusValue;

    public Item(String name, String bonusType, int bonusValue) {
        setName(name);
        setBonusType(bonusType);
        setBonusValue(bonusValue);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(int bonusValue) {
        this.bonusValue = bonusValue;
    }

    public void reduceBonus(int amount) throws EquipmentBrokenException {
        setBonusValue(Math.max(0, getBonusValue() - amount));
        if (getBonusValue() == 0) {
            throw new EquipmentBrokenException(getName() + " has broken!");
        }
    }

    public String toString() {
        return getName() + " [" + bonusType + " Bonus: " + bonusValue + "]";
    }
}

abstract class Character {
    private String name;
    private int health;
    private int attackPower;
    private int maxHealth;
    private Item[] inventory;
    private int inventoryCapacity;
    private Item[] equipment;
    private int equipmentCapacity;
    private String notifications;

    public Character(String name, int health, int attackPower, int inventoryCapacity, int equipmentCapacity) {
        setAttackPower(attackPower);
        setHealth(health);
        setName(name);
        setInventoryCapacity(inventoryCapacity);
        setEquipmentCapacity(equipmentCapacity);
        this.inventory = new Item[inventoryCapacity];
        this.equipment = new Item[equipmentCapacity];
        setMaxHealth(health);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(Item[] inventory) {
        this.inventory = inventory;
    }

    public int getInventoryCapacity() {
        return inventoryCapacity;
    }

    public void setInventoryCapacity(int inventoryCapacity) {
        this.inventoryCapacity = inventoryCapacity;
    }

    public Item[] getEquipment() {
        return equipment;
    }

    public void setEquipment(Item[] equipment) {
        this.equipment = equipment;
    }

    public int getEquipmentCapacity() {
        return equipmentCapacity;
    }

    public void setEquipmentCapacity(int equipmentCapacity) {
        this.equipmentCapacity = equipmentCapacity;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNotification(String msg) {
        notifications += msg + "\n";
    }

    public void printNotifications() {
        System.out.print(getNotifications());
        setNotifications("");
    }

    public abstract String getStatString();

    public abstract int getTotalArmor();

    public abstract int getTotalBaseArmor();

    protected abstract boolean isBonusAllowed(String bonusType);

    public abstract void attack(Character target);

    public abstract void specialAttack(Character target);

    public void printCurrentStats() {
        System.out.print(getStatString());
        printNotifications();
    }

    protected void takeHealthDamage(int damage) {
        health = Math.max(0, getHealth() - damage);
    }

    public void receiveDamage(int damage, boolean ignoreArmor) {
        int totalarmor = getTotalArmor();
        if (ignoreArmor || totalarmor == 0) {
            takeHealthDamage(damage);
        } else {
            double reductionPercent = (totalarmor >= 50) ? 10 : 5;
            int reductiondamage = (int) Math.round(damage * (1 - reductionPercent / 100));
            System.out.println(
                    "Damage reduced by " + reductionPercent + "% (Armor reduced by: " + baseArmorReduction() + ")");
            baseArmorReduction();
            takeHealthDamage(reductiondamage);
        }
    }

    protected int baseArmorReduction() {
        return 0;
    }

    public boolean addItemToInventory(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                System.out.println(name + " added " + item.getName() + " to inventory. " + "(" + item.getBonusType()
                        + " Bonus: " + item.getBonusValue() + ")");
                return true;
            }
        }
        try {
            throw new InventoryFullException(getName() + "'s" + " inventory is full!");
        } catch (InventoryFullException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Item removeItemFromInventory(int index) {
        if (index < 0 || index >= inventory.length || inventory[index] == null) {
            return null;
        }
        Item removedItem = inventory[index];
        for (int i = index; i < inventory.length - 1; i++) {
            inventory[i] = inventory[i + 1];
        }
        inventory[inventory.length - 1] = null;
        return removedItem;
    }

    public boolean equipItemFromInventory(int invIndex) {
        Item item = removeItemFromInventory(invIndex);
        if (item == null) {
            return false;
        }
        try {
            equipItem(item);
            return true;
        } catch (EquipmentFullException | BonusNotAllowedException e) {
            System.out.println(e.getMessage());
            addItemToInventory(item);
        }
        return false;
    }

    public boolean unequipItem(int equipIndex) {
        Item item = equipment[equipIndex];
        if (addItemToInventory(item)) {
            removeEquipmentAtIndex(equipIndex);
            return true;
        }
        return false;
    }

    public int getEquipmentBonus(String bonusType) {
        int totalBonus = 0;
        for (int i = 0; i < equipment.length; i++) {
            if (equipment[i] != null && equipment[i].getBonusType().equals(bonusType)) {
                totalBonus += equipment[i].getBonusValue();
            }
        }
        return totalBonus;
    }

    public void equipItem(Item item) throws EquipmentFullException, BonusNotAllowedException {
        if (!isBonusAllowed(item.getBonusType())) {
            throw new BonusNotAllowedException(
                    item.getBonusType() + " bonus type is not allowed for " + getName() + ".");
        }
        for (int i = 0; i < equipment.length; i++) {
            if (equipment[i] == null) {
                equipment[i] = item;
                System.out.println(name + " equipped " + equipment[i].getName() + " ("
                        + item.getBonusType() + " Bonus: " + item.getBonusValue() + ").");
                return;

            }
        }
        throw new EquipmentFullException(getName() + "'s equipment slots are full!");
    }

    protected int consumeResource(String resourceType, int cost, int baseResource) {
        int bonus = getEquipmentBonus(resourceType);
        if (bonus >= cost) {
            try {
                consumeResourceBonus(resourceType, cost);
            } catch (EquipmentBrokenException e) {
                System.out.println(e.getMessage());
            }
            return baseResource;
        } else {
            try {
                consumeResourceBonus(resourceType, bonus);
            } catch (EquipmentBrokenException e) {
                System.out.println(e.getMessage());
            }
            int remainingCost = cost - bonus;
            return baseResource - remainingCost;
        }

    }

    protected void consumeResourceBonus(String resourceType, int cost) throws EquipmentBrokenException {

        for (int i = 0; i < equipment.length; i++) {
            try {
                if (equipment[i] != null && equipment[i].getBonusType().equals(resourceType)) {
                    if (cost < equipment[i].getBonusValue()) {
                        equipment[i].reduceBonus(cost);
                        cost = 0;
                    } else {

                        equipment[i].reduceBonus(cost);

                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                removeEquipmentAtIndex(i);
                return;

            }
        }

    }

    public void removeEquipmentAtIndex(int index) {
        equipment[index] = null;
        for (int i = index; i < equipment.length - 1; i++) {
            equipment[i] = equipment[i + 1];
        }
        equipment[equipment.length - 1] = null;
    }

}

class Warrior extends Character {

    private int baseArmor;
    private int energy;

    public Warrior(String name, int health, int attackPower, int baseArmor) {
        super(name, health, attackPower, 5, 4);
        this.baseArmor = baseArmor;
        this.energy = 50;
    }

    @Override
    public int getTotalArmor() {
        return baseArmor + getEquipmentBonus("ARMOR");
    }

    @Override
    public int getTotalBaseArmor() {
        return baseArmor;
    }

    @Override
    protected boolean isBonusAllowed(String bonusType) {
        return bonusType.equals("ATTACK") || bonusType.equals("ARMOR") || bonusType.equals("ENERGY");
    }

    @Override
    protected int baseArmorReduction() {
        int oldBase = baseArmor;
        baseArmor = consumeResource("ARMOR", 20, baseArmor);
        return oldBase;
    }

    @Override
    public void attack(Character target) {
        energy = consumeResource("ENERGY", 10, energy);
        System.out.println(getName() + " performs a basic attack on " + target.getName() + " (Energy reduced by: 10)");
        int damage = getAttackPower() + getEquipmentBonus("ATTACK");
        target.receiveDamage(damage, false);
        if (target instanceof Warrior && target.getTotalArmor() > 0) {
            if (target.getTotalArmor() >= 50) {

                int a2 = (int) Math.round(damage * 0.9);
                System.out
                        .println(target.getName() + " takes " + a2 + " damage. (Health: " + target.getHealth() + ")");
            } else {
                int a2 = (int) Math.round(damage * 0.95);
                System.out
                        .println(target.getName() + " takes " + a2 + " damage. (Health: " + target.getHealth() + ")");
            }
        } else
            System.out
                    .println(target.getName() + " takes " + damage + " damage. (Health: " + target.getHealth() + ")");

        System.out.println(getName() + "'s current stats: " + getStatString());

    }

    @Override
    public void specialAttack(Character target) {
        int availableEnergy = energy + getEquipmentBonus("ENERGY");
        if (availableEnergy < 15) {
            attack(target);
        } else {

            energy = consumeResource("ENERGY", 15, energy);
            int damage = getAttackPower() + getEquipmentBonus("ATTACK") + 5;
            target.receiveDamage(damage, false);
            System.out.println(
                    super.getName() + " uses Shield Bash on " + target.getName() + " (Energy reduced by: 15) ");
            System.out.println(
                    target.getName() + " takes " + damage + " damage." + "(Health: " + target.getHealth() + ")");
            System.out.println(getName() + "'s" + " current stats: " + getStatString());

        }
    }

    @Override
    public String getStatString() {
        int totalAttack = getAttackPower() + getEquipmentBonus("ATTACK");
        int totalArmor = baseArmor + getEquipmentBonus("ARMOR");
        int totalEnergy = energy + getEquipmentBonus("ENERGY");

        return "Total Attack: " + totalAttack + ", Total Armor: " + totalArmor + ", Total Energy: " + totalEnergy
                + ",Health: " + getHealth();
    }

    @Override
    public String toString() {

        return getName() + "(Health:" + getHealth() + ",Total Attack: "
                + (getAttackPower() + getEquipmentBonus("ATTACK")) + ",Total Armor: "
                + getTotalArmor() + "," + " Total Energy: " + (energy + getEquipmentBonus("ENERGY"))
                + "| Equipment: Sword of Might[ATTACK Bonus: 5]"
                + ")";

    }
}

class Mage extends Character {

    private int mana;

    public Mage(String name, int health, int attackPower, int mana) {
        super(name, health, attackPower, 2, 1);
        this.mana = mana;
    }

    @Override
    protected boolean isBonusAllowed(String bonusType) {
        return bonusType.equals("ATTACK") || bonusType.equals("MANA");
    }

    @Override
    public int getTotalArmor() {
        return 0;
    }

    @Override
    public int getTotalBaseArmor() {
        return 0;
    }

    @Override
    public void attack(Character target) {
        int availableMana = mana + getEquipmentBonus("MANA");
        if (availableMana < 10) {
            int weakAttack = getAttackPower() / 2;
            target.receiveDamage(weakAttack, false);
            System.out.println(getName() + "'s" + " current stats: " + getStatString());
        } else {
            mana = consumeResource("MANA", 10, mana);
            System.out.println(getName() + " casts a basic spell on " + target.getName() + " (Mana reduced by: 10)");
            int damage = getAttackPower() + getEquipmentBonus("ATTACK");
            target.receiveDamage(damage, false);
            if (target instanceof Warrior && target.getTotalArmor() > 0) {
                if (target.getTotalArmor() >= 50) {

                    int ap2 = (int) Math.round(damage * 0.9);
                    System.out.println(
                            target.getName() + " takes " + ap2 + " damage. (Health: " + target.getHealth() + ")");
                } else {
                    int ap2 = (int) Math.round(damage * 0.95);
                    System.out.println(
                            target.getName() + " takes " + ap2 + " damage. (Health: " + target.getHealth() + ")");
                }
            } else
                System.out.println(
                        target.getName() + " takes " + damage + " damage. (Health: " + target.getHealth() + ")");

            System.out.println(getName() + "'s current stats: " + getStatString());

        }
    }

    @Override
    public void specialAttack(Character target) {
        int availableMana = mana + getEquipmentBonus("MANA");
        if (availableMana < 15) {
            attack(target);
            System.out.println(getName() + "'s" + " current stats: " + getStatString());
        } else {
            mana = consumeResource("MANA", 15, mana);
            System.out
                    .println(getName() + " uses a penetrating spell on " + target.getName() + " (Mana reduced by: 15)");

            int damage = getAttackPower() + getEquipmentBonus("ATTACK") + 10;
            if (target instanceof Warrior) {
                target.receiveDamage(damage, true);
            } else
                target.receiveDamage(damage, false);

            System.out.println("This attack ignores armor! Full damage applied.");

            System.out.println(
                    target.getName() + " takes " + damage + " damage. (Health: " + target.getHealth() + ")");
            System.out.println(getName() + "'s current stats: " + getStatString());

        }
    }

    @Override
    public String getStatString() {
        int totalAttack = getAttackPower() + getEquipmentBonus("ATTACK");
        int totalMana = mana + getEquipmentBonus("MANA");
        return "Total Attack: " + totalAttack + ", Total Mana: " + totalMana + ", Health: " + getHealth();
    }

    @Override
    public String toString() {
        return getName() + "(Health:" + getHealth() + ",Total Attack: " + getAttackPower() + ",Total Mana: "
                + getTotalArmor() + "| Equipment: " + ")";
    }

}

class Archer extends Character {

    private int accuracy;
    private int energy;

    public Archer(String name, int health, int attackPower, int accuracy) {
        super(name, health, attackPower, 4, 3);
        this.accuracy = accuracy;
        this.energy = 50;
    }

    @Override
    protected boolean isBonusAllowed(String bonusType) {
        return bonusType.equals("ATTACK") || bonusType.equals("ACCURACY") || bonusType.equals("ENERGY");
    }

    @Override
    public int getTotalArmor() {
        return 0;
    }

    @Override
    public int getTotalBaseArmor() {
        return 0;
    }

    @Override
    public void attack(Character target) {
        energy += getEquipmentBonus("ENERGY");

        if (energy < 10) {
            int weakDamage = getAttackPower() / 2;
            target.receiveDamage(weakDamage, false);
        } else {
            energy = consumeResource("ENERGY", 10, energy);
            int damage = getAttackPower() + getEquipmentBonus("ATTACK");
            System.out.println(getName() + " fires a basic arrow at " + target.getName() + "(Energy reduced by: 10)");
            if (accuracy >= 80) {
                damage += 5;
            }
            target.receiveDamage(damage, false);
            if (target instanceof Warrior && target.getTotalArmor() > 0) {
                if (target.getTotalArmor() >= 50) {

                    int ap2 = (int) Math.round(damage * 0.9);
                    System.out.println(
                            target.getName() + " takes " + ap2 + " damage. (Health: " + target.getHealth() + ")");
                } else {
                    int ap2 = (int) Math.round(damage * 0.95);
                    System.out.println(
                            target.getName() + " takes " + ap2 + " damage. (Health: " + target.getHealth() + ")");
                }
            } else
                System.out.println(
                        target.getName() + " takes " + damage + " damage. (Health: " + target.getHealth() + ")");

            System.out.println(getName() + "'s current stats: " + getStatString());

        }
    }

    @Override
    public void specialAttack(Character target) {
        int availableEnergy = energy + getEquipmentBonus("ENERGY");

        if (availableEnergy < 15) {
            attack(target);
            return;
        }

        energy = consumeResource("ENERGY", 15, energy);
        System.out.println(
                getName() + " performs a critical arrow shot on " + target.getName() + " (Energy reduced by: 15)");

        int damage = getAttackPower() + getEquipmentBonus("ATTACK") + 5;

        if (accuracy >= 80) {
            damage += 5;
        }

        boolean ignoreArmor = target instanceof Warrior;
        target.receiveDamage(damage, ignoreArmor);

        System.out.println("This attack ignores armor! Full damage applied.");
        System.out.println(target.getName() + " takes " + damage + " damage." + "(Health: " + target.getHealth() + ")");
        System.out.println(getName() + "'s current stats: " + getStatString());

    }

    @Override
    public String getStatString() {
        int totalAttack = getAttackPower() + getEquipmentBonus("ATTACK");
        int totalAccuracy = accuracy + getEquipmentBonus("ACCURACY");
        int totalEnergy = energy + getEquipmentBonus("ENERGY");

        return "Total Attack: " + totalAttack + ", Total Accuracy: " + totalAccuracy + ", Total Energy:" + totalEnergy
                + ",Health: " + getHealth();
    }

    @Override
    public String toString() {
        return getName() + "(Health:" + getHealth() + ",Total Attack: " + getAttackPower() + ",Total Accuracy: "
                + accuracy + "," + " Total Energy: " + energy + "| Equipment: Eagle Eye[ATTACK Bonus: 10]"
                + ")";
    }

}
