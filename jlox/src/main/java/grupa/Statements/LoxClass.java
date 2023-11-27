package grupa.Statements;

import grupa.Interpreter.Interpreter;
import grupa.Interpreter.LoxCallable;
import grupa.Interpreter.LoxInstance;

import java.util.List;

public class LoxClass implements LoxCallable {
    private final String name;

    public LoxClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "LoxClass{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int getArity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        LoxInstance instance = new LoxInstance(this);
        return instance;
    }
}
