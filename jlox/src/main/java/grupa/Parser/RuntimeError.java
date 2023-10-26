package grupa.Parser;


import grupa.Scanner.Token;
import grupa.Scanner.TokenType;

public class RuntimeError extends Exception {
    private final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }


    public Token getToken() {
        return token;
    }
}
