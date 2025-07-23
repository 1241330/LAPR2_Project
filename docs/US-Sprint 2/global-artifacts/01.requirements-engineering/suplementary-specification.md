# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

>The system requires all users to authenticate using an alphanumeric password with a minimum of seven characters, including at least three capital letters and two digits. Data persistence must be done through object serialisation, so that data can be kept between successive runs of the system. The simulation tool will make it possible to build railway lines, purchase trains and manage the delivery of cargo along defined routes. In addition, users will be able to start and pause the simulation, visualise events and play games.

## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

>The user interface should be intuitive and visually structured, supporting the graphical placement of simulation elements. The interface should also prevent user errors by always providing clear and explanatory messages whenever there are incorrect entries - for example, in the case of overlapping or erroneous station coordinates. To facilitate understanding and maintenance, the interface and source code should be documented in English.

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

>System reliability is related to software integrity, compliance and interoperability. All core methods (except input/output methods) should be tested with JUnit 5. Code coverage reports should be generated with JaCoCo.The system must also be resilient to unexpected failures, implementing mechanisms to prevent data corruption in case of abrupt termination.In addition, the simulation must maintain its state even in the event of minor interruptions, such as temporary system crashes.

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

>The application must optimise memory and CPU usage. Simulation events, such as cargo generation and train movements, must be processed in real time. Regarding performance, critical operations such as loading maps and starting simulations must have efficient response times.

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

>To ensure testability, the development team must implement unit tests for all methods, except for methods that implement input/output operations. Unit tests should be implemented using the JUnit 5 framework and the JaCoCo plugin should be used to generate the coverage report.
To ensure maintainability and adaptability, the class structure should be designed to allow easy maintenance and the addition of new functionalities - such as new types of industries - following object-orientation (OO) best practices. It should work on Windows, macOS and Linux.

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

>The system must be developed in Java, as required by the project. The development process will follow SCRUM, with four-week sprints. Tools such as GitHub will be used for version control, and SVG format will be used for images.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

>The application must be developed in the Java programming language. All images/figures produced during the software development process must be saved in SVG format. The class structure should be designed to allow easy maintenance and the addition of new features, following best object-oriented (OO) practices. Javadoc must be used to generate useful documentation for the Java code. Passwords must comply with the defined rules.


### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

>The interface will be developed using JavaFX.

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

>No specific physical constraints are imposed for now.