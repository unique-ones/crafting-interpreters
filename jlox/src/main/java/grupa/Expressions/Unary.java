package grupa.Expressions;

import grupa.Parser.RuntimeError;
import grupa.Scanner.Token;

public class Unary extends Expression {
    final Token operator;
    final Expression right;

    public Unary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }


    @Override
    public <R> R accept(Visitor<R> visitor) throws RuntimeError {
        return visitor.visitUnaryExpression(this);
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }
}
