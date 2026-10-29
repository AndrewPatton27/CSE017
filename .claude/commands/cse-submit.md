---
description: Build the Gradescope submission zip for a CSE 017 assignment (reads the write-up to pick the right .java files)
argument-hint: [assignment folder, e.g. CSE017-pa4 or ALA/ala7]
---

Package a CSE 017 assignment for submission using `submissions/MakeSubmision.java`.

Assignment folder: `$ARGUMENTS` (if empty, use the most recently modified `CSE017-paN` or `ALA/alaN` folder and say which one you picked).

## Steps

1. **Find the write-up.** Look in the assignment folder (and its parent) for the handout: a PDF or .md/.txt write-up. Extract text with `pdftotext -layout file.pdf -` (fall back to `pypdf`). If no write-up exists, ask Drew to attach it or name the files. Do not guess.

2. **Work out which files to submit.** From the write-up, find the section on submission/deliverables ("submit", "Gradescope", "upload", "files to turn in") and the classes/files the assignment says to write or modify. Rules:
   - Submit only `.java` files Drew writes or modifies. Do NOT include provided files he was told not to change (e.g. `Collection.java`, `List.java`, `Test.java`, `Testing.java` if it is given), `.class` files, `output`, `output.reference`, `runTests.sh`, or `tests/`.
   - If the write-up asks for a test program or a written file (e.g. `Discussion.md`, a .txt/.pdf), include it only if the write-up says to submit it, and note that MakeSubmision.java only warns about non-.java files but still zips them.
   - Every required file must exist in the folder. If one is missing, stop and tell Drew which one.
   - If the write-up's list is ambiguous, show your list and the reason for each file, then ask before zipping.

3. **Sanity check before zipping.**
   - Compile the chosen files together with any provided dependencies in the folder into a scratch dir (`javac -d /tmp/cse-submit-out *.java`). If it fails, report the errors and do not zip.
   - Grep the chosen files for leftover `// TODO: [0-9]` coding-order labels and report any as a warning (not a blocker).
   - If `runTests.sh` exists, mention that Drew should run it; only run it if he asks.

4. **Make the zip.** From inside `submissions/`:
   ```
   cd submissions
   javac MakeSubmision.java      # only if MakeSubmision.class is missing or older than the .java
   java MakeSubmision ../<assignment folder> File1.java File2.java ...
   ```
   This creates `Drew Patton - <folder name>.zip` in `submissions/` with the files flattened (no folder structure), which is the format Gradescope expects. Overwriting an existing zip for the same assignment is expected on resubmission.

5. **Verify and report.** Run `unzip -l "<zip>"` and confirm the entries match the list. Report briefly:
   - the zip's path and name
   - the files included, and any files deliberately left out
   - warnings (TODOs left, compile issues, missing items)
   Then remind Drew to upload the zip to Gradescope himself. Never submit for him.

Keep the reply short. Do not edit his source files.

**Never run any git commands** (no add, commit, push, or stash) as part of this command. Only create the zip; leave the repo's state to Drew.
