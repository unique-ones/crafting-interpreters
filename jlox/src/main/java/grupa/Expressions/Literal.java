package grupa.Expressions;

public class Literal extends Expr {
    public Literal(Object value) {
        this.value = value;
    }

    final Object value;

    @Override
    public <R> R accept(ExprVisitor<R> exprVisitor) {
        return exprVisitor.visitLiteralExpression(this);
    }

    public Object getValue() {
        return value;
    }
}
