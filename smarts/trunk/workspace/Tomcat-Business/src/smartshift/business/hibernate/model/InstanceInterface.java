package smartshift.business.hibernate.model;

/**
 * @author drew
 * A simple polymorphism to indicate instancability
 */
public interface InstanceInterface {
    
    /**
     * @return the id
     */
    public Integer getId();

    /**
     * @return the templateID
     */
    public Integer getTemplateID();
}
