
public class CarRentalSystem {

    public static void main(String[] args) {
        // Create a RentalManager with capacity for 5 vehicles.
        RentalManager manager = new RentalManager(5);

        // Create instances of different vehicle types.
        Car car1 = new Car("Renault Clio", 2020, "34 ABC 123", 15000, 300);
        Truck truck1 = new Truck("Mercedes Actros", 2018, "06 DEF 456", 80000, 18);
        Motorcycle motorcycle1 = new Motorcycle("Yamaha YZF-R3", 2021, "35 GHI 789", 5000, 42);

        // Add vehicles to the system.
        // manager.addVehicle(car1);
        manager.addVehicle(truck1);
        manager.addVehicle(motorcycle1);

        // Test functionalities:
        System.out.println("==== Initial Vehicle List ====");
        manager.listVehicles();

        // Test renting car1.
        System.out.println("\n==== Car Rent Test ====");
        manager.rentVehicle(0); // Rent car1 (index 0)
        // Attempt to rent car1 again (should display an error message).
        manager.rentVehicle(0);

        // Test renting truck1.
        System.out.println("\n==== Truck Rent Test ====");
        manager.rentVehicle(1);

        // List vehicles to see current statuses.
        System.out.println("\n==== Updated Vehicle List ====");
        manager.listVehicles();

        // Test returning car1 with additional mileage.
        System.out.println("\n==== Car Return Test ====");
        manager.returnVehicle(0, 120); // Add 120 km to car1

        // List vehicles after return.
        System.out.println("\n==== Vehicle List After Return ====");
        manager.listVehicles();

        // Test renting and returning the motorcycle.
        System.out.println("\n==== Motorcycle Rent and Return Test ====");
        manager.rentVehicle(2);
        manager.returnVehicle(2, 80); // Add 80 km to motorcycle

        System.out.println("\n==== Final Vehicle Status ====");
        manager.listVehicles();
    }
}
