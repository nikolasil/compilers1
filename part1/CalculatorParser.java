import java.io.InputStream;
import java.io.IOException;
import java.lang.Math;

/*      -- GRAMMAR --

goal -> term exp

exp -> '+' term exp
      | '-' term exp
      | e 

term -> number termTail

termTail -> '*' '*' number termTail
       | e

number -> digit numTail
        | '(' term exp ')'

numTail -> number
        | e

digit -> '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'

*/

class CalculatorParser {
    private final InputStream in; // the input stream
    private int lookahead; // the next symbol
    private int count = -1; // every time we consume we count++ to print the exact position that the error
                            // occurs

    private static final int PLUS = '+';
    private static final int MINUS = '-';
    private static final int STAR = '*';
    private static final int LPAREN = '(';
    private static final int RPAREN = ')';

    private static final int BACKSLASH_N = '\n';
    private static final int LOOKAHEAD_END = -1;
    private static final int ZERO = '0';
    private static final int NINE = '9';
    private static final String EMPTY_STRING = "";

    public CalculatorParser(InputStream input) throws IOException { // constructor
        this.in = input;
        this.lookahead = this.in.read();
    }

    private void consume(int symbol) throws IOException, ParseError { // consume the symbol
        if (this.lookahead == symbol) { // if for some reason the lookahead is different than the symbol throw error
            this.lookahead = in.read();
            this.count++; // +1 to count to know where the error occurs
        } else
            throw new ParseError(this.count,
                    "In consume(): lookahead=" + (char) this.lookahead + " symbol=" + (char) symbol);
    }

    public int parse() throws IOException, ParseError {
        int value = goal();

        if (this.lookahead != LOOKAHEAD_END && this.lookahead != BACKSLASH_N) // if we end the reccursion and the
                                                                              // lookahead is not at the end throw error
            throw new ParseError(this.count, "In eval(): lookahead=" + (char) this.lookahead);

        return value;
    }

    private int goal() throws IOException, ParseError {
        // in goal the lookahead must be either
        // a number or LPAREN
        // else throw error
        if (ZERO <= this.lookahead && this.lookahead <= NINE) {
            return exp(term()); // goal -> term exp
        }

        if (this.lookahead == LPAREN) {
            return exp(term()); // goal -> term exp
        }

        throw new ParseError(this.count, "In goal(): lookahead=" + (char) this.lookahead);
    }

    private int exp(int left_num) throws IOException, ParseError {
        // in exp the lookahead must be either
        // PLUS, MINUS, RPAREN, LOOKAHEAD_END, BACKSLASH_N
        // else throw error

        // the left_num is the number that we must add with
        // the future value that the term will return

        switch (this.lookahead) {
        case PLUS:
            consume(PLUS);
            return exp(left_num + term()); // exp -> PLUS term exp
        case MINUS:
            consume(MINUS);
            return exp(left_num - term()); // exp -> MINUS term exp
        case RPAREN:
            return left_num; // exp -> e
        case LOOKAHEAD_END:
            return left_num; // exp -> e
        case BACKSLASH_N:
            return left_num; // exp -> e
        }

        throw new ParseError(this.count, "In exp(): lookahead=" + (char) this.lookahead + " left_num=" + left_num);
    }

    private int term() throws IOException, ParseError {
        // in term the lookahead must be either a
        // number or LPAREN
        // else throw error
        if (ZERO <= this.lookahead && this.lookahead <= NINE) {
            return termTail(Integer.parseInt(number())); // term -> number termTail
        }

        if (this.lookahead == LPAREN) {
            return termTail(Integer.parseInt(number())); // term -> number termTail
        }

        throw new ParseError(this.count, "In term() lookahead=" + (char) this.lookahead);
    }

    private int termTail(int base) throws IOException, ParseError {
        // in termTail the lookahead must be STAR followed by another STAR
        // or PLUS, MINUS, RPAREN, LOOKAHEAD_END, BACKSLASH_N
        // else throw error
        switch (this.lookahead) {
        case STAR:
            consume(STAR);
            if (this.lookahead == STAR) {
                consume(STAR);
                return termTail((int) Math.pow(base, Integer.parseInt(number()))); // termTail -> '**' number termTail
            }
        case PLUS:
            return base; // termTail -> e
        case MINUS:
            return base; // termTail -> e
        case RPAREN:
            return base; // termTail -> e
        case LOOKAHEAD_END:
            return base; // termTail -> e
        case BACKSLASH_N:
            return base; // termTail -> e
        }

        throw new ParseError(this.count, "In termTail() lookahead=" + (char) this.lookahead + " base=" + base);
    }

    private String number() throws IOException, ParseError {
        // in number the lookahead must be a number or LPAREN
        // else throw error

        // number is type of String cause we must concatenate
        // all the single numbers together first and
        // then parse the the big String into an integer

        // cause if we have 100 and we concatenate the '0'+'0' = '00'
        // then the '00' into string is only one 0 [we lost a zero]
        // so we must concatenate '1'+'0'+'0' = '100'
        // then the '100' into string is 100 [no zero lost]

        // The parse is done from were the number was called
        if (ZERO <= this.lookahead && this.lookahead <= NINE) {
            String num1 = Integer.toString(digit(this.lookahead));
            consume(this.lookahead);
            return num1 + numTail(); // number -> digit numTail
        }

        if (this.lookahead == LPAREN) {
            consume(LPAREN);
            int result = exp(term());
            consume(RPAREN);

            return Integer.toString(result); // number -> '(' term exp ')'
        }

        throw new ParseError(this.count, "In number() lookahead=" + (char) this.lookahead);
    }

    private String numTail() throws IOException, ParseError {
        // in numTail the lookahead must be a number
        // or LPAREN, STAR, PLUS, MINUS, RPAREN, LOOKAHEAD_END, BACKSLASH_N
        // else thow error
        if (ZERO <= this.lookahead && this.lookahead <= NINE) {
            return number(); // numTail -> number
        }
        switch (this.lookahead) {
        case LPAREN:
            return number(); // numTail -> number
        case STAR:
            return EMPTY_STRING; // numTail -> e
        case PLUS:
            return EMPTY_STRING; // numTail -> e
        case MINUS:
            return EMPTY_STRING; // numTail -> e
        case RPAREN:
            return EMPTY_STRING; // numTail -> e
        case LOOKAHEAD_END:
            return EMPTY_STRING; // numTail -> e
        case BACKSLASH_N:
            return EMPTY_STRING; // numTail -> e
        }
        throw new ParseError(this.count, "In numTail() lookahead=" + (char) this.lookahead);
    }

    private int digit(int num) throws IOException, ParseError {
        // in digit the lookahead must be only a number
        // else throw error
        if (ZERO <= num && num <= NINE)
            return num - ZERO;
        throw new ParseError(this.count, "In digit() lookahead=" + (char) this.lookahead + " num=" + (char) num);
    }
}
