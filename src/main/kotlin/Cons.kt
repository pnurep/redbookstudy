fun main() {

//    val list1 = RList.Cons(1, RList.Cons(2, RList.Cons(3, RList.Cons(4, RList.Nil))))
//    println(list1.sum())
//
//    val array1 = arrayOf(1, 2, 3, 4, 5)
//    println(array1.toRList())
//
//    println(list1.foldLeft(0) { acc, head -> acc + head})
//
//    println(list1.sum1())
//
//    println(list1.addTail(1))
//
//    list1.map { it+1 }.forEach { print(it) }
//    println()
//
//    list1.forEach { print(it) }

//    val a = arrayOf(
//        arrayOf(1),
//        arrayOf(1),
//        arrayOf(1),
//        arrayOf(1),
//        arrayOf(1)
//    )
//
//    println(a.toRList())

    val doubleList = RList.Cons(
        RList.Cons(1,
            RList.Cons(2,
                RList.Cons(3,
                    RList.Cons(4,
                        RList.Cons(5, RList.Nil))))),
        RList.Nil
    )

    val list2
            = RList.Cons(1, RList.Cons(2, RList.Cons(3, RList.Cons(4, RList.Nil))))
    println(doubleList.flatMap {
        it
    })

}

sealed class RList<out T> {
    object Nil : RList<Nothing>()
    data class Cons<out T>(val head: T, val tail: RList<T>) : RList<T>()

    override fun toString(): String = when (this) {
        is Nil  -> "Nil"
        is Cons -> "$head, $tail"
    }
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



//fun <T> RList<T>.flatten(acc: RList<T> = RList.Nil): RList<T> = when(this) {
//    RList.Nil -> acc
//    is RList.Cons -> tail.flatten(head.append(acc))
//}


tailrec fun <T> RList<T>.forEach(f: (T) -> Unit): Unit = when(this) {
    RList.Nil -> Unit
    is RList.Cons -> {
        f(head)
        tail.forEach(f)
    }
}

tailrec fun <T, R> RList<T>.map(acc: RList<R> = RList.Nil, transformer: (T) -> R): RList<R> = when(this) {
    RList.Nil -> acc.reverse()
    is RList.Cons -> tail.map(acc.addHead(transformer(head)), transformer)
}

tailrec fun <T> RList<T>.filter(acc: RList<T> = RList.Nil, predicate: (T) -> Boolean): RList<T> = when(this) {
    RList.Nil -> acc.reverse()
    is RList.Cons -> {
        if (predicate(head)) {
            tail.filter(acc.addHead(head), predicate)
        } else {
            tail.filter(acc, predicate)
        }
    }
}

tailrec fun <T> RList<T>.reverse(acc: RList<T> = RList.Nil): RList<T> = when(this) {
    RList.Nil -> acc
    is RList.Cons -> tail.reverse(acc.addHead(head))
}

fun <T> RList<T>.addHead(input: T): RList<T> = RList.Cons(input, this)

tailrec fun <T> RList<T>.addTail(input: T, acc: RList<T> = RList.Nil): RList<T> = when(this) {
    RList.Nil -> RList.Cons(input, acc).reverse()
    is RList.Cons -> tail.addTail(input, acc.addHead(head))
}



fun <T, R> RList<T>.flatMap(acc: RList<R> = RList.Nil, f: (T) -> RList<R>): RList<R> = when(this) {
    RList.Nil -> acc
    is RList.Cons -> {
        tail.flatMap(  acc.apply(input = f(head)) , f)
    }
}

fun <T> RList<T>.apply(acc: RList<T> = RList.Nil, input: RList<T>): RList<T> = when(this) {
    RList.Nil -> acc
    is RList.Cons -> {
        RList.Cons(head, tail.apply(input = input))
    }
}


















