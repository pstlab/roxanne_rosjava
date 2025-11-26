## ROXANNE | ROSJava Package

**ROXANNE** (ROs fleXible ActiNg coNtrollEr) is an **FTP ROSIN project** developing **ROS** packages that facilitate the integration of Artificial Intelligence planning and execution capabilities with robotic platforms. ROXANNE specifically supports the development of timeline-based goal-oriented architectures in ROS. The project aim at integrating of timeline-based planning and execution technologies with “standard” robot control techniques to enhance robustness and flexibility of robot behaviors when dealing with uncontrollable dynamics of an environment or other “external” and concurrent agents like e.g., human operators in Human-Robot Collaboration (HRC) scenarios.

### Fundings

ROXANNE has received funding from the European Union’s Horizon 2020 research and innovation programme under the project ROSIN with the gran agreement No 732287.


### Objectives

**ROXANNE** aims at creating a ROS compliatn framework facilitating the use of timeline-based planning and execution capabilities in industrial settings. Target users are either researchers and manufacturing companies that want to evaluate flexible task-level controllers to better support production processes. The objectives of the project are thus the following:

- **Facilitate industrial robot programming** by providing a general-purpose specification language to model operational and temporal constraints of production processes and the dynamics of working environment, human operators and robot behaviors.
- Realize a **ROS-integrated goal-oriented planning and execution system** capable of autonomously synthesize the set of tasks a robot must perform to achieve production goals. Minimize production interruptions by dynamically coordinating and adapting robot behaviors by taking into account uncontrollable dynamics of a working environment.
- Preovide process-driven modeling and robot-agnostic control capabilities to support **interoperability and integration with different robotic platforms**.
- Define an interoperability communication protocol characterizing events and information exchanged within the life-cycle of a dynamic task planning system. Such a protocol defines services and dependencies that are necessary for the effective integration of the task planner with robot controllers and realize an autonomous robot architecture.
- Enable and **facilitate the use of timeline-based control techniques** in real-world production contexts by leveraging a standard platform like ROS and an existing timeline-based framework called [**PLATINUm**](https://github.com/pstlab/PLATINUm)

## Docker Installation

A public Docker image is available on [Docker Hub](https://hub.docker.com/r/pstlab/roxanne). The docker image consists of a **ROS Melodic** distribution, an configured **ROSJava workspace** and an installed and ready-to-use **ROXANNE ROSJava** package. You may refer to the section **Package Usage** for some instructions about how to run and interact with a ROXANNE Acting Node.

In order to update the docker with the latest versions of the package set your GitHub credentials into the file ```gradle.properties``` of the package (see section **Gradle Configuration**).

_Note that it could be necessary to explicitly start the **mongod service** on the docker. Run the script under ```/root/bin/start_mongo``` if necessary_ 

## ROS Installation

The package has been developed and tested for **ROS Melodic** distribution on **Ubunut 18.04**. It requires a ROSJava workspace for correct execution and generation of the Java artifacts needed for custom messages and installed Java software.

_We recommend the use of Java 8 since other higher or lower versions of Java may have some dependency issues with rosjava. Alternative versions of Java can be installed using the following command:_ 

```sudo update-alternatives --config java```

In addition, the package requires an installed locally running instance of **MongoDB server** (the current package has been developed and tested using [_MongDB Cumminuty Server v.4.4.5_](https://www.mongodb.com/try/download/community)). 

### ROSJava Workspace Configuration

Create an empty workspace.

```
mkdir -p ~/ws/src
cd ~/ws
catkin_make
```

Here are the instructions for downloading and building **ROSJava from source** (recommended). Please refer to the official [ROSJava Wiki](http://wiki.ros.org/rosjava) for further details about installation and availabe distributions.

```
wstool init -j4 ~/ws/src https://raw.githubusercontent.com/rosjava/rosjava/kinetic/rosjava.rosinstall
source /opt/ros/melodic/setup.bash
cd ~/ws
rosdep update
rosdep install --from-paths src -i -y
catkin_make
```
Note that one of the dependecies of **rosjava_messages** package is [world_canvas_msgs](http://wiki.ros.org/world_canvas_msgs) which is not supported by ROS Melodic. To successfully build **rosjava** thus remove this dependency by editing the file ```package.xml``` of ```rosjava_messages``` and commenting the following line

```
<build_depend>world_canvas_msgs</build_depend>
```
Note that the installation of rosjava could install the latest version of openjdk. In such a case use the command ```sudo update-alternatives --config java``` to enforce the use of **opnejdk-8-jdk** (or other versions of java 8) which is recommended for the correct use of **ROXANNE**.

### Package preparation

Once that a ROSJava workspace has been successfully configured everything is ready for the installation of the **roxanne_rosjava package**. 

First, it is necessary to install the ROSjava package **roxanne_rosjava_msgs** defining custom messages and services. This package is available on GitHub (https://github.com/pstlab/roxanne_rosjava_msgs.git) and can be installed into the ROSJava workspace as follows: 

```
cd ~/ws/src
git clone https://github.com/pstlab/roxanne_rosjava_msgs.git
cd ~/ws
catkin_make
source ~/ws/devel/setup.bash
```

It is possible to verify the successful built of the package by checking ROS service description through ```rossrv show```. For example the following commands

```
cd ~/ws
source devel/setup.bash
rossrv show roxanne_rosjava_msgs/DeliberativeService
```
should give the following output which describes the request and response defined for the service **DeliberativeService**

```
string ddl
string pdl
---
uint8 result
roxanne_rosjava_msgs/Timeline[] timelines
  int64 id
  string component
  roxanne_rosjava_msgs/Token[] tokens
    int64 id
    string component
    string predicate
    string[] parameters
    int64[] start
    int64[] end
    int64[] duration
```

### Gradle Configuration

Before buliding the ROS package configure **gradle** so that all the dependencies are correctly downloaded. This module indeed relies on some packages (e.g., [PLATINUm](https://github.com/pstlab/PLATINUm)) that are not deployed on maven central but on GitHub. 

To allow gradle to download packages from GitHub repositories create a text file ```gradle.properties``` under ```roxanne_rosjava```. This file should specifiy a **GitHub username** and a **GitHub access token** as follows: 

```
gpr.user=<github-user>
gpr.token=<github-personal-access-token>
```
See how to create personal access token on [the official GitHub page](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) if necessary). Be sure to select the scope ```read:packages``` when creating the personal access token in order to successfully read GitHub packages.

As alternative to the file ```gradle.properties``` the same configuration parameters can be specified using environment variables. For example add to  ```.bashrc``` the definition of the following variables:

```
export GITHUB_USER=<github-user>
export GITHUB_TOKEN=<github-personal-access-token>
```

### Bulding the Package

At this point install the **roxanne_rosjava package** into the ROSJava workspace by cloning and installing the current repository.

```
cd ~/ws/src
git clone https://github.com/pstlab/roxanne_rosjava.git
cd ..
source devel/setup.bash
catkin_make
source devel/setup.bash
```

To finalize the installation just define the environment variable **PLATINUM_HOME** in order to point to the folder containing the installed package. This would tell the underlying reasoning framework where to find the configuration files (see the content of the ```etc``` of the package).

```
export PLATINUM_HOME=<path-to-the-workspace>/src/roxanne_rosjava
```

The above line of code can be added to the ```.bashrc``` file to automatically export the environment variable when the terminal is open. 

## Package Usage 

If the the installation and configuration of the **roxanne_rosjava** package has been successfully done then it should be possible to run the ROS node starting the **goal-oriented agent** for continuous planning and execution.

First open a terminal and start core ROS nodes using ```roscore```. 

Then, open a different terminal and launch the acting node as follows:

```
cd ~/ws
source devel/setup.bash
rosrun roxanne_rosjava roxanne_rosjava_taskplanner com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.ActingNode
```

At this point the acting node is running and can be initialized on a specific timeline-based model using the service ``` roxanne_rosjava_msgs/ActingConfigurationService```. Run the following command in a new terminal.

```
cd ~/ws
source devel/setup.bash
rosservice call /roxanne/acting/configuration "configFilePath: '<abosolute-path-to-the-workspace>/src/roxanne_rosjava/etc/agent.properties'"
```

The above command initializes the acting agent using a sample configuration file that can be found under the folder ```etc``` of the package.

If the agent is successfully initialized then send a planning and execution request to the agent by posting a dedicated message on the input topic ```/roxanne/acting/goal```.

```
rostopic pub --once /roxanne/acting/goal roxanne_rosjava_msgs/ActingGoal "goalId: 0
goals:
- id: 0
  component: 'Goal'
  predicate: 'DoSomething'
  parameters: []
  start: [0, 100]
  end: [0, 100]
  duration: [1, 100]
facts:
- id: 0
  component: 'Acting'
  predicate: 'Idle'
  parameters: []
  start: [0, 0]
  end: [0, 100]
  duration: [1, 100]"
```

The terminal of the acting node should display information about the lifecycle of the request within the acting agent. After few seconds it shows a textual description of the synthesized plan and starts its execution.

The file ```etc/platform/roxanne_platform.xml``` specifies the topics used to dispatch commands, receive feedback and the types of messge exchanged over such topics. 

````
<ros>

    <goal-topic
        name="/roxanne/acting/goal"
        msg="roxanne_rosjava_msgs/ActingGoal"
        delegate="com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform.RoxanneGoalListener" />

    <environment-topic
            name="/roxanne/acting/observation"
            msg="roxanne_rosjava_msgs/Observation"
            delegate="com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform.RoxanneObservationListener" />

    <!-- wildcard matching any type of dispatchable token of a plan -->

    <command component="*" name="*" exclude="idle">

        <dispatch-topic
                name="/roxanne/acting/dispatching"
                msg="roxanne_rosjava_msgs/TokenExecution"
                publisher="com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform.RoxanneTokenPublisher" />

        <feedback-topic
                name="/roxanne/acting/feedback"
                msg="roxanne_rosjava_msgs/TokenExecutionFeedback"
                delegate="com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.platform.RoxanneFeedbackListener" />

    </command>
</ros>
````

This configuration is the default expected when using **ROXANNE**. However, the framework allows users to specify different combinations of topics and messages. In such cases it would be necessary to develop custom Java delegates responsible for "marshaling/umarshaling" the specific message formats


## Additional resources

Check the wiki or visit [ROXANNE | ROSJava Website](https://pstlab.github.io/roxanne_rosjava/) for more details.



