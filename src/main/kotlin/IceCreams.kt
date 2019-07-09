//import java.lang.NullPointerException
//
fun main() {

//    val flist1 = FPList(1, 2, 3, 4)
//    println(flist1)
//
//    val flist2 = FPList.Cons(1, FPList.Cons(2, FPList.Cons(3, FPList.Nil)))
//    println((flist2.getLastNode() as FPList.Cons).x)
//    println((flist2.getLastNode() as FPList.Cons).xs)

}


sealed class FPList<out T> {

    companion object {

        operator fun <T> invoke(vararg elements: T): FPList<T> {
            var results: FPList<T> = FPList.Nil
            for(value in elements.reversed()) {
                results = Cons(value, results)
            }
            return results
        }


//        operator fun <T> invoke(vararg elements: T): FPList<T> {
//
//            tailrec fun <T> recursive(elements: Array<out T>, acc: FPList<T>): FPList<T> = when {
//                elements.isEmpty() -> acc
//                else -> {
//                    when (acc) {
//                        Nil -> recursive(elements.copyOfRange(1, elements.size), Cons(elements[0], Nil))
//                        is Cons -> {
//                            recursive(elements.copyOfRange(1, elements.size), ) //(acc.getLastNode() as Cons<T>).copy(xs = Cons(elements[0], Nil))
//                        }
//                    }
//                }
//            }
//
//            return recursive(elements, Nil)
//        }
    }

    object Nil : FPList<Nothing>()
    data class Cons<out T>(val x: T, val xs: FPList<T>) : FPList<T>()
}

fun <T> FPList<T>.getLastNode(): FPList<T> = when(this) {
    FPList.Nil -> throw NullPointerException()
    is FPList.Cons -> if (xs is FPList.Cons) xs.getLastNode() else this
}

sealed class FList<out T> {
    companion object {
        operator fun <T> invoke(vararg elements: T): FList<T> {
            fun<T> make(elements: Array<out T>): FList<T> = when {
                elements.isEmpty() -> Nil
                else -> {
                    IceCreamCons(elements[0], make(elements.copyOfRange(1, elements.size)))
                }
            }
            return make(elements)
        }
    }

    object Nil : FList<Nothing>()
    data class IceCreamCons<out T>(val x: T, val xs: FList<T>) : FList<T>()
}



















//tailrec fun <T, R> RList<T>.flatMap(acc: RList<R> = RList.Nil, f: (v: T) -> RList<R>): RList<R> = when(this) {
//    RList.Nil -> acc
//    is RList.Cons -> tail.flatMap(acc.append(f(head)), f)
//}
//
//fun <T> RList<T>.append(other: RList<T>): RList<T> = when(this) {
//    RList.Nil -> other
//    is RList.Cons -> if (tail !== RList.Nil) {
//        RList.Cons(head, tail.append(other))
//    } else {
//        RList.Cons(head, other)
//    }
//}