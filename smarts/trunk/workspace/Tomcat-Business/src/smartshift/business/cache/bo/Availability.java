package smartshift.business.cache.bo;

import java.util.Date;
import org.joda.time.LocalTime;
import smartshift.business.hibernate.dao.AvailabilityInstanceDAO;
import smartshift.business.hibernate.dao.AvailabilityTemplateDAO;
import smartshift.business.hibernate.model.AvailabilityInstanceModel;
import smartshift.business.hibernate.model.AvailabilityModel;
import smartshift.business.hibernate.model.AvailabilityTemplateModel;
import smartshift.business.hibernate.model.InstanceInterface;
import smartshift.business.hibernate.model.TemplateInterface;
import smartshift.common.util.log4j.SmartLogger;

public abstract class Availability extends CachedObject implements Templatable {
    public static final String TYPE_IDENTIFIER = "A";
    
    private static final SmartLogger logger = new SmartLogger(Availability.class);
    
    private LocalTime _time;
    private int _duration;  //in minutes
    private int _repeatEvery;
    private int _repeatCount;
    private int _repeatOffset;
    private boolean _unavailable;
       
    private AvailabilityModel _availModel;
    
    
    public static final class AvailabilityArchetype extends Availability implements TemplatableArchetype<Availability>{
        
        public AvailabilityTemplateDAO _templateDAO;
        public AvailabilityInstanceDAO _instanceDAO;
        
        public AvailabilityTemplateModel _templateModel;
        public AvailabilityInstanceModel _instanceModel;
        
        public AvailabilityArchetype(Cache cache, AvailabilityTemplateDAO templateDAO, AvailabilityInstanceDAO instanceDAO) {
            super(cache, null, 0, 0, 0, 0, false);
            _templateDAO = templateDAO;
            _instanceDAO = instanceDAO;
        }
        
        public Availability load(int id) {
            return Availability.load(getCache(), id);
        }
        
        public TemplateInterface saveTemplate(Template<Availability> template, String name, Employee owner) {
            try {
                if(_templateModel != null) {
                    _templateModel.setName(name);
                    _templateModel.setEmployeeID(owner.getID());
                    _templateDAO.update(_templateModel);
                } else {
                    _templateModel = _templateDAO.add(name, owner.getID()).execute();
                    for(Availability a : template.getComponents())
                        a.save();
                }
                TemplateInterface out = _templateModel;
                _templateModel = null;
                return out;
            } catch (Exception e) {
                getLogger().warn("Failed to save the template!", e);
                return null;
            }
        }
        
        public InstanceInterface saveInstance(Instance<Availability> instance, int templateID, Object ...params ) {
            try {
                if(_instanceModel != null) {
                    _instanceModel.setStartDate((Date) params[0]);
                    _instanceModel.setEndDate((Date) params[1]);
                    _instanceDAO.update(_instanceModel);
                } else {
                    _instanceModel = _instanceDAO.add(templateID, (Date) params[0], (Date) params[1]).execute();
                }
                InstanceInterface out = _instanceModel;
                _instanceModel = null;
                return out;
            } catch (Exception e) {
                getLogger().warn("Failed to save the instance!", e);
                return null;
            }
        }

        @Override
        public SmartLogger getLogger() {
            return logger;
        }
    }
    
    public Availability(Cache cache, LocalTime time, int duration, int repeatEvery, int repeatCount, int repeatOffset, boolean unavailable) {
        super(cache);
        _time = time;
        _duration = duration;
        _repeatEvery = repeatEvery;
        _repeatCount = repeatCount;
        _repeatOffset = repeatOffset;
        _unavailable = unavailable;
    }
    
    private Availability(Cache cache, AvailabilityModel model) {
        this(cache, null , model.getDuration(), model.getRepeatEvery(), model.getRepeatCount(), model.getRepeateOffset(), model.getUnavailable());
        _time = LocalTime.fromMillisOfDay(model.getStart() * 1000);
        _availModel = model;
    }

    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    @Override
    public int getID() {
        if(_availModel != null)
            return _availModel.getId();
        return -1;
    }

    @Override
    public void save() {
        // TODO Auto-generated method stub    
    }
    
    public void addToTemplate(Template<Availability> template) {
        template.add(this);
    }

    @Override
    public void loadAllChildren() {
        // do nothing
    }

    public static Availability load(Cache cache, int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static AvailabilityArchetype getArchetype(Cache cache) {
        return new AvailabilityArchetype(cache, cache.getDAOContext().dao(AvailabilityTemplateDAO.class), cache.getDAOContext().dao(AvailabilityInstanceDAO.class));
    }
}
