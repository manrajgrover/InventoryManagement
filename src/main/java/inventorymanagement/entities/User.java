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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user", catalog = "inventory")
public class User implements java.io.Serializable {
  private Integer id;
  private String name;
  private String email;
  private Date createdTimestamp;
  private Set<UserRole> userRoles = new HashSet<UserRole>(0);
  private Set<History> histories = new HashSet<History>(0);
  private Set<Request> requests = new HashSet<Request>(0);

  public User() {}

  public User(int id) {
    this.id = id;
  }

  public User(String name, String email) {
    this.name = name;
    this.email = email;
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

  @Column(name = "name", nullable = false, length = 65535)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "email", nullable = false, length = 65535)
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_timestamp", nullable = false, insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", length = 19)
  public Date getCreatedTimestamp() {
    return this.createdTimestamp;
  }

  public void setCreatedTimestamp(Date createdTimestamp) {
    this.createdTimestamp = createdTimestamp;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  public Set<UserRole> getUserRoles() {
    return this.userRoles;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  public Set<History> getHistories() {
    return this.histories;
  }

  public void setHistories(Set<History> histories) {
    this.histories = histories;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  public Set<Request> getRequests() {
    return this.requests;
  }

  public void setRequests(Set<Request> requests) {
    this.requests = requests;
  }

}
