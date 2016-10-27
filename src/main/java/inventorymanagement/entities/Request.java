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
@Table(name = "request", catalog = "inventory")
public class Request implements java.io.Serializable {
  private static final long serialVersionUID = -3802227845438880223L;
  private int id;
  private Product product;
  private User user;
  private String reply;
  private Date modifiedTimestamp;
  private Date createdTimestamp;
  private boolean status;

  public Request() {}

  public Request(User user, Product product, String reply, boolean status) {
    this.user = user;
    this.product = product;
    this.reply = reply;
    this.status = status;
  }

  public Request(Product product, User user, String reply, Date modifiedTimestamp,
      Date createdTimestamp, boolean status) {
    this.product = product;
    this.user = user;
    this.reply = reply;
    this.modifiedTimestamp = modifiedTimestamp;
    this.createdTimestamp = createdTimestamp;
    this.status = status;
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
  @JoinColumn(name = "product_id", nullable = false)
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

  @Column(name = "reply", nullable = false, length = 20)
  public String getReply() {
    return this.reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_timestamp", nullable = false, updatable = false, length = 19)
  public Date getModifiedTimestamp() {
    return this.modifiedTimestamp;
  }

  public void setModifiedTimestamp(Date modifiedTimestamp) {
    this.modifiedTimestamp = modifiedTimestamp;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_timestamp", nullable = false, length = 19)
  public Date getCreatedTimestamp() {
    return this.createdTimestamp;
  }

  public void setCreatedTimestamp(Date createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  @Column(name = "status", nullable = false)
  public boolean isStatus() {
    return this.status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Request [id=" + id + ", product=" + product + ", user=" + user + ", reply=" + reply
        + ", modifiedTimestamp=" + modifiedTimestamp + ", createdTimestamp=" + createdTimestamp
        + ", status=" + status + "]";
  }

}
