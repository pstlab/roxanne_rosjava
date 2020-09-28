package std_srvs;

public interface SetBoolRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "std_srvs/SetBoolRequest";
  static final java.lang.String _DEFINITION = "bool data # e.g. for hardware enabling / disabling\n";
  boolean getData();
  void setData(boolean value);
}
