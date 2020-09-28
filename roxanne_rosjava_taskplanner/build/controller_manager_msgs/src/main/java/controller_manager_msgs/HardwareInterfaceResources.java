package controller_manager_msgs;

public interface HardwareInterfaceResources extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "controller_manager_msgs/HardwareInterfaceResources";
  static final java.lang.String _DEFINITION = "# Type of hardware interface\nstring hardware_interface\n# List of resources belonging to the hardware interface\nstring[] resources\n";
  java.lang.String getHardwareInterface();
  void setHardwareInterface(java.lang.String value);
  java.util.List<java.lang.String> getResources();
  void setResources(java.util.List<java.lang.String> value);
}
