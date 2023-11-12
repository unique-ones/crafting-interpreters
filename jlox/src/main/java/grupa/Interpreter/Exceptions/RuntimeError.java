package grupa.Interpreter.Exceptions;


import grupa.Scanner.Token;
import grupa.Scanner.TokenType;

public class RuntimeError extends RuntimeException {
    private final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }


    public Token getToken() {
        return token;
    }
}
