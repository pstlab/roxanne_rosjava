package rosapi;

public interface GetTimeResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosapi/GetTimeResponse";
  static final java.lang.String _DEFINITION = "time time";
  org.ros.message.Time getTime();
  void setTime(org.ros.message.Time value);
}
