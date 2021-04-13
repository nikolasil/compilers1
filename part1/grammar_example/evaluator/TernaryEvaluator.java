import java.io.InputStream;
import java.io.IOException;
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
        int value = Tern();

        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError();

        return value;
    }

    private int Tern() throws IOException, ParseError {
        if (isDigit(lookahead)) {
            int cond = evalDigit(lookahead);

            consume(lookahead);
            return TernTail(cond); 
        }

        throw new ParseError();
    }

    private int TernTail(int condition) throws IOException, ParseError {
        switch (lookahead) {
            case '?':
                consume('?');
                int left = Tern();
                consume(':');
                int right = Tern();

                return condition != 0 ? left : right;
            case -1:
            case '\n':
            case ':':
                return condition;
        }

        throw new ParseError();
    }
}
