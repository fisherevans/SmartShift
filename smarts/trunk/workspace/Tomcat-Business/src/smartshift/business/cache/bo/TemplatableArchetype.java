package smartshift.business.cache.bo;

import smartshift.business.hibernate.model.InstanceInterface;
import smartshift.business.hibernate.model.TemplateInterface;
import smartshift.common.hibernate.dao.BaseDAO;

public interface TemplatableArchetype<T extends Templatable> extends Templatable{
    public T load(int id);
    
    public TemplateInterface saveTemplate(Template<T> template, String name, Employee owner);
    
    public InstanceInterface saveInstance(Instance<T> instance, int templateID, Object ...params);
    
    public BaseDAO<? extends TemplateInterface> getTemplateDAO();
    
    public BaseDAO<? extends InstanceInterface> getInstanceDAO();

    public void loadTemplate(Cache cache, Integer id);
    
    public void loadInterface(Cache cache, Integer id);
}
