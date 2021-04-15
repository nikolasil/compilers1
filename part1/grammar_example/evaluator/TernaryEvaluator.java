import java.io.InputStream;
import java.io.IOException;
import java.lang.Math;

class TernaryEvaluator {
    private final InputStream in;
    int count = -1;
    private int lookahead;

    public TernaryEvaluator(InputStream in) throws IOException {
        this.in = in;
        lookahead = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
        if (lookahead == symbol) {
            lookahead = in.read();
            count++;
        } else
            throw new ParseError(count, "In consume(): lookahead=" + (char) lookahead + " symbol=" + (char) symbol);
    }

    public int eval() throws IOException, ParseError {
        int value = goal();

        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError(count, "In eval(): lookahead=" + (char) lookahead);

        return value;
    }

    private int goal() throws IOException, ParseError {
        if ('0' <= lookahead && lookahead <= '9') {
            return exp(term());
        }

        if (lookahead == '(') {
            return exp(term());
        }

        throw new ParseError(count, "In goal(): lookahead=" + (char) lookahead);
    }

    private int exp(int left_num) throws IOException, ParseError {
        switch (lookahead) {
        case '+':
            consume('+');
            return exp(left_num + term());
        case '-':
            consume('-');
            return exp(left_num - term());
        case ')':
            return left_num;
        case -1:
            return left_num;
        case '\n':
            return left_num;
        }

        throw new ParseError(count, "In exp(): lookahead=" + (char) lookahead + " left_num=" + left_num);
    }

    private int term() throws IOException, ParseError {
        if ('0' <= lookahead && lookahead <= '9') {
            return termTail(Integer.parseInt(number()));
        }

        if (lookahead == '(') {
            return termTail(Integer.parseInt(number()));
        }

        throw new ParseError(count, "In term() lookahead=" + (char) lookahead);
    }

    private int termTail(int base) throws IOException, ParseError {
        switch (lookahead) {
        case '*':
            consume('*');
            if (lookahead == '*') {
                consume('*');
                return termTail((int) Math.pow(base, Integer.parseInt(number())));
            }
        case '+':
            return base;
        case '-':
            return base;
        case ')':
            return base;
        case -1:
            return base;
        case '\n':
            return base;
        }

        throw new ParseError(count, "In termTail() lookahead=" + (char) lookahead + " base=" + base);
    }

    private String number() throws IOException, ParseError {
        if ('0' <= lookahead && lookahead <= '9') {
            String num1 = Integer.toString(digit(lookahead));
            consume(lookahead);
            return num1 + numTail();
        }

        if (lookahead == '(') {
            consume('(');
            int result = exp(term());
            consume(')');

            return Integer.toString(result);
        }

        throw new ParseError(count, "In number() lookahead=" + (char) lookahead);
    }

    private String numTail() throws IOException, ParseError {
        if ('0' <= lookahead && lookahead <= '9') {
            return number();
        }
        switch (lookahead) {
        case '(':
            return number();
        case '*':
            return "";
        case '+':
            return "";
        case '-':
            return "";
        case ')':
            return "";
        case -1:
            return "";
        case '\n':
            return "";
        }
        throw new ParseError(count, "In numTail() lookahead=" + (char) lookahead);
    }

    private int digit(int num) throws IOException, ParseError {
        if ('0' <= num && num <= '9')
            return num - '0';
        throw new ParseError(count, "In digit() lookahead=" + (char) lookahead + " num=" + (char) num);
    }
}
