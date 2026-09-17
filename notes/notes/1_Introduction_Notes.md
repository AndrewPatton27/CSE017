# CSE 017 — Introduction & Java Review — Study Notes

## Key Terms

- **CSE 017**: "Programming and Data Structures" — builds on CSE 003/004/007. Focus: more programming + data structures.
- **Encapsulation**: protecting an object's internal state; controlling access via classes/objects and access modifiers.
- **Inheritance**: creating new classes (subclasses) that reuse and extend an existing class (superclass). "IS-A" relationship.
- **Polymorphism**: "one type, many forms" — a super type reference can behave differently depending on the actual object type (via abstract classes/interfaces).
- **Recursion**: a method solving a problem by calling itself on a smaller version of the problem.
- **Algorithm Analysis**: comparing algorithms/data structures by performance (time/space), independent of hardware.
- **IDE**: Integrated Development Environment — write/compile/execute/debug code (course uses VS Code + Sunlab remote machines).

## Course Topics (What's in CSE 017)

1. Exceptions and File IO (useful Java classes)
2. Interfaces (special abstract classes)
3. Generics and Data Structures (classes to store/manipulate data of any type)
4. Recursion
5. Algorithm Analysis
6. Sorting Algorithms

## Student Learning Outcomes

- Apply OOP to design and implement programs.
- Use and implement different data structures.
- Use recursion to implement algorithms.
- Implement sorting algorithms.
- Compare data structures and sorting algorithms using algorithm analysis techniques.

## The Three OOP Pillars (intro-level)

1. **Data Encapsulation** — Classes and Objects → protect the data.
2. **Class Inheritance** — Super/Sub classes → reuse and extend code.
3. **Polymorphism** — Abstract Classes and Interfaces → one type, many forms.

### Example: Encapsulation

```java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

### Example: Inheritance

```java
class Animal {
    public void eat() {
        System.out.println("Eating");
    }
}

class Dog extends Animal {
    public void bark() {
        System.out.println("Woof");
    }
}

Dog d = new Dog();
d.eat();   // inherited from Animal
d.bark();  // defined in Dog
```

### Example: Polymorphism (method overriding)

```java
class Animal {
    public void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("Woof");
    }
}

class Cat extends Animal {
    @Override
    public void sound() {
        System.out.println("Meow");
    }
}

// Same type Animal can represent different forms:
Animal a1 = new Dog();
Animal a2 = new Cat();
a1.sound();   // Woof
a2.sound();   // Meow
```

### Example: Recursion preview (sum 1..n)

```java
// Iterative
public static int sum(int n){
    int s = 0;
    for(int i = 1; i <= n; i++)
        s += i;
    return s;
}

// Recursive
public static int sum(int n){
    if (n == 1)
        return 1;
    else
        return n + sum(n - 1);
}
```

## Sorting Algorithms Preview (taxonomy)

- **Quadratic / Comparison-based**: Selection Sort, Insertion Sort, Bubble Sort
- **Recursive / Comparison-based**: Merge Sort, Quick Sort
- **Heap-based**: Heap Sort
- **Data-classification based (non-comparison)**: Bucket Sort, Radix Sort

## Java Review — Key Points

### Data types
- Primitive: `char, byte, short, int, long, float, double, boolean`
- Class (reference): `String`, `Scanner`, any user-defined class

### Operators
- Arithmetic: `+ - * / % ++ --`
- Logical: `&& || !`
- Bitwise: `& | ^ ~`
- Relational: `< <= > >= == != ?:`

### Selection
- `if-else`: one/two execution alternatives
- Nested `if`: multiple alternatives
- `switch`: multiple alternatives for `int`/`char`/`String` expressions

### Iteration
- `while`: 0+ iterations (unknown count)
- `do-while`: 1+ iterations (unknown count, runs at least once)
- `for`: 0+ iterations (known count)
- Nested loops; `break` / `continue`

### Input/Output
- `Scanner` object reads from keyboard (`System.in`)
- Command-line arguments passed to `main`
- `PrintWriter` writes output to console (`System.out`)

### Methods
- Block of code with 0+ inputs, one output (or `void`)
- Parameters = inputs, return value = output
- **Primitive arguments are passed by value.**
- **Array arguments are passed by reference.**

### Arrays
- Collection of variables of the same type
- 1D (one index), 2D (two indices), multidimensional (n indices)

## Important Note on AI Use (per syllabus)

- Allowed as an assistant to understand concepts / prepare for exams.
- **Not** to be used to write code for assignments.
- Advantage: faster understanding and quick responses.
- Disadvantage: risk of relying on AI instead of learning to solve problems and write code yourself.

## Tools

- IDE: VS Code (free at code.visualstudio.com), with remote access to Sunlab (CSE department) machines.
- Submissions: GitHub (version control, submit code — use Lehigh email to create an account) + Gradescope (grades/feedback).
