# US30

> **US30 - As a Product Owner, I want to obtain the best-fitting curve for the previous sample, and see it graphically.**
> - **AC01: The fitted curve should align with the reference function obtained from the theoretical analysis of the procedure’s complexity.**

---
## 1. Theoretical Concepts
### **Simple Linear Regression and Empirical Analysis — US30**


### 2. Pearson Correlation Coefficient

The **linear correlation** between input size \( x \) and execution time \( y \) is given by:

$$
r = \frac{S_{xy}}{\sqrt{S_{xx} \cdot S_{yy}}}
$$

Where:

- $S_{xx}$: sum of squares of deviations in \( x \)
  $$
  S_{xx} = \sum (x_i - \bar{x})^2
  $$

- $S_{yy}$: sum of squares of deviations in \( y \)
  $$
  S_{yy} = \sum (y_i - \bar{y})^2
  $$

- $S_{xy}$: sum of the cross-products
  $$
  S_{xy} = \sum (x_i - \bar{x})(y_i - \bar{y})
  $$

The **coefficient of determination** \( R^2 \), which quantifies how well the regression explains the variability in the data, is:

$$
R^2 = r^2
$$

---

### 3. Simple Linear Regression Model

The simple linear regression model is:

$$
\hat{y} = \hat{\beta}_0 + \hat{\beta}_1 x
$$

Where:
- \( \hat{\beta}_1 \): estimated slope (increase in time per unit of input),
- \( \hat{\beta}_0 \): estimated intercept (base execution time).

#### Coefficient Estimates:

- Slope:
  $$
  \hat{\beta}_1 = \frac{S_{xy}}{S_{xx}}
  $$

- Intercept:
  $$
  \hat{\beta}_0 = \bar{y} - \hat{\beta}_1 \bar{x}
  $$

---

### 4. Residual Analysis

#### Residual Sum of Squares (SSE):
Measures the total squared error between observed and predicted values:

$$
SSE = \sum (y_i - \hat{y}_i)^2 = S_{yy} - \frac{S_{xy}^2}{S_{xx}}
$$

#### Residual Standard Error:
The estimated standard deviation of the residuals:

$$
s = \sqrt{\frac{SSE}{n - 2}}
$$

---

### 5. Confidence Intervals for Model Parameters

To express uncertainty in the estimates \( \hat{\beta}_0 \) and \( \hat{\beta}_1 \), we use **confidence intervals** at a 95% level:

#### Standard Errors:

- For the slope:
  $$
  SE_{\hat{\beta}_1} = \frac{s}{\sqrt{S_{xx}}}
  $$

- For the intercept:
  $$
  SE_{\hat{\beta}_0} = s \cdot \sqrt{ \frac{1}{n} + \frac{\bar{x}^2}{S_{xx}} }
  $$

#### 95% Confidence Intervals:

- For the slope:
  $$
  CI_{\hat{\beta}_1} = \hat{\beta}_1 \pm t_{1 - \frac{\alpha}{2},\, n-2} \cdot SE_{\hat{\beta}_1}
  $$

- For the intercept:
  $$
  CI_{\hat{\beta}_0} = \hat{\beta}_0 \pm t_{1 - \frac{\alpha}{2},\, n-2} \cdot SE_{\hat{\beta}_0}
  $$

Where \( t_{1 - \frac{\alpha}{2},\, n-2} \) is the critical value from the **Student’s t-distribution**.

---

### 6. Prediction for Future Observations

#### Point Estimate at a Specific Input \( x_p \):

$$
\hat{y}_0 = \hat{\beta}_0 + \hat{\beta}_1 x_p
$$

---

### 7. Confidence Interval for the Mean Response at \( x_p \)

This interval estimates the expected value of \( y \) at a given \( x_p \):

$$
CI = \hat{y}_0 \pm t_{1 - \frac{\alpha}{2},\, n-2} \cdot s \cdot \sqrt{ \frac{1}{n} + \frac{(x_p - \bar{x})^2}{S_{xx}} }
$$

---

### 8. Prediction Interval for a New Observation at \( x_p \)

This interval estimates the range where a **single future** observation of \( y \) might fall:

$$
PI = \hat{y}_0 \pm t_{1 - \frac{\alpha}{2},\, n-2} \cdot s \cdot \sqrt{ 1 + \frac{1}{n} + \frac{(x_p - \bar{x})^2}{S_{xx}} }
$$

---

### 9. Probabilistic Interpretation of the Regression Model

The linear regression model can be written as:

$$
Y = \beta_0 + \beta_1 x + \varepsilon
$$

Where:
- \( E(Y|x) = \beta_0 + \beta_1 x \) is the deterministic part,
- \( \varepsilon \sim N(0, \sigma^2) \) is the random error term.

---

### 10. Model Assumptions

- Linearity: The relationship between \( x \) and \( y \) is linear.
- Independence: Observations are independent of each other.
- Homoscedasticity: The variance of residuals is constant.
- Normality: The residuals are normally distributed:
  $$
  Y|x \sim N(\beta_0 + \beta_1 x, \sigma^2)
  $$

---

### 11. Conclusion for US30

- A **positive and significant** \( \hat{\beta}_1 \) suggests that execution time grows with input size.
- A **high \( R^2 \)** indicates a strong linear relationship.
- **Confidence intervals** help quantify uncertainty in slope and intercept estimates.
- **Prediction intervals** offer insight into the variability of individual execution times.

This empirical approach helps validate whether the algorithms' real-world performance aligns with their theoretical complexity.


---
## 2. Justification for the choice of Regression for the US13 and US14 graphs

The regression model for each data set (US13 and US14) was chosen based on the coefficient of determination (R²), which measures how well the model fits the data. The closer the R² value is to 1, the better the model explains the variability of the data.

### US13 - Quadratic Regression (R² = 0.8874)
**Justification:** 

The quadratic model had the highest R², indicating the best fit to the data of all the regressions tested (linear, logarithmic, exponential and n log n). Visually, the behaviour of the data also indicates accelerated growth, compatible with a second degree function.

**Comparison with other models:**
 - The n log n regression performed well (R² = 0.8357), suggesting that it would also be reasonable - but lower than the quadratic.


 - The linear model (R² = 0.8168) captures a general trend but not the obvious acceleration.


 - The exponential model had a negative R² (-0.8879), indicating a disastrous fit - worse than an average horizontal line.


### US14 - Quadratic Regression (R² = 0.6699)
**Justification:**

Although the R² value was relatively lower, the quadratic regression still fitted the data best. This suggests that the complexity of growth, although not as pronounced as in US13, can also be captured by a second-degree model.

**Comparison with other models:**
 - The n log n (R² = 0.6565) and linear (R² = 0.6462) models were almost equivalent in quality.


 - The exponential model again had a negative R² (-0.8285), confirming its inadequacy.


 - The low overall value of the coefficients of determination (R²) indicates more dispersion in the US14 data, which makes a precise fit difficult.

---

## 3. Analysing and Interpreting of Results

- The execution time of US14 is significantly lower than that of US13 for all input sizes. This can be seen directly in the values on the y-axis. The scale on the y-axis of US14's graph goes up to 0.04 seconds, while that of US13 goes up to 3.2 seconds.


- The growth in execution time in US13 is clearly non-linear, which indicates that the algorithm or structure of US13 has a significantly higher time complexity (approximately quadratic). The growth in US14, on the other hand, is more controlled, although it is also better described by a quadratic model.


- The R² values for US13 indicate that the data follows a predictable trend well, especially quadratically. In US14, the R² values are lower, which suggests that there is more variability in the data.


- The use of linear regression to model the performance data of the US13 and US14 algorithms proves to be methodologically inadequate due to the non-linear nature of the relationship between the number of stations and execution time. Linear regression assumes a similar relationship between the variables - that is, equal variations in *x* (number of stations) result in constant variations in *y* (execution time). However, the observed data, particularly from US13, shows a pattern of accelerated growth, reflected in an increasing curvature in the graphs and in time values that increase disproportionately in relation to the number of stations.


- This behaviour is typical of algorithms with superlinear complexity, such as *O(n²)* or *O(n log n)*, in contrast to the *O(n)* implicit in linear regression. The inadequacy of linearity is further quantified by the coefficient of determination *R²*, which measures the proportion of the variance in the data explained by the model. In the US13 data, linear regression achieves *R² = 0.8168*, while quadratic regression achieves *R² = 0.8874*, which represents a considerable improvement in the model's explanatory capacity. In US14, although the difference is more slight (Linear Regression: *R²=0.6462* and Quadratic Regression: *R²=0.6699*), the trend continues.


- Linear regression is not suitable for modelling the US13 and US14 data because it assumes constant growth between execution time and the number of stations.
  The graphs show accelerated growth, especially in US13, where the time increases rapidly with the size of the problem. Visually, the data shows an upward curvature, which is more compatible with quadratic or n log n models, which can better capture this acceleration.
  The R² values also reinforce this inadequacy: for US13, the linear regression obtained R²=0.8168, against 0.8874 for the quadratic regression. For US14, the linear regression had R²=0.6462, while the quadratic regression reached 0.6699. From an algorithmic point of view, a linear regression implies a complexity of O(n), when the data points to a behaviour closer to O(n²) or O(n log n).
  Thus, using a linear regression leads to an incorrect assessment of the real computational cost.






