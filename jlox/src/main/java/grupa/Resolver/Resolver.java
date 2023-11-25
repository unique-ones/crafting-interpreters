package grupa.Resolver;

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
    private final Stack<Map<String, Variable>> scopes = new Stack<>();

    private FunctionType currentFunction = FunctionType.NONE;

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
        if (currentFunction == FunctionType.NONE) {
            Lox.error(statement.getKeyword(), "Can't return from top-level code");
        }
        if (statement.getExpr() != null) resolve(statement.getExpr());
        return null;
    }


    private void beginScope() {
        scopes.push(new HashMap<String, Variable>());
    }

    public void resolve(List<Stmt> stmts) {
        stmts.forEach(stmt -> resolve(stmt));
    }

    public void resolve(Stmt stmt) {
        stmt.accept(this);
    }

    private void endScope() {
        Map<String, Variable> scope = this.scopes.pop();
        for (Map.Entry<String, Variable> entry : scope.entrySet()) {
            if (entry.getValue().variableState != VariableState.USED) {
                Lox.error(entry.getValue().getName(), "Local variable is never used");
            }
        }
    }

    private void define(Token name) {
        if (scopes.isEmpty()) return;
        scopes.peek().put(name.getLexeme(), new Variable(name, VariableState.DEFINED));
    }

    private void declare(Token name) {
        if (scopes.isEmpty()) return;
        Map<String, Variable> scope = scopes.peek();
        if (scope.containsKey(name.getLexeme())) {
            Lox.error(name, "This variable is already defined in this scope.");
        }
        scope.put(name.getLexeme(), new Variable(name, VariableState.DECLARED));
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
        if (!scopes.isEmpty() && scopes.peek().containsKey(expression.getName().getLexeme()) && scopes.peek().get(expression.getName().getLexeme()).variableState == VariableState.DECLARED) {
            Lox.error(expression.getName(), "Can't read local variable in its own initializer");
        }
        resolveLocal(expression, expression.getName(), true);
        return null;
    }


    @Override
    public Void visitAssignExpression(Assign expression) {
        resolve(expression.getValue());
        resolveLocal(expression, expression.getName(), false);
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
        FunctionType enclosingFunction = currentFunction;
        //@TODO will get problems when adding methods
        currentFunction = FunctionType.FUNCTION;
        beginScope();
        for (Token param : expression.getParamters()) {
            declare(param);
            define(param);
        }
        resolve(expression.getBody());
        endScope();
        currentFunction = enclosingFunction;
        return null;
    }

    private void resolve(Expr expr) {
        expr.accept(this);
    }

    private void resolveLocal(Expr expression, Token name, boolean isRead) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name.getLexeme())) {
                interpreter.resolve(expression, scopes.size() - 1 - i);

                if (isRead) {
                    scopes.get(i).get(name.getLexeme()).variableState = VariableState.USED;
                }
                return;
            }
        }
    }
}
