package grupa.Statements;

import grupa.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

public class Class extends Stmt {
    private final Token name;
    private final List<Function> methods;

    public Class(Token name, List<Function> methods) {
        this.name = name;
        this.methods = methods;
    }

    public Token getName() {
        return name;
    }

    public List<Function> getMethods() {
        return methods;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitClassStatement(this);
    }
}
