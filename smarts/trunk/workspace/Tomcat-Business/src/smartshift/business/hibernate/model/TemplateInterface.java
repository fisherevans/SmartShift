package smartshift.business.hibernate.model;

/**
 * @author drew
 * A simple polymorphism to indicate templatability
 */
public interface TemplateInterface {
  
    /**
     * @return the id
     */
    public Integer getId();
    
    /**
     * @param name the name to set
     */
    public void setName(String name);
    
    /**
     * @return the name
     */
    public String getName();
}
