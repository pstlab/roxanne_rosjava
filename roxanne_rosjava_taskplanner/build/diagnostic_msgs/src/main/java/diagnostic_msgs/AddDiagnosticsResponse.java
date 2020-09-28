package diagnostic_msgs;

public interface AddDiagnosticsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "diagnostic_msgs/AddDiagnosticsResponse";
  static final java.lang.String _DEFINITION = "\n# True if diagnostic aggregator was updated with new diagnostics, False\n# otherwise. A false return value means that either there is a bond in the\n# aggregator which already used the requested namespace, or the initialization\n# of analyzers failed.\nbool success\n\n# Message with additional information about the success or failure\nstring message";
  boolean getSuccess();
  void setSuccess(boolean value);
  java.lang.String getMessage();
  void setMessage(java.lang.String value);
}
