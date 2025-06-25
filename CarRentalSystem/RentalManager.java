
class RentalManager {

    private Vehicle[] vehicleArray;
    private int count;

    public RentalManager(int capacity) {
        vehicleArray = new Vehicle[capacity];
        this.count = 0;

    }

    public void addVehicle(Vehicle vehicle) {
        if (count < vehicleArray.length) {
            vehicleArray[count] = vehicle;
            count++;
        } else {
            System.out.println("No space available to add more vehicles.");
        }
    }

    public void listVehicles() {
        for (int i = 0; i < count; i++) {
            System.out.println("Index: " + i + ", " + vehicleArray[i].vehicleDetails());
        }
    }

    public void rentVehicle(int index) {
        if (index >= 0 && index < count) {
            String result = vehicleArray[index].rent();
            System.out.println(result);
        } else {
            System.out.println("Invalid vehicle index.");
        }
    }

    public void returnVehicle(int index, int additionalMileage) {
        if (index >= 0 && index < count) {
            vehicleArray[index].returnVehicle(additionalMileage);
        } else {
            System.out.println("Invalid vehicle index.");
        }
    }
}
