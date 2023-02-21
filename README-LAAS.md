
# Instructions to run roxanne on the assistive scenario

Before starting configure the correct version of ROXANNE as follows: 

- Step 1. Clone (or switch to) the "laas" branch of the repository and rebuild the ROSJava workspace using catkin_make

- Step 2. Check the paths specified in the configuration files under the folder "roxanne_rosjava/etc" (especially the file "agent.properties")

- Step 3. set the environment variables **ROXANNE_HOME** and **PLATINUM_HOME** pointing to the folder "<path-to-rosjava-workspace>/src/roxanne_rosjava"

## Run the task planner

- Step 1. Run the roxanne node

```
rosrun roxanne_rosjava roxanne_rosjava_taskplanner com.github.roxanne_rosjava.roxanne_rosjava_taskplanner.ActingNode
```

- Step 2. Call the configuration service of ROXANNE passing the agent configuration file under the folder "roxanne_rosjava/etc"

```
rosservice call /roxanne/acting/configuration "configFilePath: '<path-to-rosjava-workspace>/src/roxanne_rosjava/etc/agent.properties'"
```

- Step 3. Send a planning request on the goal topic "/roxanne/acting/goal". The request shoul
d include the planning goal and some initial facts as follows

```
rostopic pub --once /roxanne/acting/goal roxanne_rosjava_msgs/ActingGoal "goalId: 0
goals:
- id: 0
  component: 'RobotService'
  predicate: 'DeliverDrug'
  parameters: ['room1', 'room1_hri', 'room1_user']
  start: [0, 1000]
  end: [0, 1000]
  duration: [1, 1000]
facts:
- id: 0
  component: 'RobotBase'
  predicate: 'At'
  parameters: ['home']
  start: [0, 0]
  end: [0, 1000]
  duration: [0, 1000]
- id: 1
  component: 'RobotMotionController'
  predicate: 'Still'
  parameters: ['home']
  start: [0, 0]
  end: [0, 1000]
  duration: [1, 1000]
- id: 2
  component: 'RobotSkill'
  predicate: 'Idle'
  parameters: []
  start: [0, 0]
  end: [0, 1000]
  duration: [1, 1000]
- id: 3
  component: 'RobotService'
  predicate: 'Idle'
  parameters: []
  start: [0, 0]
  end: [0, 1000]
  duration: [1, 1000]" 
```

The task planner should synthesize a plan and start executing the tokens of the RobotBase (see the DDL domain "path-to-rosjava-workspace>/roxanne_rosjava/domains/assistive_map1.ddl").

Dispatched toknes can be observed listening to the dispatching topic of roxanne 

```
$ rostopic echo /roxanne/acting/dispatching
```


