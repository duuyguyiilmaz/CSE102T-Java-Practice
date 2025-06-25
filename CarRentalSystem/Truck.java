
class Truck extends Vehicle {

    private int loadCapacity;

    public Truck(String model, int year, String licensePlate, int mileage, int loadCapacity) {
        super(model, year, licensePlate, mileage);
        this.loadCapacity = loadCapacity;
    }

    public int getLoadCapacity() {
        return loadCapacity;

    }

    public void setLoadCapacity(int loadCapacity) {
        if (loadCapacity > 0) {
            this.loadCapacity = loadCapacity;
        }
    }

    @Override
    public String vehicleDetails() {
        return super.vehicleDetails() + ", Load Capacity: " + loadCapacity + " tons";

    }
}
