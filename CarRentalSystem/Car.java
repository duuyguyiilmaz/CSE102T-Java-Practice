
class Car extends Vehicle {

    private int trunkCapacity;

    public Car(String model, int year, String licensePlate, int mileage, int trunkCapacity) {
        super(model, year, licensePlate, mileage);
        this.trunkCapacity = trunkCapacity;
    }

    public int getTrunkCapacity() {
        return trunkCapacity;
    }

    public void setTrunkCapacity(int trunkCapacity) {

        if (trunkCapacity > 0) {
            this.trunkCapacity = trunkCapacity;
        }
    }

    @Override
    public String vehicleDetails() {
        return super.vehicleDetails() + ", Trunk Capacity: " + trunkCapacity + " liters";

    }
}
