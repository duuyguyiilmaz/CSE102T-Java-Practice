public class MainBusSeniors {
    public static void main(String args[]) {
        Card card1 = new Card(50, 20, "Student", "123");
        Passenger passenger1 = new Passenger("Duygu", "Yılmaz", 24, "Student", card1);
        String routeStopss[] = { "A", "B", "C" };
        Bus bus1 = new Bus("1234", "A", routeStopss, 2);
        boolean result1 = bus1.boardPassenger(passenger1);
        System.out.println("Boarding success: " + result1);
        System.out.println("Card balance after boarding: " + passenger1.getCard().getBalance());
        System.out.println("Total earnings: " + bus1.getTotalEarnings());
        System.out.println("Current passenger count: " + bus1.getPassengers().length);
        System.out.println("\nScenario 2: Boarding Fails Due to Insufficient Balance");
        Card card2 = new Card(5, 30, "adult", "1234");
        Passenger passenger2 = new Passenger("Bahar", "Yılmaz", 30, "adult", card2);
        boolean result2 = bus1.boardPassenger(passenger2);
        System.out.println("Boarding success: " + result2);
        System.out.println("Card balance after boarding: " + passenger2.getCard().getBalance());
        System.out.println("Total earnings: " + bus1.getTotalEarnings());
        System.out.println("Current passenger count: " + bus1.getPassengers().length);

    }
}
