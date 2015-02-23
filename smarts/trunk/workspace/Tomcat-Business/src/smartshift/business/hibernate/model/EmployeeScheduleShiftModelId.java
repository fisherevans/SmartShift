package smartshift.business.hibernate.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author "D. Fisher Evans <contact@fisherevans.com>"
 *
 */
@Entity
@Table(name = "EmpScheduleShift")
public class EmployeeScheduleShiftModelId implements Serializable {
    private static final long serialVersionUID = 6808506517477398625L;
    
    /**
     * employee schedule id
     */
    public Integer employeeScheduleID;

    /**
     * shift id
     */
    public Integer shiftID;

    /**
     * Initializes the object.
     */
    public EmployeeScheduleShiftModelId() {
        
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return employeeScheduleID.hashCode() + shiftID.hashCode()*31;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof EmployeeScheduleShiftModelId) {
            EmployeeScheduleShiftModelId other = (EmployeeScheduleShiftModelId) obj;
            return employeeScheduleID == other.employeeScheduleID && shiftID == other.shiftID;
        }
        return false;
    }
}

