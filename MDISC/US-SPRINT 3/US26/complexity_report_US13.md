
# Worst-Case Time Complexity Analysis of Algorithms from US13

## Context and Objectives
>This report fulfills the requirements of User Story US26, which specifies the requirement to analyse the efficiency of the algorithms developed in US13 and US14 through an analysis of their worst-case time complexity.
>
>US13 specifies that, as a player, one must be able to determine whether a given train type (steam, diesel, or electric) can travel between two stations within a railway network. This determination depends on the type of railway line (electrified or not) and the station type (depot, station, or terminal). The system must allow filtering of train types in real time and enable exclusion of specific station types.
>
>In accordance with Acceptance Criterion AC01 of US26, all algorithms must be presented using pseudocode and accompanied by a thorough worst-case complexity analysis. The methodology adheres strictly to the principles outlined in Chapters 3 and 4 of the course textbook on Discrete Mathematics, which define primitive operations and asymptotic notation using Big-O.

---

## Key Algorithms Identified in US13
Upon reviewing the code implemented for US13, four core algorithmic procedures have been identified:

>1. **Construction of the Adjacency Matrix**  
    Implemented via the `buildAdjacencyMatrix()` method, this procedure constructs an undirected graph where the vertices correspond to stations and the edges represent the railway lines connecting them. The adjacency matrix is a binary $n \times n$ matrix (where $n$ is the number of stations), where an entry of 1 indicates the existence of a connection between two stations.

>2. **Computation of the Transitive Closure**  
    Using the adjacency matrix, the method `getTransitiveClosureMatrix()` applies Warshall’s algorithm, a classic graph-theoretic technique. This computes whether a path (sequence of edges) exists between all pairs of stations, effectively determining the reachability relation and producing a transitive closure matrix.

>3. **Verification of Connectivity Between Two Stations* *
    The method `isConnectedTransitive()` checks whether two specified stations are connected, using the transitive closure matrix. It corresponds to a simple lookup of the matrix entry $(i,j)$, representing whether there exists a path between vertex $i$ and vertex $j$.

>4. **Global Connectivity Check Among All Valid Stations**  
    The method `verifyConnectivity()` checks whether all stations not marked as invalid (based on their type) are mutually connected. This corresponds to verifying that the graph induced by the valid vertices is connected by iterating over all valid pairs and inspecting the transitive closure matrix.

Each of these procedures is presented below using pseudocode, along with a formal justification of its worst-case time complexity using asymptotic notation.

---

# 1. Construction of the Adjacency Matrix

## Pseudocode
```plaintext
procedure buildAdjacencyMatrix(stationList, railwayLines)
n := size of stationList
matrix := n x n matrix initialized with zeros

for each line in railwayLines
    i := index of origin station of line
    j := index of destination station of line
    matrix[i][j] := 1
    matrix[j][i] := 1   (undirected graph)

return matrix
```

## Complexity Analysis and Justification
Let:      
$n = \lvert \text{stationList} \rvert$ → the number of vertices (stations),      
$m = \lvert \text{railwayLines} \rvert$ → the number of edges (railway segments).

We analyse the algorithm in terms of its temporal complexity in the worst case, considering each primitive operation (assignment, comparison, arithmetic operation, method call or return) as having unit cost. This yields an abstract machine-independent estimation of performance.

---

### Phase 1: Initialisation of the Matrix

The algorithm constructs a square matrix of dimension $n \times n$ which it initialises by setting all entries to zero. Each entry assignment is a primitive operation.

Total number of assignments:  
$$n^2$$

Since each assignment has constant cost, this results in a function:
$$f_1(n) = n^2$$

By applying **Theorem 3.2** (*Polynomial functions: asymptotic upper bound*), we know that any polynomial of degree $d$ is $O(x^d)$. Hence:
$$f_1(n) \in O(n^2)$$

---

### Phase 2: Insertion of Edges

For each undirected edge (railway line), the adjacency matrix is updated at two symmetric positions, $[i][j]$ and $[j][i]$, each corresponding to a constant-time assignment.

Total number of updates:  
$$2m$$

Thus, the complexity function is:
$$f_2(m) = 2m \Rightarrow f_2(m) \in O(m)$$

This follows directly from the definition of Big-O notation (**Definition 3.1: Asymptotic upper bound**).

---

## Overall Complexity

The overall number of primitive operations performed by the algorithm is the sum of those performed in the two phases. According to **Theorem 3.3** (*Asymptotic behaviour of sums*), if  
$$f_1(x) \in O(g_1(x)) \quad \text{and} \quad f_2(x) \in O(g_2(x))$$  
then:
$$f_1(x) + f_2(x) \in O(\max\{g_1(x), g_2(x)\})$$

Applying this theorem with  
$$f_1(n) = O(n^2) \quad \text{and} \quad f_2(m) = O(m)$$

we obtain:
$$f(n, m) = O(n^2 + m)$$

This represents the worst-case temporal complexity of the algorithm.

---

### Behaviour Under Graph Density

The asymptotic expression $O(n^2 + m)$ may be interpreted differently depending on the nature of the graph:

**Dense graphs:**  
If $m = O(n^2)$, i.e., the number of edges is proportional to the square of the number of vertices, then:

$$O(n^2 + m) = O(n^2 + n^2) = O(2n^2) = O(n^2)$$

since constant factors are neglected in Big-O notation, in accordance with the properties outlined in **Theorem 3.1** (*Definition of Big-O and bounding constants*).

**Sparse graphs:**  
If $m \ll n^2$ retaining the expression $O(n^2 + m)$ provides a more accurate upper bound than simplifying it to $O(n^2)$.  
This is particularly relevant when comparing alternative graph representations such as adjacency lists.

---

## Final Statement

Thus, the temporal complexity of constructing the adjacency matrix for an undirected graph is, in the worst case:
$$O(n^2 + m)$$

---

---

# 2. Computation of the Transitive Closure (Warshall’s Algorithm)

## Pseudocode
```plaintext
procedure computeTransitiveClosure(matrix, n)
for k := 0 to n - 1
    for i := 0 to n - 1
        for j := 0 to n - 1
            if matrix[i][j] = 1 or (matrix[i][k] = 1 and matrix[k][j] = 1) then
                matrix[i][j] := 1

return matrix
```

## Complexity Analysis and Justification

Let $n$ denote the number of vertices in the graph, and suppose the graph is represented by an $n \times n$ adjacency matrix. The aim of Warshall’s algorithm is to compute the transitive closure of the graph by successively updating reachability information between all pairs of vertices using intermediate nodes.

---
### Identification of Primitive Operations

According to the sebenta, each of the following counts as a primitive operation:

- Assignments (e.g., `matrix[i][j] := 1`)
- Comparisons (e.g., `matrix[i][j] = 1`)
- Logical operations (e.g., `OR`, `AND`)
- Loop control (increment and bound checking)
- Return statements

We now examine the number of such operations performed by the algorithm in the worst case.

---
### Loop Structure and Operation Count

The algorithm comprises three nested loops, each iterating exactly $n$ times:

- **Outer loop**: index $k \in \{0, \dots, n - 1\}$ → $n$ iterations
- **Middle loop**: index $i \in \{0, \dots, n - 1\}$ → $n$ iterations
- **Inner loop**: index $j \in \{0, \dots, n - 1\}$ → $n$ iterations

Hence, the body of the innermost loop is executed a total of:
$$n \cdot n \cdot n = n^3 \text{ times}$$

Within the innermost block, the following primitive operations are performed per iteration:

- Two comparisons: `matrix[i][j] = 1`, `matrix[i][k] = 1`, and `matrix[k][j] = 1`
- One logical conjunction (AND) and one disjunction (OR)
- One assignment, conditionally (at most once per iteration)

Assuming that all such operations are of constant cost, the total cost is bounded above by a constant multiplied by $n^3$.

---
### Asymptotic Complexity

The total number of primitive operations is of order $n^3$, so the time complexity is:
$$f(n) = c \cdot n^3 \Rightarrow f(n) \in O(n^3)$$

This follows directly from **Theorem 3.2** (Polynomial Functions: Asymptotic Upper Bound), which establishes that a function of the form  
$$f(n) = a_n n^3 + \cdots + a_0 → O(n^3)$$ Furthermore, we apply **Definition of Big-O Notation** to formally bound the execution cost from above.

---
## Final Conclusion

By applying **Theorem 3.3** (Sum Rule) and considering all constant-time operations within the three nested loops, we conclude that Warshall’s algorithm, as implemented with an adjacency matrix, exhibits the following worst-case time complexity:

$$O(n^3)$$

This result is optimal for adjacency-matrix-based algorithms that compute all-pairs reachability via transitive closure, since any such method must in the worst case examine all $n^2$ possible vertex pairs and consider up to $n$ intermediate vertices.


---

---

# 3. Verification of Connectivity Between Two Stations

## Pseudocode
```plaintext
procedure isConnected(origin, destination, matrix)
i := index of origin
j := index of destination

return matrix[i][j] = 1
```

## Complexity Analysis and Justification

Let $n$ denote the number of stations in the network. The goal of this procedure is to determine whether there exists a direct connection (i.e., an edge) between a given origin and destination, using a precomputed adjacency matrix.

The algorithm consists of two distinct phases:

### Phase 1: Index Retrieval

The first task is to determine the indices $i$ and $j$ corresponding to the identifiers of the origin and destination stations. Assuming the use of a linear search over a station list of length $n$, we must, in the worst case, inspect all $n$ elements.

Each iteration of this search entails a comparison operation, and we assume that identifying each station involves a constant number of such operations.

Thus, the cost of this step is at most $n$ comparisons.

This yields a complexity function:
$$f_1(n) = n \Rightarrow f_1(n) \in O(n)$$

This follows directly from **Definition 3.1 of Big-O Notation**.

### Phase 2: Matrix Access

Once the indices are obtained, we perform a single lookup operation:
$$\text{matrix}[i][j] = 1$$

This involves:
- Accessing a matrix cell using two integer indices.
- Performing a comparison against the value 1.

Both of these actions are **primitive operations** and thus are assumed to incur constant cost.

Hence:

$$f_2(n) = 1 \Rightarrow f_2(n) \in O(1)$$

---
## Overall Complexity

The total number of primitive operations is the sum of those in both phases. By **Theorem 3.3 (Asymptotic Sum Rule)**, we have:

$$f(n) = f_1(n) + f_2(n) \in O(\max\{n, 1\}) = O(n)$$

Hence, the **worst-case temporal complexity** of the procedure is:
$$O(n)$$

---

---

# 4. Verification of Global Connectivity Among Valid Stations

## Pseudocode
```plaintext
procedure verifyConnectivity(matrix, isInvalid[])
    for i from 0 to n-1:
        if isInvalid[i]: continue
        for j from 0 to n-1:
            if isInvalid[j]: continue
            if matrix[i][j] == 0:
                return false
    return true
```

## Complexity Analysis and Justification

Let us consider a square adjacency matrix of size $n \times n$, where each entry $\text{matrix}[i][j]$ indicates the existence of a direct connection between stations $i$ and $j$. The boolean array `isInvalid[]` denotes which stations are to be ignored.

---
### Step-by-Step Analysis:

#### 1) Structure of Iteration

The procedure consists of two nested `for` loops:

- The outer loop runs over all $i \in \{0, 1, \dots, n-1\}$
- The inner loop runs over all $j \in \{0, 1, \dots, n-1\}$

Hence, the maximum number of iterations is:
$$n \times n = n^2$$

#### 2) Operations per Iteration

Within each iteration:

- Two conditional checks are made: `isInvalid[i]` and `isInvalid[j]`
- One matrix lookup is performed: `matrix[i][j]`
- One comparison is executed: `matrix[i][j] == 0`

These are all classified as *primitive operations*, i.e., elementary operations such as comparisons, assignments, and list accesses. Each has a constant cost and thus runs in:
$$O(1)$$

---
## Worst-Case Scenario

In the worst case — where no station is marked invalid — all $n^2$ iterations are performed without skipping. Thus, for each of the $n^2$ pairs $(i, j)$, the procedure executes a constant number of operations (precisely 4 per iteration).

Consequently, the total number of primitive operations in the worst case is bounded above by:
$$T(n) = 4n^2$$

Therefore:
$$T(n) \in O(n^2)$$

---

### Big-O Justification

Using the formal definition of Big-O notation:

Let $f(x)$ and $g(x)$ be real functions. Then $f(x) = O(g(x))$ if and only if:

$$\exists c \in \mathbb{R}^+, \exists k \in \mathbb{R}^+ \text{ such that } \forall x > k, \ |f(x)| \leq c \cdot |g(x)|$$

In our case:
$$f(n) = 4n^2$$
$$g(n) = n^2$$

Choosing $c = 4$ and $k = 0$, the condition above is satisfied for all $n > 0$. Therefore:
$$f(n) = 4n^2 \Rightarrow f(n) \in O(n^2)$$

Furthermore, by Theorem 3.2, any polynomial of degree $k$ is in $O(n^k)$, and by Theorem 3.3 (sum of functions) and Theorem 3.4 (product of functions), combining constant-time operations across nested loops yields the multiplicative bound:
$$O(n) \cdot O(n) = O(n^2)$$

---
## Conclusion

In the worst-case scenario, where all stations are valid, the procedure `verifyConnectivity` executes in:
$$O(n^2)$$

---

---

## Final Table: Primitive Operation Count for US13 Algorithms

| Algorithm                     | Phase                                                              | Type of Primitive Operations                     | No. Primitive Operations | Total Cost Function   | Asymptotic Complexity  | Justification (with Coursework Theorems)                                                                   |
|-------------------------------|--------------------------------------------------------------------|--------------------------------------------------|--------------------------|-----------------------|------------------------|------------------------------------------------------------------------------------------------------------|
| **1. buildAdjacencyMatrix()** | A: Initialisation of $n \times n$ matrix                           | $n^2$ assignments of 0                           | $n^2$                    | $f_1(n) = n^2$       | $O(n^2)$              | Each matrix entry is an assignment ⇒ primitive operation. Theorem 3.2 applied to polynomial functions.     |
|                               | B: Insertion of $m$ (bidirectional) edges                          | 2 assignments per row                            | $2m$                     | $f_2(m) = 2m$        | $O(m)$                | Each row updates 2 matrix entries: $[i][j]$ and $[j][i]$. Constant cost per operation.                     |
|                               | **Total**                                                         | —                                                | $n^2 + 2m$               | $f(n, m) = n^2 + 2m$ | $O(n^2 + m)$          | By Theorem 3.3 (asymptotic summation). Sum of costs from both phases.                                      |
| **2. getTransitiveClosureMatrix()** | A: Three nested loops ($k, i, j$ from $0$ to $n-1$)         | Triple loop with $n^3$ iterations                 | $n^3$                    | $T_{loops} = n^3$    | —                     | Each iteration executes fixed body.                                                                        |
|                               | B: Inner body: comparisons (3), AND/OR (2), conditional assignment (1) | Up to 6 primitives per iteration           | $\leq 6n^3$              | $f(n) = 6n^3$        | $O(n^3)$              | All control and update operations are primitive. Theorem 3.4 (product of $n \cdot n \cdot n$ with $O(1)$). |
| **3. isConnectedTransitive()** | A: Index retrieval via search (source and target)                  | Linear search: $n$ comparisons per index          | $2n$                     | $f_1(n) = 2n$        | $O(n)$                | Assuming linear search, each comparison is primitive.                                                      |
|                               | B: Matrix access $[i][j]$ and comparison with 1                    | Access + comparison                              | $2$                      | $f_2 = 2$            | $O(1)$                | Both are constant-time primitive operations.                                                               |
|                               | **Total**                                                         | —                                                | $\leq 2n + 2$            | $f(n) = 2n + 2$      | $O(n)$                | By Theorem 3.3, linear sum with constant.                                                                  |
| **4. verifyConnectivity()**   | A: Double loop over $i, j \in \{0, ..., n-1\}$                     | $n^2$ iterations in worst case (no invalid nodes) | $n^2$                    | $T_{loops} = n^2$    | —                     | Two nested loops over the matrix.                                                                          |
|                               | B: Per iteration: <ul><li>Check isInvalid[i]</li><li>Check isInvalid[j]</li><li>Access matrix[i][j]</li><li>Compare with 0</li></ul> | 4 primitives per iteration | $4n^2$        | $f(n) = 4n^2$        | $O(n^2)$              | Each operation is primitive. Total: $4 \cdot n^2$. Theorem 3.2 on polynomial functions.                    |
---

## Final Considerations
>The analysis of the algorithms implemented for US13 reveals that the most computationally intensive operation is the computation of the transitive closure via Warshall’s algorithm, with worst-case complexity $O(n^3)$. This is expected, given that full connectivity propagation across all vertex pairs inherently requires cubic time in matrix-based implementations.
>
>All procedures comply with the constraint defined in US13 to utilise only primitive operations and avoid Java’s built-in graph libraries. The use of an adjacency matrix to model the railway network ensures direct access to graph structure and supports a rigorous analysis of computational costs.
---