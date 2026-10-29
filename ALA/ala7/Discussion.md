# ALA 7 Discussion

## Part 1: ArrayList vs LinkedList scenarios
TODO (Objective 1): for each of the three scenarios in the activity handout, pick ArrayList or LinkedList and justify.

## Part 5: Results

The lists hold n = 488 animals. Each operation was tested on 20 random animals. Averages from one run (they change slightly each run because the animals are random):

| Operation | ArrayList | LinkedList | Time complexity |
|---|---|---|---|
| contains | 255 | 255 | O(n) |
| remove(Object) | 487 | 254 | O(n) |
| add(int, E) | 224 | 237 | O(n) |
| sort (natural) | 118828 | 118828 | O(n^2) |
| sort (by length) | 118828 | 118828 | O(n^2) |

### Comparison

- **contains:** Both lists are O(n) in time, and the measured average of about 255 iterations is close to n/2 = 244 in both, because a random animal is about halfway through the list. Both lists are O(1) in space, because the iterator only stores one position or one node reference. The ArrayList is usually faster in practice because its elements sit next to each other in memory.
- **remove(Object):** Both lists are O(n) in time, but the ArrayList always does 487 iterations (search steps plus shifts always add up to n - 1) while the LinkedList does about n/2 (254) because it only traverses to the node and relinks it. Both lists are O(1) in space, because they only use a few index or node variables and never copy the data.
- **add(int, E):** Both lists are O(n) in time, with averages of about n/2 (224 for the ArrayList shifting elements right, 237 for the LinkedList walking to the index), and the two counts add up to roughly n. The LinkedList is O(1) in extra space per add (one new node), and the ArrayList is O(1) too except when the array is full and it must grow to a new array, which costs O(n) space. An ArrayList is best for adding near the end, and a LinkedList is best for adding near the front.
- **sort:** Both lists are O(n^2) in time, and the measured count of 118,828 matches n(n - 1)/2 exactly for both orderings, since selection sort always compares every pair. Both lists are O(1) in space, because selection sort works in place and only needs a temp variable to swap. The ArrayList swaps array slots and the LinkedList swaps node values, so no nodes are relinked.

Overall, the iteration counts match the time complexity analysis for all four operations, and the ArrayList and LinkedList have the same Big-O in time. The practical differences come from where the work happens (shifting vs. traversing) and from memory layout, not from the Big-O class.
