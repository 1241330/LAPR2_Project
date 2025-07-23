# US07 - View station details 

## 1. Requirements Enginnering
### 1.1 User Story Description 
>As a Player, I want to list all the stations to select one to see its
details, including the existing building and the demand/supply cargoes.

### 1.2. Customer Specifications and Clarifications 

### Customer questions

>**Question:** When the user selects an existing station, does it automatically show all the associated info to that station or the user selects the station then has to select the required option ? eg: 'show details'
>
>**Answer:** That's a matter of UX/UI, each team can decide what works best.
> 
> [Customer Clarifications - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=35100)

>**Question:** 
>1) Should the list be sorted in any particular way? (e.g., by name, by cargo volume, etc.)?
>2) Will the playerExample be able to apply filters to the list? If so, which ones would be useful? (e.g., only active ones, by type, by region…)?
>3) What information should be visible directly in the list? Just the station name, or also other details like type, location, and operational status (active/inactive), etc.?
>4) Besides the existing buildings and demand/supply cargo, are there any other details that should be shown when viewing a station's information? For example, location, capacity, or operational status?
>
> **Answer:**
>1) tbd
>2) tbd
>3) name and a summary of available cargos to be collected and the ones that are demanded
>4) available cargos to be collected and the ones that are demanded
>
> [Customer Clarifications - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=35087)

>**Question:** This user story says that it wants to list all the stations in order to select one. Shouldn't the system return a list of all the stations to select one and not list them, as this would already be done in US05?
> 
>**Answer:** I don't think I fully understood your question, US05 wants to create a station, US07 wants to list and select a station to consult the details.
> 
> [Customer Clarifications - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=34879)

### 1.3. Acceptance Criteria

>Any acceptance criteria for now.

### 1.4. Found out Dependencies

>**US05:** Stations must first be created before they can be listed and displayed.

>**US06:** Station buildings and upgrades must be properly implemented to show complete details.

### 1.5 Input and Output Data

>**Input Data:**
>For US07, the playerExample must select a station (selected data - from the system-generated list of existing stations).

>**Output Data:**
>The system outputs the station's details including its name, buildings/upgrades, and cargo demand/supply data. If no stations exist, it displays an appropriate message.

### 1.6. System Sequence Diagram (SSD)

![US07-SSD](svg/US07-requirements.svg)

### 1.7 Other Relevant Remarks

>**(i) Special requirements**
> 
>* Station buildings/upgrades must reflect historical availability.

>**(ii) Data and/or technology variations**
> 
>* There are three types of stations: Depots (50k,
3x3 radius), Stations (100k, 4x4 radius), and Terminals (200k, 5x5 radius).

