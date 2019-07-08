fun main() {

    val list1 = RList.Cons(1, RList.Cons(2, RList.Cons(3, RList.Cons(4, RList.Nil))))
    println(list1.sum())


    val array1 = arrayOf(1, 2, 3, 4, 5)
    println(array1.toRList())

    println(list1.foldLeft(0) { acc, head -> acc + head})

    println(list1.sum1())

}


sealed class RList<out A> {
    object Nil : RList<Nothing>()
    data class Cons<out A>(val head: A, val tail: RList<A>) : RList<A>()
}


fun RList<Int>.sum(): Int = when (this) {
    RList.Nil -> 0
    is RList.Cons -> head + tail.sum()
}

fun RList<Int>.sum1(): Int = foldLeft(0) {acc, head -> acc + head}

fun <T, R> RList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    RList.Nil -> acc
    is RList.Cons -> f(head, tail.foldRight(acc, f))
}

fun <T, R> RList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when(this) {
    RList.Nil -> acc
    is RList.Cons -> tail.foldLeft(f.invoke(acc, head), f)
}

fun <T> Array<out T>.toRList(): RList<T> = when {
    this.isEmpty() -> RList.Nil
    else -> RList.Cons(this[0], this.copyOfRange(1, this.size).toRList())
}

fun RList<Int>.product(): Int = when (this) {
    RList.Nil -> 1
    is RList.Cons -> head * tail.product()

}

