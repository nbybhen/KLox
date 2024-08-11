class Environment(val enclosing: Environment? = null) {
    private val values: MutableMap<String, Any?> = mutableMapOf()

    fun define(name: String, value: Any?) {
        values[name] = value
    }

    fun get(name: Token): Any? {
        return values.getOrElse(name.lexeme) {
            enclosing?.get(name) ?: throw RuntimeError(name, "Undefined variable '${name.lexeme}'.")
        }
    }

    fun assign(name: Token, value: Any?) {
        if (values.containsKey(name.lexeme)) {
            values[name.lexeme] = value
            return
        }

        enclosing?.assign(name, value) ?: throw RuntimeError(name, "Undefined variable '${name.lexeme}'.")
    }
}