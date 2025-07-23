# Worst-Case Time Complexity Analysis of Algorithms from US27

## Context and Objectives

>This report responds to the requirements of user story US28, which specifies the need to carry out an analysis of the worst-case time complexity of the algorithms implemented in US27.
>
>US27 defines that, as a player, I want to obtain one of the shortest paths between two stations. This path depends on the topology of the railway network, the types of stations and the weight matrix that represents the distances.
>
>According to Acceptance Criterion AC01 of US28, the algorithms must be presented in pseudocode and accompanied by an exhaustive worst-case complexity analysis.

---

## Key Algorithms Identified in US27

Upon reviewing the code implemented for US27, four core algorithmic procedures have been identified:

>1. **Construction of the Weight Matrix**  
>Implemented via the `createWeightMatrix()` method, this procedure builds an $n \times n$ adjacency matrix representing the distances between stations. For each valid railway line, the method locates the origin and destination indices within the station list using linear search and inserts the corresponding weight symmetrically in the matrix (since the network is undirected).

>2. **Shortest Path Computation Using Dijkstra’s Algorithm**
>The method `dijkstra()` computes the shortest path between two stations using Dijkstra's algorithm with a matrix-based graph representation. The implementation uses linear search to select the unvisited node with the minimum tentative distance and updates paths through relaxation. No advanced data structures (e.g., heaps) are employed, in compliance with US28 constraints.

>3. **Path Distance Calculation**  
>The method `calculatePathDistance()` computes the total distance of a given path by iterating through each pair of consecutive stations and scanning the full list of railway lines to identify the matching connection. This brute-force approach can be expensive for long paths and dense graphs.

>4. **Best Path Calculation with Intermediate Stations**  
>Implemented by `findBestPath()`, this method determines a complete route through a set of ordered stations (start, intermediates, and end). For each pair of consecutive stations, it invokes Dijkstra’s algorithm and calculates the subpath distance. All subpaths are concatenated, skipping overlaps when needed, and the full path and total distance are stored.

Each of these procedures is presented below using pseudocode, along with a formal justification of its worst-case time complexity.

---

# 1. Construction of the Weight Matrix

## Pseudocode
```plaintext
procedure createWeightMatrix(stationsList, railwayLines)
    n := size of stationsList
    matrix := n x n matrix initialized with null

    for each line in railwayLines:
        index1 := position of line.station1 in stationsList
        index2 := position of line.station2 in stationsList

        if index1 ≠ -1 and index2 ≠ -1:
            matrix[index1][index2] := line.distance
            matrix[index2][index1] := line.distance  // undirected graph

    return matrix
```
---

## Complexity Analysis and Justification


### 1. `n := size of stationsList`
Constant operation: $\mathcal{O}(1)$

### 2. `matrix := n × n matrix initialized with null`
Creating and initializing an $n \times n$ matrix takes time proportional to $n^2$.

Complexity: $\mathcal{O}(n^2)$

### 3. `for each line in railwayLines:`
Let $m$ be the number of elements in `railwayLines`, i.e., $m = |\text{railwayLines}|$.

For each `line`, we perform:

- **(a)** `index1 := position of line.station1 in stationsList`
- **(b)** `index2 := position of line.station2 in stationsList`

Searching for the position of an element in a list without order or structure requires a linear search in the worst case.

Complexity of each search: $\mathcal{O}(n)$

Two searches per iteration: $\mathcal{O}(2n) = \mathcal{O}(n)$

- **(c)** Matrix assignments (if indices are valid)

Assignments such as `matrix[index1][index2]` and `matrix[index2][index1]` are constant time operations: $\mathcal{O}(1)$

So, each iteration of the loop has a cost: $\mathcal{O}(n)$

The loop runs $m$ times:

**Total loop complexity:** $\mathcal{O}(m \times n)$

---

### Total Worst-Case Time Complexity

Summing up the parts:

- Matrix initialization: $\mathcal{O}(n^2)$
- Iteration over `railwayLines`: $\mathcal{O}(m \times n)$

Thus, in the worst case, the time complexity of the algorithm is:

$$\mathcal{O}(n^2 + m \times n)$$

If $m$ is proportional to $n^2$ (as in a dense graph where nearly all possible connections exist), then:

$$
\mathcal{O}(n^2 + n^2 \times n) = \mathcal{O}(n^3)
$$

But in the general worst case (without assuming graph density), the answer is:

$$\mathcal{O}(n^2 + m \times n)$$

---

### Justification Based on Chapter 3



- Complexity is analyzed in terms of the number of elementary operations, such as assignments, comparisons, and searches.
- The worst-case analysis considers the maximum number of operations the algorithm may perform for a given input size, as discussed in the examples of search and sorting algorithms (e.g., Examples 3.5 to 3.9).

By applying Theorems 3.3 and 3.4 regarding sums and products of functions, we conclude that:

 The total cost is the sum of $\mathcal{O}(n^2)$ (from matrix initialization) and $\mathcal{O}(m \times n)$ (from iterations and searches).

Therefore, using the rule of “selecting the dominant term,” we obtain:
$$ \mathcal{O}(n^2 + m \times n) $$


---

# 2. Dijkstra's Algorithm for Shortest Path

## Pseudocode
```plaintext
procedure dijkstra(weightMatrix, stations, start, end)
    n := number of stations

    source := index of start in stations
    target := index of end in stations

    dist[0..n-1] := array initialized with ∞
    visited[0..n-1] := array initialized with false
    prev[0..n-1] := array initialized with null

    dist[source] := 0

    while true:
        u := index of unvisited node with smallest dist[u]
        if u = -1 or u = target:
            break

        visited[u] := true

        for v from 0 to n - 1:
            if not visited[v] and weightMatrix[u][v] ≠ null:
                alt := dist[u] + weightMatrix[u][v]
                if alt < dist[v]:
                    dist[v] := alt
                    prev[v] := u

    return reconstructPath(prev, source, target)

```

## Complexity Analysis and Justification

### 1. Initializations

- `n := number of stations`: $\mathcal{O}(1)$
- `source := index of start in stations`: linear search → $\mathcal{O}(n)$
- `target := index of end in stations`: same → $\mathcal{O}(n)$
- `dist[0..n-1] := ∞`, `visited[0..n-1] := false`, `prev[0..n-1] := null`: each takes $\mathcal{O}(n)$ → total $\mathcal{O}(n)$

 **Subtotal:** $\mathcal{O}(n) + \mathcal{O}(n) + \mathcal{O}(n) + \mathcal{O}(n) = \mathcal{O}(n)$


### 2. Main Loop: `while true`

In each iteration, the node `u` with the smallest `dist[u]` that has not been visited is selected.

Worst case: the loop goes through all nodes → up to $n$ iterations.

- **(a)**`u := index of unvisited node with smallest dist[u]`

Scans the `dist[0..n-1]` array → $\mathcal{O}(n)$


-  **(b)** `for v = 0 to n - 1`

Loops through all nodes `v` to check neighbors and relax distances.

Within the loop:

- Check `visited[v]`: $\mathcal{O}(1)$
- Check `weightMatrix[u][v] ≠ null`: $\mathcal{O}(1)$
- Arithmetic + comparison + possible update: $\mathcal{O}(1)$

→ $\mathcal{O}(1)$ per inner iteration × $n$ iterations → $\mathcal{O}(n)$

Since the `for` loop is inside the `while`, and the `while` runs up to $n$ times:

**Total cost of the two loops:** $\mathcal{O}(n \times n) = \mathcal{O}(n^2)$


### 3. `reconstructPath(prev, source, target)`

Reconstructs the path from destination to source by traversing predecessors.

In the worst case, the path may contain up to $n$ nodes → $\mathcal{O}(n)$

---

### Total Worst-Case Time Complexity

Summing up:

- **Initializations:** $\mathcal{O}(n)$
- **Main loop:** $\mathcal{O}(n^2)$
- **Path reconstruction:** $\mathcal{O}(n)$
 
**Total time complexity:** $\mathcal{O}(n^2)$

---

### Justification Based on Chapter 3

According to Chapter 3:

 **Relevant Definitions:**

- **Time complexity**: number of primitive operations as a function of input size $n$.
- Worst-case analysis ignores constants and lower-order terms (see Theorems 3.2 and 3.3).
- Primitive operations include: reads, assignments, comparisons, arithmetic operations, and method calls.

 **Application of Theorems:**

- The cost of `for v from 0 to n - 1` is a product between number of iterations and internal cost (Theorem 3.4):  
  $\mathcal{O}(n \times 1) = \mathcal{O}(n)$

- The outer `while` loop with up to $n$ iterations and internal cost $\mathcal{O}(n)$ gives:  
  $\mathcal{O}(n^2)$ (Theorem 3.4)

- The total sum of all these operations results in an asymptotic complexity dominated by $\mathcal{O}(n^2)$ (Theorem 3.3).

---

### Conclusion:

The worst-case time complexity of the Dijkstra algorithm with an adjacency matrix, as described in the pseudocode, is:

> ${\mathcal{O}(n^2)}$

This is consistent with asymptotic analysis principles discussed in Chapter 3 and follows best practices: consider primitive operations, ignore constants, and focus on growth with input size $n$.

---


# 3. Calculate Path Distance

## Pseudocode
```plaintext
procedure calculatePathDistance(path, lines)
    total := 0

    for i from 0 to length(path) - 2:
        s1 := path[i]
        s2 := path[i + 1]

        for each line in lines:
            if (line.station1.name = s1.name and line.station2.name = s2.name) or
               (line.station1.name = s2.name and line.station2.name = s1.name):
                total := total + line.distance
                break

    return total
```
---

## Complexity Analysis and Justification

Let:
- $ p $: length of the path
- $ m $: number of elements in `lines`

### 1. `total := 0`
Constant-time operation → $ \mathcal{O}(1) $

### 2. Loop `for i from 0 to length(path) - 2`
This loop runs $ p - 1 $ iterations ⇒ $ \mathcal{O}(p) $

Inside each iteration:

- **(a)** `s1 := path[i]`, `s2 := path[i + 1]` → $ \mathcal{O}(1) $ each
- **(b)** `for each line in lines:`
  - In the worst case, it traverses all $ m $ elements in `lines` (e.g., if the target line is at the end or does not exist).
  - Name comparisons (station pairs): assumed to be $ \mathcal{O}(1) $ each
  - Assignment to `total`: $ \mathcal{O}(1) $
  - `break` — in the worst case may not occur until the end

⇒ $ \mathcal{O}(m) $ in the worst case per iteration of `i`

**Total accumulated cost of the loop**

$
(p - 1) \times \mathcal{O}(m) = \mathcal{O}(p \times m)
$

### 3. `return total`
Constant-time operation → $ \mathcal{O}(1) $

---

### Total Worst-Case Time Complexity

Adding all components:

$$
\mathcal{O}(1) + \mathcal{O}(p \times m) + \mathcal{O}(1) = \mathcal{O}(p \times m)
$$

---

### Justification Based on Chapter 3

**Fundamentals:**

- The analysis focuses on the number of primitive operations (assignments, comparisons, etc.)
- Big-O notation expresses asymptotic behavior in the worst case
- The cost of a `for` loop is the product of the number of iterations and the internal cost (see Theorem 3.4)
- Sum of partial costs follows Theorem 3.3: $ \mathcal{O}(f + g) = \mathcal{O}(\max\{f, g\}) $

**Application:**
- The main loop has cost $ \mathcal{O}(p) $
- The nested loop has cost $ \mathcal{O}(m) $
- Product of both: $ \mathcal{O}(p \times m) $
- Constants and minor factors (e.g., `+1`, `break`, etc.) are ignored as recommended

---

### Conclusion

The worst-case time complexity of the `calculatePathDistance` algorithm is:

> $${\mathcal{O}(p \times m)}$$

where $ p $ is the number of stations in the path and $ m $ is the total number of rail lines.

If you would like a more efficient solution (e.g., using a hash map for $ \mathcal{O}(1) $ access), I can also suggest optimizations.


# 4. Calculate best

## Pseudocode
```plaintext
procedure findBestPath()
    orderedStations := empty list
    append startStation to orderedStations
    append all intermediateStations to orderedStations
    append finalStation to orderedStations

    currentPath := empty list
    valid := true
    totalDistance := 0

    for i from 0 to length(orderedStations) - 2:
        from := orderedStations[i]
        to := orderedStations[i + 1]

        subPath := dijkstra(stationsList, filteredLines, from, to)

        if subPath is empty:
            valid := false
            break

        totalDistance := totalDistance + calculatePathDistance(subPath, filteredLines)

        if i > 0:
            remove first element from subPath

        append all elements of subPath to currentPath

    if valid:
        path := currentPath
        lengthOfPath := totalDistance
    else:
        clear path
        lengthOfPath := 0

```

---
## Complexity Analysis and Justification

Let:

- \( k = \) total number of stations in the path (start + intermediates + end)
- \( n = \) total number of stations in `stationsList`
- \( m = \) number of railway connections (in `filteredLines`)


### 1. Initialization of lists and variables

```plaintext
orderedStations := empty list
append startStation, all intermediateStations, finalStation
currentPath := empty list
valid := true
totalDistance := 0
```

Each append and initialization operation is $ O(1) $.  
Assuming there are $ k $ stations, we have $ O(k) $ insertions \rightarrow \ O(k) \)


### 2. Main loop: `for \( i \) from 0 to \( k - 2 \)`

This loop has \( k - 1 \) iterations, and in each one several operations are executed:

- **(a)** $ \text{subPath} := \text{dijkstra}(\ldots) $  
Dijkstra's algorithm, based on an adjacency matrix, has worst-case complexity $ O(n^2) $.

- **(b)** $ \text{calculatePathDistance}(\text{subPath}, \text{filteredLines}) $  
This algorithm goes through `subPath`, and for each pair of stations it checks the respective line in `filteredLines`.  
If $ p $ is the length of `subPath` (worst case up to $ n $), we have:  
For each pair, it scans $ m $ lines $ \rightarrow  O(p \times m) $  
In the worst case: $ O(n \times m) $

- **(c)** Remove first element from `subPath` $ \rightarrow  O(1) $ 
- **(d)** Append all elements of `subPath` to `currentPath` $ \rightarrow  O(n) $ worst case

---

### Total per iteration of the loop:

$$
O(n^2) \quad \text{(Dijkstra)} + O(n \times m) \quad \text{(calculatePathDistance)} + O(n) \quad \text{(append)}
$$

$$
\Rightarrow O(n^2 + n \times m + n) = O(n^2 + n \times m)
$$

---

### Total for all $ k - 1 $ iterations:

$$
O((k - 1) \times (n^2 + n \times m)) = O(k \times (n^2 + n \times m))
$$

---

### 3. Final phase: check `valid` and assignments

```plaintext
if valid:
    path := currentPath
    lengthOfPath := totalDistance
else:
    clear path
    lengthOfPath := 0
```

Simple assignment and clearing operations rightarrow $ O(n) $ at most.

---

### Total Worst-Case Time Complexity

The dominant part is the loop involving calls to Dijkstra and `calculatePathDistance`. Therefore:

$$
{
\text{Total complexity (worst case)} = O(k \times (n^2 + n \times m))
}
$$

If we consider the case where the number of stations in the path $ k $ is proportional to $ n $ (e.g., passing through almost all):

$$
\Rightarrow O(n^3 + n^2 \times m)
$$

---

### Justification Based on Chapter 3

**According to Chapter 3:**

- The complexity of an algorithm is estimated based on the number of primitive operations (assignments, comparisons, loops) performed as a function of the input size.
- We use Big-O notation to express the asymptotic behavior of this operation count function (Definition of Big-O notation).
- Sum of functions: we use Theorem 3.3, which states that the sum of two functions is dominated by the larger one.
- Product of functions: Theorem 3.4 applies to the case of a for loop with variable internal cost (Dijkstra and distance calculation), resulting in a product between $ k $ and the internal cost.

---

### Conclusion

The worst-case time complexity of the `findBestPath()` algorithm is:

>$$
O(k \times (n^2 + n \times m))
$$

and assuming $ k \approx n $:

>$$
{
O(n^3 + n^2 \times m)
}
$$

This is based on the rules and definitions presented in Chapter 3 on asymptotic analysis of algorithms.

---

## Final Table: Primitive Operation Count for US27 Algorithms

| Algorithm / Procedure       | Phase / Block                                     | Primitive Operations (per iteration)                       | Worst-Case Iterations        | Total Cost Function        | Asymptotic Complexity | Justification (Chapter 3 Theorems)                                                                                                                                      |
|----------------------------|---------------------------------------------------|-------------------------------------------------------------|-------------------------------|-----------------------------|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **1. createWeightMatrix**  | A: Matrix initialization                          | 1 assignment per cell                                       | $n \times n = n^2$            | $f_1(n) = n^2$              | $\mathcal{O}(n^2)$     | Theorem 3.2 — Polynomial function: $n^2$ assignments.                                                                                                                  |
|                            | B: Index lookup and matrix update per line        | 2 linear searches ($\mathcal{O}(n)$) + 2 assignments         | $m$ lines                     | $f_2(n, m) = m \cdot n$     | $\mathcal{O}(m \cdot n)$ | Theorem 3.4 — Product: each line → $\mathcal{O}(n)$ cost × $m$.                                                                                                       |
|                            | **Total**                                         | —                                                           | —                             | $f(n, m) = n^2 + m \cdot n$ | $\mathcal{O}(n^2 + mn)$ | Theorem 3.3 — Sum of terms: dominant is $\max\{n^2, m \cdot n\}$.                                                                                                     |
| **2. dijkstra**            | A: Initialization and index lookup                | $\mathcal{O}(n)$ per array + 2 linear searches              | —                             | $f_1(n) = 4n$               | $\mathcal{O}(n)$       | Theorem 3.2 — Each step is linear.                                                                                                                                     |
|                            | B: Main loop (while + inner for)                  | 1 min search ($n$) + loop over all $v$ ($n$): $n + n$        | $n$ times                     | $f_2(n) = n \cdot (n + n)$  | $\mathcal{O}(n^2)$     | Theorem 3.4 — Nested loop: $\mathcal{O}(n) \cdot \mathcal{O}(n) = \mathcal{O}(n^2)$.                                                                                   |
|                            | C: Path reconstruction                            | Follow path of length $\leq n$                              | $\leq n$                      | $f_3(n) = n$                | $\mathcal{O}(n)$       | Theorem 3.2 — Linear traversal.                                                                                                                                        |
|                            | **Total**                                         | —                                                           | —                             | $f(n) = n^2 + 5n$           | $\mathcal{O}(n^2)$     | Theorem 3.3 — Dominant term: $n^2$.                                                                                                                                    |
| **3. calculatePathDistance** | A: Loop over pairs of stations                    | Search in lines (linear in $m$)                             | $p - 1$ station pairs         | $f(p, m) = p \cdot m$       | $\mathcal{O}(p \cdot m)$ | Theorem 3.4 — Nested loop: path pairs × line scan.                                                                                                                    |
| **4. findBestPath**        | A: Initialization of list                         | Assignments + list appends                                  | $k$ stations                  | $f_1(k) = k$                | $\mathcal{O}(k)$       | Theorem 3.2 — Each operation constant.                                                                                                                                |
|                            | B: Loop over station pairs (calls dijkstra + calc) | $O(n^2 + n \cdot m)$ per iteration                          | $k - 1$                       | $f_2(n, m, k) = k \cdot (n^2 + n \cdot m)$ | $\mathcal{O}(k(n^2 + n \cdot m))$ | Theorem 3.4 — Product rule.                                                                                                  |
|                            | C: Final assignments                              | Assign / clear list / integer                               | —                             | $\mathcal{O}(n)$            | $\mathcal{O}(n)$       | Theorem 3.2 — Constant per element.                                                                                                                                    |
|                            | **Total**                                         | —                                                           | —                             | $f(n, m, k) = k(n^2 + n \cdot m) + n$ | $\mathcal{O}(k(n^2 + n \cdot m))$ | Theorem 3.3 — Dominant term is product of $k$ with Dijkstra and distance cost.                                                   |
--- 

## Final Considerations

> The analysis of the algorithms implemented for **US27** shows that the most computationally intensive operation is the construction of the weight matrix, particularly when the railway network is dense (i.e., when the number of railway lines $ m $ approaches $ n^2 $ ). This cost arises from the repeated linear searches for station indices during matrix construction, leading to a worst-case time complexity of $ \mathcal{O}(n^2 + m \times n) $.
>
> Additionally, both the Dijkstra algorithm and the weight matrix formatting** method exhibit worst-case time complexity $ \mathcal{O}(n^2) $, primarily due to the use of an adjacency matrix and nested iteration over the station list. The `findBestPath()` method compounds these costs by repeatedly invoking both Dijkstra and path distance calculation procedures—resulting in a worst-case complexity of $ \mathcal{O}(k \cdot (n^2 + n \cdot m)) $, where $ k $ is the number of station segments to visit.
>
> All procedures comply with the constraint defined in US28 to utilise only primitive operations and avoid Java’s built-in graph libraries. 
