package inventorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Application run class
 * 
 * @author manrajsingh
 * 
 */
@SpringBootApplication
public class InventoryManagement extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(InventoryManagement.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(InventoryManagement.class, args);
  }
}
