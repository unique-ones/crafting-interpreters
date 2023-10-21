package grupa.Expressions;

public class Grouping extends Expression {
    final Expression expression;
    public Grouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitGroupingExpression(this);
    }

    public Expression getExpression() {
        return expression;
    }
}
