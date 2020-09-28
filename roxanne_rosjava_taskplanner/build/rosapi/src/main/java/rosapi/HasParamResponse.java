package rosapi;

public interface HasParamResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/HasParamResponse";
  static final java.lang.String _DEFINITION = "bool exists";
  boolean getExists();
  void setExists(boolean value);
}
