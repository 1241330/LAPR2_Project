# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

>The system requires all users to authenticate using an alphanumeric password with a minimum of seven characters, including at least three uppercase letters and two digits. Data persistence must be done by object serialisation so that data can be retained between successive runs of the system. Business rules must be correctly checked — for instance, map dimensions must be positive, and city names must not contain special characters. Besides, the system must provide basic reporting features, such as station, train, and cargo listings as outlined in user stories US07 and US11.

## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

>The system interface has to be uniform design and naming conventions based. It should also avoid user errors by providing correct and explanatory messages at all times whenever there are incorrect inputs — for example, in the event of overlap or mistake in station coordinates. For ease of comprehension and easy maintenance, the interface as well as the source code must be documented in English, adhering to the project's non-functional requirements.

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

>The system’s reliability will be ensured through data integrity, making certain that information is accurately maintained between sessions via serialisation. The system must also be resilient to unexpected failures, implementing mechanisms to prevent data corruption in case of abrupt termination.

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

>Regarding performance, critical operations such as loading maps and starting simulations must have efficient response times.

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

>To ensure testability, the development team must implement unit tests for all methods, except for methods that implement input/output operations. Unit tests should be implemented using the JUnit 5 framework and the JaCoCo plugin should be used to generate the coverage report.
>
>To ensure maintainability and adaptability, the class structure should be designed to allow easy maintenance and the addition of new functionalities - such as new types of industries - following object-orientation (OO) best practices.

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

>The system must be developed in Java, as required by the project. The development process will follow SCRUM, with four-week sprints. Tools such as GitHub will be used for version control, and SVG format will be used for images.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

>The application must be developed in the Java language.
>
>All images/figures produced during the software development process must be registered in SVG format.
>
>Adopt the best practices for identifying requirements and analysing and designing OO software.
>
>The class structure should be designed in such a way as to allow easy maintenance and the addition of new functionalities, following the best practices of object-orientation (OO).
>
>Adopt recognised coding standards (e.g. CamelCase).
Use Javadoc to generate useful documentation for Java code.

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

>The interface must be developed in JavaFX.

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

>No specific physical constraints are imposed for now.