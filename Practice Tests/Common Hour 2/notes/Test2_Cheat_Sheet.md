# CSE 017 - Test 2 Cheat Sheet

Covers the review-session list: **Recursion, Algorithm Analysis, Generics, Using Data Structures**
(ArrayList, LinkedList, Stack, Queue, PriorityQueue, iterators, Collections).
Lectures 7 (Implementing Data Structures) and 8 (Binary Trees/Heap) are *not* on the list.

Style of the real thing (Practice Test 2): about 60% of `Test.java` is given. You write a **recursive method that
uses an iterator + helper**, **generic utility methods** (`union`, `intersection`...) using only a few `List`
methods, **two Comparator classes**, then **Big-O for each method**. Expect output to be compared to a sample run.

---

## 1. Recursion

Every recursive method = **base case** (stops) + **recursive case** (smaller problem, moves *toward* the base case).

```java
public static int rFactorial(int n) {
    if (n <= 1) return 1;              // base case
    return n * rFactorial(n - 1);      // recursive case: n-1 is smaller
}
```
Call cascade goes down, returns cascade back up: `5 * (4 * (3 * (2 * 1)))`.

**StackOverflowError** = no base case, or the recursive call never gets closer to it
(`sum(n + 1)` when the base case is `n == 0`). Each call pushes a frame on the call stack; too many = overflow.

**Helper method** = an overloaded (usually `private`) method with extra parameters to carry state
(indexes, an iterator, a result list). The public method has the clean signature the user sees.

```java
public static int binarySearch(int[] a, int key) {                 // public: clean signature
    return binarySearch(a, key, 0, a.length - 1);
}
private static int binarySearch(int[] a, int key, int first, int last) {   // helper
    if (first > last) return -1;                                   // base case: empty range
    int mid = (first + last) / 2;
    if (key == a[mid]) return mid;
    if (key < a[mid]) return binarySearch(a, key, first, mid - 1);
    return binarySearch(a, key, mid + 1, last);
}
```

### The exam pattern: recursion + iterator (Practice Test 2 `findMessages`)
The **iterator is the "index"**. `it.next()` consumes one element, so each call works on a smaller problem.
Base case: `!it.hasNext()`.

```java
public static List<Order> find(List<Order> list, String customer) {
    ArrayList<Order> found = new ArrayList<>();
    findHelper(list.iterator(), customer, found);        // helper does the recursion
    return found.isEmpty() ? null : found;               // "null if nothing found"
}
private static void findHelper(Iterator<Order> it, String customer, List<Order> found) {
    if (!it.hasNext()) return;                           // base case
    Order o = it.next();                                 // take ONE element
    if (o.getCustomer().equals(customer)) found.add(o);
    findHelper(it, customer, found);                     // recurse on the rest
}
```
Returning a value instead (sum): `return it.next().getPrice() + total(it);` - **careful**: call `it.next()` exactly once per call.

Checklist when writing one: (1) base case first (2) consume one element/shrink by one (3) recursive call actually uses the shrunk problem
(4) no loop (5) iterator must be created ONCE in the public method, not inside the helper.

**Recursion vs iteration:** recursion = fewer lines, uses call stack (more memory), can overflow; iteration = usually faster.
Recursive Fibonacci is O(2^n) (recomputes subproblems), iterative is O(n).

Patterns to be able to write cold: factorial, sum of digits, power, reverse a String, palindrome (helper w/ two indexes),
count/sum over an array with an index helper, binary search, gcd, toBinary, count/find over a list with an iterator.
(See `drills/RecursionDrills.java`.)

---

## 2/3. Algorithm Analysis (Big-O)

Big-O = **upper bound on the growth rate** (worst case) as input size n grows.
- Drop constants: `O(100n) = O(n)`. Drop smaller terms: `O(5n^2 + 2n + 15) = O(n^2)`.
- Step 1: **count iterations as a function of n**. Step 2: keep the dominant term.

Order (best -> worst): `O(1) < O(log n) < O(n) < O(n log n) < O(n^2) < O(n^3) < O(2^n)`

| Loop shape | Iterations | Big-O |
|---|---|---|
| `for (i=0; i<n; i++)` (also `i+=2`, `i+=3`, `i<n/2`) | n (or n/2, n/3...) | O(n) |
| nested, both to n | n * n | O(n^2) |
| inner depends on outer (`j < i`) | 1+2+...+n = n(n+1)/2 | O(n^2) |
| inner is a constant (`j < 20`) | 20n | O(n) |
| **sequential** loops (not nested) | add them: n + n^2 | O(n^2) (biggest wins) |
| variable multiplied/divided each time (`i*=2`, `i/=2`) | log2 n | O(log n) |
| linear outer x doubling inner | n log n | O(n log n) |
| `n + n/2 + n/4 + ...` | about 2n | O(n) (a classic trap - NOT n log n) |
| three nested to n | n^3 | O(n^3) |

Useful sums: `1+2+...+n = n(n+1)/2`; `2^0+2^1+...+2^n = 2^(n+1) - 1`.

**Recursive:** count calls. `f(n)` calls `f(n-1)` once -> O(n). `f(n)` calls `f(n/2)` once -> O(log n).
`f(n)` calls `f(n-1)` **twice** -> O(2^n). Two calls on `n/2` -> O(n) (add a linear merge step and it becomes O(n log n)).

**Hidden loops!** A call to `list.contains(x)` is O(n). `contains` inside a loop over n items = **O(n^2)**.
Same for `ArrayList.add(0, x)` / `remove(0)` (shifts everything) and `LinkedList.get(i)` (walks the list).

| Algorithm | Time |
|---|---|
| Linear search | O(n) |
| Binary search (sorted data!) | O(log n) |
| Selection / insertion sort | O(n^2) |
| `union` (loop list1 then list2, add each) | O(n) |
| `intersection`, `unionNoDuplicates`, `difference` (loop + `contains`) | O(n^2) |
| `findMessages`-style recursive scan | O(n) |
| Iterative Fibonacci / Recursive Fibonacci | O(n) / O(2^n) |

Exam tip: say what n is (size of the lists), and *why* ("loop of n iterations, each calls contains which is O(n)").
Try the counter lab: `java ComplexityLoops` (prints iteration counts for n = 10..160 so you can see x2, x4, +1...).

---

## 4. Generics

`<E>` (type parameter) is replaced by a **reference type** when used: `ArrayList<Integer>`, never `ArrayList<int>`
(use wrappers: Integer, Double, Boolean...). Names: `E`, `T`, `<K,V>`, `<E1,E2>`.

```java
public class Pair<E1, E2> { private E1 first; private E2 second; ... }      // generic class
public static <E> void printArray(E[] list) { ... }                        // generic method: <E> BEFORE return type
public static <E extends Comparable<E>> E max(E[] arr) { ... }             // bounded: lets you call compareTo
```

**Generic type vs Object:** with `Object` anything goes in (`push(1.75); push("text")`) and you must cast on the way out ->
**runtime** `ClassCastException`. With generics the compiler checks types -> errors are found at **compile time**,
no casts needed. Raw types (`ArrayList list = new ArrayList();`) are unsafe: never write them.

**Restrictions on generic types (know all four):**
1. Cannot instantiate: `new E()` -> illegal
2. Cannot create a generic array: `new E[10]` -> illegal (use `ArrayList<E>` or `(E[]) new Object[10]`)
3. Exception classes cannot be generic: `class MyEx<E> extends Exception` -> illegal
4. `static` fields/methods can't use the class's `E`: `static E item` -> illegal (a static method can declare its *own* `<T>`)
(+ E can't be a primitive.)

**Wildcards:** `List<?>` any list (read-only); `List<? extends Animal>` Animal or subclasses (read as Animal);
`List<? super Dog>` Dog or its parents (safe to add a Dog).

### Comparable vs Comparator
| | `Comparable<E>` | `Comparator<E>` |
|---|---|---|
| Method | `int compareTo(E other)` (in the class itself) | `int compare(E a, E b)` (separate class) |
| Gives | the **natural ordering** (one) | **any number** of orderings |
| Use | `Arrays.sort(arr)`, `list.sort(null)`, `Collections.sort(list)`, `new PriorityQueue<>()` | `Arrays.sort(arr, c)`, `list.sort(c)`, `Collections.sort(list, c)`, `new PriorityQueue<>(c)` |

Return **negative** if first < second, **0** if equal, **positive** if first > second.
Reverse order = swap the operands (`b.compareTo(a)`). `int` fields: `a - b` works; `double`: `Double.compare(a, b)`
(or box and `compareTo`); `String`: `a.compareTo(b)`.

```java
public class ComparatorByRecipient implements Comparator<Message> {
    @Override
    public int compare(Message m1, Message m2) {
        return m1.getRecipient().compareTo(m2.getRecipient());
    }
}
```
Don't forget `import java.util.Comparator;` and the `<Message>` type argument.

### The generic list utilities (Practice Test 2) - memorize the shapes
```java
public static <E> void union(List<E> l1, List<E> l2, List<E> out) {            // O(n)
    Iterator<E> i1 = l1.iterator();  while (i1.hasNext()) out.add(i1.next());
    Iterator<E> i2 = l2.iterator();  while (i2.hasNext()) out.add(i2.next());
}
public static <E> void intersection(List<E> l1, List<E> l2, List<E> out) {     // O(n^2)
    Iterator<E> i1 = l1.iterator();
    while (i1.hasNext()) { E e = i1.next(); if (l2.contains(e)) out.add(e); }
}
public static <E> void unionNoDuplicates(List<E> l1, List<E> l2, List<E> out) {// O(n^2)
    // for each element of l1 then l2:  if (!out.contains(e)) out.add(e);
}
```
`contains` uses the element's `equals` - so the class's `equals` decides what "duplicate" means (Message.equals compares only the sender!).

---

## 5. Using Data Structures (Java Collection Framework)

Three parts: **containers** (data structures), **iterators**, **algorithms** (`Collections`).

```
Iterable<E> -> Collection<E> -> List<E> -> ArrayList<E>, LinkedList<E>, (Stack<E>)
                             -> Queue<E> -> LinkedList<E>, PriorityQueue<E>
```
### `Collection<E>` methods (every container has them)
`add(E)`, `addAll(c)` (**union**), `clear()`, `contains(Object)`, `containsAll(c)`, `remove(Object)`,
`removeAll(c)` (**difference**), `retainAll(c)` (**intersection**), `size()`, `isEmpty()`, `toArray()`, `iterator()`, `equals`.

### Iterators
```java
Iterator<E> it = list.iterator();                 // hasNext(), next(), remove()
while (it.hasNext()) { E e = it.next(); if (bad(e)) it.remove(); }   // ONLY safe way to delete while looping

ListIterator<E> li = list.listIterator(list.size());   // start at the END to go backward
while (li.hasPrevious()) System.out.println(li.previous());   // also: hasNext/next, set(E), add(E)
```
`list.remove(x)` inside a for-each loop -> **ConcurrentModificationException**. Use `Iterator.remove()`.
`remove()` must come right after `next()`/`previous()`.

### `Collections` (algorithms; lists only)
`sort(list)` / `sort(list, comparator)`, `binarySearch(list, key)` (list must be sorted; returns `-(insertion point)-1` if missing),
`reverse`, `shuffle`, `swap(list, i, j)`, `max`, `min`, `fill`, `copy`, `Collections.reverseOrder()`.

### Which container?
| Structure | Model | Key methods | Good at | Bad at |
|---|---|---|---|---|
| **ArrayList** | array (contiguous) | `get(i)`, `set`, `add(E)`, `add(i,E)`, `remove(i)`, `remove(Object)` | random access `get(i)` O(1), add at end | add/remove at front or middle O(n) (shifting) |
| **LinkedList** | chain of nodes (head/tail) | `addFirst`, `addLast`, `getFirst`, `getLast`, `removeFirst`, `removeLast` | add/remove at ends O(1) | `get(i)` O(n) (walk from head); contains O(n) |
| **Stack** | LIFO (trays) | `push`, `pop`, `peek`, `isEmpty` | undo, reverse, matching brackets, method calls | no access to the bottom |
| **Queue** (LinkedList) | FIFO (deli line) | `offer` (back), `poll` (front), `peek` | scheduling in arrival order | no cutting / no middle access |
| **PriorityQueue** | min-heap | `offer`, `poll`, `peek` | always gives you the smallest (natural) or first by Comparator | iteration/toString are NOT in sorted order; only `poll` is |

Notes you can be asked about:
- `Queue<E> q = new LinkedList<>();` (Queue is an interface; LinkedList implements it). Use `offer/poll/peek`.
- `PriorityQueue` default: **smallest first**. `new PriorityQueue<>(new ComparatorByPrice())` -> order set by the comparator. Elements must be Comparable otherwise (`ClassCastException`).
- `poll()`/`peek()` return `null` on an empty queue; `Stack.pop()/peek()` **throw** `EmptyStackException`.
- `ArrayList.remove(int)` vs `remove(Object)`: `list.remove(1)` removes the element at **index** 1; `list.remove(Integer.valueOf(1))` removes the **value** 1.
- Polymorphism: `Collection<String>[] ds` can hold all five; cast to call specific methods `((Stack) ds[2]).push(x)`.
- Time complexity table (typical): ArrayList get O(1), add(end) O(1), add(i)/remove(i) O(n), contains O(n);
  LinkedList get(i) O(n), addFirst/addLast/removeFirst/removeLast O(1), contains O(n).

---

## 6. Common mistakes (lose easy points)
- Forgetting the **base case** or recursing on the same-size problem.
- Calling `it.next()` twice in one pass (skips elements) or creating a new iterator every recursive call (infinite recursion).
- Returning an empty list instead of **null** when the spec says null; or `return` inside the loop too early.
- Using `==` for Strings/objects; use `.equals`.
- Writing `<E>` after the return type (`public static void <E> f()` wrong -> `public static <E> void f()`).
- `compareTo` on a generic `E` without `extends Comparable<E>`.
- Sorting direction reversed in a Comparator.
- Complexity: forgetting that `contains` inside a loop makes it O(n^2); writing `O(2n)` or `O(n+5)` instead of `O(n)`.
- Using a `List` method not on the allowed list in the "IMPORTANT NOTE" table (e.g. `get(i)`, `addAll`) - read it twice.
- Forgetting imports (`java.util.Iterator`, `java.util.Comparator`...).
