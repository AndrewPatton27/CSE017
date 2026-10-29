# CSE 017 - Practice Test 3 (my own mock for Test 2)

Same format as Practice Test 2: a written part, then a coding part on a UML-less but similar program.
Suggested time: 75 minutes. **No notes, no IDE help** for the first attempt - write the code on paper or in a plain editor, then compile.
Answers: `Practice_Test_3_Answers.md` (don't peek).

---

## Part A - Recursion (written)

**A1.** What does `mystery(5)` return? What does `mystery(6)` return?
```java
public static int mystery(int n) {
    if (n <= 1) return 1;
    return n + mystery(n - 2);
}
```

**A2.** What is printed by `p(3)`?
```java
public static void p(int n) {
    if (n > 0) {
        System.out.print(n + " ");
        p(n - 1);
        System.out.print(n + " ");
    }
}
```

**A3.** What is returned by `g("abcdef")`?
```java
public static String g(String s) {
    if (s.length() < 2) return s;
    return g(s.substring(2)) + s.charAt(0);
}
```

**A4.** Each method below throws a `StackOverflowError`. Explain why in one sentence and fix each.
```java
public static int sum1(int n) { return n + sum1(n - 1); }
public static int sum2(int n) { if (n == 0) return 0; return n + sum2(n + 1); }
```

**A5.** Write a **recursive** method `public static int countChar(String s, char c)` that returns how many times `c` appears in `s`.
Use a helper method if you want. No loops.

**A6.** Why do recursive `binarySearch` implementations usually have a second (overloaded, private) helper method?

---

## Part B - Algorithm analysis (written)

For each fragment: (1) the **number of iterations as a function of n**, (2) the **Big-O**.
```java
// B1
for (int i = 0; i < n; i += 2)
    for (int j = 0; j < n; j++)
        count++;

// B2
int i = n;
while (i > 0) { i = i / 3; count++; }

// B3
for (int i = 0; i < n; i++)
    for (int j = 0; j < i; j++)
        for (int k = 0; k < 10; k++)
            count++;

// B4
for (int i = 0; i < n; i++) count++;
for (int j = 1; j < n; j *= 2) count++;

// B5   list is an ArrayList<Integer> with n elements
for (int i = 0; i < n; i++)
    if (list.contains(i)) count++;

// B6
public static int f(int n) { if (n <= 1) return 1; return f(n - 1) + f(n - 1); }

// B7
public static int h(int n) { if (n <= 1) return 1; return 1 + h(n / 2); }
```
**B8.** n times `arrayList.add(0, x)` vs. n times `linkedList.addFirst(x)`: give the Big-O of each and explain.

**B9.** Which is faster for large n, linear search or binary search? What must be true to use binary search?

---

## Part C - Generics (written)

**C1.** Which lines do **not** compile, and which restriction does each violate?
```java
public class Box<E> {
    private E item = new E();                       // (1)
    private E[] items = new E[10];                  // (2)
    private static E shared;                        // (3)
    private ArrayList<E> list = new ArrayList<>();  // (4)
    public static <T> void show(T t) { }            // (5)
    public static void show2(E e) { }               // (6)
}
class MyException<E> extends Exception { }          // (7)
```

**C2.** Why is `ArrayList<String>` better than a list of `Object`? Give a concrete example of the error you avoid and say whether it is found at compile time or runtime.

**C3.** Write the generic method `public static <E extends Comparable<E>> E min(List<E> list)` (non-empty list, iterator or for-each).
Why is `extends Comparable<E>` needed?

**C4.** Complete the class so `Arrays.sort(books)` orders by title, and write the comparator that orders by number of pages, **most pages first**.
```java
public class Book /* ??? */ {
    private String title; private int pages;
    ...
}
public class ComparatorByPages /* ??? */ { ... }
```

**C5.** Compare `Comparable` and `Comparator` (where is the method, how many orderings, one example call of each).

---

## Part D - Using data structures (written)

**D1.** What is printed?
```java
Stack<Integer> s = new Stack<>();
Queue<Integer> q = new LinkedList<>();
PriorityQueue<Integer> pq = new PriorityQueue<>();
for (int i : new int[] {4, 1, 3}) { s.push(i); q.offer(i); pq.offer(i); }
System.out.println(s.pop() + " " + q.poll() + " " + pq.poll() + " "
                 + s.peek() + " " + q.peek() + " " + pq.peek());
```

**D2.** What is printed? (`Collections.binarySearch` returns `-(insertion point) - 1` when the key is missing)
```java
List<Integer> l = new ArrayList<>(Arrays.asList(5, 3, 8, 1));
Collections.sort(l);
System.out.println(l + " " + Collections.binarySearch(l, 5) + " " + Collections.binarySearch(l, 4));
Collections.reverse(l);
Collections.swap(l, 0, 3);
System.out.println(l);
```

**D3.** What is printed?
```java
LinkedList<String> ll = new LinkedList<>();
ll.addFirst("a"); ll.addLast("b"); ll.addFirst("c"); ll.removeLast(); ll.addLast("d");
System.out.println(ll);
ListIterator<String> it = ll.listIterator(ll.size());
while (it.hasPrevious()) System.out.print(it.previous());
System.out.println();
PriorityQueue<String> ps = new PriorityQueue<>(Comparator.comparing(String::length));
ps.offer("ccc"); ps.offer("a"); ps.offer("bb");
System.out.println(ps.poll() + ps.poll() + ps.poll());
```

**D4.** This code crashes. Why? Rewrite it correctly.
```java
for (Integer x : list)
    if (x < 0) list.remove(x);
```

**D5.** Which data structure would you use, and why? (a) undo history of a text editor (b) print jobs processed in arrival order
(c) hospital patients, most critical first (d) a playlist where you jump to track #k often and mostly append at the end
(e) a to-do list where you constantly add/remove at the front.

**D6.** Fill in the Big-O: `ArrayList.get(i)`, `ArrayList.add(E)` at end, `ArrayList.add(0,E)`, `LinkedList.get(i)`,
`LinkedList.addFirst(E)`, `list.contains(E)` (both), `PriorityQueue.peek()`.

**D7.** Using only `Collection` methods (no loops), write `public static List<Integer> onlyInFirst(List<Integer> a, List<Integer> b)`
that returns the elements of `a` that are not in `b`.

---

## Part E - Coding (like Practice Test 2)

Folder `mock_exam_3/starter/` has a program for an online shop. You get `Order.java` (complete), `Test.java` (about 55% complete),
and two comparator stubs, plus `orders1.txt` / `orders2.txt`.
Your output must match `mock_exam_3/expected_output.txt` exactly (`diff` it).

`Order` implements `Comparable<Order>` (natural order: price, cheapest first). `equals` compares customer + item + price.

**E1 - Recursive method (Part 1).** In `Test.java`:
`public static List<Order> findOrders(List<Order> list, String customer)` returns the orders placed by `customer`
(same order as in the list) or `null` if there are none. **Must be recursive and use an iterator** (use a helper).

**E2 - More recursion (Part 1b).** `public static double total(Iterator<Order> it)` (sum of prices of the remaining orders) and
`public static int countAbove(Iterator<Order> it, double limit)` (how many remaining orders cost more than `limit`). Both **recursive**.

**E3 - Generic utility methods (Part 2).** Define `union`, `unionNoDuplicates`, `intersection` (same as Practice Test 2) and also
`difference(List<E> list1, List<E> list2, List<E> list)` = elements of list1 that are **not** in list2.

> The only methods you may use from `java.util.List` are:
> `int size()` O(1), `Iterator<E> iterator()` O(1), `boolean add(E item)` (adds at the end) O(1), `boolean contains(E item)` O(n).

**E4 - Comparators (Part 3).** `ComparatorByCustomer` (customer A-Z) and `ComparatorByPrice` (price, **highest first**).

**E5 - Stack (Part 4).** `customersReversed(List<Order> list)`: with a `java.util.Stack<String>`, return the customer names (each once) in reverse
order of first appearance, formatted like `[Gus, Fay, Ana]`.

**E6 - Time complexity.** Replace each `// O(?)` in `Test.java` and give the Big-O of `findOrders`, `total`, `countAbove`,
`union`, `unionNoDuplicates`, `intersection`, `difference`, `customersReversed`.
Assume both lists have about n elements.

Do not edit `Order.java`. Only `Test.java` and the two comparator files.

---

### Bonus drills
`drills/` has focused practice (recursion, generics, collections, complexity counter). Grade yourself with `DrillTest`.
