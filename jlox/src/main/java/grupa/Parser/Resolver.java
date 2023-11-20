package grupa.Parser;

import grupa.Expressions.*;
import grupa.Expressions.Function;
import grupa.Interpreter.Interpreter;
import grupa.Statements.*;

public class Resolver implements StmtVisitor<Void>, ExprVisitor<Void> {
    private final Interpreter interpreter;

    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Void visitBinaryExpression(Binary expression) {
        return null;
    }

    @Override
    public Void visitGroupingExpression(Grouping expression) {
        return null;
    }

    @Override
    public Void visitLiteralExpression(Literal expression) {
        return null;
    }

    @Override
    public Void visitUnaryExpression(Unary expression) {
        return null;
    }

    @Override
    public Void visitConditionalExpression(Conditional expression) {
        return null;
    }

    @Override
    public Void visitVariableExpression(Variable expression) {
        return null;
    }

    @Override
    public Void visitAssignExpression(Assign expression) {
        return null;
    }

    @Override
    public Void visitLogicalExpression(Logical expression) {
        return null;
    }

    @Override
    public Void visitCallExpression(Call expression) {
        return null;
    }

    @Override
    public Void visitFunctionExpression(Function expression) {
        return null;
    }

    @Override
    public Void visitExpressionStatement(Expression statement) {
        return null;
    }

    @Override
    public Void visitPrintStatement(Print statement) {
        return null;
    }

    @Override
    public Void visitVarStatement(Var statement) {
        return null;
    }

    @Override
    public Void visitBlockStatement(Block block) {
        return null;
    }

    @Override
    public Void visitIfStatement(If Statement) {
        return null;
    }

    @Override
    public Void visitWhileStatement(While statement) {
        return null;
    }

    @Override
    public Void visitBreakStatement(Break statement) {
        return null;
    }

    @Override
    public Void visitContinueStatement(Continue statement) {
        return null;
    }

    @Override
    public Void visitFunctionStatement(grupa.Statements.Function statement) {
        return null;
    }

    @Override
    public Void visitReturnStatement(Return statement) {
        return null;
    }
}
