/*
 * @author Duygu Yilmaz 20220808704
 * @version: 1
 * @Date: 05/03/2025
 */

public class FlightReservationSystem {
    public static void main(String[] args) {
        try {
            // === Example 1: Domestic flight booking ===
            long departure = System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000); // 1 week later
            long arrival = departure + (2L * 60 * 60 * 1000); // +2 hours

            DomesticFlight domesticFlight = new DomesticFlight(
                    "TK101", "Istanbul", "Ankara", departure, arrival, 1000.0, 0.18);
            long birth1 = System.currentTimeMillis() - (35L * 365 * 24 * 60 * 60 * 1000);
            Passenger p1 = new Passenger("Ahmet", "Yılmaz", birth1,
                    "A12345678", "Turkish",
                    "ahmet@example.com", "+905551234567");
            long birth2 = System.currentTimeMillis() - (28L * 365 * 24 * 60 * 60 * 1000);
            Passenger p2 = new Passenger("Ayşe", "Kaya", birth2,
                    "B98765432", "Turkish",
                    "ayse@example.com", "+905559876543");

            Passenger[] domesticPassengers = { p1, p2 };
            SeasonalPricingStrategy domPricing = new SeasonalPricingStrategy(0.18, 0.05);
            StandardBooking domBooking = new StandardBooking(domesticFlight, domesticPassengers, domPricing, true);

            boolean created1 = domBooking.createBooking();
            boolean seats1 = domBooking.assignSeats();

            System.out.println("=== Example 1 ===");
            System.out.println("Booking OK: " + created1 + ", Seats assigned: " + seats1);
            if (created1 && seats1) {
                CreditCardPayment payment1 = new CreditCardPayment(domBooking,
                        "1234567890123456", "Ahmet Yılmaz", "12/25", "123");
                System.out.println("Payment OK: " + payment1.processPayment()
                        + ", PaymentStatus: " + payment1.getStatus());
                // İptal & iade örneği
                if (domBooking.cancel()) {
                    double refund = domBooking.calculateRefundAmount();
                    payment1.refundPayment();
                    System.out.println("Cancelled & refunded: " + refund + " TL");
                }
            }

            // === Example 2: International flight booking ===
            long intlDep = System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000); // 30 days later
            long intlArr = intlDep + (5L * 60 * 60 * 1000); // +5 hours

            InternationalFlight intlFlight = new InternationalFlight(
                    "TK202", "Istanbul", "Paris", intlDep, intlArr, 5000.0);
            long birth3 = System.currentTimeMillis() - (40L * 365 * 24 * 60 * 60 * 1000);
            Passenger p3 = new Passenger("Mehmet", "Çelik", birth3,
                    "C11223344", "Turkish",
                    "mehmet@example.com", "+905551112233");
            long birth4 = System.currentTimeMillis() - (30L * 365 * 24 * 60 * 60 * 1000);
            Passenger p4 = new Passenger("Elif", "Demir", birth4,
                    "D55667788", "Turkish",
                    "elif@example.com", "+905559998877");

            Passenger[] intlPassengers = { p3, p4 };
            SeasonalPricingStrategy intlPricing = new SeasonalPricingStrategy(0.20, 0.05);
            StandardBooking intlBooking = new StandardBooking(intlFlight, intlPassengers, intlPricing, false);

            boolean created2 = intlBooking.createBooking();
            boolean seats2 = intlBooking.assignSeats();
            System.out.println("\n=== Example 2 ===");
            System.out.println("Intl booking OK: " + created2 + ", Seats assigned: " + seats2);
            if (created2 && seats2) {
                CreditCardPayment payment2 = new CreditCardPayment(intlBooking,
                        "9876543210987654", "Elif Demir", "06/26", "456");
                System.out.println("Payment OK: " + payment2.processPayment()
                        + ", Status: " + payment2.getStatus());
            }

            // === Example 3: Too many passengers (seat shortage) ===
            Passenger[] many = new Passenger[60];
            for (int i = 0; i < 60; i++) {
                many[i] = new Passenger("Test" + i, "User" + i, birth1,
                        "X" + i, "Country",
                        "test" + i + "@example.com", "+900000000000");
            }
            StandardBooking bigBooking = new StandardBooking(domesticFlight, many, domPricing, false);
            boolean created3 = bigBooking.createBooking();
            boolean seats3 = bigBooking.assignSeats();
            System.out.println("\n=== Example 3 ===");
            System.out.println("Big booking created: " + created3
                    + ", Seats assigned (should be false): " + seats3);

            // === Example 4: Change request too late ===
            long closeDep = System.currentTimeMillis() + (10L * 60 * 60 * 1000); // 10h later
            long closeArr = closeDep + (1L * 60 * 60 * 1000); // +1h
            DomesticFlight lateFlight = new DomesticFlight(
                    "TK103", "Istanbul", "Izmir", closeDep, closeArr, 500.0, 0.18);
            Passenger lateP = new Passenger("Test", "Late", birth1,
                    "L12345", "Turkish",
                    "late@example.com", "+900000000000");
            StandardBooking lateBooking = new StandardBooking(
                    lateFlight, new Passenger[] { lateP },
                    new SeasonalPricingStrategy(0.18, 0.0), false);
            lateBooking.createBooking();
            lateBooking.assignSeats();
            ChangeRequest req = new ChangeRequest(
                    lateBooking.getBookingId(),
                    closeDep + (2L * 60 * 60 * 1000),
                    closeArr + (2L * 60 * 60 * 1000),
                    new String[] { "D1" });
            System.out.println("\n=== Example 4 ===");
            System.out.println("Change allowed (should be false): "
                    + ((IChangeable) lateFlight).change(req));

            // === Example 5: Cancellation not allowed ===
            boolean cancelLate = lateBooking.cancel();
            System.out.println("\n=== Example 5 ===");
            System.out.println("Cancellation allowed (should be false): " + cancelLate);

            // === Example 6: Invalid payment details ===
            CreditCardPayment badPay = new CreditCardPayment(
                    lateBooking, "1111222233334444", "Late User", "01/20", "12");
            System.out.println("\n=== Example 6 ===");
            System.out.println("Process bad payment (should be false): "
                    + badPay.processPayment());

            // === Example 7: Promo code application ===
            SeasonalPricingStrategy promoStrat = new SeasonalPricingStrategy(0.15, 0.0);
            boolean promoOK = promoStrat.applyPromoCode("SUMMER2023");
            double promoPrice = promoStrat.calculateFinalPrice(domesticFlight, 2);
            System.out.println("\n=== Example 7 ===");
            System.out.println("Promo applied: " + promoOK
                    + ", New price for 2 pax: " + promoPrice + " TL");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}

enum FlightStatus {
    SCHEDULED, BOARDING, DEPARTED, ARRIVED, DELAYED, CANCELLED
}

enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED, FAILED
}

enum PaymentStatus {
    PENDING, COMPLETED, FAILED, REFUNDED
}

enum ClassType {
    ECONOMY, BUSINESS, FIRST
}

abstract class AbstractFlight {
    protected String flightNumber;
    protected String origin;
    protected String destination;
    protected long departureTime;
    protected long arrivalTime;
    protected FlightStatus status;
    protected Seat[] seats;
    protected double basePrice;

    public AbstractFlight(String flightNumber, String origin, String destination, long departureTime, long arrivalTime,
            double basePrice) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.basePrice = basePrice;
        this.status = FlightStatus.SCHEDULED;
        this.seats = initializeSeats();
    }

    protected abstract Seat[] initializeSeats();

    public long calculateDuration() {
        long durationTime = arrivalTime - departureTime;
        return durationTime / (60 * 1000);
    }

    public Seat[] getAvailableSeats() {
        int seatcount = 0;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i].isAvailable()) {
                seatcount++;
            }
        }
        Seat[] avaliableseats = new Seat[seatcount];
        int index = 0;
        for (int a = 0; a < seats.length; a++) {
            if (seats[a].isAvailable()) {
                avaliableseats[index] = seats[a];
                index++;
            }
        }
        return avaliableseats;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public Seat[] getSeats() {
        return seats;
    }

    public double getBasePrice() {
        return basePrice;
    }
}

abstract class AbstractBooking {
    protected String bookingId;
    protected AbstractFlight flight;
    protected Passenger[] passengers;
    protected Seat[] assignedSeats;
    protected long bookingTime;
    protected BookingStatus status;
    protected AbstractPricingStrategy pricingStrategy;
    protected double totalPrice;

    public AbstractBooking(AbstractFlight flight, Passenger[] passengers, AbstractPricingStrategy pricingStrategy) {
        this.flight = flight;
        this.passengers = passengers;
        this.pricingStrategy = pricingStrategy;
        this.bookingTime = System.currentTimeMillis();
        this.bookingId = generateBookingId();
        this.status = BookingStatus.PENDING;
        this.assignedSeats = new Seat[passengers.length];
        this.totalPrice = 0.0;
    }

    protected String generateBookingId() {
        long timestamp = System.currentTimeMillis();
        return "BK" + timestamp;
    }

    public boolean createBooking() {
        try {
            this.totalPrice = pricingStrategy.calculateFinalPrice(flight, passengers.length);
            this.status = BookingStatus.CONFIRMED;
            return true;
        } catch (Exception e) {
            this.status = BookingStatus.FAILED;
            return false;
        }
    }

    public double calculateTotalPrice() {
        return pricingStrategy.calculateFinalPrice(flight, passengers.length);
    }

    public boolean assignSeats() {
        Seat[] avaliablSeats = flight.getAvailableSeats();
        if (avaliablSeats.length < passengers.length) {
            return false;
        }
        for (int i = 0; i < passengers.length; i++) {
            Seat seat = avaliablSeats[i];
            seat.reserve();
            assignedSeats[i] = seat;
        }
        return true;
    }

    public String getBookingId() {
        return bookingId;
    }

    public AbstractFlight getFlight() {
        return flight;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public Seat[] getAssignedSeats() {
        return assignedSeats;
    }

    public long getBookingTime() {
        return bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public AbstractPricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

abstract class AbstractPricingStrategy {
    protected double basePrice;
    protected double taxRate;
    protected double discountRate;

    public AbstractPricingStrategy(double taxRate, double discountRate) {
        this.taxRate = taxRate;
        this.discountRate = discountRate;
    }

    public double calculateBasePrice(AbstractFlight flight) {
        this.basePrice = flight.getBasePrice();
        return basePrice;
    }

    public double applyDiscounts(int passengerCount) {
        double discountAmount = basePrice * discountRate;
        return basePrice - discountAmount;
    }

    public double applyTaxes(double priceAfterDiscount) {
        double taxAmount = priceAfterDiscount * taxRate;
        return priceAfterDiscount + taxAmount;

    }

    public double calculateFinalPrice(AbstractFlight flight, int passengerCount) {
        calculateBasePrice(flight);
        double discounted = applyDiscounts(passengerCount);
        double taxed = applyTaxes(discounted);
        return taxed * passengerCount;
    }
}

abstract class AbstractPaymentProcessor {
    protected String paymentId;
    protected AbstractBooking booking;
    protected double amount;
    protected long paymentTime;
    protected PaymentStatus status;

    public AbstractPaymentProcessor(AbstractBooking booking) {
        this.booking = booking;
        this.paymentId = generatePaymentId();
        this.paymentTime = System.currentTimeMillis();
        this.amount = booking.getTotalPrice();
        this.status = PaymentStatus.PENDING;

    }

    protected String generatePaymentId() {
        long timestamp = System.currentTimeMillis();
        return "PAY" + timestamp;
    }

    public abstract boolean processPayment();

    public abstract boolean validatePaymentDetails();

    public boolean refundPayment() {
        if (this.status == PaymentStatus.COMPLETED) {
            this.status = PaymentStatus.REFUNDED;
            return true;
        }
        return false;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public AbstractBooking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public long getPaymentTime() {
        return paymentTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}

interface IChangeable {
    boolean change(ChangeRequest request);

    double calculateChangeFee();

    boolean isChangeAllowed();
}

interface IPricingStrategy {
    double getPriceForClass(ClassType type);

    double getPriceForDate(long date);

    boolean applyPromoCode(String code);
}

interface ICancellable {
    boolean cancel();

    double calculateCancellationFee();

    boolean isCancellationAllowed();
}

interface IRefundable {
    boolean processRefund();

    double calculateRefundAmount();

    boolean isRefundable();
}

class DomesticFlight extends AbstractFlight implements IChangeable {
    private double domesticTaxRate;

    public DomesticFlight(String flightNumber, String origin, String destination, long departureTime, long arrivalTime,
            double basePrice, double domesticTaxRate) {
        super(flightNumber, origin, destination, departureTime, arrivalTime, basePrice);
        this.domesticTaxRate = domesticTaxRate;
    }

    @Override
    protected Seat[] initializeSeats() {
        Seat seats[] = new Seat[50];
        for (int i = 0; i < 10; i++) {
            seats[i] = seats[i] = new Seat("D" + (i + 1), ClassType.BUSINESS);
        }
        for (int i = 10; i < 50; i++) {
            seats[i] = new Seat("D" + (i + 1), ClassType.ECONOMY);
        }
        return seats;
    }

    public double calculateDomesticTax() {
        return basePrice * domesticTaxRate;
    }

    @Override
    public boolean change(ChangeRequest request) {
        if (!isChangeAllowed()) {
            return false;
        } else {
            this.departureTime = request.getNewDepartureTime();
            this.arrivalTime = request.getNewArrivalTime();
            return true;
        }
    }

    @Override
    public double calculateChangeFee() {
        return basePrice * 0.1;
    }

    @Override
    public boolean isChangeAllowed() {
        long currentTime = System.currentTimeMillis();
        long hours = (departureTime - currentTime) / (1000 * 60 * 60);
        return hours >= 24;
    }
}

class InternationalFlight extends AbstractFlight implements IChangeable {
    private String[] requiredDocuments;

    public InternationalFlight(String flightNumber, String origin, String destination, long departureTime,
            long arrivalTime, double basePrice) {
        super(flightNumber, origin, destination, departureTime, arrivalTime, basePrice);
        this.requiredDocuments = new String[0];
    }

    @Override
    protected Seat[] initializeSeats() {
        Seat[] seats = new Seat[100];
        for (int i = 0; i < 10; i++) {
            seats[i] = new Seat("I" + (i + 1), ClassType.FIRST);
        }
        for (int i = 10; i < 30; i++) {
            seats[i] = new Seat("I" + (i + 1), ClassType.BUSINESS);
        }
        for (int i = 30; i < 100; i++) {
            seats[i] = new Seat("I" + (i + 1), ClassType.ECONOMY);
        }
        return seats;
    }

    @Override
    public boolean change(ChangeRequest request) {
        if (!isChangeAllowed()) {
            return false;
        }
        this.departureTime = request.getNewDepartureTime();
        this.arrivalTime = request.getNewArrivalTime();
        return true;
    }

    @Override
    public double calculateChangeFee() {
        return basePrice * 0.2;
    }

    @Override
    public boolean isChangeAllowed() {
        long currentTime = System.currentTimeMillis();
        long hours = (departureTime - currentTime) / (1000 * 60 * 60);
        return hours >= 72;
    }
}

class StandardBooking extends AbstractBooking implements ICancellable, IRefundable {
    private boolean insuranceIncluded;

    private boolean insuranceIncluded() {
        return insuranceIncluded;
    }

    public StandardBooking(AbstractFlight flight, Passenger[] passengers, AbstractPricingStrategy pricingStrategy,
            boolean insuranceIncluded) {
        super(flight, passengers, pricingStrategy);
        this.insuranceIncluded = insuranceIncluded;
    }

    @Override
    public boolean cancel() {
        if (!isCancellationAllowed()) {
            return false;
        }
        this.status = BookingStatus.CANCELLED;
        for (int i = 0; i < assignedSeats.length; i++) {
            if (assignedSeats[i] != null) {
                assignedSeats[i].release();
            }
        }
        return true;
    }

    @Override
    public double calculateCancellationFee() {
        if (insuranceIncluded) {
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        long millisUntilDeparture = flight.getDepartureTime() - currentTime;
        long daysUntilDeparture = millisUntilDeparture / (1000 * 60 * 60 * 24);

        if (daysUntilDeparture > 30) {
            return totalPrice * 0.10;
        } else if (daysUntilDeparture >= 7) {
            return totalPrice * 0.30;
        } else {
            return totalPrice * 0.50;
        }
    }

    @Override
    public boolean isCancellationAllowed() {
        long currentTime = System.currentTimeMillis();
        long hoursUntilDeparture = (flight.getDepartureTime() - currentTime) / (1000 * 60 * 60);
        return hoursUntilDeparture >= 24;
    }

    @Override
    public boolean processRefund() {

        if (!isRefundable()) {
            return false;
        }
        double refundAmount = calculateRefundAmount();
        return true;
    }

    @Override
    public double calculateRefundAmount() {
        return totalPrice - calculateCancellationFee();
    }

    @Override
    public boolean isRefundable() {
        return status == BookingStatus.CANCELLED ||
                (status == BookingStatus.CONFIRMED && isCancellationAllowed());
    }
}

class SeasonalPricingStrategy extends AbstractPricingStrategy implements IPricingStrategy {
    private double lowSeasonRate;
    private double highSeasonRate;
    private long[] highSeasonStartDates;
    private long[] highSeasonEndDates;

    public SeasonalPricingStrategy(double taxRate, double discountRate) {
        super(taxRate, discountRate);
        this.lowSeasonRate = 0.8;
        this.highSeasonRate = 1.3;
        this.highSeasonStartDates = new long[] {
                1686787200000L,
                1702598400000L
        };

        this.highSeasonEndDates = new long[] {
                1694736000000L,
                1705276800000L
        };
    }

    private boolean isHighSeason(long date) {
        for (int i = 0; i < highSeasonStartDates.length; i++) {
            if (date >= highSeasonStartDates[i] && date <= highSeasonEndDates[i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double calculateBasePrice(AbstractFlight flight) {
        double basePrice = super.calculateBasePrice(flight);
        long departureTime = flight.getDepartureTime();

        if (isHighSeason(departureTime)) {
            return basePrice * highSeasonRate;
        } else {
            return basePrice * lowSeasonRate;
        }
    }

    @Override
    public double getPriceForClass(ClassType type) {
        if (type == ClassType.FIRST) {
            return basePrice * 3.0;
        } else if (type == ClassType.BUSINESS) {
            return basePrice * 2.0;
        } else {
            return basePrice * 1.0;
        }
    }

    @Override
    public double getPriceForDate(long date) {
        if (isHighSeason(date)) {
            return basePrice * highSeasonRate;
        } else {
            return basePrice * lowSeasonRate;
        }
    }

    @Override
    public boolean applyPromoCode(String code) {
        if (code.equals("SUMMER2023")) {
            this.discountRate += 0.10;
            return true;
        } else if (code.equals("WINTER2023")) {
            this.discountRate += 0.15;
            return true;
        } else {
            return false;
        }
    }

    public double getLowSeasonRate() {
        return lowSeasonRate;
    }

    public double getHighSeasonRate() {
        return highSeasonRate;
    }

    public long[] getHighSeasonStartDates() {
        return highSeasonStartDates;
    }

    public long[] getHighSeasonEndDates() {
        return highSeasonEndDates;
    }
}

class CreditCardPayment extends AbstractPaymentProcessor {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(AbstractBooking booking, String cardNumber, String cardHolderName, String expiryDate,
            String cvv) {
        super(booking);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;

    }

    @Override
    public boolean processPayment() {
        if (validatePaymentDetails()) {
            this.status = PaymentStatus.COMPLETED;
            this.paymentTime = System.currentTimeMillis();
            return true;
        } else {
            this.status = PaymentStatus.FAILED;
            return false;
        }
    }

    @Override
    public boolean validatePaymentDetails() {
        return cardNumber != null && cardNumber.matches("\\d{13,19}") &&
                cardHolderName != null && !cardHolderName.trim().isEmpty() &&
                expiryDate != null && expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}") &&
                cvv != null && cvv.matches("\\d{3,4}");
    }
}

class Passenger {
    private final String id;
    private String firstName;
    private String lastName;
    private long birthDate;
    private String passportNumber;
    private String nationality;
    private String contactEmail;
    private String contactPhone;

    public Passenger(String firstName, String lastName, long birthDate, String passportNumber, String nationality,
            String contactEmail, String contactPhone) {
        this.id = getId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    public String getId() {
        int random = (int) (Math.random() * 1000);
        return "P" + System.currentTimeMillis() + "-" + random;
    }

    public boolean validateDetails() {
        return !firstName.isEmpty() && !lastName.isEmpty() && passportNumber.length() >= 5
                && contactEmail.contains("@")
                && contactPhone.isEmpty();
    }

    public int getAge() {
        long age = System.currentTimeMillis() - birthDate;
        return (int) (age / 1000 / 60 / 60 / 24 / 365);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}

class Seat {
    private final String seatNumber;
    private final ClassType classType;
    private boolean available = true;

    public Seat(String seatNumber, ClassType classType) {
        this.seatNumber = seatNumber;
        this.classType = classType;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean reserve() {
        if (available) {
            available = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean release() {
        if (!available) {
            available = true;
            return true;
        } else {
            return false;
        }
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public ClassType getClassType() {
        return classType;
    }

}

class ChangeRequest {
    private final String bookingId;
    private long newDepartureTime;
    private long newArrivalTime;
    private String[] newSeatNumbers;

    public ChangeRequest(String bookingId, long newDepartureTime, long newArrivalTime, String[] newSeatNumbers) {
        this.bookingId = bookingId;
        this.newDepartureTime = newDepartureTime;
        this.newArrivalTime = newArrivalTime;
        this.newSeatNumbers = newSeatNumbers;
    }

    public String getBookingId() {
        return bookingId;
    }

    public long getNewDepartureTime() {
        return newDepartureTime;
    }

    public long getNewArrivalTime() {
        return newArrivalTime;
    }

    public String[] getNewSeatNumbers() {
        return newSeatNumbers;
    }

}
