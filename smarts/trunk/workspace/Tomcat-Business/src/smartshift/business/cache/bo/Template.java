package smartshift.business.cache.bo;

import java.util.ArrayList;
import java.util.List;
import smartshift.business.hibernate.model.InstanceInterface;
import smartshift.business.hibernate.model.TemplatableInterface;
import smartshift.business.hibernate.model.TemplateInterface;
import smartshift.common.util.collections.ROCollection;

public class Template<T extends Templatable> extends CachedObject{
    
    public static final String TYPE_IDENTIFIER = "T";
    
    private List<T> _components;
    private TemplatableArchetype<T> _archetype;
    private String _name;
    private Employee _owner;
    
    private TemplateInterface _model;
    
    public Template(Cache cache, String name, Employee owner, TemplatableArchetype<T> archetype){
        super(cache);
        _name = name;
        _owner = owner;
        _components = new ArrayList<T>();
    }
    
    public Template(Cache cache, T component, String name, Employee owner, TemplatableArchetype<T> archetype) {
        this(cache, name, owner, archetype);
        add(component);
    }
    
    public void add(T component) {
        _components.add(component);
    }
    
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER + _archetype.typeCode();
    }

    @Override
    public int getID() {
        if(_model != null)
            return _model.getId();
        return -1;
    }

    @Override
    public void save() {
        _archetype.save();
    }

    @Override
    public void loadAllChildren() {

    }

    public ROCollection<T> getComponents() {
        return ROCollection.wrap(_components);
    }
}
