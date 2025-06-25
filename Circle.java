public class Circle {
    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.color = "Purple";
        circle.radius = 3;
        circle.getArea();
        System.out.println(circle.toString());

    }

    private int radius;
    private String color;

    public double getArea() {
        return Math.PI * (radius) * (radius);
    }

    public String toString() {
        return "Area:" + getArea() + " Color: " + color;
    }
}