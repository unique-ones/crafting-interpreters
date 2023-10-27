package grupa.Expressions;

import grupa.Parser.RuntimeError;
import grupa.Scanner.Token;

public class Unary extends Expr {
    final Token operator;
    final Expr right;

    public Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }


    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) throws RuntimeError {
        return exprVisitor.visitUnaryExpression(this);
    }

    public Token getOperator() {
        return operator;
    }

    public Expr getRight() {
        return right;
    }
}
