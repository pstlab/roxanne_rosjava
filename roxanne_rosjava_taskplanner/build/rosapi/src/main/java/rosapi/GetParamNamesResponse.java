package rosapi;

public interface GetParamNamesResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/GetParamNamesResponse";
  static final java.lang.String _DEFINITION = "string[] names";
  java.util.List<java.lang.String> getNames();
  void setNames(java.util.List<java.lang.String> value);
}
