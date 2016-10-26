package inventorymanagement.dao;

import java.util.List;

import inventorymanagement.entities.Product;

public interface ProductDaoInterface {

  public void save(Product product);

  public void delete(Product product);

  public void update(Product product);

  public List<Product> getAll();

  public Product getById(int id);

}
