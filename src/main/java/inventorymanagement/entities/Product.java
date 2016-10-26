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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "product", catalog = "inventory")
@SQLDelete(sql = "UPDATE product SET removed_timestamp = NOW() WHERE id = ?")
@Where(clause = "removed_timestamp IS NULL")
public class Product implements java.io.Serializable {
	private static final long serialVersionUID = -2484074216261529988L;
	private Integer id;
	private String version;
	private String name;
	private String company;
	private Date createTimestamp;
	private Date modifiedTimestamp;
	private Date removedTimestamp;
	private Set<Item> items = new HashSet<Item>(0);
	private Set<History> histories = new HashSet<History>(0);
	private Set<Request> requests = new HashSet<Request>(0);

	@PreUpdate
	public void setModifiedTimestamp() {
		this.modifiedTimestamp = new Date();
	}

	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	public Product(String name, String company, String version) {
		this.name = name;
		this.company = company;
		this.version = version;
	}

	public Product(String name, String company, Date createTimestamp, Date modifiedTimestamp) {
		this.name = name;
		this.company = company;
		this.createTimestamp = createTimestamp;
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Product(String name, String company, Date createTimestamp, Date modifiedTimestamp, Date removedTimestamp,
			Set<Item> items, Set<History> histories, Set<Request> requests) {
		this.name = name;
		this.company = company;
		this.createTimestamp = createTimestamp;
		this.modifiedTimestamp = modifiedTimestamp;
		this.removedTimestamp = removedTimestamp;
		this.items = items;
		this.histories = histories;
		this.requests = requests;
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

	@Column(name = "version", nullable = false, length = 10)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "company", nullable = false, length = 50)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_timestamp", nullable = false, length = 19)
	public Date getCreateTimestamp() {
		return this.createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
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
	@Column(name = "removed_timestamp", length = 19)
	public Date getRemovedTimestamp() {
		return this.removedTimestamp;
	}

	public void setRemovedTimestamp(Date removedTimestamp) {
		this.removedTimestamp = removedTimestamp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<History> getHistories() {
		return this.histories;
	}

	public void setHistories(Set<History> histories) {
		this.histories = histories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}

}
