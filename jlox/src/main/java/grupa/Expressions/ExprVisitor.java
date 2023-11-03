package grupa.Expressions;

import grupa.Interpreter.RuntimeError;

public interface ExprVisitor<R> {
    R visitBinaryExpression(Binary expression) throws RuntimeError;

    R visitGroupingExpression(Grouping expression) throws RuntimeError;

    R visitLiteralExpression(Literal expression);

    R visitUnaryExpression(Unary expression) throws RuntimeError;

    R visitConditionalExpression(Conditional expression) throws RuntimeError;

    R visitVariableExpression(Variable expression) throws RuntimeError;

    R visitAssignExpression(Assign expression) throws RuntimeError;

    R visitLogicalExpression(Logical expression) throws RuntimeError;


}
