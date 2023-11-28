package grupa.Expressions;

import grupa.Scanner.Token;

public class Set extends Expr {
    private final Expr object;
    private final Token name;
    private final Expr value;

    public Set(Expr object, Token name, Expr value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    public Expr getObject() {
        return object;
    }

    public Token getName() {
        return name;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitSetExpression(this);
    }
}
