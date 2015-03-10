package smartshift.business.cache.bo;

import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.business.hibernate.model.InstanceInterface;
import smartshift.business.hibernate.model.TemplateInterface;

public interface TemplatableArchetype<T extends Templatable> extends Templatable{
    public T load(int id);
    
    public TemplateInterface saveTemplate(Template<T> template, String name, Employee owner);
    
    public InstanceInterface saveInstance(Instance<T> instance, int templateID, Object ...params);
}
