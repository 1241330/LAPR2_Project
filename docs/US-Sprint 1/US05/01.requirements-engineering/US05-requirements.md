# US05 - As a Player, I want to build a station.

## 1. Requirements Engineering

### 1.1. User Story Description


> As a Player, I want to build station (can be a depot, a station
or a terminal) with a location in the current map. The system should
propose a name for the station based on the closest city and the station
(e.g., Porto Terminal, Ovar Station or Silvalde Depot). In case of Depot
and Terminal, the center is the geometric one, in the case of station,
the centre should be defined by the Player (NE,SE,NW,SW).
### 1.2. Customer Specifications and Clarifications

>**Question:**
Is the budget the only concern to build a station, or does the playerExample also needs to have other specific resources, like steel, etc.? Are resources included on the budget, or the budget represent only the playerExample's monetary value?
>
>**Answer:**
Just the budget in a generic currency.
>
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=35149)

>**Question:**
Should users have options to select different station types (Depot, Station, Terminal) based on the game rules?
>
>**Answer:**
Just the budget in a generic currency.
>
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=35047)


### 1.3. Acceptance Criteria

>AC1: Overbuilding is not possible in the Simulator



### 1.4. Found out Dependencies

>This User Story depends on US01 and US03:
>
>US01 - Create a map with size and name.
>- Dependency: US05 depends on US01 to ensure there is a valid map where the station can be added.
>
>US03 - I want to add a city
>- Dependency: US05 depends on US03 to ensure that there is a valid city that can be named after the station.

### 1.5 Input and Output Data

>For US05, the playerExample choose the Station Location, name acceptance (accept or not the name suggested by the simulator), gives the type of station and in case of station type is station, gives the center location.

>The system outputs the proposed station name, if exists overlap, outputs Overlap error message, outputs the possible types of stations, in case of selected station type, outputs the possible center positions and if everything goes well display success message.

### 1.6. System Sequence Diagram (SSD)


![US05- System Sequence Diagram](svg/US05-requirements.svg)

### 1.7 Other Relevant Remarks

>(i) Special requirements;  
> 
> Station names are dynamically generated using the closest city’s name and the station type
> 
> For Depot/Terminal types, the center is automatically set as the geometric center. For Station type, the playerExample must manually select a valid quadrant (NE, SE, NW, SW).
> 

> (ii) How often this US is held;
>
> Expected to be a frequent action during gameplay, as players expand their railway networks.
 