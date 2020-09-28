package controller_manager_msgs;

public interface ControllerState extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "controller_manager_msgs/ControllerState";
  static final java.lang.String _DEFINITION = "string name\nstring state\nstring type\ncontroller_manager_msgs/HardwareInterfaceResources[] claimed_resources\n";
  java.lang.String getName();
  void setName(java.lang.String value);
  java.lang.String getState();
  void setState(java.lang.String value);
  java.lang.String getType();
  void setType(java.lang.String value);
  java.util.List<controller_manager_msgs.HardwareInterfaceResources> getClaimedResources();
  void setClaimedResources(java.util.List<controller_manager_msgs.HardwareInterfaceResources> value);
}
