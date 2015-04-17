package smartshift.business.cache.bo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import smartshift.business.hibernate.dao.EmployeeScheduleDAO;
import smartshift.business.hibernate.dao.EmployeeScheduleShiftDAO;
import smartshift.business.hibernate.dao.ScheduleDAO;
import smartshift.business.hibernate.model.EmployeeScheduleModel;
import smartshift.business.hibernate.model.EmployeeScheduleShiftModel;
import smartshift.business.hibernate.model.ScheduleModel;
import smartshift.common.util.UID;

/**
 * an instance of a schedule of shifts
 * @author drew
 *
 */
public class ScheduleInstance extends CachedObject{
    /** the type identifier for a schedule instance */
    public static final String TYPE_IDENTIFIER = "SI";
    
    private ScheduleTemplateVersion _template;
    private LocalDate _startDate;
    // from surrogate key of the employee schedule map to employee
    private Map<Integer, Employee> _employees;
    private Map<Shift, Set<Employee>> _shiftEmployees;
    private Map<Employee, Set<Shift>> _employeeShifts;
    // is the schedule ready to be published to employees?
    private boolean _locked;
    
    // are all the employees loaded in the current view
    private transient boolean _allEmployeesLoaded;
    
    
    private ScheduleInstance(Cache cache, ScheduleTemplateVersion template, LocalDate startDate) {
        super(cache);
        _template = template;
        _startDate = startDate;
        _locked = false;
        _employees = new HashMap<Integer, Employee>();
        _shiftEmployees = new HashMap<Shift, Set<Employee>>();
        _employeeShifts = new HashMap<Employee, Set<Shift>>();
        _allEmployeesLoaded = true;
    }
    
    private ScheduleInstance(Cache cache, int id) {
        super(cache, id);
    }
    
    private ScheduleInstance(Cache cache, int id, Employee employee) {
        super(cache, id);
        _allEmployeesLoaded = false;
    }
    
    /**
     * publish the schedule to all relevant employees
     */
    public synchronized void publish() {
        _locked = true;
        if(isInitialized() && _allEmployeesLoaded) {
            for(Integer surrogate : _employees.keySet()) {
                EmployeeScheduleDAO dao = getDAO(EmployeeScheduleDAO.class);
                EmployeeScheduleModel esm = dao.uniqueByID(surrogate).execute();
                esm.setLocked(true);
                getDAO(EmployeeScheduleDAO.class).update(esm).enqueue();
            }
        }
        
    }
    
    /**
     * @return whether the schedule is ready to be viewed by employees
     */
    public boolean isLocked() {
        return _locked;
    }

    /**
     * @see smartshift.common.util.Identifiable#typeCode()
     */
    @Override
    public String typeCode() {
        return TYPE_IDENTIFIER;
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#loadAllChildren()
     */
    @Override
    public void loadAllChildren() {
        for(Shift sh : _template.getShifts()) {
            if(!_shiftEmployees.containsKey(sh))
                _shiftEmployees.put(sh, new HashSet<Employee>());
        }
    }
    
    /**
     * add an employee to a shift
     * @param shift the shift to add the employee to
     * @param emp the employee to add
     */
    public void addEmployeeShift(Shift shift, Employee emp) {
        if(!_shiftEmployees.containsKey(shift)) {
            throw new RuntimeException("attempting to add employee to a shift out of scope!");
        }
        synchronized(this) {
            _shiftEmployees.get(shift).add(emp);
            if(!_employeeShifts.containsKey(emp))
                _employeeShifts.put(emp, new HashSet<Shift>());
            _employeeShifts.get(emp).add(shift);
            if(isInitialized()) {
                EmployeeScheduleDAO esd = getDAO(EmployeeScheduleDAO.class);
                int surrogate = 0;
                if(esd.linkCount(emp.getID(), getID()).execute() < _employeeShifts.keySet().size()) {
                    surrogate = esd.getNextID(); 
                    esd.link(emp.getID(), getID()).enqueue();
                    _employees.put(surrogate, emp);
                } else {
                    surrogate = esd.uniqueByEmployeeSchedule(emp.getID(), getID()).execute().getId();
                    _employees.put(surrogate, emp);
                }
                EmployeeScheduleShiftModel model = new EmployeeScheduleShiftModel();
                model.setEmployeeScheduleID(surrogate);
                model.setShiftID(shift.getID());
                model.setGroupID(shift.getGroupID());
                model.setRoleID(shift.getRoleID() != 0? shift.getRoleID() : null);
                getDAO(EmployeeScheduleShiftDAO.class).add(model).enqueue();
            }
        }
    }
    
    /**
     * remove an employee from a shift
     * @param shift the shift to remove the employee from
     * @param emp the employee to remove
     */
    public void removeEmployeeShift(Shift shift, Employee emp) {
        if(!_shiftEmployees.containsKey(shift)) {
            throw new RuntimeException("attempting to remove employee from a shift out of scope!");
        }
        synchronized(this) {
            _shiftEmployees.get(shift).remove(emp);
            _employeeShifts.get(emp).remove(shift);
            int surrogate = 0;
            if(_employeeShifts.get(emp).isEmpty()) {
                _employeeShifts.remove(emp);
                if(isInitialized()) {
                    EmployeeScheduleDAO esd = getDAO(EmployeeScheduleDAO.class);
                    surrogate = esd.uniqueByEmployeeSchedule(emp.getID(), getID()).execute().getId();
                    _employees.remove(surrogate);
                    esd.unlink(emp.getID(), getID()).enqueue();
                }
            }
            if(isInitialized()) {
                EmployeeScheduleShiftModel model = new EmployeeScheduleShiftModel();
                model.setEmployeeScheduleID(surrogate);
                model.setShiftID(shift.getID());
                getDAO(EmployeeScheduleShiftDAO.class).delete(model);
            }
        }
    }
    
    /**
     * load all shifts for all employees currently in scope
     */
    public void loadShifts() {
        for(Integer surrogate : _employees.keySet()) {
            Employee emp = _employees.get(surrogate);
            if(!_employeeShifts.containsKey(emp)) {
                _employeeShifts.put(emp, new HashSet<Shift>());
                for(EmployeeScheduleShiftModel essm : getDAO(EmployeeScheduleShiftDAO.class).list(Restrictions.eq("employeeScheduleID", surrogate)).execute()) {
                    Shift shift = Shift.load(getCache(), essm.getShiftID());
                    _employeeShifts.get(emp).add(shift);
                    if(!_shiftEmployees.containsKey(shift))
                        _shiftEmployees.put(shift, new HashSet<Employee>());
                    _shiftEmployees.get(shift).add(emp);
                }
            }
        }
    }

    /**
     * @see smartshift.common.util.hibernate.Stored#getModel()
     */
    @Override
    public ScheduleModel getModel() {
        ScheduleModel model = new ScheduleModel();
        model.setId(getID());
        model.setStartDate(_startDate.toDate());
        model.setScheduleTemplateVersionID(_template.getID());
        return model;
    }

    /**
     * @see smartshift.business.cache.bo.CachedObject#init()
     */
    @Override
    public void init() {
        ScheduleModel model = getDAO(ScheduleDAO.class).uniqueByID(getID()).execute();
        _startDate = new LocalDate(model.getStartDate());
        _template = ScheduleTemplateVersion.load(getCache(), model.getScheduleTemplateVersionID());
        _shiftEmployees = new HashMap<Shift, Set<Employee>>();
        _employeeShifts = new HashMap<Employee, Set<Shift>>();
    }
    
    /**
     * @see smartshift.business.cache.bo.CachedObject#initialize(Employee)
     */
    @Override
    public void initialize(Employee emp) {
        super.initialize(emp);
        EmployeeScheduleDAO dao = getDAO(EmployeeScheduleDAO.class);
        Criterion schedMatch = Restrictions.eq("scheduleID", getID());
        if(_allEmployeesLoaded)
            return;
        if(_employees == null)
            _employees = new HashMap<Integer, Employee>();
        if(emp == null) {
            for(EmployeeScheduleModel esm : dao.list(schedMatch).execute()) {
                int surrogate = esm.getId();
                if(!_employees.containsKey(surrogate)) {
                    _employees.put(surrogate, Employee.load(getCache(), esm.getEmployeeID()));
                }
            }
            _allEmployeesLoaded = true;
        } else {
            if(!_employees.containsValue(emp)) {
                EmployeeScheduleModel model = dao.uniqueByEmployeeSchedule(emp.getID(), getID()).execute();
                _employees.put(model.getId(), emp);
            }
            _allEmployeesLoaded = dao.rowCount(schedMatch).execute() == _employees.size();    
        }
        loadShifts();
    }
    
    /**
     * create a new schedule instance
     * @param businessID the id of the business to create the schedule instance for
     * @param template the template to create the instance from
     * @param start the start date of the schedule instance
     * @return the new schedule instance
     */
    public static ScheduleInstance create(int businessID, ScheduleTemplateVersion template, LocalDate start) {
        Cache cache = Cache.getCache(businessID);
        ScheduleInstance sched = new ScheduleInstance(cache, template, start);
        ScheduleDAO dao = sched.getDAO(ScheduleDAO.class);
        sched.setID(dao.getNextID());
        dao.add(sched.getModel()).enqueue();
        cache.cache(new UID(sched), sched);
        return sched;
    }
    
    /**
     * load a schedule instance into memory (pulls from the cache 
     *   if it already exists in memory)
     * @param cache the cache to load into
     * @param schedID the id of the schedule to load
     * @return the schedule instance requested
     */
    public static ScheduleInstance load(Cache cache, int schedID) {
        UID uid = new UID(TYPE_IDENTIFIER, schedID);
        if(cache.contains(uid)) {
            ScheduleInstance sched = cache.getCached(uid, ScheduleInstance.class);
            synchronized(sched) {
                sched.initialize(null);
            }
            return sched;
        } else {    
            ScheduleInstance sched = new ScheduleInstance(cache, schedID);
            cache.cache(uid, sched);
            sched.loadAllChildren();
            sched.initialize(null);
            return sched;
        }
    }
    
    /**
     * load a schedule instance into memory for the scope of a single employee
     *   (pulls from the cache if it already exists in memory)
     * @param cache the cache to load into
     * @param scope the employee to load data relative to
     * @param schedID the id of the schedule to load
     * @return the schedule instance requested
     */
    public static ScheduleInstance load(Cache cache, int schedID, Employee scope) {
        UID uid = new UID(TYPE_IDENTIFIER, schedID);
        if(cache.contains(uid)) {
            ScheduleInstance sched = cache.getCached(uid, ScheduleInstance.class);
            synchronized(sched) {
                sched.initialize(scope);
            }
            return sched;
        }else {    
            ScheduleInstance sched = new ScheduleInstance(cache, schedID);
            cache.cache(uid, sched);
            sched.loadAllChildren();
            sched.initialize(null);
            return sched;
        }
    }
}
