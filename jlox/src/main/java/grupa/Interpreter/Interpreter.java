package grupa.Interpreter;

import grupa.Expressions.*;
import grupa.Lox;
import grupa.Scanner.Token;
import grupa.Scanner.TokenType;
import grupa.Statements.*;

import java.util.List;

public class Interpreter implements ExprVisitor<Object>, StmtVisitor<Void> {
    private Environment environment = new Environment();

    public void interpret(List<Stmt> stmts) {
        try {
            for (Stmt stmt : stmts) {
                execute(stmt);
            }
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        }
    }

    public String interpret(Expr expression) {
        try {
            Object value = evaluate(expression);
            return stringify(value);
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
            return null;
        }
    }

    private void execute(Stmt stmt) throws RuntimeError {
        stmt.accept(this);
    }

    private String stringify(Object value) {
        if (value == null) return "nil";
        if (value instanceof Double) {
            if (value.toString().endsWith(".0")) return value.toString().substring(0, value.toString().length() - 2);
        }
        return value.toString();
    }

    @Override
    public Object visitBinaryExpression(Binary expression) throws RuntimeError {
        Object left = evaluate(expression.getLeft());
        Object right = evaluate(expression.getRight());

        switch (expression.getOperator().getType()) {
            case SLASH:
                checkNumberOperands(expression.getOperator(), left, right);
                return (double) left / (double) right;
            case STAR:
                checkNumberOperands(expression.getOperator(), left, right);
                return (double) left * (double) right;
            case MINUS:
                checkNumberOperands(expression.getOperator(), left, right);
                return (double) left - (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) return (double) right + (double) left;
                else if ((left instanceof String || left instanceof Double) && (right instanceof String || right instanceof Double))
                    return stringify(left) + stringify(right);
                throw new RuntimeError(expression.getOperator(), "Operands must be Number or String");
            case GREATER:
                checkNumberOperands(expression.getOperator(), left, right);
                return (double) left > (double) right;
            case GREATER_EQUAL:
                checkNumberOperands(expression.getOperator(), left, right);
                return (double) left >= (double) right;
            case LESS:
                checkNumberOperands(expression.getOperator(), left, right);
                return (double) left < (double) right;
            case LESS_EQUAL:
                checkNumberOperands(expression.getOperator(), left, right);
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
    public Void visitExpressionStatement(Expression statement) throws RuntimeError {
        Object value = evaluate(statement.getExpression());
        stringify(value);
        return null;
    }

    @Override
    public Void visitPrintStatement(Print statement) throws RuntimeError {
        Object value = evaluate(statement.getExpression());
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitVarStatement(Var statement) throws RuntimeError {
        Object initializer = null;
        if (statement.getInitializer() != null) {
            initializer = evaluate(statement.getInitializer());
        }
        environment.define(statement.getName(), initializer);
        return null;
    }

    @Override
    public Void visitBlockStatement(Block block) throws RuntimeError {
        executeBlock(block.getStmts(), new Environment(environment));
        return null;
    }

    @Override
    public Void visitIfStatement(If statement) throws RuntimeError {
        if (isTruthy(evaluate(statement.getCondition()))) {
            execute(statement.getThenBranch());
        } else if (statement.getElseBranch() != null) {
            execute(statement.getElseBranch());
        }
        return null;
    }

    @Override
    public Void visitWhileStatement(While statement) throws RuntimeError {
        while (isTruthy(evaluate(statement.getCondition()))) {
            execute(statement.getBody());
        }
        return null;
    }

    private void executeBlock(List<Stmt> stmts, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;
            for (Stmt stmt : stmts) {
                execute(stmt);
            }
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        } finally {
            this.environment = previous;
        }

    }

    @Override
    public Object visitGroupingExpression(Grouping expression) throws RuntimeError {
        return evaluate(expression.getExpression());
    }

    @Override
    public Object visitLiteralExpression(Literal expression) {
        return expression.getValue();
    }

    @Override
    public Object visitUnaryExpression(Unary expression) throws RuntimeError {
        Object right = evaluate(expression.getRight());
        switch (expression.getOperator().getType()) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
                checkNumberOperand(expression.getOperator(), right);
                return -(double) right;
        }
        return null;
    }

    @Override
    public Object visitConditionalExpression(Conditional expression) throws RuntimeError {
        Object value = evaluate(expression.getCondition());
        checkBoolean(expression.getColon(), value);
        boolean condition = (boolean) value;
        if (condition) {
            return evaluate(expression.getTrueBranch());
        } else {
            return evaluate(expression.getFalseBranch());
        }
    }

    @Override
    public Object visitVariableExpression(Variable expression) throws RuntimeError {
        return environment.get(expression.getName());
    }

    @Override
    public Object visitAssignExpression(Assign expression) throws RuntimeError {
        Object value = evaluate(expression.getValue());
        environment.assign(expression.getName(), value);
        return value;
    }

    @Override
    public Object visitLogicalExpression(Logical expression) throws RuntimeError {
        Object left = evaluate(expression.getLeft());

        if (expression.getOperator().getType() == TokenType.OR) {
            if (isTruthy(left)) return left;
        } else {
            if (!isTruthy(left)) return left;
        }
        return evaluate(expression.getRight());
    }

    private void checkNumberOperand(Token token, Object operand) throws RuntimeError {
        if (operand instanceof Double) return;
        throw new RuntimeError(token, "Operand must be a number");
    }

    private void checkNumberOperands(Token token, Object left, Object right) throws RuntimeError {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(token, "Operand must be a number");
    }

    private void checkBoolean(Token token, Object value) throws RuntimeError {
        if (value instanceof Boolean) return;
        throw new RuntimeError(token, "Expression must return boolean");
    }

    private Object evaluate(Expr expr) throws RuntimeError {
        return expr.accept(this);
    }

    private boolean isTruthy(Object right) {
        if (right == null) return false;
        if (right instanceof Boolean) return (Boolean) right;
        return true;
    }


}
