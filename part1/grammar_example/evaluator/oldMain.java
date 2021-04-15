import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        List<String> tests = new ArrayList<String>();
        tests.add("5");
        tests.add("54");
        tests.add("5+2");
        tests.add("5**2**3");
        tests.add("(5+2)");
        tests.add("54+20");
        tests.add("(54+20)");
        tests.add("(54)+20");
        tests.add("54+(20)");
        tests.add("(54+20)-**");
        tests.add("(54+20)-8*2");
        tests.add("(54+)-8*2");
        tests.add("(54)-8**2");
        tests.add("103");
        tests.add("123+242");
        tests.add("((2+3)**10)-9765000");
        tests.add("30**(6+2**(10)-(1020+2**1+88-80)+2)");
        for (String test : tests) {
            try {
                InputStream stream = new ByteArrayInputStream(test.getBytes());
                System.out.print(test + " = ");
                System.out.println((new TernaryEvaluator(stream)).eval());
            } catch (IOException | ParseError e) {
                System.err.println(e.getMessage());
            }
        }
    }
}