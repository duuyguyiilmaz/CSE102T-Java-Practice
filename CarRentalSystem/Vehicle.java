
class Vehicle {

    private String model;
    private int year;
    private String licensePlate;
    private boolean available;
    private int mileage;

    public Vehicle(String model, int year, String licensePlate, int mileage) {

        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.mileage = mileage;
        this.available = true;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getMileage() {
        return mileage;
    }

    public void setAvailable(boolean available) {

        this.available = available;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String rent() {
        if (available) {
            this.available = false;
            return "Vehicle rented successfully.";
        } else {
            return "Vehicle is not available for rent.";
        }
    }

    public void returnVehicle(int additionalMileage) {

        if (!available) {
            this.mileage += additionalMileage;
            this.available = true;
            System.out.println("Vehicle returned successfully.");
        } else {
            System.out.println("Vehicle is already available.");
        }
    }

    public String vehicleDetails() {
        return "Model: " + model + ", Year: " + year + ", License Plate: " + licensePlate
                + ", Mileage: " + mileage + ", Available: " + available;
    }
}
