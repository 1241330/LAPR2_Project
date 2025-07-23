# Worst-Case Time Complexity Analysis of Algorithms from US14
## Context and Objectives
>This report fulfills the requirements of User Story US26, which specifies the requirement to analyse the efficiency of the algorithms developed in US13 and US14 through an analysis of their worst-case time complexity.
>
> The implementation of User Story US14 involves the identification and execution of several interdependent algorithmic procedures. These are responsible for determining the feasibility of a maintenance route that traverses each railway line exactly once, satisfying the conditions for an Eulerian or semi-Eulerian path in an undirected graph. Each procedure is outlined below with formal purpose, role in the system, and theoretical basis.

## Key Algorithms Identified in US14

Upon reviewing the code implemented for US14, three core algorithmic procedures have been identified:

> 1. **Filtering of Train Types**  
     This method `filterRailwayLinesType()` applies user-specified constraints to the set of available railway lines by filtering them according to the type of train selected (e.g., steam, diesel, electric). The filtering respects operational limitations, such as restricting electric trains to electrified lines. The resulting filtered graph determines the subgraph on which subsequent connectivity and Eulerian analysis is conducted.

>2. **Connectivity Verification**  
    This procedure `isNotGraphConnected()` checks whether the resulting (filtered) undirected graph is connected. According to graph theory, an undirected graph must be connected for an Eulerian path or circuit to exist.
    A graph $G = (V, E)$ must be connected for an Eulerian or semi-Eulerian walk to be possible.

>3. **Eulerian Classification**  
    Determine whether the graph (connected and unoriented) is Eulerian based on the degree of the graph's vertices, specifically checking whether all vertices have even degrees or whether they have exactly two odd-degree vertices - semi-Eulerian (and not Eulerian).
    >
    >   This algorithm `setEulerianPathType()` evaluates the degree of each vertex in the graph to determine its Eulerian property.
    >   - If all vertices have even degree, the graph admits an Eulerian circuit.
>   - If exactly two vertices have odd degree, the graph is semi-Eulerian and admits an Eulerian path (but not a circuit).
>   - Otherwise, the graph is non-Eulerian, and such a path is not possible.

>4. **Initial Station Selection**   
    This method `(getValidInitialStation()` identifies a suitable starting vertex (station) for the traversal algorithm, depending on the Eulerian classification:
    >   - For Eulerian circuits, any vertex may serve as the starting point.
>   - For semi-Eulerian paths, the traversal must start at one of the two odd-degree vertices.

>5. **Eulerian Path Construction**  
    This procedure `setFinalPath()` implements the construction of the final maintenance route using an appropriate traversal algorithm such as Hierholzer’s algorithm, which guarantees an Eulerian path/circuit when one exists.6.

Each of these procedures is presented below using pseudocode, along with a formal justification of its worst-case time complexity using asymptotic notation.

---
# 1. Filtering of Train Types

## Pseudocode
```plaintext
procedure filterRailwayLinesType()
if railwayLinesType = null then
    throw exception "Railway lines type cannot be null"

if railwayLinesType contains "All" then
    return

linesCurrentlyAvailable := copy of availableLines

for each line in linesCurrentlyAvailable
    type := line.getTypeEnum().getType()
    if (not railwayLinesType contains "Non" and type contains "Non") or
       (railwayLinesType contains "Non" and not type contains "Non") then
        remove line from availableLines
```

## Complexity Analysis and Justification

Let:   
$m = | \text{availableLines} |$ be the number of railway lines before filtering.
---
This algorithm filters out railway lines based on compatibility with the selected train types. It executes in three distinct phases, each of which is analysed and justified individually with reference to the theorems and asymptotic definitions:

### 1. Initialization and Validation Phase

Each of these operations constitutes a primitive operation and executes in constant time. Thus, both operations combined yield:
$$T_{\text{validation}}(m) = O(1)$$

This follows directly from the definition of Big-O, since any constant-time function $f(m) \leq c$ satisfies:
$$f(m) \in O(1)$$


---
### 2. Copy Phase

A shallow copy of the list `availableLines` is made into `linesCurrentlyAvailable`. Given $m$ elements, each copied in constant time, this operation scales linearly:

$$T_{\text{copy}}(m) = m \cdot O(1) = O(m)$$

By Theorem 3.4, the product of a constant-time operation and a linear number of iterations is:

$$f_1(m) \in O(1), \quad f_2(m) \in O(m) \Rightarrow f_1(m) \cdot f_2(m) \in O(m)$$

---
### 3. Filtering Phase

The loop iterates over the $m$ elements of `linesCurrentlyAvailable`. In each iteration:

- One method chain to get the line type: $$O(1)$$
- Two string containment checks: $$O(1)$$
- A conditional check and possibly removing an element: $$O(1)$$

Each of these is a primitive operation, so each iteration executes in constant time.

Thus, the total cost of this phase is:
$$T_{\text{loop}}(m) = m \cdot O(1) = O(m)$$

Again, this is justified using Theorem 3.4 (product rule), and by treating the total as a sum of primitive costs over iterations, Theorem 3.3 (sum rule) implies the cost remains in:
$$O(m)$$

---
## Total Worst-Case Time Complexity

Using Theorem 3.3, the total cost is the sum of the complexities of the three independent phases:

$$T(m) = O(1) + O(m) + O(m) = O(m)$$

This is because, given:

$$f_1(m) \in O(1), \quad f_2(m), f_3(m) \in O(m) \Rightarrow f_1 + f_2 + f_3 \in O(\max\{1, m\}) = O(m)$$

---
## Final Conclusion

The overall worst-case time complexity of the `filterRailwayLinesType` procedure is:

$$O(m)$$

This reflects a linear growth rate with respect to the number of railway lines available, with each line processed exactly once using only constant-time operations.

Such linear complexity is considered efficient and lies within the class of tractable problems, where the total time required grows proportionally with the input size.

---

---
# 2. Connectivity Verification

## Pseudocode
```plaintext
procedure isGraphConnected(railwayLines)
visited := empty set
stack := [startStation]

while stack is not empty
    current := stack.removeLast()
    if current not in visited then
        visited.add(current)
        for each neighbor of current via availableLines
            if neighbor not in visited then
                stack.add(neighbor)

for each station in railwayLines
    if station not in visited then
        return false

return true
```

## Complexity Analysis and Justification

Let:
- $n = |stationList|$: denote number of vertices (stations)
- $m = |railwayLines|$: denote number of edges (railway connections)

The algorithm determines whether the railway network is connected via **Depth-First Search (DFS)** approach implemented with an explicit stack. Its complexity will be analysed in three distinct phases.

### 1. Initialization Phase

The algorithm begins by:

- Initialising an empty set `visited`, and
- Placing one starting station in the stack.

Each of these steps constitutes a primitive operation, taking constant time:
$$T_{\text{init}} = O(1)$$

Justified by the formal definition of Big-O, any bounded operation:
$$f(n) \leq c \Rightarrow f(n) \in O(1)$$

---
### 2. DFS Traversal Phase — Depth-First Search Using an Explicit Stack

The core of the algorithm performs a DFS traversal over the graph. Assuming the graph is implemented using adjacency lists, the total cost depends on:

- Visiting each vertex at most once → $O(n)$
- Exploring each edge at most twice (undirected graph) → $O(m)$

#### Primitive Operations in the DFS Loop

Each iteration of the while loop performs:

- `stack.removeLast()` → $O(1)$
- Membership test in `visited` → $O(1)$
- Insertion into `visited` → $O(1)$
- Iteration over neighbours and stack insertion → $O(1)$ per neighbour

These are all elementary operations with constant time.

As each vertex and edge is processed a constant number of times, the total cost is:
$$T_{\text{DFS}}(n, m) = O(n) + O(m) = O(n + m)$$

Using Theorem 3.3, the sum of functions in Big-O is dominated by the maximum growth term:
$$f_1(n) = O(n), \quad f_2(n) = O(m) \Rightarrow f_1 + f_2 = O(\max\{n, m\}) = O(n + m)$$

---
### 3. Post-Traversal Verification Phase

After traversal, the algorithm verifies whether all stations in the graph have been visited. This is done by iterating over all $n$ stations and performing membership checks:
- Each check → $O(1)$

Thus, the cost of this phase is:
$$T_{\text{verify}}(n) = n \cdot O(1) = O(n)$$

By Theorem 3.4, the product of a constant and a linear term yields:
$$O(1) \cdot O(n) = O(n)$$

---
## Total Worst-Case Time Complexity

Summing all phases:
$$T(n, m) = O(1) + O(n + m) + O(n)$$

Using Theorem 3.3, we conclude:
$$T(n, m) = O(n + m)$$

This reflects the standard complexity of DFS on a graph represented via adjacency lists.

---
### Asymptotic Bound for Dense Graphs

In the worst-case scenario, for a dense undirected graph, the number of edges satisfies:
$$m = \frac{n(n - 1)}{2} \in O(n^2)$$

Hence, substituting into the complexity expression:
$$T(n, m) = O(n + n^2) = O(n^2)$$

Justified via Theorem 3.2, since the polynomial function $n^2 + n$ is in $O(n^2)$, as lower-order terms and constants are asymptotically negligible.

---
## Final Conclusion

The total time complexity of the `isGraphConnected` procedure is:

- $O(n + m)$ in general
- $O(n^2)$ in the worst-case for dense graphs

This efficiency makes the algorithm suitable for sparse and moderately dense graphs, placing it in the class of tractable problems (*sebenta*, p. 86), where the complexity is polynomial in the size of the input.

---

---
# 3. Eulerian Classification
## Pseudocode

```plaintext
procedure setEulerianPathType()
oddDegreeStations := empty list

for each station in stationList
degree := count number of edges connected to station
if degree mod 2 ≠ 0 then
add station to oddDegreeStations

if size of oddDegreeStations = 0 then
set graph as Eulerian
else if size of oddDegreeStations = 2 then
set graph as Semi-Eulerian
else
set graph as Non-Eulerian
```

## Complexity Analysis and Theoretical Justification

Let:

- $n = |\text{stationList}|$ denote the number of vertices (stations)
- $m = |\text{railwayLines}|$ denote the number of edges (railway connections)

The algorithm classifies a graph as Eulerian, Semi-Eulerian, or Non-Eulerian based on the parity of vertex degrees, which are computed via an adjacency matrix representation of the graph.

---

### 1. Adjacency Matrix Construction (Assumed Preprocessing)

Assuming the graph is represented by an adjacency matrix $A \in \{0, 1\}^{n \times n}$, two steps are required:

#### a) Initialisation:

The matrix is initialised with zeros: $n^2$ entries.

**Cost**:

$$T_{\text{init}} = O(n^2)$$

#### b) Population of Edges:

Each undirected edge between stations $i$ and $j$ increments both $A[i][j]$ and $A[j][i]$.

For $m$ edges:
$$T_{\text{edges}} = O(m)$$

#### Total Matrix Construction Time:

Using Theorem 3.3, which states that the sum of two asymptotic functions is bounded by the one with higher growth:
$$T_{\text{matrix}}(n, m) = O(n^2 + m)$$

---

### 2. Degree Computation Phase

For each station (vertex), the algorithm computes its degree by summing the corresponding row of the adjacency matrix.

Each of the $n$ rows contains $n$ entries, requiring $O(n)$ operations.

Over all rows:
$$T_{\text{degree}}(n) = n \cdot O(n) = O(n^2)$$

This follows directly from Theorem 3.4, where:
$$O(n) \cdot O(n) = O(n^2)$$

---

### 3. Eulerian Classification Logic

After computing the degrees, the algorithm checks how many vertices have odd degree.

At most two comparisons (number of odd-degree vertices = 0 or = 2) and a constant number of assignments.

All are primitive operations and run in constant time:
$$T_{\text{logic}} = O(1)$$

Justified by the definition of Big-O, since constant-time operations are in $O(1)$.

---

## Final Complexity (General Case)

Summing all three phases:
$$T(n, m) = O(n^2 + m) + O(n^2) + O(1)$$

Using Theorem 3.3 again:
$$T(n, m) = O(n^2 + m)$$

This indicates the classification runs efficiently even for large graphs, provided $m \leq n^2$.

---

## Special Case: Dense Graph

In the worst-case scenario, where the graph is complete (fully connected), the number of undirected edges is:
$$m = \frac{n(n - 1)}{2} \in O(n^2)$$

Substituting into the complexity expression:
$$T(n, m) = O(n^2 + n^2) = O(n^2)$$

Justified formally by Theorem 3.2, which asserts that a polynomial function of the form:
$$a_n n^2 + a_1 n + a_0 \in O(n^2)$$

---

## Final Conclusion

The worst-case time complexity of the `setEulerianPathType` procedure is:

- $O(n^2 + m)$ in general
- $O(n^2)$ when the graph is dense

This result places the procedure within the class of **tractable problems**, where the number of operations grows at most quadratically with input size.


---

---
# 4. Initial Station Selection
## Pseudocode

```plaintext
procedure getValidInitialStations()
if graph is Semi-Eulerian then
    return list of stations with odd degree
else if graph is Eulerian then
    return list of all stations
else
    throw exception "No valid initial station"
```

## Complexity Analysis and Justification

Let:

- $n = |\text{stationList}|$ denote the number of vertices (stations) in the graph.

The purpose of this procedure is to return the list of valid initial stations for traversing an Eulerian path or circuit, depending on the Eulerian classification of the graph, which is assumed to be precomputed (see Section 3).

---

### 1. Eulerian Status Check

The algorithm begins by verifying the Eulerian status of the graph, which can be:

- **Semi-Eulerian**: exactly two vertices of odd degree
- **Eulerian**: all vertices of even degree
- **Non-Eulerian**: more than two vertices of odd degree

Since this classification is assumed to be stored as metadata from a previous computation, the check is a constant-time operation, independent of $n$:
$$T_{\text{check}} = O(1)$$

This is a direct application of the definition of Big-O, which asserts that any operation bounded by a constant is in $O(1)$.

---

### 2. Return of Valid Stations

Depending on the classification:

- **Semi-Eulerian**: returns a fixed-size list containing two stations of odd degree → constant-time operation:
  $$T_{\text{semi}} = O(1)$$

- **Eulerian**: returns the full list of all stations → requires iterating through $n$ vertices:
  $$T_{\text{eulerian}} = O(n)$$

  This corresponds to a linear-time operation over the input size.

- **Non-Eulerian**: an exception is thrown, which is constant-time:
  $$T_{\text{non}} = O(1)$$

Applying Theorem 3.3, which allows us to take the maximum of Big-O terms when combining alternative execution paths, we obtain:
$$T_{\text{return}}(n) = \max\{O(1), O(n)\} = O(n)$$

---

## Final Worst-Case Time Complexity

Summing the two phases:
$$T(n) = O(1) + O(n) = O(n)$$

This follows again from Theorem 3.3, which tells us that the sum of two functions is bounded by the one with the higher growth rate.

---

## Conclusion

The worst-case time complexity of the `getValidInitialStations` procedure is:

- $O(n)$ — when the graph is **Eulerian**, and the procedure must return all $n$ stations.
- $O(1)$ — in all other cases (**Semi-Eulerian** or **Non-Eulerian**).

Thus, the algorithm is **efficient**, and belongs to the class of **tractable problems**, with complexity linear in the number of vertices only in the worst case.

---

---
# 5. Eulerian Path Construction

## Pseudocode

```plaintext
procedure setFinalPath()
path := empty list
stack := [initialStation]

while stack is not empty
    current := top of stack
    if current has unvisited edge then
        neighbor := next connected station
        stack.add(neighbor)
        remove edge between current and neighbor
    else
        path.add(stack.removeLast())

return reverse(path)

```

## Complexity Analysis and Theoretical Justification

Let:

- $n = |\text{stationList}|$: the number of vertices (stations)
- $m = |\text{railwayLines}|$: the number of edges (railway connections)

This procedure implements **Hierholzer’s Algorithm**, which constructs an Eulerian path by traversing each edge exactly once. A stack is used for managing the current traversal, enabling backtracking as necessary.

---

### 1. Stack Operations and Traversal Logic

The main `while` loop executes as long as there are vertices on the stack. For each iteration, the following operations are executed:

- **Access the top of the stack**: $O(1)$
- **If an unvisited adjacent edge exists**:
    - Retrieve a neighbour: $O(1)$
    - Push the neighbour to the stack: $O(1)$
    - Remove the edge between current and neighbour: $O(1)$
- **Otherwise**:
    - Pop the vertex from the stack and append it to the path: $O(1)$

All of these are **primitive operations** and take constant time per execution.

### Operation Count

- Each edge is processed exactly once (traversed and removed):  
  $$T_{\text{edges}} = O(m)$$

- Each vertex may be pushed to and popped from the stack a number of times bounded by its degree. Since the sum of degrees is $2m$, we have at most $2m$ stack operations:  
  $$T_{\text{stack}} = O(m)$$

Using **Theorem 3.3**, the sum of two $O(m)$ terms remains $O(m)$.  
Thus, the total traversal logic is bounded by:
$$T_{\text{traversal}}(m) = O(m)$$

---

### 2. Final Path Reversal

At the end, the constructed path is reversed before being returned. Since the path includes all traversed edges plus one additional vertex, it contains $m + 1$ elements.

- Reversing a list of size $m + 1$ requires linear time:
  $$T_{\text{reverse}} = O(m)$$

This follows from the fact that list reversal is a linear-time primitive operation, and by **Theorem 3.2**, the function $m + 1 \in O(m)$ as additive constants are asymptotically irrelevant.

---

## Final Worst-Case Time Complexity

Summing the two main phases:
$$T(n, m) = O(m) + O(m) = O(m)$$

This is justified again using **Theorem 3.3** on the sum of asymptotic functions.

---

## Special Case: Dense Graph

In a dense undirected graph, the maximum number of edges is:
$$m = \frac{n(n - 1)}{2} \in O(n^2)$$

Therefore, in the worst case (i.e., a complete graph):
$$T(n, m) = O(n^2)$$

This corresponds to a **quadratic complexity**, consistent with **Theorem 3.2**, as the leading term $n^2$ dominates the expression.

---

## Final Conclusion

The worst-case time complexity of the `setFinalPath` procedure is:

- $O(m)$ in general — linear with respect to the number of edges
- $O(n^2)$ in the worst case for dense graphs

This algorithm is **highly efficient for sparse and moderately dense graphs**, and belongs to the class of **tractable problems**, where the number of operations grows polynomially with the size of the input.

---

---


## Final Table: Primitive Operation Count for US14 Algorithms

| Algorithm                  | Phase                     | Primitive Operation Type             | Operation Count         | Total Cost Function         | Asymptotic Complexity | Justification                                                         |
|---------------------------|---------------------------|--------------------------------------|--------------------------|-----------------------------|------------------------|----------------------------------------------------------------------------------------------|
| **1. filterRailwayLinesType()** | A: Validation checks        | Null check, string check             | $\leq 2$                 | $f_1 = 2$                   | $O(1)$                 | Fixed number of control statements. Each check is a primitive comparison.            |
|                           | B: Copy of line list       | Assignments (shallow copy)           | $m$                      | $f_2(m) = m$                | $O(m)$                 | Each copy is a primitive assignment. Linear complexity (Theorem 3.4).                      |
|                           | C: Filtering each line     | 3 checks + 1 conditional removal     | $m$                      | $f_3(m) = m$                | $O(m)$                 | Constant work per line. Sum rule (Theorem 3.3).                                            |
| **Total**                 | —                         | —                                    | $2 + 2m$                 | $f(m) = 2 + 2m$             | $O(m)$                 | Dominated by linear term. Sum rule (Theorem 3.3).                                          |
| **2. isGraphConnected()** | A: Initialisation          | Stack and set creation               | $2$                      | $f_1 = 2$                   | $O(1)$                 | Primitive initialisations.                                                           |
|                           | B: DFS traversal           | Node and edge visits                 | $n + 2m$                 | $f_2(n,m) = n + 2m$         | $O(n + m)$             | Standard DFS complexity. Each operation is primitive.                               |
|                           | C: Post-traversal check    | Membership tests                     | $n$                      | $f_3(n) = n$                | $O(n)$                 | Linear scan. Product rule (Theorem 3.4).                                                   |
| **Total**                 | —                         | —                                    | $2 + n + 2m$             | $f(n,m) = 2 + n + 2m$       | $O(n + m)$             | Sum rule (Theorem 3.3).                                                                    |
| **3. setEulerianPathType()** | A: Matrix initialisation     | Assignments                          | $n^2$                    | $f_1(n) = n^2$              | $O(n^2)$               | Zero-filling matrix. Polynomial complexity (Theorem 3.2).                                 |
|                           | B: Edge insertion          | Matrix writes                        | $2m$                     | $f_2(m) = 2m$               | $O(m)$                 | One write per edge endpoint (p. 74).                                                       |
|                           | C: Degree calculation      | Row summation                        | $n^2$                    | $f_3(n) = n^2$              | $O(n^2)$               | Arithmetic operations. Product rule (Theorem 3.4).                                         |
|                           | D: Classification logic    | Comparisons and assignment           | $3$                      | $f_4 = 3$                   | $O(1)$                 | Fixed conditional block.                                                            |
| **Total**                 | —                         | —                                    | $n^2 + 2m + 3$           | $f(n,m) = n^2 + 2m + 3$     | $O(n^2 + m)$           | Sum rule (Theorem 3.3).                                                                    |
| **4. getValidInitialStations()** | A: Type retrieval           | Read operation                       | $1$                      | $f_1 = 1$                   | $O(1)$                 | Metadata-based retrieval .                                                          |
|                           | B: Return logic            | Conditional return                   | $\leq n$                 | $f_2(n) = n$                | $O(n)$                 | Worst-case dominates. Sum rule (Theorem 3.3).                                              |
| **Total**                 | —                         | —                                    | $1 + n$                  | $f(n) = 1 + n$              | $O(n)$                 | Worst-case analysis.                                                                |
| **5. setFinalPath()**     | A: Hierholzer traversal    | Edge processing                      | $m$                      | $f_1(m) = m$                | $O(m)$                 | Stack operations and adjacency access are constant .                               |
|                           | B: Path reversal           | List reversal                        | $m$                      | $f_2(m) = m$                | $O(m)$                 | List reversal is linear.                                                      |
| **Total**                 | —                         | —                                    | $2m$                     | $f(m) = 2m$                 | $O(m)$                 | Sum rule (Theorem 3.3).                                                                    |
---

## Final Considerations

> The analysis of the US14 algorithms shows that the most computationally demanding procedure is the Eulerian Classification, with a worst-case complexity of $O(n^2)$, primarily due to the use of an adjacency matrix for degree computation.
>
> The filtering, connectivity verification, and Eulerian path construction operations have linear or near-linear complexity with respect to the number of vertices and edges, which aligns with typical graph algorithmic performance expectations.
>
> These implementations respect the constraints of US14 by relying on primitive operations and fundamental data structures without employing advanced graph libraries, thus enabling precise control and assessment of computational complexity within the domain of railway network maintenance route planning.

---
