package grupa.Expressions;

public class Conditional extends Expression {
    private final Expression condition;
    private final Expression trueBranch;
    private final Expression falseBranch;

    public Conditional(Expression condition, Expression trueBranch, Expression falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getTrueBranch() {
        return trueBranch;
    }

    public Expression getFalseBranch() {
        return falseBranch;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitConditionalExpression(this);
    }
}
