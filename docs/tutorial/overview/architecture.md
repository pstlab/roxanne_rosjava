# ROXANNE Software Architecture 

ROXANNE is organized as a **Layered Architecture** in order to support **modularity**. Software layers consists of a number of coherent and low-coupled functional elements. Each layer extends the functionalities of underlying layers and provides upper layers with increasingly abstract and complex functionalities. 

![ROXANNE Layered Architecture](roxanne-archi.png)


## Representation Layer

The **Representation Layer** is in charge of providing timeline-based representation and reasoning functionalities. Two bottom components realize primitive functionalities necessary to represent and check the consistency of data and constraints encapsulated by a timeline-based plan. The component “Time points and constraints” is in charge of representing temporal information and constraints and to perform temporal inference to check temporal bounds of activities and the consistency of a plan. 

Representation and low-level reasoning on time rely on the **Simple Temporal Network with Uncertainty** (STNU) formalism. STNU supports “classical” temporal reasoning features (see the Simple Temporal Network formalism) and controllability information about the duration of actions and/or activities of a plan. This component encapsulates an implementation of the Floyd-Warshall algorithm to infer minimal and maximal distance bounds between time points and accordingly check the consistency pseudo-controllability of a plan. The component “CSP Variables” is in charge of representing other variables and constraints that should be considered into a plan (e.g., variables representing parameters of plan predicates and their possible value assignments). Representation and low-level reasoning on this information rely on the **Constraint Satisfaction Problem** (CSP) formalism. An integrated third-party CSP Solver (the Choco Solver  ) is in charge of reasoning and checking the consistency of this kind of information. 

On top of these two architectural elements the framework defines data structures and algorithms necessary to represent the dynamics of the features of a planning domain and synthesize valid and flexible temporal behaviors. The element “Domain Component” defines a number of ready-to-use data structures of the supported types of features that can be modeled in a timeline-based planning domain. An example is the **State Variable** component which represents the possible temporal behaviors of an element of a domain. Such dynamics are modeled in terms of: (i) values representing states or actions a feature may assume or perform over time; (ii) duration bounds representing the expected minimal and maximal execution time of such values; (iii) controllability tags stating whether the execution of a value is controllable or not; (iv) transition constraints describing the allowed sequences of values (i.e., the “legal” temporal behaviors of a domain feature). Then, the component “Plan Database” represents the “Facade element” providing other architectural layers with all the manipulation functionalities necessary to refine and manage timeline-based plans at planning and execution level. 


## Deliberative Layer

The **Deliberative Layer** is in charge of synthesizing valid and pseudo-controllable timeline-based plans, starting from a domain description. It realizes a modular architecture to facilitate the composing and design of timeline-based planners. The element “Hierarchy and Decomposition Graph” analyzes domain knowledge to infer information useful at solving level like e.g., domain hierarchy or the possible decompositions of plan goals. The elements “Strategy”, “Heuristics” and “Solver” encapsulate specific logic to manage respectively the search space of a planner, the selection of flaws of a plan database and the general structure of the solving procedure and its backtracking policy. On top of these elements a (timeline-based) planner is the result of the composition of one or more of these elements. In this way, the actual behavior of a planner can be easily extended/enhanced by introducing new strategies and heuristics or by changing the structure and behavior of the solving approach.

## Executive Layer

The **Executive Layer** is in charge of executing a given timeline-based plan. It realizes a modular architecture to facilitate the customization of an executive process to the specific needs of deployment scenarios. The element “Dependency Graph” analyzes temporal relations of a plan in order to extract execution dependencies concerning the start and end of the execution. The element “Clock”, is in charge of discretizing the temporal axis in a number of ticks that synchronize the execution cycle. The element “Monitor” is in charge of handling execution feedback and observations in order propagate the observed duration of uncontrollable actions that have ended their execution as well as verify the validity of the plan with respect to the state of the environment. The element “Dispatcher” is in charge of deciding the actions that can start their execution and the controllable actions that can end their execution.	



### Next Chapter

[Acting Node Structure](acting.md)





