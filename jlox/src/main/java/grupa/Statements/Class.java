package grupa.Statements;

import grupa.Expressions.Variable;
import grupa.Scanner.Token;

import java.util.ArrayList;
import java.util.List;

public class Class extends Stmt {
    private final Token name;
    private final List<Function> methods;

    private final Variable superClass;

    private final List<Function> classMethods;


    public Class(Token name, List<Function> methods, List<Function> classMethods, Variable superClass) {
        this.name = name;
        this.methods = methods;
        this.classMethods = classMethods;
        this.superClass = superClass;
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

    public Variable getSuperClass() {
        return superClass;
    }

    @Override
    public <R> R accept(StmtVisitor<R> stmtVisitor) {
        return stmtVisitor.visitClassStatement(this);
    }
}
