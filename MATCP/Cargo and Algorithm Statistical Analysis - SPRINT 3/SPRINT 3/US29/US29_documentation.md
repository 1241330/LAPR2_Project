# US29

>**As a Product Owner, I want to run tests for variable-sized inputs to observe graphically the asymptotic behavior of the execution time of the US13 and US14 procedures (excluding graphic visualization).**
>- **AC01: The graphic referring to the asymptotic behavior of the execution running time tests, as a function of the input size, should be presented in a time unit that allows to distinguish the running times of all tested examples (use any package, for example, Gnuplot).**

---
## 1. Theoretical Concepts

### 1.1 Population, Sample, and Statistics

As stated in Chapter 4:

> “Population is the set of all objects whose characteristics we want to study and sample is any finite subset of the population.”

In the context of US29:

- The **population** is the set of all possible execution times for the algorithm on any input.
- The **sample** consists of execution times measured experimentally for a finite set of scenarios, such as:
    - 250 stations → 27 ms (US13), 0 ms (US14)
    - 1500 stations → 2989 ms (US13), 20 ms (US14)

These observed values are treated as **realizations of random variables**, from which we compute **sample statistics**, such as the mean and standard deviation.


### 1.2 Sample Mean and Sample Variance

As per Chapter 4, the sample mean is given by:

$$\bar{X} = \frac{1}{n} \sum_{i=1}^{n} X_i$$

The sample variance is defined as:
$$S^2 = \frac{1}{n - 1} \sum_{i=1}^{n} (X_i - \bar{X})^2$$

And the sample standard deviation is:
$$S = \sqrt{S^2}$$


### 1.3 Central Limit Theorem (C.L.T)

The Central Limit Theorem (Chapter 4) states that if $X_1, X_2, ..., X_n$ are i.i.d. with finite mean and variance, then:

$$Z = \frac{\bar{X} - \mu_X}{\sigma_X / \sqrt{n}} \xrightarrow{D} N(0, 1)$$

Or equivalently:

$$\bar{X} \xrightarrow{D} N\left( \mu_X, \frac{\sigma^2_X}{n} \right)$$

This allows us to apply inferential reasoning based on sample means when $n \geq 30$.


### 2. Simple Linear Regression

The relationship between input size \( x \) (number of stations) and execution time \( y \) is modelled by the regression equation:

$$y = \beta_0 + \beta_1 x + \varepsilon$$

Where:

- $y$: response variable (execution time),
- $x$: explanatory variable (number of stations),
- $beta_0$: intercept (fixed time component),
- $beta_1$: slope (increase in time per station),
- $varepsilon$: random error.


### 2.1 Estimation of Coefficients – Method of Least Squares

As per Chapter 6, the coefficients are estimated using:

$$\hat{\beta}_1 = \frac{S_{xy}}{S_{xx}}$$
$$\quad \hat{\beta}_0 = \bar{y} - \hat{\beta}_1 \cdot \bar{x}$$

Where:

- The sum of squares of the deviations of $x$:

$$S_{xx} = \sum (x_i - \bar{x})^2$$

- The sum of squares of the deviations of $y$:

$$S_{yy} = \sum (y_i - \bar{y})^2$$

- The sum of the cross-products of deviations:

$$S_{xy} = \sum (x_i - \bar{x})(y_i - \bar{y})$$

- The sum of the squares of the results:

$$sqe = \sum_{i=1}^n (y_i - \hat{y}_i)^2 = S_{yy} - \frac{S_{xy}^2}{S_{xx}}$$

These formulas are used to compute the regression line that best fits the observed data.

### 2.2. Pearson’s sample correlation coefficient and coefficient of determination

> "The Pearson’s sample correlation coefficient, r, is a measure of the direction and degree with which two variables are linearly associated."

- Pearson’s sample correlation coefficient:
  $$r = \frac{S_{xy}}{\sqrt{S_{xx} \cdot S_{yy}}},  -1 ≤ r ≤ 1$$

- Coefficient of determination:
  $$r^2 = \left( \frac{S_{xy}}{\sqrt{S_{xx} \cdot S_{yy}}} \right)^2$$


- **Positive Correlation** (`r > 0`):  
  Indicates a positive linear association between variables. When the values ​​of one variable increase, there is a tendency for the other variable to increase.

- **Negative Correlation** (`r < 0`):  
  Indicates a negative linear association. When the values ​​of one variable increase, there is a tendency for the other to decrease.

- **Perfect Correlation** (`|r| = 1`):  
  Perfect linear correlation, where the sign (±) indicates the direction of the relationship.


- **Coefficient of Determination** (`r²`):
    - `r² = 1`: All observations are on the regression line (perfect fit).
    - `r² = 0`: The regression model is of no use.
    - `r²` Provides an estimate of the relative reduction in total variability due to the use of the regression model to predict the value of y.


#### Pearson Correlation Coefficient

| Correlation Coefficient (`r`) | Correlation Strength       | Direction   |
|-------------------------------|----------------------------|-------------|
| `1.0`                         | Perfect                    | Positive    |
| `0.8 ≤ r < 1.0`               | Strong                     | Positive    |
| `0.5 ≤ r < 0.8`               | Moderate                   | Positive    |
| `0.1 ≤ r < 0.5`               | Weak                       | Positive    |
| `0.0 < r < 0.1`               | Slight                     | Positive    |
| `0.0`                         | Zero                       | None        |
| `-0.1 < r < 0.0`              | Slight                     | Negative    |
| `-0.5 < r ≤ -0.1`             | Weak                       | Negative    |
| `-0.8 < r ≤ -0.5`             | Moderate                   | Negative    |
| `-1.0 < r ≤ -0.8`             | Strong                     | Negative    |
| `-1.0`                        | Perfect                    | Negative    |

### 2.3. Simple Linear Regression Model

To introduce uncertainty into the predicted result, as opposed to a deterministic model, we must adopt a **probabilistic mathematical model**.

In the probabilistic model, we state that the **mean or expected value of $Y$ for a given value of $x$**, represented by $E(Y|x)$, has a straight line as its graph. That is:

$$
E(Y|x) = \beta_0 + \beta_1 x
$$

For each value of $x$, the value of the random variable $Y$ varies randomly around the mean $E(Y|x)$. This can be expressed as:

$$
y = \underbrace{\beta_0 + \beta_1 x}_{\text{deterministic or regression component, } E(Y|x)} + \underbrace{E}_{\text{random or error component}}
$$

Here, $\beta_0$ and $\beta_1$ are the **regression coefficients**.


### 2.3.1 Probabilistic Model Assumptions

- For each given value of $x$, $Y$ has a **normal distribution**, with mean given by:
  $$E(Y|x) = \beta_0 + \beta_1 x$$

- And with **variance** $\sigma^2$:
  $$E \sim N(0, \sigma^2)$$

- Each value of $Y$ is **independent** of all others.

- Each value of the error term $E$ is **independent** of all others.

These assumptions allow us to construct **confidence intervals** and perform **hypothesis tests** for $\beta_0$, $\beta_1$, and $E(Y|x)$.

---
## 2. Calculation of Linear Regression – US13 and US14

### Introduction
The objective is to fit a simple linear regression line to the observed execution times for two distinct procedures (US13 and US14), as a function of the number of stations $x_i$ for each scenario. This modelling allows us to describe the asymptotic behaviour of each procedure, quantifying its growth in execution time as the number of stations increases.


### 1. Data Preparation
Let:

- $x_1, x_2, \ldots, x_n$ be the values of the number of stations for $n = 30$ scenarios.
- $y_1, y_2, \ldots, y_n$ be the corresponding execution times for either US13 or US14.

All pairs $x_i, y_i$ are experimental observations representing an independent and identically distributed (i.i.d.) random sample, as described in Chapter 4:

> “An independent and identically distributed (i.i.d.) random sample is one where the random variables are independent and share the same probability distribution.”


### 2. Calculation of Sample Means
As defined in Chapter 4:
$$\bar{x} = \frac{1}{30}\sum_{i=1}^{30} x_i, \quad \bar{y} = \frac{1}{30}\sum_{i=1}^{30} y_i$$

Using the data:
$$\sum x_i = 23\,250 \Rightarrow \bar{x} = \frac{23\,250}{30} = 775.0$$

$$\sum y_i^{(13)} = 28\,949 \Rightarrow \bar{y}_{13} = \frac{28\,949}{30} = 964.97$$

$$\sum y_i^{(14)} = 235 \Rightarrow \bar{y}_{14} = \frac{235}{30} = 7.83$$

These represent the average number of stations and the average execution time for each procedure.


### 3. Calculation of Sums of Squares
As per Chapter 6:

- Sum of squared deviations of $x$:
  $$S_{xx} = \sum_{i=1}^{30} (x_i - \bar{x})^2$$

- Sum of squared deviations of $y$:
  $$S_{yy} = \sum_{i=1}^{30} (y_i - \bar{y})^2$$

- Cross sum between $x$ and $y$:
  $$S_{xy} = \sum_{i=1}^{30} (x_i - \bar{x})(y_i - \bar{y})$$

Computed values:

$$S_{xx} = 5,\!618,\!750.0$$
- US13: $$S_{xy}^{(13)} = 12,\!016,\!525.0$$
  $$S_{yy}^{(13)} = 31,\!461,\!702.97$$

- US14: $$S_{xy}^{(14)} = 89,\!825.0$$
  $$S_{yy}^{(14)} = 2,\!222.17$$

### 4. Calculation of Pearson’s sample correlation coefficient and coefficient of determination

#### For US13:

- Pearson’s sample correlation coefficient:
  $$r_{(13)} = \frac{12,\!016,\!525.0}{\sqrt{5,\!618,\!750.0 \cdot 31,\!461,\!702.97}} \approx \frac{12,\!016,\!525.0}{\sqrt{1.767 \times 10^{14}}} \approx \frac{12,\!016,\!525.0}{13,\!290,\!295.7} \approx 0.9045$$


- Coefficient of determination:
  $$r^{2}_{(13)} = (0.9045)^2 \approx 0.8181$$



#### For US14:

- Pearson’s sample correlation coefficient:
  $$r_{(14)} = \frac{89,\!825.0}{\sqrt{5,\!618,\!750.0 \cdot 2,\!222.17}} \approx \frac{89,\!825.0}{111,\!732.4} \approx 0.804$$


- Coefficient of determination:
  $$r^{2}_{(14)} = (0.804)^2 \approx 0.646$$


### 5. Estimation of Regression Coefficients
As defined in Chapter 6 of the course material, the least squares estimators are:

$$\hat{\beta}_1 = \frac{S_{xy}}{S_{xx}}, \quad \hat{\beta}_0 = \bar{y} - \hat{\beta}_1 \cdot \bar{x}$$

#### For US13:

- Slope:

$$\hat{\beta}_1^{(13)} = \frac{12,\!016,\!525.0}{5,\!618,\!750.0} \approx 2.14$$

- Intercept:

$$\hat{\beta}_0^{(13)} = 964.97 - 2.14 \cdot 775.0 = -692.49$$

- Regression equation:

$$\hat{y}_{13} = -692.49 + 2.14x$$

#### For US14:

- Slope:

$$\hat{\beta}_1^{(14)} = \frac{89,\!825.0}{5,\!618,\!750.0} \approx 0.016$$

- Intercept:

$$\hat{\beta}_0^{(14)} = 7.83 - 0.016 \cdot 775.0 = -4.56$$

- Regression equation:

$$\hat{y}_{14} = -4.56 + 0.016x$$

### 6. Estimation of Predicted Values – $\hat{y}_i$

According to the simple linear regression model defined in Chapter 6, the value of the response variable $y_i$ is composed of a deterministic component and a random error term:

$$y_i = \hat{y}_i + e_i = \hat{\beta}_0 + \hat{\beta}_1 x_i + e_i$$

To estimate the predicted execution times $\hat{y}_i$ for each scenario, we substitute the observed values $x_i$ into the regression equations previously obtained:

#### For US13:
Using the regression line:
$$\hat{y}_{13} = -692.49 + 2.14x$$

The predicted values are:
$$\hat{y}_{13}^{(i)} = -692.49 + 2.14x_i \quad \text{for } i = 1, 2, \ldots, 30$$

#### For US14:
Using the regression line:
$$\hat{y}_{14} = -4.56 + 0.016x$$

The predicted values are:
$$\hat{y}_{14}^{(i)} = -4.56 + 0.016x_i \quad \text{for } i = 1, 2, \ldots, 30$$

These predicted values $\hat{y}_i$ represent the **expected execution time** under the linear model for each number of stations $x_i$, and can be compared with the measured values $y_i$ to analyse the goodness of fit and model validity.

---
## 4. Analysis and Interpretation of Results – Asymptotic Execution Time: US13 vs US14

The graph presented illustrates the relationship between the number of stations (predictor variable $x$) and the execution time (response variable $y$) for two distinct procedures: **US13** and **US14**. For each case, a simple linear regression model was fitted using the method of least squares, in accordance with the principles outlined in Chapter 6 of the Computational Mathematics powerpoints.

**4.1. Graphical Representation and Nature of the Data**

The scatter plot allows for a visual assessment of the behaviour of the observations $(x_i, y_i)$, complemented by the regression line corresponding to each procedure. These lines represent the deterministic component $\hat{y} = \hat{\beta}_0 + \hat{\beta}_1 x$, that is, the estimate of the expected value $E(Y|x)$ of the random variable $Y$, for each value of the predictor variable $x$.

**4.2. Interpretation of the Linear Fit – US13**

In the case of **US13**, there is a clear linear growth trend between the number of stations and execution time, evidenced by the positive slope of the regression line. This behaviour is supported by:

- A high sample Pearson correlation coefficient ($r \approx 0.9045$), indicating a **strong positive linear correlation**, as per the classification in the textbook ($0.8 \leq r < 1$).

- A coefficient of determination $r^2 \approx 0.817$, which reveals that **81.7% of the total variability in the response variable** is explained by the fitted linear regression model.

The density of points around the regression line and the steep slope confirm that procedure US13 exhibits an execution time that is strongly dependent on the number of stations, suggesting that its asymptotic behaviour is linearly increasing, with a tendency to worsen as $x$ increases.

**4.3. Interpretation of the Linear Fit – US14**

In contrast, **US14** displays a nearly horizontal regression line, indicating a very weak association between the number of stations and execution time. This behaviour is reflected by:

- A Pearson correlation coefficient $r \approx 0.804$, which still corresponds to a **strong positive correlation**, albeit weaker than that observed for US13.

- A coefficient of determination $r^2 \approx 0.646$, indicating that **only 64.6% of the variability in execution times** is explained by the linear regression.

Visually, there is a concentration of points with minimal vertical dispersion, located near the horizontal axis, suggesting that the execution time remains almost constant as $x$ increases. Thus, the asymptotic behaviour of US14 can be characterised as approximately constant.

