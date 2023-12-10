package grupa.Runtime.Environment;

import grupa.Runtime.Interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoxClass extends LoxInstance implements LoxCallable {
    private final String name;
    private final Map<String, LoxFunction> methods;
    private final LoxClass superClass;

    public LoxClass(LoxClass metaClass, String name, Map<String, LoxFunction> methods, LoxClass superClass) {
        super(metaClass);
        this.superClass = superClass;
        this.name = name;
        this.methods = methods;
    }

    public Map<String, LoxFunction> getMethods() {
        return methods;
    }

    public LoxClass getSuperClass() {
        return superClass;
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
        if (superClass != null)
            return superClass.findMethod(lexeme);

        return null;
    }
}
