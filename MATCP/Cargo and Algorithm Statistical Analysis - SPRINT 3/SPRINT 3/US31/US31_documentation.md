# US31

> **As a Player, using the same data file as in the previous Sprint (Sprint 2), I want to perform a statistical analysis to determine which type of cargo contributes most significantly to the revenue of a given station, in a specific year, both defined by the user. The goal for the specified station/year is to identify the cargo (independent variable) that shows the highest correlation with revenue (dependent variable). Based on the selected cargo, a linear regression model should be applied, presenting the error associated with the respective fit. Assuming the amount of cargo increased by 10% in January of the following year, estimate the expected revenue for January with a 95% confidence level.**

## 1. Theoretical Concepts
### **Simple Linear Regression and Pearson Correlation**

### 1.1 Pearson Correlation Coefficient

The **linear correlation** between two variables, $X$ and $Y$, is given by:

$$
r = \frac{S_{xy}}{\sqrt{S_{xx} \cdot S_{yy}}}
$$

Where:

- The sum of squares of deviations in $x$:
  $$
  S_{xx} = \sum (x_i - \bar{x})^2
  $$

- The sum of squares of deviations in $y$:
  $$
  S_{yy} = \sum (y_i - \bar{y})^2
  $$

- The sum of cross-products of deviations:
  $$
  S_{xy} = \sum (x_i - \bar{x})(y_i - \bar{y})
  $$

The **coefficient of determination** is the square of the Pearson correlation coefficient:

$$
R^2 = r^2
$$

---
### 1.2. Interpretation of the Pearson Correlation Coefficient

| Correlation Coefficient ($r$) | Strength of Association | Direction     |
|------------------------------|--------------------------|---------------|
| $1.0$                        | Perfect                  | Positive      |
| $0.8 \le r < 1.0$            | Strong                   | Positive      |
| $0.5 \le r < 0.8$            | Moderate                 | Positive      |
| $0.1 \le r < 0.5$            | Weak                     | Positive      |
| $0.0 < r < 0.1$              | Very Weak                | Positive      |
| $0.0$                        | None                     | None          |
| $-0.1 < r < 0.0$             | Very Weak                | Negative      |
| $-0.5 < r \le -0.1$          | Weak                     | Negative      |
| $-0.8 < r \le -0.5$          | Moderate                 | Negative      |
| $-1.0 < r \le -0.8$          | Strong                   | Negative      |
| $-1.0$                       | Perfect                  | Negative      |


---

### 1.3. Simple Linear Regression

The simple linear regression model estimates the relationship between a dependent variable $Y$ and an independent variable $X$ as:

$$
\hat{y} = \hat{\beta}_0 + \hat{\beta}_1 x
$$

#### Estimation of Regression Coefficients

- Slope (rate of change in $Y$ with respect to $X$):
  $$
  \hat{\beta}_1 = \frac{S_{xy}}{S_{xx}}
  $$

- Intercept (value of $Y$ when $X = 0$):
  $$
  \hat{\beta}_0 = \bar{y} - \hat{\beta}_1 \bar{x}
  $$

---

### 1.4. Residual Sum of Squares and Standard Error

- **Residual sum of squares (SSE):**

$$
SSE = \sum (y_i - \hat{y}_i)^2 = S_{yy} - \frac{S_{xy}^2}{S_{xx}}
$$

- **Residual standard error** (standard deviation of residuals):

$$
s = \sqrt{\frac{SSE}{n - 2}}
$$

---

### 1.5. Point Estimate for a Future Observation

To predict a future value $y$ at a specific $x_p$:

$$
\hat{y}_0 = \hat{\beta}_0 + \hat{\beta}_1 x_p
$$

---

### 1.6. Confidence Interval for the Mean Response

The confidence interval for the **expected value of $Y$** when $X = x_p$ is:

$$
CI = \hat{y}_0 \pm t_{1 - \frac{\alpha}{2}} \cdot s \cdot \sqrt{\frac{1}{n} + \frac{(x_p - \bar{x})^2}{S_{xx}}}
$$

---

### 1.7. Prediction Interval for a New Observation

The prediction interval for an **individual future observation** at $x_p$ is:

$$
PI = \hat{y}_0 \pm t_{1 - \frac{\alpha}{2}} \cdot s \cdot \sqrt{1 + \frac{1}{n} + \frac{(x_p - \bar{x})^2}{S_{xx}}}
$$

---

### 1.8. Probabilistic Regression Model

The regression model can be interpreted as a combination of a deterministic and a random component:

$$
Y = \beta_0 + \beta_1 x + \varepsilon
$$

Where:

- The **expected value** of $Y$ given $x$ is:
  $$
  E(Y|x) = \beta_0 + \beta_1 x
  $$

- The **random error term**, $\varepsilon$, is assumed to be normally distributed:
  $$
  \varepsilon \sim N(0, \sigma^2)
  $$

---

### 1.9. Assumptions of the Simple Linear Regression Model

- For each fixed value of $x$, the response variable $Y$ is normally distributed:
  $$
  Y|x \sim N(\beta_0 + \beta_1 x, \sigma^2)
  $$

- The variance $\sigma^2$ is constant (homoscedasticity).
- The observations are independent.
- The error terms $\varepsilon$ are mutually independent and identically distributed.

---

# 2. Analysis and Interpretation of Results – Simple Linear Regression: US31


The graph produced by the implementation of **US31** represents the relationship between the volume of a specific **cargo type** (predictor variable \( x \)) and the **total revenue** (response variable \( y \)) for a given station and year. A **simple linear regression** model is fitted to this data using the **least squares method**, following the standard methodology presented in Chapter 6 of the Computational Mathematics slides and the Discrete Mathematics textbook (Chapters 3 and 4 for asymptotic reasoning).


### 2.1. Graphical Representation and Nature of the Data

The scatter plot visually displays the distribution of observed data points $ (x_i, y_i) $, where $ x_i $ is the monthly volume of the selected cargo (e.g., **Iron**) and $ y_i $ is the corresponding monthly revenue. The **regression line**, defined by the equation:

$$
\hat{y} = \hat{\beta}_0 + \hat{\beta}_1 x
$$

represents the best linear estimate of the expected revenue given a certain volume of cargo. The closer the data points are to this line, the stronger the linear relationship between the variables.

---

### 2.2. Interpretation of the Linear Fit – US31: Frankfurt (2016)

For the case selected in the run — **Station: Frankfurt**, **Year: 2016**, and **Cargo: Iron**, the regression output revealed:

- **Pearson Correlation Coefficient**:  
  $$
  r \approx 0.5683
  $$  
  According to standard statistical interpretation, this represents a **moderate positive linear correlation** $ ( 0.5 \leq r < 0.8 ) $.

- **Coefficient of Determination**:
  $$
  r^2 \approx 0.3229
  $$ 
  This indicates that **only 32.29\% of the variance in revenue** is explained by the linear model.

- **Regression Equation**:
  $$
  \hat{y} = 989.6334 + 0.1197x
  $$
  - $ \hat{\beta}_0 = 989.63 $ baseline revenue.
  - $ \hat{\beta}_1 = 0.1197 $: for each additional unit of cargo, revenue increases by approximately **€0.12 million**.

- **Residual Standard Error**:
  $$
  s \approx 173.57
  $$

---

### 2.3. Forecast Interpretation – January Projection

Assuming a 10\% increase in January cargo volume:

$$
\hat{y}_{\text{January}} = \hat{\beta}_0 + \hat{\beta}_1 \cdot (1.10 \cdot x_{\text{January}})
$$

Forecast:

- Estimated revenue: $ \( \hat{y} \approx €1085.11 \) $ million
- 95\% Confidence Interval: $ [€676.43,\; €1293.80] $ million

---

### 2.4. Summary and Asymptotic Insight

Although a linear regression was applied, the **low $ r^2 $** value and **high residual dispersion** suggest that **Iron cargo volume alone is insufficient** to explain most of the variation in revenue.

From a computational complexity perspective:

- Regression fitting is done in $ \mathcal{O}(n) $, efficient and fast.
- Predictive power is limited; further model refinement is advised.

---