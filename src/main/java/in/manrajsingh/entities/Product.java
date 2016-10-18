package in.manrajsingh.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "product", catalog = "inventory")
public class Product implements java.io.Serializable {

	private Integer id;
	private String version;
	private String name;
	private String company;
	private Date timestamp;
	private Set<Items> itemses = new HashSet<Items>(0);
	private Set<History> histories = new HashSet<History>(0);
	private Set<Request> requests = new HashSet<Request>(0);

	public Product() {
	}

	public Product(String name, String company, Date timestamp) {
		this.name = name;
		this.company = company;
		this.timestamp = timestamp;
	}

	public Product(String name, String company, Date timestamp, Set<Items> itemses, Set<History> histories,
			Set<Request> requests) {
		this.name = name;
		this.company = company;
		this.timestamp = timestamp;
		this.itemses = itemses;
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
	@Column(name = "timestamp", nullable = false, length = 19)
	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public Set<Items> getItemses() {
		return this.itemses;
	}

	public void setItemses(Set<Items> itemses) {
		this.itemses = itemses;
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
