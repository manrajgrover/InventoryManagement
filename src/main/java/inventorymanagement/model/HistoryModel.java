package inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class HistoryModel {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private int id;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean availability;

  public HistoryModel() {}

  public HistoryModel(Boolean availability, String message) {
    this.availability = availability;
    this.message = message;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

}
