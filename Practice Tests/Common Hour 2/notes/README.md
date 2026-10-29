# Test 2 Prep Kit (CSE 017)

Topics (from the review-session sheet): Recursion, Algorithm Analysis, Generics, Using Data Structures
(Collection/List, iterators, Collections, ArrayList, LinkedList, Stack, Queue, PriorityQueue).

| File | What it is |
|---|---|
| `Test2_Cheat_Sheet.md` | everything to know on ~4 pages, plus the exam patterns and common mistakes |
| `Practice_Test_3.md` / `Practice_Test_3_Answers.md` | full mock: written parts A-D + coding part E |
| `mock_exam_3/starter/` | Part E starter code (Practice Test 2 style). Finish it, then `diff` with `expected_output.txt` |
| `mock_exam_3/solution/` | reference solution (try first!) |
| `drills/` | small drills with `// TODO` stubs: `RecursionDrills`, `GenericsDrills`, `CollectionsDrills`, `Pair`, `MyStack`, `Student` + comparators, `Task`, `ComplexityLoops` |
| `solutions/` | completed drills |
| `DrillTest.java` | auto-grader for the drills (`Score: x / y`) |

## How to run (do this in a COPY of the folder, not inside your repo)
```
# drills (your work)
mkdir out
javac -d out drills/*.java DrillTest.java
java -cp out DrillTest

# complexity lab
javac -d out drills/ComplexityLoops.java && java -cp out ComplexityLoops        # add "answers" to print the answer key

# mock exam coding part
cd mock_exam_3/starter && javac *.java && java Test > mine.txt && diff mine.txt ../expected_output.txt
```

## Suggested plan
1. Read the cheat sheet (30 min). 2. Drills in order: recursion -> generics -> collections (60-90 min). 3. `ComplexityLoops`: predict, then run.
4. Practice Test 3 timed (75 min), then check. 5. Redo Practice Test 2 (your `Test.java`) without looking, both recursion and complexities.
Weak spot after that? Re-do the matching drill file.
