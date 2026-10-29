# ALA 6 – Computational Thinking Activity

| Subsystem | Data Structure | Why |
|---|---|---|
| Traffic Light Controller | **Queue** | FIFO — first car to arrive is the first to leave. Enqueue/dequeue are O(1). |
| Emergency Dispatch | **Priority Queue** | Calls are served by severity, not arrival order. Highest-priority call is removed in O(log n). |
| GPS Navigation "Undo" | **Stack** | LIFO — "pop" back to the most recent valid intersection in O(1). |
| Maintenance Log | **ArrayList** | Frequent jumps to a specific ID/index need O(1) random access via `get(i)`. |
| Live Event Guestlist | **LinkedList** | Constant inserts/removals in the middle; a linked list relinks nodes in O(1) at the position instead of shifting elements like an ArrayList (O(n)). |
