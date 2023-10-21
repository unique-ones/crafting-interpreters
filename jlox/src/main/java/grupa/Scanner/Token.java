package grupa.Scanner;

public class Token {
    private final String lexeme;
    private final TokenType type;

    private final Object literal;
    private final int line;

    public Token(String lexeme, TokenType type, Object literal, int line) {
        this.lexeme = lexeme;
        this.type = type;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "lexeme='" + lexeme + '\'' +
                ", type=" + type +
                ", literal=" + literal +
                ", line=" + line +
                '}';
    }

    public String getLexeme() {
        return lexeme;
    }

    public TokenType getType() {
        return type;
    }

    public Object getLiteral() {
        return literal;
    }

    public int getLine() {
        return line;
    }
}
