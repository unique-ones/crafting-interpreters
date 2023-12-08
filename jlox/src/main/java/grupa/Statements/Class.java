package grupa.Statements;

import grupa.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

public class Class extends Stmt {
    private final Token name;
    private final List<Function> methods;

    private final List<Function> classMethods;


    public Class(Token name, List<Function> methods, List<Function> classMethods) {
        this.name = name;
        this.methods = methods;
        this.classMethods = classMethods;
    }

    public Token getName() {
        return name;
    }

    public List<Function> getMethods() {
        return methods;
    }

    public List<Function> getClassMethods() {
        return classMethods;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitClassStatement(this);
    }
}
