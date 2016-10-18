package in.manrajsingh.entities;

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

@Entity
@Table(name = "items", catalog = "inventory", uniqueConstraints = @UniqueConstraint(columnNames = "tag"))
public class Items implements java.io.Serializable {

	private Integer id;
	private Product product;
	private String tag;
	private Date buyTimestamp;
	private String available;
	private Set<History> histories = new HashSet<History>(0);

	public Items() {
	}

	public Items(Product product, String tag, String available) {
		this.product = product;
		this.tag = tag;
		this.available = available;
	}

	public Items(Product product, String tag, Date buyTimestamp, String available, Set<History> histories) {
		this.product = product;
		this.tag = tag;
		this.buyTimestamp = buyTimestamp;
		this.available = available;
		this.histories = histories;
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

	@Column(name = "tag", unique = true, nullable = false, length = 50)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "buy_timestamp", length = 19)
	public Date getBuyTimestamp() {
		return this.buyTimestamp;
	}

	public void setBuyTimestamp(Date buyTimestamp) {
		this.buyTimestamp = buyTimestamp;
	}

	@Column(name = "available", nullable = false, length = 50)
	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "items")
	public Set<History> getHistories() {
		return this.histories;
	}

	public void setHistories(Set<History> histories) {
		this.histories = histories;
	}
}
