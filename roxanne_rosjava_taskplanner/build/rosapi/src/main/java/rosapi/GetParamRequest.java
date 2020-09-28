package rosapi;

public interface GetParamRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/GetParamRequest";
  static final java.lang.String _DEFINITION = "string name\nstring default\n";
  java.lang.String getName();
  void setName(java.lang.String value);
  java.lang.String getDefault();
  void setDefault(java.lang.String value);
}
