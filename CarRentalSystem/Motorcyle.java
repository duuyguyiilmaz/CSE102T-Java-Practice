
class Motorcycle extends Vehicle {

    private int enginePower;

    public Motorcycle(String model, int year, String licensePlate, int mileage, int enginePower) {
        super(model, year, licensePlate, mileage);
        this.enginePower = enginePower;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {

        if (enginePower > 0) {
            this.enginePower = enginePower;
        }
    }

    @Override
    public String vehicleDetails() {
        return super.vehicleDetails() + ", Engine Power: " + enginePower + " HP";
    }
}
