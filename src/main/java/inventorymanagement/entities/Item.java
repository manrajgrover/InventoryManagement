package inventorymanagement.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "item", catalog = "inventory",
    uniqueConstraints = @UniqueConstraint(columnNames = "tag"))
@SQLDelete(sql = "UPDATE item SET removed_timestamp = NOW() WHERE id = ?")
@Where(clause = "removed_timestamp IS NULL")
public class Item implements java.io.Serializable {

  private static final long serialVersionUID = 2235715607429226599L;
  private Integer id;
  private Product product;
  private String tag;
  private Date createTimestamp;
  private String available;
  private Date modifiedTimestamp;
  private Date removedTimestamp;
  private Set<History> histories = new HashSet<History>(0);

  public Item() {}

  public Item(Product product, String tag, String available) {
    this.product = product;
    this.tag = tag;
    this.available = available;
  }

  public Item(int id) {
    this.id = id;
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

  @Column(name = "tag", unique = true, nullable = false, length = 30)
  public String getTag() {
    return this.tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_timestamp", nullable = false, insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", length = 19)
  public Date getCreateTimestamp() {
    return this.createTimestamp;
  }

  public void setCreateTimestamp(Date createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  @Column(name = "available", nullable = false, length = 50)
  public String getAvailable() {
    return this.available;
  }

  public void setAvailable(String available) {
    this.available = available;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_timestamp", nullable = false, updatable = false, insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
      length = 19)
  public Date getModifiedTimestamp() {
    return this.modifiedTimestamp;
  }

  public void setModifiedTimestamp(Date modifiedTimestamp) {
    this.modifiedTimestamp = modifiedTimestamp;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "removed_timestamp", length = 19)
  public Date getRemovedTimestamp() {
    return this.removedTimestamp;
  }

  public void setRemovedTimestamp(Date removedTimestamp) {
    this.removedTimestamp = removedTimestamp;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
  public Set<History> getHistories() {
    return this.histories;
  }

  public void setHistories(Set<History> histories) {
    this.histories = histories;
  }

}
