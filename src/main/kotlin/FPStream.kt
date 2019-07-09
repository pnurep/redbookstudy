import java.lang.Exception

sealed class FPStream<out T> {

    companion object {
        operator fun <T> invoke(vararg element: () -> T): FPStream<T> {
            var results: FPStream<T> = Nil
            for (value in element) {
                results = Seq(value, results)
            }
            return results
        }
    }

    data class Seq<T>(private val _head: () -> T, val tail: FPStream<T>): FPStream<T>() {
        var head: T? = null
            private set
            get() = field ?: _head.invoke().also {
                field = it
            }
    }
    object Nil : FPStream<Nothing>()
}

fun <T> FPStream<T>.head() = when(this) {
    FPStream.Nil -> throw Exception("empty")
    is FPStream.Seq -> head
}

fun <T> FPStream<T>.tail() = when(this) {
    FPStream.Nil -> throw Exception("empty")
    is FPStream.Seq -> tail
}