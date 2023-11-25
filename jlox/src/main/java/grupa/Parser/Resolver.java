package grupa.Parser;

import grupa.Expressions.*;
import grupa.Interpreter.Interpreter;
import grupa.Lox;
import grupa.Scanner.Token;
import grupa.Statements.*;
import grupa.Statements.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Resolver implements StmtVisitor<Void>, ExprVisitor<Void> {
    private final Interpreter interpreter;
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();

    public Resolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Void visitExpressionStatement(Expression statement) {
        resolve(statement.getExpression());
        return null;
    }

    @Override
    public Void visitPrintStatement(Print statement) {
        resolve(statement.getExpression());
        return null;
    }

    @Override
    public Void visitVarStatement(Var statement) {
        declare(statement.getName());
        if (statement.getInitializer() != null) resolve(statement.getInitializer());
        define(statement.getName());
        return null;
    }

    @Override
    public Void visitBlockStatement(Block block) {
        beginScope();
        resolve(block.getStmts());
        endScope();
        return null;
    }

    @Override
    public Void visitIfStatement(If statement) {
        resolve(statement.getCondition());
        resolve(statement.getThenBranch());
        if (statement.getElseBranch() != null) resolve(statement.getElseBranch());
        return null;
    }

    @Override
    public Void visitWhileStatement(While statement) {
        resolve(statement.getCondition());
        resolve(statement.getBody());
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
    public Void visitFunctionStatement(Function statement) {
        declare(statement.getName());
        define(statement.getName());
        resolve(statement.getDeclaration());
        return null;
    }


    @Override
    public Void visitReturnStatement(Return statement) {
        if (statement.getExpr() != null) resolve(statement.getExpr());
        return null;
    }


    private void resolveFunction(Function statement) {
        beginScope();
        for (Token param : statement.getParams()) {
            declare(param);
            define(param);
        }
        resolve(statement.getBody());
        endScope();
    }

    private void beginScope() {
        scopes.push(new HashMap<String, Boolean>());
    }

    private void resolve(List<Stmt> stmts) {
        stmts.forEach(stmt -> resolve(stmt));
    }

    private void resolve(Stmt stmt) {
        stmt.accept(this);
    }

    private void endScope() {
        this.scopes.pop();
    }

    private void define(Token name) {
        if (scopes.isEmpty()) return;
        scopes.peek().put(name.getLexeme(), true);
    }

    private void declare(Token name) {
        if (scopes.isEmpty()) return;
        Map<String, Boolean> scope = scopes.peek();
        scope.put(name.getLexeme(), false);
    }

    @Override
    public Void visitBinaryExpression(Binary expression) {
        resolve(expression.getLeft());
        resolve(expression.getRight());
        return null;
    }

    @Override
    public Void visitGroupingExpression(Grouping expression) {
        resolve(expression.getExpression());
        return null;
    }

    @Override
    public Void visitLiteralExpression(Literal expression) {
        return null;
    }

    @Override
    public Void visitUnaryExpression(Unary expression) {
        resolve(expression.getRight());
        return null;
    }

    @Override
    public Void visitConditionalExpression(Conditional expression) {
        resolve(expression.getCondition());
        resolve(expression.getTrueBranch());
        resolve(expression.getFalseBranch());
        return null;
    }

    @Override
    public Void visitVariableExpression(Variable expression) {
        if (!scopes.isEmpty() && scopes.peek().get(expression.getName().getLexeme()) == Boolean.FALSE) {
            Lox.error(expression.getName(), "Can't read local variable in its own initializer");
        }
        resolveLocal(expression, expression.getName());
        return null;
    }


    @Override
    public Void visitAssignExpression(Assign expression) {
        resolve(expression.getValue());
        resolveLocal(expression, expression.getName());
        return null;
    }

    @Override
    public Void visitLogicalExpression(Logical expression) {
        resolve(expression.getLeft());
        resolve(expression.getRight());
        return null;
    }

    @Override
    public Void visitCallExpression(Call expression) {
        resolve(expression.getCallee());
        for (Expr arg : expression.getArguments()) {
            resolve(arg);
        }
        return null;
    }

    @Override
    public Void visitFunctionExpression(grupa.Expressions.Function expression) {
        beginScope();
        for (Token param : expression.getParamters()) {
            define(param);
            declare(param);
        }
        resolve(expression.getBody());
        endScope();

        return null;
    }

    private void resolve(Expr expr) {
        expr.accept(this);
    }

    private void resolveLocal(Expr expression, Token name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name.getLexeme())) {
                interpreter.resolve(expression, scopes.size() - 1 - i);
                return;
            }
        }
    }
}
