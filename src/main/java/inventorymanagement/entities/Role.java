package inventorymanagement.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role", catalog = "inventory")
public class Role implements java.io.Serializable {

  private static final long serialVersionUID = -6841448109227415008L;
  private Integer id;
  private String name;
  private Set<UserRole> userRoles = new HashSet<UserRole>(0);

  public Role() {}

  public Role(int id) {
    this.id = id;
  }

  public Role(String name) {
    this.name = name;
  }

  public Role(String name, Set<UserRole> userRoles) {
    this.name = name;
    this.userRoles = userRoles;
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

  @Column(name = "name", nullable = false, length = 50)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
  public Set<UserRole> getUserRoles() {
    return this.userRoles;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }

}
