package grupa.Runtime.Environment;

import grupa.Expressions.Function;
import grupa.Runtime.Exceptions.ReturnException;
import grupa.Runtime.Interpreter;

import java.util.List;

public class LoxFunction implements LoxCallable {
    private final String name;
    private final grupa.Expressions.Function declaration;
    private final Environment closure;
    private boolean isInitializer;


    public LoxFunction(String name, grupa.Expressions.Function declaration, Environment closure, boolean isInitializer) {
        this.isInitializer = isInitializer;
        this.name = name;
        this.declaration = declaration;
        this.closure = closure;
    }

    public String getName() {
        return name;
    }

    public Function getDeclaration() {
        return declaration;
    }

    public Environment getClosure() {
        return closure;
    }

    @Override
    public int getArity() {
        return this.declaration.getParamters().size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        try {
            Environment environment = new Environment(this.closure);

            for (int i = 0; i < this.declaration.getParamters().size(); i++) {
                environment.define(this.declaration.getParamters().get(i).getLexeme(), args.get(i));
            }
            interpreter.executeBlock(this.declaration.getBody(), environment);
        } catch (ReturnException e) {
            if (isInitializer) return closure.getAt(0, "this");

            return e.getValue();
        }
        if (isInitializer) return closure.getAt(0, "this");
        return null;
    }

    @Override
    public String toString() {
        return "LoxFunction{" +
                "name='" + name + '\'' +
                ", declaration=" + declaration +
                ", closure=" + closure +
                '}';
    }

    public LoxFunction bind(LoxInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new LoxFunction(this.name, this.declaration, environment, isInitializer);
    }
}
