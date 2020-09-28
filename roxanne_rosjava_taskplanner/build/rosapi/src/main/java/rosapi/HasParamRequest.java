package rosapi;

public interface HasParamRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/HasParamRequest";
  static final java.lang.String _DEFINITION = "string name\n";
  java.lang.String getName();
  void setName(java.lang.String value);
}
