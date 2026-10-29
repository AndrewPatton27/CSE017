# Practice Test 3 - Answers

## Part A
**A1.** `mystery(5) = 5 + 3 + 1 = 9`. `mystery(6) = 6 + 4 + 2 + mystery(0) = 12 + 1 = 13`.

**A2.** `3 2 1 1 2 3 ` (prints on the way down, then again on the way back up).

**A3.** `"eca"`. g("abcdef") = g("cdef") + 'a' = (g("ef") + 'c') + 'a' = ((g("") + 'e') + 'c') + 'a'. It keeps every 2nd character, starting from the first, in reverse order.

**A4.** Neither has a working base case that is reachable.
`sum1` has **no base case**: fix with `if (n <= 0) return 0;`.
`sum2` has a base case but the recursive call moves **away** from it (`n + 1`): fix with `sum2(n - 1)`.

**A5.**
```java
public static int countChar(String s, char c) {
    return countChar(s, c, 0);
}
private static int countChar(String s, char c, int index) {
    if (index == s.length()) return 0;                       // base case
    int here = s.charAt(index) == c ? 1 : 0;
    return here + countChar(s, c, index + 1);                // recursive case
}
// or without a helper:
public static int countChar2(String s, char c) {
    if (s.isEmpty()) return 0;
    return (s.charAt(0) == c ? 1 : 0) + countChar2(s.substring(1), c);
}
```

**A6.** The public method should be `binarySearch(list, key)`, but the recursion needs the current search range, so a helper with extra parameters
`first`/`last` carries that state. (Method overloading.)

## Part B
| | iterations | Big-O |
|---|---|---|
| B1 | outer runs n/2 times, inner n -> n^2/2 | O(n^2) |
| B2 | i = n, n/3, n/9 ... -> log3(n) | O(log n) |
| B3 | 10 * (0+1+...+(n-1)) = 10 * n(n-1)/2 = 5n^2 - 5n | O(n^2) |
| B4 | sequential: n + log2(n) | O(n) |
| B5 | n iterations x `contains` O(n) = n^2 | O(n^2) |
| B6 | two calls of size n-1 each: T(n)=2T(n-1) -> 2^n calls | O(2^n) |
| B7 | n -> n/2 -> ... -> 1: log2(n) calls | O(log n) |

**B8.** `ArrayList.add(0, x)` is O(n) (shifts all elements) so n of them is O(n^2). `LinkedList.addFirst` is O(1), so n of them is O(n).

**B9.** Binary search, O(log n) vs O(n). The data must be **sorted** (and random access, as in an array/ArrayList).

## Part C
**C1.** (1) `new E()` - can't instantiate E. (2) `new E[10]` - can't create a generic array. (3) `static E` - static members can't use the class's type parameter.
(4) compiles. (5) compiles (the method has its own `<T>`). (6) does **not** compile - static method using the class's `E`.
(7) does not compile - exception classes can't be generic.

**C2.** Example: `list.add(42)` into `ArrayList<String>` is rejected by the **compiler**. With a raw/`Object` list it compiles, and
`String s = (String) list.get(1)` throws `ClassCastException` at **runtime**. Generics also remove the casts.

**C3.**
```java
public static <E extends Comparable<E>> E min(List<E> list) {
    Iterator<E> it = list.iterator();
    E best = it.next();
    while (it.hasNext()) {
        E current = it.next();
        if (current.compareTo(best) < 0) best = current;
    }
    return best;
}
```
Without the bound, `E` is only known to be `Object`, which has no `compareTo`.

**C4.**
```java
public class Book implements Comparable<Book> {
    ...
    @Override public int compareTo(Book b) { return title.compareTo(b.title); }
}
public class ComparatorByPages implements Comparator<Book> {
    @Override public int compare(Book b1, Book b2) { return b2.getPages() - b1.getPages(); }   // b2 first = descending
}
```

**C5.** `Comparable<E>`: method `compareTo(E)` lives **in the class**, gives the one natural ordering, `Arrays.sort(arr)` / `list.sort(null)`.
`Comparator<E>`: method `compare(E, E)` lives in a **separate class**, any number of orderings, `Arrays.sort(arr, new ComparatorByX())` / `list.sort(new ComparatorByX())`.

## Part D
**D1.** `3 4 1 1 1 3` - stack pop 3 (peek now 1), queue poll 4 (peek now 1), priority queue poll 1 (peek now 3, the smallest left).

**D2.** `[1, 3, 5, 8] 2 -3` then `[1, 5, 3, 8]`.
(sorted [1,3,5,8]; 5 is at index 2; 4 would be inserted at index 2 -> -(2)-1 = -3; reverse -> [8,5,3,1]; swap indexes 0 and 3 -> [1,5,3,8].)

**D3.** `[c, a, d]` (addFirst a -> [a]; addLast b -> [a,b]; addFirst c -> [c,a,b]; removeLast -> [c,a]; addLast d -> [c,a,d]),
then backward: `dac`, then shortest first: `abbccc`.

**D4.** Removing from a list with the enhanced for loop (which uses an iterator internally) modifies it behind the iterator's back ->
`ConcurrentModificationException`. Fix:
```java
Iterator<Integer> it = list.iterator();
while (it.hasNext()) {
    if (it.next() < 0) it.remove();
}
```
(Also: `list.remove(x)` with an `Integer x` removes by value - fine - but the exception is the point.)

**D5.** (a) **Stack** - last action undone first (LIFO). (b) **Queue** - FIFO. (c) **PriorityQueue** - order by severity (Comparator).
(d) **ArrayList** - O(1) `get(k)` and O(1) append. (e) **LinkedList** - `addFirst`/`removeFirst` O(1) (ArrayList would shift everything).

**D6.** ArrayList `get(i)` O(1); `add(E)` at end O(1) (amortized); `add(0,E)` O(n); LinkedList `get(i)` O(n); `addFirst` O(1);
`contains` O(n) for both; `PriorityQueue.peek()` O(1) (`offer`/`poll` are O(log n) - that's the heap lecture).

**D7.**
```java
public static List<Integer> onlyInFirst(List<Integer> a, List<Integer> b) {
    List<Integer> result = new ArrayList<>(a);   // copy so a is not changed
    result.removeAll(b);
    return result;
}
```

## Part E - complexities
Let n = size of each list.

| Method | Big-O | Why |
|---|---|---|
| `findOrders` | O(n) | one recursive call per element, constant work each (`add` is O(1)) |
| `total`, `countAbove` | O(n) | n+1 calls, constant work each |
| `union` | O(n) | loop over both lists, `add` O(1) |
| `unionNoDuplicates` | O(n^2) | n iterations x `contains` O(n) |
| `intersection` | O(n^2) | n iterations x `contains` O(n) |
| `difference` | O(n^2) | n iterations x `contains` O(n) |
| `customersReversed` | O(n * c) where c = distinct customers; **O(n^2)** worst case | `Stack.contains` is O(size) inside a loop |

Solutions are in `mock_exam_3/solution/`. Run:
```
cd mock_exam_3/solution
javac *.java
java Test > mine.txt
diff mine.txt ../expected_output.txt        # no output = identical
```
