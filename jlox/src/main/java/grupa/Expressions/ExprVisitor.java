package grupa.Expressions;

import grupa.Interpreter.RuntimeError;

public interface ExprVisitor<R> {
    R visitBinaryExpression(Binary expression);

    R visitGroupingExpression(Grouping expression);

    R visitLiteralExpression(Literal expression);

    R visitUnaryExpression(Unary expression);

    R visitConditionalExpression(Conditional expression);

    R visitVariableExpression(Variable expression);

    R visitAssignExpression(Assign expression);

    R visitLogicalExpression(Logical expression);

    R visitCallExpression(Call expression);

}
