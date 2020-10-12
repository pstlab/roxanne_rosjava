# ROXANNE

**ROXANNE** (**RO**s fle**X**ible **A**cti**N**g co**N**troll**E**r)  is an FTP ROSIN project developing ROS packages that facilitate the integration of Artificial Intelligence planning and execution capabilities with robotic platforms. ROXANNE specifically supports the development of timeline-based goal-oriented architectures in ROS. The project aim at integrating of timeline-based planning and execution technologies with “standard” robot control techniques to enhance robustness and flexibility of robot behaviors when dealing with uncontrollable dynamics of an environment or other “external” and concurrent agents like e.g., human operators in Human-Robot Collaboration (HRC) scenarios. 

## Context and Motivations

Current control techniques enable an effective and successful employment of robotic solutions in manufacturing in order to improve the efficiency of production systems. Robot controllers provide _functional layers_ that can be used to program and configure robots. They manage the execution of a number of fixed primitives or skills that allow a robot to operate/act in a particular working environment and support production processes. Usually _standard controllers_ synthesize static behaviors that force robot to perform fixed sets of tasks limiting their autonomy. Such robtos are neither capable of autonomously deciding the tasks to execute according to the current needs of the factory nor dynamically adapting their behaviors (e.g., task execution or configuration) to the observed state of the working environment.

Although reliable and effective, standard control techniques are _static_ and may limit the adaptability of robots to the evolving needs of modern production systems. Namely, standard approaches to industrial robot control are conservative and lead to rigid behaviors that are not much effective in dynamic environments, where high variability of tasks and production dynamics must be considered. 

Human-Robot Collaboration (HRC) scenarios are well suited examples where a high level of flexibility and adaptability is necessary to take advantages of the tight interaction between humans and robots at production level. The co-existence of robots and humans sharing a fence-less environment raises a number of issues that must be properly solved to achieve safety, riability and efficiency of HRC cells. The uncontrollable dynamics of human operators introduce a source of uncertainty robot controllers must take into account to cary out collaborative production processes in an effective way.

AI and plan-based control technologies can enhance standard robot controllers with the flexibility and the _cognitive capabilities_ needed to achieve the desired level of autonomy, reliability and adaptability. Automated planning and execution techniques allow a robot to dynamically synthesize a set of tasks to be carried out and dynamically adapt control policies to the observed state of the working environment like e.g., production objectives and constraints, or the behavior and status of human workers collaborating within the production processes. However, plan-based controllers usually require significant efforts to be configured and deployed to real-world applications. There is a lack of standards that can facilitate the integration of plan-based control techniques with existing robot controllers and theri deployment to real-world industrial contexts.


## Objectives

ROXANNE aims at creating a ROS compliatn framework facilitating the use of timeline-based planning and execution capabilities in industrial settings. Target users are either researchers and manufacturing companies that want to evaluate flexible task-level controllers to better support production processes. The objectives of the project are thus the following:
- **Facilitate industrial robot programming** by providing a general-purpose specification language to model operational and temporal constraints of production processes and the dynamics of working environment, human operators and robot behaviors.
- Realize a **ROS-integrated goal-oriented planning and execution system** capable of autonomously synthesize the set of tasks a robot must perform to achieve production goals. Minimize production interruptions by dynamically coordinating and adapting robot behaviors by taking into account _uncontrollable_ dynamics of a working environment.
- Preovide **process-driven modeling and robot-agnostic control capabilities** to support interoperability and integration with different robotic platforms. 
- Define an **interoperability communication protocol** characterizing events and information exchanged within the life-cycle of a dynamic task planning system. Such a protocol defines services and dependencies that are necessary for the effective integration of the task planner with robot controllers and realize an autonomous robot architecture.
- Enable and **facilitate the use of timeline-based control techniques** in real-world production contexts by leveraging a standard platform like ROS and an existing timeline-based framework called PLATINUm.

## Tutorial

- Framework Overview
  - [Architecture](/tutorial/overview/architecture.md)
  - [Acting Node Structure](/tutorial/overview/acting.md)

## Acknowledgment

ROXANNE has received funding from the European Union's Horizon 2020 research and innovation programme under the project ROSIN with the gran agreement No 732287. 


## Publications
- (M. Faroni et al. 2020) M. Faroni, M. Beschi, S. Ghidini, N. Pedrocchi, A. Umbrico, A. Orlandini, A. Cesta "A layered control approach to human-aware task and motion planning for Human-Robot Collaboration". 29th IEEE International Conference on Robot and Human Interactive Communication (RO-MAN). 2020.
