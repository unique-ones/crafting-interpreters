package grupa.Expressions;

import grupa.Scanner.Token;

public class Get extends Expr {
    private final Expr object;
    private final Token name;

    public Get(Expr object, Token name) {
        this.object = object;
        this.name = name;
    }

    public Expr getObject() {
        return object;
    }

    public Token getName() {
        return name;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitGetExpression(this);
    }
}
