package grupa.Expressions;

import grupa.Parser.RuntimeError;

public class Grouping extends Expression {
    final Expression expression;
    public Grouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) throws RuntimeError {
        return visitor.visitGroupingExpression(this);
    }

    public Expression getExpression() {
        return expression;
    }
}
