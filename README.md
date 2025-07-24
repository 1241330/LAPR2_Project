> This repository contains the development of the Integrated Project for the 2nd semester of 2024/25, aiming to create a software solution that simulates the operation of railway networks. The project is developed within the scope of the following curricular units:

- **ESOFT** – Software Engineering
- **PPROG** – Programming Paradigms
- **MATCP** – Computational Mathematics
- **MDISC** – Discrete Mathematics
- **LAPR2** – Project Laboratory II

## Main Components

1. **Map and Scenario Editor**: Allows the creation of maps with cities and industries, as well as the definition of historical and technological scenarios.
2. **Simulation Tool**: Manages railway networks, including stations, trains, routes, and cargo transport, with performance and connectivity analysis tools.

## Project Structure

- `src/main/java/` – Main application source code.
- `src/main/resources/` – Resources used by the application (configuration files, etc).
- `src/test/java/` – Automated tests.
- `Data/` – Serialised data for maps, scenarios, and simulations.
- `MATCP/` – Algorithms and statistical analyses developed for MATCP.
- `MDISC/` – Examples, graphs, and artefacts related to discrete mathematics.
- `UserManual_SelfAssessment/` – User manual and self-assessment.
- `brainStorming/` – Diagrams, ideas, and initial planning.
- `docs/` – Detailed documentation, global artefacts, and team tasks.
- `javadoc/` – Automatically generated Java code documentation.

## Technologies Used

- **Java 17+**
- **Maven** (dependency and build management)
- **JUnit** (testing)
- **PlantUML** (diagrams)

## Credits
Project developed for the curricular unit LAPR2 (2024/2025).

This project was developed with the collaboration of the following team:
- [André Pinho](https://github.com/AndreSiPinho)
- [Bianca Almeida](https://github.com/Brma505)
- [Carlota Lemos](https://github.com/1241330) 
- [Eduardo Almeida](https://github.com/EduardoAlmeida-1241418)  
- [Mara Santos](https://github.com/1241452)

## How to Build and Run

1. Ensure you have Java 17+ and Maven installed.
2. In the project root, run:

```bash
mvn clean install
```

3. To run the application:

```bash
mvn exec:java -Dexec.mainClass="<main-class-path>"
```

(Replace `<main-class-path>` with
