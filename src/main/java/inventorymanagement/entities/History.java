package inventorymanagement.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "history", catalog = "inventory")
public class History implements java.io.Serializable {

  private static final long serialVersionUID = 6518556062188561612L;
  private Integer id;
  private Item item;
  private Product product;
  private User user;
  private String userName;
  private Date issueTimestamp;
  private Date returnTimestamp;

  public History() {}

  public History(User user, Product product, Item item, String userName) {
    this.item = item;
    this.product = product;
    this.user = user;
    this.userName = userName;
  }

  public History(Item item, Product product, User user, String userName, Date issueTimestamp) {
    this.item = item;
    this.product = product;
    this.user = user;
    this.userName = userName;
    this.issueTimestamp = issueTimestamp;
  }

  public History(Item item, Product product, User user, String userName, Date issueTimestamp,
      Date returnTimestamp) {
    this.item = item;
    this.product = product;
    this.user = user;
    this.userName = userName;
    this.issueTimestamp = issueTimestamp;
    this.returnTimestamp = returnTimestamp;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", nullable = false)
  public Item getItem() {
    return this.item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "prod_id", nullable = false)
  public Product getProduct() {
    return this.product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Column(name = "user_name", nullable = false, length = 50)
  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "issue_timestamp", nullable = false, length = 19)
  public Date getIssueTimestamp() {
    return this.issueTimestamp;
  }

  public void setIssueTimestamp(Date issueTimestamp) {
    this.issueTimestamp = issueTimestamp;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "return_timestamp", length = 19)
  public Date getReturnTimestamp() {
    return this.returnTimestamp;
  }

  public void setReturnTimestamp(Date returnTimestamp) {
    this.returnTimestamp = returnTimestamp;
  }

}
