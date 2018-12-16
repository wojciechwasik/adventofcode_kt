package util

inline fun <T> timer(message: String, code: () -> T):T {
    val t1 = System.nanoTime()
    try {
       return code()
    } finally {
        val t2 = System.nanoTime()
        println("$message: ${t2 - t1} ns")
    }
}
