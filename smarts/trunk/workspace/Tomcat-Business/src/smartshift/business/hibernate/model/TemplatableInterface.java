package smartshift.business.hibernate.model;

/**
 * @author drew
 * A simple polymorphism to indicate the ability to be added to a template
 */
public interface TemplatableInterface {
    /**
     * @return the id
     */
    public Integer getId();
    
    /**
     * @param id the id to set
     */
    public void setId(Integer id);
    
    /**
     * @return the templateID
     */
    public Integer getTemplateID();

    /**
     * @param templateID the templateID to set
     */
    public void setTemplateID(Integer templateID);   
}
