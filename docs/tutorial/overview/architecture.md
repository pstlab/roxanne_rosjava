ROXANNE is organized as a **Layered Architecture** in order to support **modularity**. Software layers consists of a number of coherent and low-coupled functional elements. Each layer extends the functionalities of underlying layers and provides upper layers with increasingly abstract and complex functionalities. 

![ROXANNE Layered Architecture](/assets/img/layers.pdf)


## Representation Layer

The **Representation Layer** is in charge of providing timeline-based representation and reasoning functionalities. Two bottom components realize primitive functionalities necessary to represent and check the consistency of data and constraints encapsulated by a timeline-based plan. The component “Time points and constraints” is in charge of representing temporal information and constraints and to perform temporal inference to check temporal bounds of activities and the consistency of a plan. 

Representation and low-level reasoning on time rely on the **Simple Temporal Network with Uncertainty** (STNU) formalism. STNU supports “classical” temporal reasoning features (see the Simple Temporal Network formalism) and controllability information about the duration of actions and/or activities of a plan. This component encapsulates an implementation of the Floyd-Warshall algorithm to infer minimal and maximal distance bounds between time points and accordingly check the consistency pseudo-controllability of a plan. The component “CSP Variables” is in charge of representing other variables and constraints that should be considered into a plan (e.g., variables representing parameters of plan predicates and their possible value assignments). Representation and low-level reasoning on this information rely on the **Constraint Satisfaction Problem** (CSP) formalism. An integrated third-party CSP Solver (the Choco Solver  ) is in charge of reasoning and checking the consistency of this kind of information. 

On top of these two architectural elements the framework defines data structures and algorithms necessary to represent the dynamics of the features of a planning domain and synthesize valid and flexible temporal behaviors. The element “Domain Component” defines a number of ready-to-use data structures of the supported types of features that can be modeled in a timeline-based planning domain. An example is the **State Variable** component which represents the possible temporal behaviors of an element of a domain. Such dynamics are modeled in terms of: (i) values representing states or actions a feature may assume or perform over time; (ii) duration bounds representing the expected minimal and maximal execution time of such values; (iii) controllability tags stating whether the execution of a value is controllable or not; (iv) transition constraints describing the allowed sequences of values (i.e., the “legal” temporal behaviors of a domain feature).

The synthesis of valid temporal behaviors of state variables (and other domain components) consists in detecting and solving a number of **flaws** affecting the completeness and validity of such behaviors. Thus, each type of domain component is associated to a set of specific algorithms in charge of detecting and resolving behavior flaws. In the case of state variables, planning and scheduling resolvers decide the values a state variable assumes over time and schedule these values in order to define a continuous (valid and flexible) sequence of not overlapping values. Then, the component “Plan Database” represents the “Facade element” providing other architectural layers with all the manipulation functionalities necessary to refine and manage timeline-based plans at planning and execution level. 


## Deliberative Layer


## Executive Layer



