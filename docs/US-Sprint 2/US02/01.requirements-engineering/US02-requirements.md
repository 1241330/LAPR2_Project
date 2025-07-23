# US02 - Add an Industry

## 1. Requirements Engineering

### 1.1. User Story Description

>As an Editor, I want to add an industry (selected from the available industries) in a position XY of the selected (current) map.

### 1.2. Customer Specifications and Clarifications

### Customer enquiries
>**Question:** Can an industry be placed anywhere on the map, or are there specific restrictions?
> 
>**Answer:** Industries can be placed anywhere on the map as long as the position (X, Y) is within the map boundaries and does not overlap with another existing element.

>**Question:** As an editor, when adding a new factory, do I need to check for overbuild?
> 
> **Answer:** Yes.
> 
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=34906)

>**Question:** Does the system provide a list of available industries, or does the user need to type the industry name manually? 
> 
>**Answer:** The system provides a predefined list of available industries from which the user can select.
> 
>[Customer Specification - Integrative Project Assignment (Version 1.1)](https://moodle.isep.ipp.pt/mod/resource/view.php?id=261025)

>**Question:** When adding a new industry to a map, is there any other necessary input aside from position and industry type?
>
>**Answer:** No.
> 
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=35185)

>**Question:** For US2, is it possible to select more than one industry at the same time to be placed on the map?
> 
>**Awnswer:** This is more a question of UX/UI than of how the US itself works, so the teams are free to decide.
> 
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=35107)

>**Question:** As an editor, are there limits to how many industries/factories can be placed on the map?
> 
>**Awnswer:** No.
> 
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=34920)

>**Question:** Do we have to considerate if there are industries there are close together?
> 
>**Awnswer:** No.
>
> [Customer Clarification - Fórum](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=34920)


### 1.3. Acceptance Criteria

>AC1: The industry must be selected from a predefined list of available industries.

>AC2: Industries cannot overlap with each other.

### 1.4. Found out Dependencies

>This User Story depends on US01:

>US01 - Create a map with a size and a name.
>- Dependency: US02 depends on US01 as the map must exist before industries can be added.

### 1.5 Input and Output Data
>**Input**
For US02, the editor must provide the following inputs:
>
>- **Industry type**: selected from a predefined list of available industries.  
>  *(AC1: The industry must be selected from a predefined list)*
>- **Industry name**: entered manually. The name must be unique, and the system will validate its uniqueness.
>- **X and Y coordinates**: typed as positive integers. The system validates that the position:
>    - Is within the map boundaries.
>    - Is not already occupied by another element.  
>      *(AC2: Industries cannot overlap with each other)*
>- **Map selection**: a map must be selected prior to placement. If no map is selected, the system prompts the editor to select one.

>**Output**
>
>The system provides the following outputs:
>
>- A **confirmation message** upon successful industry placement on the selected map.
>- An **error message** in any of the following cases:
>    - The industry type is not selected.
>    - The industry name already exists.
>    - The coordinates are outside the map boundaries.
>    - The position is already occupied.
>    - No map is selected.
### 1.6. System Sequence Diagram (SSD)

![US02-SSD](svg/US02-requirements.svg)

### 1.7 Other Relevant Remarks

**(i) Special requirements:**

- The system must validate that the position (X, Y) provided is within the limits of the current map.
- The system must check whether any elements already exist in the chosen position, avoiding overbuilding (no overlapping industries).
- The industry must be selected from a list provided by the system, with predefined names and types (AC1).
- The system must validate that the industry name entered by the editor is unique, rejecting duplicates.
- The interface can allow multiple selection and placement of industries, although this additional functionality is at the team's discretion (UX/UI issue).

**(ii) How often this US is held:**

This user story (US02) is executed whenever an editor needs to modify or populate a map by placing industries.
