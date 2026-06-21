package top.liaoxyucm.ssdotjava;

public class Ssdot {
  private StringBuilder storedString = new StringBuilder("");

  public String ssdotCompile(String text, boolean keepResidue) {
    StringBuilder result = new StringBuilder("");
    int status = 0;
    for (char c : text.toCharArray()) {
      if (status == 1) {
        this.storedString.append(c);
        status = 0;
        continue;
      }
      switch (c) {
        case '.':
          if (status == 0) {
            result.append(this.storedString.toString() + '\n');
            this.storedString.setLength(0);
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
            this.storedString.append(c);
          }
          break;
      }
    }
    if (!keepResidue) {
      this.storedString.setLength(0);
    }
    return result.toString();
  }
}
