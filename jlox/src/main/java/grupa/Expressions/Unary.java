package grupa.Expressions;

import grupa.Token;

public class Unary extends Expression {
    final Token operator;
    final Expression right;

    public Unary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }


    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitUnaryExpression(this);
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }
}
