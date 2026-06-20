package top.liaoxyucm.ssdotjava;

public class Ssdot {
  public static String ssdotCompile(String text) {
    StringBuilder result = new StringBuilder("");
    StringBuilder storedString = new StringBuilder("");
    int status = 0;
    for (char c : text.toCharArray()) {
      if (status == 1) {
        storedString.append(c);
        status = 0;
        continue;
      }
      switch (c) {
        case '.':
          if (status == 0) {
            result.append(storedString.toString() + '\n');
            storedString.setLength(0);
          }
          break;

        case '\\':
          if (status == 0) {
            status = 1;
          }
          break;

        case '/':
          if (status == 0) {
            status = 2;
          } else if (status == 2) {
            status = 0;
          }
          break;

        default:
          if (status == 0) {
            storedString.append(c);
          }
          break;
      }
    }
    return result.toString();
  }
}
