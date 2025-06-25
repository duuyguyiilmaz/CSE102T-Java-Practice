public class Bus {
    private String busId;
    private String currentLocation;
    private String[] routeStops;
    private Passenger[] passengers;
    private int currentPassengerCount;
    private double totalEarnings;
    private int totalPassengerCount;
    private int maxCapacity;

    public Bus(String busId, String currentLocation, String[] routeStops, int maxCapacity) {
        setBusıd(busId);
        setCurrentLocation(currentLocation);
        setRouteStops(routeStops);
        this.maxCapacity = maxCapacity;
        this.passengers = new Passenger[maxCapacity];
        this.currentPassengerCount = 0;
        this.totalEarnings = 0;
        this.totalPassengerCount = 0;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusıd(String busId) {
        this.busId = busId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentlocation) {
        this.currentLocation = currentlocation;
    }

    public String[] getRouteStpos() {
        return routeStops;
    }

    public void setRouteStops(String[] routeStops) {
        this.routeStops = routeStops;
    }

    public Passenger[] getPassengers() {
        Passenger[] currentPassengers = new Passenger[currentPassengerCount];
        for (int i = 0; i < currentPassengerCount; i++) {
            currentPassengers[i] = passengers[i];
        }
        return currentPassengers;
    }

    public void setPassengers(Passenger[] passengers) {
        if (passengers.length > maxCapacity) {
            return;
        }
        this.passengers = new Passenger[maxCapacity];
        this.currentPassengerCount = passengers.length;
        this.totalPassengerCount += passengers.length;
        for (int i = 0; i < passengers.length; i++) {
            this.passengers[i] = passengers[i];
        }
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public int getTotalPassengerCount() {
        return currentPassengerCount;
    }

    public void setTotalPassengerCount(int totalPassengerCount) {
        this.totalPassengerCount = totalPassengerCount;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        Passenger newPassengers[] = new Passenger[maxCapacity];
        int limit = Math.min(currentPassengerCount, maxCapacity);
        for (int i = 0; i < limit; i++) {
            newPassengers[i] = passengers[i];
        }
        passengers = newPassengers;
        currentPassengerCount = limit;
    }

    public boolean boardPassenger(Passenger p) {
        if (currentPassengerCount >= maxCapacity) {
            return false;
        }
        double fare = calculateFare(p);

        if (!p.getCard().spendMoney(fare)) {
            return false;
        }
        passengers[currentPassengerCount] = p;
        currentPassengerCount++;
        setTotalEarnings(fare);
        totalPassengerCount++;
        return true;
    }

    public boolean alightPassenger(Passenger p) {
        int index = -1;
        for (int i = 0; i < currentPassengerCount; i++) {
            if (passengers[i] == p) {
                index = i;
                break;
            }
        }
        if (index == -1)
            return false;
        for (int i = index; i < currentPassengerCount - 1; i++) {
            passengers[i] = passengers[i + 1];
        }

        passengers[currentPassengerCount - 1] = null;
        currentPassengerCount--;
        return true;
    }

    public boolean moveToNextStop() {
        int currentIndex = -1;
        for (int i = 0; i < routeStops.length; i++) {
            if (routeStops[i].equals(currentLocation)) {
                currentIndex = i;
            }
        }
        if (currentIndex == -1 || currentIndex >= routeStops.length - 1)
            return false;
        currentLocation = routeStops[currentIndex + 1];
        return true;
    }

    private double calculateFare(Passenger p) {
        if (p.getAge() < 12) {
            return 10 * 0.50;
        } else if (p.getEducationStatus().equals("Student")) {
            return 10 * 0.75;
        } else if (p.getAge() > 65) {
            return 10 * 0.80;
        }
        return 10;
    }
}
