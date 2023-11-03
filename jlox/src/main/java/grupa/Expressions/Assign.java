package grupa.Expressions;

import grupa.Interpreter.RuntimeError;
import grupa.Scanner.Token;

public class Assign extends Expr {
    private final Token name;
    private final Expr value;

    public Assign(Token name, Expr value) {
        this.name = name;
        this.value = value;
    }

    public Token getName() {
        return name;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) throws RuntimeError {
        return exprVisitor.visitAssignExpression(this);
    }
}
