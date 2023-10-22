package grupa.Parser;

import grupa.Expressions.*;

public class Interpreter implements Visitor<Object> {
    @Override
    public Object visitBinaryExpression(Binary expression) {
        Object left = evaluate(expression.getLeft());
        Object right = evaluate(expression.getRight());

        switch (expression.getOperator().getType()) {
            case SLASH:
                return (double) left / (double) right;
            case STAR:
                return (double) left * (double) right;
            case MINUS:
                return (double) left - (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) return (double) right + (double) left;
                else if ((left instanceof String || right instanceof String) && (left instanceof Double || right instanceof Double))
                    return (String) left + (String) right;
            case GREATER:
                return (double) left > (double) right;
            case GREATER_EQUAL:
                return (double) left >= (double) right;
            case LESS:
                return (double) left < (double) right;
            case LESS_EQUAL:
                return (double) left <= (double) right;
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case BANGEQUAL:
                return !isEqual(left, right);

        }
        return null;
    }

    private boolean isEqual(Object left, Object right) {
        if (left == null && right == null) return true;
        if (left == null) return false;
        return left.equals(right);
    }

    @Override
    public Object visitGroupingExpression(Grouping expression) {
        return evaluate(expression);
    }

    @Override
    public Object visitLiteralExpression(Literal expression) {
        return expression.getValue();
    }

    @Override
    public Object visitUnaryExpression(Unary expression) {
        Object right = evaluate(expression.getRight());
        switch (expression.getOperator().getType()) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                return -(double) right;
        }

        return null;
    }

    @Override
    public Object visitConditionalExpression(Conditional expression) {
        boolean condition =(boolean) evaluate(expression.getCondition());

        if(condition){
            return evaluate(expression.getTrueBranch());
        }
        else{
            return evaluate(expression.getFalseBranch());
        }
    }


    private Object evaluate(Expression expression) {
        return expression.accept(this);
    }

    private boolean isTruthy(Object right) {
        if (right == null) return false;
        if (right instanceof Boolean) return (Boolean) right;
        return true;
    }
}
