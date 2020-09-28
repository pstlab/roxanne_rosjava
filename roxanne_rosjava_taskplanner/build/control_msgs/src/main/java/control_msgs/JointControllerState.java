package control_msgs;

public interface JointControllerState extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "control_msgs/JointControllerState";
  static final java.lang.String _DEFINITION = "# This message presents current controller state of one joint.\n\n# Header timestamp should be update time of controller state\nHeader header\n\n# The set point, that is, desired state.\nfloat64 set_point\n\n# Current value of the process (ie: latest sensor measurement on the controlled value).\nfloat64 process_value\n\n# First time-derivative of the process value.\nfloat64 process_value_dot\n\n# The error of the controlled value, essentially process_value - set_point (for a regular PID implementation).\nfloat64 error\n\n# Time between two consecutive updates/execution of the control law.\nfloat64 time_step\n\n# Current output of the controller.\nfloat64 command\n\n# Current PID parameters of the controller.\nfloat64 p\nfloat64 i\nfloat64 d\nfloat64 i_clamp\nbool antiwindup\n";
  std_msgs.Header getHeader();
  void setHeader(std_msgs.Header value);
  double getSetPoint();
  void setSetPoint(double value);
  double getProcessValue();
  void setProcessValue(double value);
  double getProcessValueDot();
  void setProcessValueDot(double value);
  double getError();
  void setError(double value);
  double getTimeStep();
  void setTimeStep(double value);
  double getCommand();
  void setCommand(double value);
  double getP();
  void setP(double value);
  double getI();
  void setI(double value);
  double getD();
  void setD(double value);
  double getIClamp();
  void setIClamp(double value);
  boolean getAntiwindup();
  void setAntiwindup(boolean value);
}
