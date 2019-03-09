package hello.dto;


public class RequestDTO {

  private String value;

  public RequestDTO() {
  }

  public RequestDTO(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
