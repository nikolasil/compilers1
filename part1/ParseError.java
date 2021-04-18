public class ParseError extends Exception {
    String Message;
    int count;

    public ParseError(int countChar, String msg) {
        this.Message = msg;
        this.count = countChar;
    }

    public String getMessage() {
        String space = "";
        for (int i = 0; i < count; i++)
            space = space + " ";
        return space + "^" + "\nParse Error: " + Message;
    }
}
