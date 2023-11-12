package grupa.Expressions;

import grupa.Scanner.Token;


public class Binary extends Expr {
    final Expr left;
    final Token operator;
    final Expr right;

    public Binary(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expr getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getRight() {
        return right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor)   {
        return exprVisitor.visitBinaryExpression(this);
    }
}
