import java.io.IOException;

class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Give mathematical expression[+,-,**,()]:");
            System.out.println((new CalculatorParser(System.in)).parse());
        } catch (IOException | ParseError e) {
            System.err.println(e.getMessage());
        }
    }
}
