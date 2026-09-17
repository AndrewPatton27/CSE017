# CSE 017 — Recursion & Algorithm Analysis — Study Notes

> **Reminder from the slides:** Exam 1 (recitation, on Sunlab machines, no personal devices) covers Exception Handling, File IO, and Interfaces.

## Key Terms

- **Recursion**: an algorithmic paradigm where a method calls itself to solve a smaller version of the same problem. Useful for problems hard to solve with loops, mathematical series defined in terms of previous terms, and traversing hierarchical/tree structures.
- **Base case**: the stopping condition of a recursive method. Without one, recursion never terminates.
- **Stack Overflow**: occurs when the number of recursive calls exceeds the size of the program (call) stack — typically the result of missing/incorrect base case.
- **Runtime/call stack**: tracks method calls; each call pushes a frame, each return pops it.
- **Helper recursive method**: an overloaded version of a method that adds extra parameters needed to carry state through the recursion (e.g., search bounds).
- **Algorithm Analysis**: comparing algorithms by performance (time/space) independent of hardware/input, to decide which solution to use *before* implementing it.
- **Big-O notation**: describes the **upper bound (worst case)** on an algorithm's growth function — how execution time/memory grows as input size grows.
- **Time Complexity**: growth of execution time as a function of input size `n`.
- **Space Complexity**: growth of memory usage as a function of input size `n`.

## Recursion vs. Iteration — Core Idea

**Factorial** (`n! = n × (n-1)!`):
```java
// Iterative
public static int factorial(int n){
  int fact = 1;
  for (int i = 2; i <= n; i++)
    fact = i * fact;
  return fact;
}

// Recursive
public static int rFactorial(int n){
  if (n == 1 || n == 0)
    return 1;
  else
    return n * rFactorial(n - 1);
}
```

**Call/return cascade for `rFactorial(5)`:**
- Calls cascade down: `rFactorial(5)` → `rFactorial(4)` → `rFactorial(3)` → `rFactorial(2)` → `rFactorial(1)` (base case hits, returns 1)
- Returns cascade back up: `1 → 2×1=2 → 3×2=6 → 4×6=24 → 5×24=120`

### Base case (critical!)

```java
public static int rFactorial(int n){
    if (n == 1 || n == 0)     // Base Case (Stopping)
        return 1;
    else{
        return n * rFactorial(n - 1);   // Recursion
    }
}
```
No base case → infinite recursion → **Stack Overflow** (the program stack fills up because the number of method calls exceeds the stack's capacity).

### Helper recursive methods (method overloading)

```java
public static int rBinarySearch(int[] list, int key){
    return rBinarySearch(list, key, 0, list.length - 1);   // calls the "real" recursive version
}
public static int rBinarySearch(int[] list, int key, int first, int last){
    if (first > last) return -1;                            // base case
    int middle = (first + last) / 2;
    if (key == list[middle]) return middle;
    else if (key < list[middle]) last = middle - 1;
    else first = middle + 1;
    return rBinarySearch(list, key, first, last);           // recursion
}
```

## Fibonacci Sequence — Recursive vs. Iterative

**Definition:** `f_n = f_(n-1) + f_(n-2)` for `n > 2`, with `f1 = f2 = 1`.
Example: `f8 = 21` from the sequence `1 1 2 3 5 8 13 21`.

```java
// Iterative Fibonacci
public static int fibonacci(int n) {
    int f1 = 1, f2 = 1;
    int f = 0;
    if (n <= 2) return 1;
    while (n > 2){
       f = f1 + f2;
       f1 = f2;
       f2 = f;
       n--;
    }
    return f;
}

// Recursive Fibonacci
public static int rFibonacci(int n){
    if (n <= 2)
        return 1;
    else{
        return rFibonacci(n - 1) + rFibonacci(n - 2);
    }
}
```

**Call tree for `rFibonacci(6)`** branches into two recursive calls at every level (not linear like factorial) — this is *why* the recursive version is exponential (see Big-O section below): each call to `rFibonacci(n)` spawns calls to `rFibonacci(n-1)` and `rFibonacci(n-2)`, and many subproblems (like `f2`, `f3`) get recomputed repeatedly.

### Recursion vs. Iteration — Trade-offs

| | Recursion | Iteration |
|---|---|---|
| Mechanism | Function calls itself | Uses a loop |
| Requires | A base case | A loop condition |
| Memory | Uses the call stack (more memory) | Usually uses less memory |
| Failure mode | Can cause `StackOverflowError` | Can cause infinite loop |
| Style | Often elegant for recursively-structured problems | Often more efficient for simple repetition |

**General guidance:** recursion often results in less code, but iteration is usually more computationally efficient (recursion has call-stack push/pop overhead). Use recursion mainly when the problem is naturally/recursively structured and hard to express with loops. Most recursive solutions can be rewritten iteratively.

## Algorithm Analysis & Big-O Notation

**Why:** determine an algorithm's performance *before* implementing/testing it, by finding the upper bound (worst case) based on problem size.

### Big-O properties

- **Constants are ignored**: `O(100n) ~ O(n/20) ~ O(n)`, `O(n+5) ~ O(n-1000) ~ O(n)`
- **Non-dominant terms are ignored**: `O((5n² + 2n + 15)/20) ~ O(n²)`
- `O(1)`: constant time, independent of input size.
- Big-O only cares about the **growth rate**, not exact operation counts.

### Useful summation formulas

```
1 + 2 + 3 + ... + n = n(n+1)/2 = n²/2 + n/2  ~ O(n²)
a^0 + a^1 + ... + a^n = (a^(n+1) - 1) / (a - 1)  ~ O(a^n)
2^0 + 2^1 + ... + 2^n = 2^(n+1) - 1  ~ O(2^n)
```

### Determining time complexity from loop structure

**Simple loop → O(n) (Linear):**
```java
for (int i = 0; i < n; i++) {
    k = k + 5;
}
// iterations = n  →  O(n)
```

**Nested loop (both bounded by n) → O(n²) (Quadratic):**
```java
for (int i = 0; i < n; i++) {
 for (int j = 0; j < n; j++) {
    k = k + i + j;
 }
}
// iterations = n * n  →  O(n²)
```

**Nested loop, inner bound depends on outer index → still O(n²):**
```java
for (int i = 1; i <= n; i++) {
 for (int j = 1; j < i; j++) {
    k = k + i + j;
 }
}
// iterations = 1+2+3+...+n = n(n+1)/2  →  O(n²)
```

**Sequential (not nested) loops → add complexities, then simplify:**
```java
for (int i = 1; i <= n; i++) { k = k + 4; }          // n iterations
for (int i = 1; i <= n; i++) {
  for (int j = 1; j <= 20; j++) { k = k + i + j; }    // 20n iterations
}
// total = n + 20n = 21n  →  O(n)  (constant 21 dropped)
```

**Loop control variable scales multiplicatively → O(log₂n) (Logarithmic):**
```java
int result = a;
for (int i = 1; i <= k; i++) {
 result *= result;   // squares each time: a, a², a⁴, a⁸, ...
}
// iterations k = log₂n  →  O(log₂n)

int count = 1;
while (count < n) {
 count *= 2;
}
// iterations = log₂n  →  O(log₂n)
```

**Linear vs. Logarithmic algorithm design example (computing aⁿ):**

| Approach | Code | Iterations | Complexity |
|---|---|---|---|
| Linear | `result = 1; for(i=1;i<=n;i++) result *= a;` | `n` | O(n) |
| Logarithmic (repeated squaring) | `result = a; for(i=1;i<=k;i++) result *= result;` | `k = log₂n` | O(log₂n) |

### Searching algorithms

```java
// Linear Search — O(n)
public static int lSearch(int[] list, int key){
  for (int i = 0; i < list.length; i++)
    if (list[i] == key)
      return i;
  return -1;
}

// Binary Search — O(log₂n) — requires a SORTED array
public static int binarySearch(int[] list, int key){
  int first, last, middle;
  first = 0;
  last = list.length - 1;
  while (first <= last){
    middle = (last + first) / 2;
    if (key == list[middle]) {
       return middle;
    }
    else if (key < list[middle])
       last = middle - 1;
    else
       first = middle + 1;
  }
  return -1;
}
```
**Why Binary Search is O(log₂n):** each iteration halves the search space (`n/2, n/4, ..., n/2^k`). The last iteration `k` has size 1, so `n / 2^k = 1` → `k = log₂n`.

**Measured performance (linear vs binary), showing why growth rate matters at scale:**

| Size | Linear time (ns) | Binary time (ns) | Linear iterations | Binary iterations |
|---|---|---|---|---|
| 100 | 2,287 | 1,288 | 16 | 5 |
| 1,000 | 10,334 | 598 | 14 | 7 |
| 10,000 | 98,421 | 786 | 10,000 | 13 |
| 100,000 | 168,542 | 3,924 | 21,629 | 16 |
| 1,000,000 | 186,606 | 4,005 | 86,934 | 18 |
| 10,000,000 | 3,658,294 | 3,612 | 140,665 | 23 |
| 100,000,000 | 19,018,055 | 4,981 | 19,325,601 | 24 |

### Sorting algorithm

```java
// Selection Sort — O(n²)
public static int selectionSort(int[] list) {
  for (int i = 0; i < list.length - 1; i++){
     int minIndex = i;
     for (int j = i + 1; j < list.length; j++){
        if (list[j] < list[minIndex]){   // NOTE: slide had a typo (list[i]); should compare list[j]
           minIndex = j;
        }
     }
     int temp = list[i];
     list[i] = list[minIndex];
     list[minIndex] = temp;
  }
}
```
**Why O(n²):** outer loop iteration `k` runs the inner loop `n-k` times. Total iterations = `1 + 2 + ... + (n-1) = n(n-1)/2` → quadratic.

### Fibonacci complexity comparison — the key exam topic

```java
// Iterative Fibonacci — O(n) Linear
public static int fibonacci(int n){
 int f1 = 1, f2 = 1, f = 0;
 if (n <= 2) return 1;
 else{
  while (n > 2){
   f = f1 + f2;
   f1 = f2; f2 = f;
   n = n - 1;
  }
 }
 return f;
}
// iterations = n - 2  →  O(n)

// Recursive Fibonacci — O(2^n) Exponential
public static int rFibonacci(int n){
 if (n <= 2)
    return 1;
 else
    return rFibonacci(n - 1) + rFibonacci(n - 2);
}
```

**Deriving the recursive Fibonacci's complexity:**
```
Time(n) = Time(n-1) + Time(n-2) ~ 2 * Time(n-1)
Time(n) = 2 * (2 * Time(n-2)) = 2² * Time(n-2)
Time(n) = 2 * (2² * Time(n-3)) = 2³ * Time(n-3)
...
Time(n) = 2^k * Time(n-k)
...
Time(n) = 2^(n-2) * Time(2) = 2^(n-2) * constant
→ Recursive Fibonacci: O(2^n) — Exponential Growth
```

**Why iterative wins:** it reuses previously computed values (`f1`, `f2`) instead of recomputing the same subproblems over and over, the way the recursive version does.

### Growth function summary (best to worst, typical order)

`O(1)` Constant < `O(log₂n)` Logarithmic < `O(n)` Linear < `O(n log n)` Log-Linear < `O(n²)` Quadratic < `O(2^n)` Exponential

### Complexity summary table (from the slides)

| Algorithm | Time Complexity |
|---|---|
| Linear Search | O(n) |
| Binary Search | O(log₂n) |
| Selection Sort | O(n²) |
| Iterative Fibonacci | O(n) |
| Recursive Fibonacci | O(2ⁿ) |

**Takeaway on efficient algorithms:** the goal is minimizing time and/or space complexity. Binary search beats linear search by exploiting sorted data. Iterative Fibonacci beats recursive Fibonacci by reusing prior calculations instead of recomputing them.

## See Also

This ties directly into `help/Fibonacci.java` and `help/FibonacciTest.java` in this repo — `fib()` there is the O(2ⁿ) recursive version, `fib2()` is the O(n) iterative version, matching this lecture's core example.
