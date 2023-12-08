package grupa.Runtime.Environment;

import grupa.Runtime.Interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoxClass extends LoxInstance implements LoxCallable {
    private final String name;
    private final Map<String, LoxFunction> methods;


    public LoxClass(LoxClass metaClass,String name, Map<String, LoxFunction> methods) {
        super(metaClass);
        this.name = name;
        this.methods = methods;
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
        LoxFunction initializer = findMethod("init");
        if (initializer == null) return 0;

        return initializer.getArity();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        LoxInstance instance = new LoxInstance(this);
        LoxFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, args);
        }
        return instance;
    }

    public LoxFunction findMethod(String lexeme) {
        if (methods.containsKey(lexeme)) {
            return this.methods.get(lexeme);
        }
        return null;
    }
}
