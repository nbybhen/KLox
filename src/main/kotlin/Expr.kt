sealed interface Expr {
    interface Visitor<R> {
        fun visitBinaryExpr(expr: Binary) : R
        fun visitUnaryExpr(expr: Unary) : R
        fun visitLiteralExpr(expr: Literal) : R
        fun visitGroupingExpr(expr: Grouping) : R
        fun visitVariableExpr(expr: Variable) : R
        fun visitAssignExpr(expr: Assign): R
        fun visitLogicalExpr(expr: Logical): R
        fun visitCallExpr(expr: Call): R
    }

    data class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr {
        override fun<R> accept(visitor: Visitor<R>): R {
            return visitor.visitBinaryExpr(this@Binary)
        }
    }

    data class Unary(val operator: Token, val right: Expr): Expr {
        override fun<R> accept(visitor: Visitor<R>): R {
            return visitor.visitUnaryExpr(this@Unary)
        }
    }

    data class Grouping(val expression: Expr) : Expr {
        override fun<R> accept(visitor: Visitor<R>): R {
            return visitor.visitGroupingExpr(this@Grouping)
        }
    }

    data class Literal(val value: Any?) : Expr {
        override fun<R> accept(visitor: Visitor<R>): R {
            return visitor.visitLiteralExpr(this@Literal)
        }
    }

    class Variable(val name: Token): Expr {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitVariableExpr(this@Variable)
        }
    }

    data class Assign(val name: Token, val value: Expr): Expr {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitAssignExpr(this@Assign)
        }
    }

    data class Logical(val left: Expr, val operator: Token, val right: Expr): Expr {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitLogicalExpr(this@Logical)
        }
    }

    data class Call(val callee: Expr, val paren: Token, val args: List<Expr>): Expr {
        override fun <R> accept(visitor: Visitor<R>): R {
            return visitor.visitCallExpr(this@Call)
        }
    }

    fun<R> accept(visitor: Visitor<R>): R
}