package grupa.Expressions;

public class Literal extends Expression {
    public Literal(Object value) {
        this.value = value;
    }

    final Object value;

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteralExpression(this);
    }

    public Object getValue() {
        return value;
    }
}
