# CSE 017 — Interfaces and Dynamic Binding — Study Notes

## Key Terms

- **Polymorphism**: a variable of the super type can hold a reference to a subtype object; the same method call can behave differently depending on the actual object.
- **Dynamic Binding**: the JVM (not the compiler) decides *at runtime* which overridden method to invoke, based on the object's **actual type**.
- **Declared type vs. Actual type**: `Person p = new Student();` → declared type `Person`, actual type `Student`.
- **Upcasting**: assigning a subclass object to a superclass reference — implicit/automatic (`Object o = new Student();`).
- **Downcasting**: assigning a superclass reference back to a subclass type — must be explicit (`Student s = (Student) o;`), and risks `ClassCastException` if the actual type doesn't match.
- **`instanceof`**: operator used for *safe downcasting* — checks the actual type before casting.
- **Abstract class**: models common behavior between **related** subclasses ("Dog IS an Animal" — describes what an object *is*).
- **Interface**: models common behavior between **unrelated** classes ("Dog CAN swim" — describes what an object *can do*).
- **`Comparable<E>`**: interface with one abstract method `compareTo(E obj)`, defines the *natural ordering* of objects.
- **`Cloneable`**: a **marker interface** (empty — no methods) that signals objects of the class are allowed to be cloned via `clone()`.
- **Shallow copy**: copies primitive fields by value, but reference fields still point to the *same* underlying objects.
- **Deep copy**: creates independent copies of referenced objects too, so changes to one don't affect the other.

## OOP Pillars (recap)

| Pillar | Mechanism | Purpose |
|---|---|---|
| Encapsulation | Classes and Objects | Protect data |
| Inheritance | Super/Sub classes | Reuse from another class |
| Polymorphism | Abstract classes and Interfaces | Same method, different behavior |

## Polymorphism & Dynamic Binding

```java
public class Test{
 public static void main(String[] args){
   Person[] people = new Person[3];
   people[0] = new Student();
   people[1] = new Employee();
   people[2] = new Faculty();
   for (int i = 0; i < people.length; i++)
     System.out.println(people[i].toString());  // which toString() runs? decided at runtime
 }
}
```

**Rule for method lookup:** the JVM looks for the method on the **actual type first**, then walks up to the declared type if not found there.

```java
Employee o = new Faculty();
System.out.println(o.toString());  // uses Faculty's toString() if it overrides it
```

## Object Casting

```java
Object o = new Student();   // OK — upcasting is implicit
Student s = o;               // Compiler Error — can't implicitly downcast

Object o = new Student();
Student s = (Student) o;    // OK — explicit downcast
```

**Safe downcasting with `instanceof`:**
```java
Object o = new Student();
if (o instanceof Student) {
  Student s = (Student) o;
}
```
If the actual type of `o` were *not* `Student`, casting without checking would throw `ClassCastException`.

### Example: overriding `equals(Object)` using `instanceof`
```java
public boolean equals(Object o){
  if (o instanceof Person) {
     Person p = (Person) o;
     return (name.equals(p.name));
  }
  return false;
}
```

## Abstract Classes vs. Interfaces

- **Abstract class**: for related classes (`Dog extends Animal`). Cannot be instantiated but can be used as a data type for polymorphism. An abstract *method* forces the class to be abstract, but an abstract class can have zero abstract methods. Subclasses must implement all abstract methods or remain abstract themselves. A subclass can be abstract even if its superclass isn't.
- **Interface**: for unrelated classes (`Dog implements Swimmable`). Defined with keyword `interface` instead of `class`. May contain only static constants, static methods, and (mainly) abstract methods. A class **implements** an interface (not "extends"). Examples: `Comparable`, `Cloneable`, `Drawable`, `List`, `Collection`, `Map`.

```
Interface: "interface" Edible { +howToEat(): String }
Abstract Class: Animal { -weight, -name; +sound(); +howToEat() }
Concrete Classes: Tiger, Chicken (extend Animal / implement Edible)
                  Orange, Apple  (implement Edible via Fruit)
```

## `Comparable<E>`

```java
public interface Comparable<E>{
  int compareTo(E obj);
}
```
`obj1.compareTo(obj2)` returns:
- `0` if equal
- `> 0` if `obj1` comes after `obj2`
- `< 0` if `obj1` comes before `obj2`

```java
public abstract class Person implements Comparable<Person>{
  // ...
  public int compareTo(Person p){
    return this.id - p.id;
  }
}
```

`java.util.Arrays.sort()` accepts an array of objects of **any type that implements `Comparable`**:
```java
System.out.println("Original List");
printArray(people);
System.out.println("List sorted by id");
java.util.Arrays.sort(people);  // sorts by whatever compareTo defines
printArray(people);
```

## `Cloneable`

```java
public interface Cloneable{ }   // empty / marker interface
```
Implementing `Cloneable` means overriding `clone()` from `Object`. Many Java API classes implement both `Comparable` and `Cloneable`.

### Shallow copy

```java
class Student implements Cloneable {
    int age;
    Address addr;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
// s2.age = 30 does NOT affect s1.age (primitives copied by value)
// s2.getAddress().setCity("Boston") DOES affect s1's address too
// (reference field addr still points to the same shared Address object)
```

### Deep copy

```java
class Student implements Cloneable {
    int age;
    Address addr;

    public Object clone() throws CloneNotSupportedException {
        Student copy = (Student) super.clone();
        copy.addr = this.addr.clone();   // clone the referenced object too
        return copy;
    }
}
// s2.getAddress().setCity("Boston") does NOT affect s1's address
// (each Student has its own independent Address object)
```

**Use case comparison:**
```java
// Shallow copy — changing the name field on the clone affects both,
// since setName mutates a shared reference field in this scenario
Student s = (Student) (people[0].clone());
s.setName("New Name");

// Deep copy — the clone is fully independent
Student s = (Student) (people[0].clone());
s.setName("New");
// people[0] keeps its old name; s has the new one
```

## Implementing Multiple Interfaces

A class can implement **more than one interface** (but can extend only **one** class). This is how Java achieves a form of multiple inheritance.

```java
public class Student implements Comparable<Student>, Cloneable {
   public int compareTo(Student s){
      return this.id == s.id;
   }
   public abstract Object clone();
}
```

## Summary Table

| | Data members | Constructors | Methods |
|---|---|---|---|
| **Interface** | Static constants only | No constructors | Abstract, default, and static |
| **Abstract Class** | No restrictions | Protected constructors | No restrictions |
| **Concrete Class** | No restrictions | Public constructors | No abstract methods |
