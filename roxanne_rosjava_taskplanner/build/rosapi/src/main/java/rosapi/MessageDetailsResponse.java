package rosapi;

public interface MessageDetailsResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/MessageDetailsResponse";
  static final java.lang.String _DEFINITION = "TypeDef[] typedefs";
  java.util.List<rosapi.TypeDef> getTypedefs();
  void setTypedefs(java.util.List<rosapi.TypeDef> value);
}
