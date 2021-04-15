import java.io.InputStream;
import java.io.IOException;
import java.lang.Math;
/*
* -------------------------------------------------------------------------
* 	        |     '0' .. '9'     |  ':'    |       '?'          |  $    |
* -------------------------------------------------------------------------
* 	        |		             |	       |	                |       |
* Tern      | '0'..'9' TernTail  |  error  |       error        | error |
*           | 	   	             |	       |    	            |       |
* -------------------------------------------------------------------------
*           |		             |	       |		            |       |
* TernTail  |       error	     |    e    |  '?' Tern ':' Tern |   e   |
* 	        |	  	             |	       |    	     	    |       |
* -------------------------------------------------------------------------
*/

class TernaryEvaluator {
    private final InputStream in;

    private int lookahead;

    public TernaryEvaluator(InputStream in) throws IOException {
        this.in = in;
        lookahead = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
        if (lookahead == symbol)
            lookahead = in.read();
        else
            throw new ParseError();
    }

    private boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    private int evalDigit(int c) {
        return c - '0';
    }

    public int eval() throws IOException, ParseError {
        int value = goal();

        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError();

        return value;
    }

    private int goal() throws IOException, ParseError {
        if (isDigit(lookahead)) {
            return exp(term());
        }

        if (lookahead == '(') {
            return exp(term());
        }

        throw new ParseError();
    }

    private int exp(int condition) throws IOException, ParseError {
        switch (lookahead) {
        case '+':
            consume('+');
            return exp(condition + term());
        case '-':
            consume('-');
            return exp(condition - term());
        case ')':
            return condition;
        case -1:
            return condition;
        case '\n':
            return condition;
        }

        throw new ParseError();
    }

    private int term() throws IOException, ParseError {
        if (isDigit(lookahead)) {
            return termTail(Integer.parseInt(number()));
        }

        if (lookahead == '(') {
            return termTail(Integer.parseInt(number()));
        }

        throw new ParseError();
    }

    private int termTail(int condition) throws IOException, ParseError {
        switch (lookahead) {
        case '*':
            consume('*');
            if (lookahead == '*') {
                consume('*');
                return termTail((int) Math.pow(condition, Integer.parseInt(number())));
            }
        case '+':
            return condition;
        case '-':
            return condition;
        case ')':
            return condition;
        case -1:
            return condition;
        case '\n':
            return condition;
        }
        throw new ParseError();
    }

    private String number() throws IOException, ParseError {
        if (isDigit(lookahead)) {
            String num1 = Integer.toString(evalDigit(lookahead));
            consume(lookahead);
            String num2 = numTail();
            if (num2 != "")
                num1 = num1 + num2;
            return num1;
        }

        if (lookahead == '(') {
            consume('(');
            int result = exp(term());
            consume(')');

            return Integer.toString(result);
        }

        throw new ParseError();
    }

    private String numTail() throws IOException, ParseError {
        if (isDigit(lookahead)) {
            return number();
        }

        if (lookahead == '(')
            return number();

        return "";
    }
}
