package grupa.Expressions;

public interface Visitor<R>{
    R visitBinaryExpression(Binary expression);
    R visitGroupingExpression(Grouping expression);
    R visitLiteralExpression(Literal expression);
    R visitUnaryExpression(Unary expression);
}
