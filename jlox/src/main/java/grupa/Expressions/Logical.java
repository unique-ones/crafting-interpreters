package grupa.Expressions;

import grupa.Scanner.Token;

public class Logical extends Expr {
    private final Expr left, right;
    private final Token operator;

    public Logical(Expr left, Expr right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public Token getOperator() {
        return operator;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor)  {
        return exprVisitor.visitLogicalExpression(this);
    }
}
