package grupa;

import grupa.Expressions.*;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Expression expression() {
        return equality();
    }

    private Expression equality() {
        Expression expression = comparison();

        while (match(TokenType.BANGEQUAL, TokenType.EQUAL_EQUAL)) {
            Token operator = previous();
            Expression right = comparison();
            expression = new Binary(expression, operator, right);

        }
        return expression;
    }

    private Expression comparison() {
        Expression expression = term();

        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            Expression right = term();
            expression = new Binary(expression, operator, right);

        }
        return expression;
    }

    private Expression term() {
        Expression expression = unary();

        while (match(TokenType.SLASH, TokenType.STAR)) {
            Token operator = previous();
            Expression right = unary();
            expression = new Binary(expression, operator, right);

        }
        return expression;
    }

    private Expression unary() {
        if (match(TokenType.BANG, TokenType.MINUS)) {
            Token operator = previous();
            Expression right = unary();
            return new Unary(operator, right);
        }
        return primary();
    }

    private Expression primary() {
        if(match(TokenType.FALSE)) return new Literal(false);
        if(match(TokenType.TRUE)) return new Literal(true);
        if(match(TokenType.NIL)) return new Literal(null);
        if(match(TokenType.NUMBER,TokenType.STRING)) return new Literal(previous().getLexeme());

        if(match(TokenType.LEFT_PAREN)){
            Expression expression= expression();
            consume(TokenType.RIGHT_PAREN,"Exprect ')' after exprssion.");
            return new Grouping(expression);
        }



    }

    private void consume(TokenType rightParen, String s) {

    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean match(TokenType... types) {
        for (var type : types) {
            if (tokens.get(current).getType() == type) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return this.peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return this.tokens.get(current);
    }


}
