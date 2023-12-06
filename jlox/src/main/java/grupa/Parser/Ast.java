package grupa.Parser;

import grupa.Expressions.*;
import grupa.Expressions.Function;
import grupa.Lox;
import grupa.Scanner.Token;
import grupa.Scanner.TokenType;
import grupa.Statements.*;
import grupa.Statements.Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ast {
    private boolean allowExpression;
    private int loopDepth = 0;
    private boolean foundExpression = false;
    private final List<Token> tokens;
    private int current = 0;

    public Ast(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> stmts = new ArrayList<>();
        while (!isAtEnd()) {
            stmts.add(declaration());
        }
        return stmts;
    }

    public Object parseRepl() {
        allowExpression = true;
        List<Stmt> stmts = new ArrayList<>();
        while (!isAtEnd()) {
            stmts.add(declaration());
            if (foundExpression) {
                Stmt last = stmts.get(stmts.size() - 1);
                return ((Expression) last).getExpression();
            }
            allowExpression = false;
        }
        return stmts;
    }

    private Stmt declaration() {
        try {
            if (match(TokenType.VAR)) return varDeclaration();
            if (check(TokenType.FUN) && checkNext(TokenType.IDENTIFIER)) {
                consume(TokenType.FUN, null);
                return funDeclaration("function");
            }
            if (match(TokenType.CLASS)) return classDeclaration();
            return statement();
        } catch (ParseError error) {
            synchronize();
            return null;
        }
    }

    private Stmt classDeclaration() {
        Token name = consume(TokenType.IDENTIFIER, "Expected class name");
        consume(TokenType.LEFT_BRACE, "Expcted '{' before class body");

        List<grupa.Statements.Function> methods = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            methods.add(funDeclaration("method"));
        }
        consume(TokenType.RIGHT_BRACE, "Epected '}' after class body");
        return new Class(name, methods);
    }

    private grupa.Statements.Function funDeclaration(String function) {
        Token name = consume(TokenType.IDENTIFIER, "Expected " + function + " name");
        return new grupa.Statements.Function(name, funBody(function));
    }

    private Function funBody(String kind) {
        consume(TokenType.LEFT_PAREN, "Expected '(' after " + kind + " name.");
        List<Token> parameters = new ArrayList<>();

        if (!check(TokenType.RIGHT_PAREN)) {
            do {
                if (parameters.size() >= 255) {
                    error(peek(), "Cannot have more than 255 params");
                }
                parameters.add(consume(TokenType.IDENTIFIER, "Expected parameter name."));
            } while (match(TokenType.COMMA));
        }
        consume(TokenType.RIGHT_PAREN, "Expected ')' after parameters");
        consume(TokenType.LEFT_BRACE, "Expected '{' before " + kind + " body.");
        List<Stmt> body = block();
        return new Function(parameters, body);
    }

    private Stmt varDeclaration() {
        Token name = consume(TokenType.IDENTIFIER, "Expected variable name");

        Expr initializer = null;
        if (match(TokenType.EQUAL)) {
            initializer = expression();
        }
        consume(TokenType.SEMICOLON, "Expected ';' after variable declaration");
        return new Var(name, initializer);
    }

    private Stmt statement() {
        if (match(TokenType.PRINT)) return printStatement();
        if (match(TokenType.LEFT_BRACE)) return new Block(block());
        if (match(TokenType.IF)) return ifStatement();
        if (match((TokenType.WHILE))) return whileStatement();
        if (match((TokenType.FOR))) return forStatement();
        if (match((TokenType.BREAK))) return breakStatement();
        if (match((TokenType.CONTINUE))) return continueStatement();
        if (match((TokenType.RETURN))) return returnStatement();

        return expressionStatement();
    }

    private Stmt returnStatement() {
        Token keyword = previous();
        Expr expr = null;
        if (!check(TokenType.SEMICOLON)) {
            expr = expression();
        }
        consume(TokenType.SEMICOLON, "Expected ';' after return value");
        return new Return(keyword, expr);
    }

    private Stmt breakStatement() {
        if (loopDepth == 0) {
            throw error(previous(), "Must be inside a loop to break");
        }
        consume(TokenType.SEMICOLON, "Expected ';' after 'break'");
        return new Break(peek());
    }

    private Stmt continueStatement() {
        if (loopDepth == 0) {
            throw error(previous(), "Must be inside a loop to continue");
        }
        consume(TokenType.SEMICOLON, "Expected ';' after 'continue'");
        return new Continue(peek());
    }

    //Desugaring is pretty cool-> LGTM!
    private Stmt forStatement() {
        try {
            loopDepth++;
            consume(TokenType.LEFT_PAREN, "Expected '(' before 'for'.");
            Stmt init = null;
            if (match(TokenType.SEMICOLON)) {
                init = null;
            } else if (match(TokenType.VAR)) {
                init = varDeclaration();
            } else {
                init = expressionStatement();
            }
            Expr condition = null;
            if (!check(TokenType.SEMICOLON)) {
                condition = expression();
            }
            consume(TokenType.SEMICOLON, "Expected ';' after loop condition");

            Expr increment = null;
            if (!check(TokenType.RIGHT_PAREN)) {
                increment = expression();
            }
            consume(TokenType.RIGHT_PAREN, "Expected ')' after for clause");
            Stmt body = statement();
            if (increment != null) {
                body = new Block(Arrays.asList(body, new Expression(increment)));
            }
            if (condition == null) condition = new Literal(true);
            body = new While(condition, body);

            if (init != null) {
                body = new Block(Arrays.asList(init, body));
            }
            return body;
        } finally {
            loopDepth--;
        }

    }

    private Stmt whileStatement() {
        try {

            consume(TokenType.LEFT_PAREN, "Expected '(' before 'while'.");
            Expr condition = expression();
            consume(TokenType.RIGHT_PAREN, "Expected ')' after 'while'.");
            loopDepth++;
            Stmt body = statement();
            return new While(condition, body);

        } finally {
            loopDepth--;
        }
    }

    private Stmt ifStatement() {
        consume(TokenType.LEFT_PAREN, "Expected '(' before 'if'.");
        Expr condition = expression();
        consume(TokenType.RIGHT_PAREN, "Expected ')' after 'if'.");
        Stmt thenBranch = statement();
        Stmt elseBranch = null;
        if (match(TokenType.ELSE)) {
            elseBranch = statement();
        }
        return new If(condition, thenBranch, elseBranch);

    }

    private List<Stmt> block() {
        List<Stmt> stmts = new ArrayList<>();
        while (!check(TokenType.RIGHT_BRACE) && !isAtEnd()) {
            stmts.add(declaration());
        }
        consume(TokenType.RIGHT_BRACE, "Expected '}' after block");
        return stmts;
    }

    private Stmt printStatement() {
        Expr expr = expression();
        consume(TokenType.SEMICOLON, "Expected ';' after statement");
        return new Print(expr);
    }

    private Stmt expressionStatement() {
        Expr expr = expression();
        if (allowExpression && isAtEnd()) {
            foundExpression = true;
        } else {
            consume(TokenType.SEMICOLON, "Expected ';' after statement");
        }
        return new Expression(expr);
    }

    private Expr expression() {
        return assignment();
    }


    private Expr assignment() {

        Expr expr = condition();
        if (match(TokenType.EQUAL)) {
            Token equals = previous();
            Expr value = assignment();
            if (expr instanceof Variable) {
                Token name = ((Variable) expr).getName();
                return new Assign(name, value);
            } else if (expr instanceof Get) {
                Get get = ((Get) expr);
                return new Set(get.getObject(), get.getName(), value);
            }
            error(equals, "Invalid assignment target.");
        }
        return expr;
    }

    private Expr condition() {
        Expr condition = or();
        if (match(TokenType.QUESTION)) {
            Token question = previous();
            Expr trueBranch = expression();
            consume(TokenType.COLON, "Expected ':' for conditional expression");
            Token colon = previous();
            Expr falseBranch = expression();
            condition = new Conditional(condition, trueBranch, falseBranch, question, colon);
        }
        return condition;
    }

    private Expr or() {
        Expr expr = and();
        while (match(TokenType.OR)) {
            Token operand = previous();
            Expr right = and();
            expr = new Logical(expr, right, operand);
        }
        return expr;
    }

    private Expr and() {
        Expr expr = equality();
        while (match(TokenType.AND)) {
            Token operand = previous();
            Expr right = equality();
            expr = new Logical(expr, right, operand);
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(TokenType.BANGEQUAL, TokenType.EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Binary(expr, operator, right);

        }
        return expr;
    }

    private Expr comparison() {
        Expr expr = term();

        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Binary(expr, operator, right);

        }
        return expr;
    }

    private Expr term() {
        Expr expr = factor();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Binary(expr, operator, right);

        }
        return expr;
    }

    private Expr factor() {
        Expr expr = unary();

        while (match(TokenType.SLASH, TokenType.STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Binary(expr, operator, right);

        }
        return expr;
    }

    private Expr unary() {
        if (match(TokenType.BANG, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Unary(operator, right);
        }
        return call();
    }

    private Expr call() {
        Expr expr = primary();
        while (true) {
            if (match(TokenType.LEFT_PAREN)) {
                expr = finishCall(expr);
            } else if (match(TokenType.DOT)) {
                Token name = consume(TokenType.IDENTIFIER, "Expect property name after '.'");
                expr = new Get(expr, name);
            } else {
                break;
            }
        }
        return expr;
    }

    private Expr finishCall(Expr expr) {
        List<Expr> args = new ArrayList<>();

        if (!check(TokenType.RIGHT_PAREN)) {
            do {
                if (args.size() >= 255) {
                    error(peek(), "Cannot have more than 255 args");
                } else {
                    args.add(expression());
                }
            } while (match(TokenType.COMMA));
        }
        Token paren = consume(TokenType.RIGHT_PAREN, "Expected ')' after arguments");
        return new Call(expr, args, paren);
    }

    private Expr primary() {
        if (match(TokenType.FALSE)) return new Literal(false);
        if (match(TokenType.TRUE)) return new Literal(true);
        if (match(TokenType.NIL)) return new Literal(null);
        if (match(TokenType.NUMBER, TokenType.STRING)) return new Literal(previous().getLiteral());
        if (match(TokenType.IDENTIFIER)) return new Variable(previous());
        if (match(TokenType.THIS)) return new This(previous());
        if (match(TokenType.FUN)) return funBody("function");
        if (match(TokenType.LEFT_PAREN)) {
            Expr expr = expression();
            consume(TokenType.RIGHT_PAREN, "Expected ')' after expression.");
            return new Grouping(expr);
        }
        throw error(peek(), "Expression expected");
    }

    private Token consume(TokenType type, String errorMessage) {
        if (check(type)) return advance();

        throw error(peek(), errorMessage);

    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean match(TokenType... types) {
        for (var type : types) {
            if (check(type)) {
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


    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }

    private boolean checkNext(TokenType tokenType) {
        if (isAtEnd()) return false;
        if (tokens.get(current + 1).getType() == TokenType.EOF) return false;
        return tokens.get(current + 1).getType() == tokenType;
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().getType() == TokenType.SEMICOLON) return;
            switch (peek().getType()) {
                case CLASS:
                case IF:
                case PRINT:
                case FUN:
                case FOR:
                case WHILE:
                case VAR:
                case RETURN:
                    return;
            }
            advance();
        }
    }


}
