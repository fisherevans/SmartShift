package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.LocalDate;
import smartshift.business.hibernate.model.InstanceInterface;

public class Instance <T extends Templatable> extends CachedObject{
    public static final String TYPE_IDENTIFIER = "I";
    
    private Template<T> _template;
    private TemplatableArchetype<T> _archetype;
    private Map<String, Object> _properties;
    
    private InstanceInterface _model;
    
    public Instance(Cache cache, Template<T> template, TemplatableArchetype<T> archetype) {
        super(cache);
        _template = template;
        _archetype = archetype;
        _properties = new HashMap<String, Object>();
    }
    
    private Instance(Cache cache, Template<T> template, InstanceInterface model, TemplatableArchetype<T> archetype) {
        this(cache, template, archetype);
    }
    
    public void setStart(LocalDate start) {
        _properties.put(START_DATE_PROPERTY, start);
    }
    
    public void setEnd(LocalDate end) {
        _properties.put(END_DATE_PROPERTY, end);
    }
    
    public LocalDate getStart() {
        return (LocalDate) _properties.get(START_DATE_PROPERTY);
    }
    
    public LocalDate getEnd() {
        return (LocalDate) _properties.get(END_DATE_PROPERTY);
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER + _template.typeCode();
    }

    @Override
    public int getID() {
        if(_model == null)
            return -1;
        return _model.getId();
    }

    @Override
    public void save() {
        _archetype.saveInstance(this, _template.getID(), _properties.values());
    }

    @Override
    public void loadAllChildren() {
        _archetype.loadTemplate(getCache(), _archetype.getTemplateDAO().uniqueByID(_model.getTemplateID()).execute().getId());      
    }
    
    // PROPERTY NAMES
    private static final String START_DATE_PROPERTY = "startDate";
    private static final String END_DATE_PROPERTY = "endDate";
 }
