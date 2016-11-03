package inventorymanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

public class InventoryModel {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private int id;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String productCompany;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String productName;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String productVersion;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getProductCompany() {
    return productCompany;
  }

  public void setProductCompany(String productCompany) {
    this.productCompany = productCompany;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductVersion() {
    return productVersion;
  }

  public void setProductVersion(String productVersion) {
    this.productVersion = productVersion;
  }
}
