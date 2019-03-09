package hello.dto;

public class ErrorDTO {

  private String status;
  private String topic;
  private String message;

  public ErrorDTO() {
  }

  public ErrorDTO(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public ErrorDTO(String status, String topic, String message) {
    this.status = status;
    this.topic = topic;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }
}
