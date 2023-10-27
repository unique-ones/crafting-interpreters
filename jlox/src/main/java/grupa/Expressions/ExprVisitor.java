package grupa.Expressions;

import grupa.Parser.RuntimeError;

public interface ExprVisitor<R>{
    R visitBinaryExpression(Binary expression) throws RuntimeError;
    R visitGroupingExpression(Grouping expression) throws RuntimeError;
    R visitLiteralExpression(Literal expression);
    R visitUnaryExpression(Unary expression) throws RuntimeError;
    R visitConditionalExpression(Conditional expression) throws RuntimeError;

}
