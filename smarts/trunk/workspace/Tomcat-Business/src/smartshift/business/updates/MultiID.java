package smartshift.business.updates;

import java.util.Arrays;

/**
 * @author D. Fisher Evans <contact@fisherevans.com>
 * @date Apr 21, 2015
 *
 * A class to be used as a dynamic/multi ID object
 */
public class MultiID {
    private Object[] _ids;
    
    /** creates the ID object
     * @param ids
     */
    public MultiID(Object ... ids) {
        _ids = ids;
    }

    /** 
     * @return the actual ID array
     */
    public Object[] getIDs() {
        return _ids;
    }

    /** 
     * @param position the position of the ID your after
     * @return the ID value for the position
     */
    public Object getID(int position) {
        return position >= 0 && position < _ids.length ? _ids[position] : null;
    }
    
    /**
     * @return number of IDs this object uses
     */
    public int getLength() {
        return _ids.length;
    }

    /** Overridden method - see parent javadoc
      * @see java.lang.Object#hashCode()
      */
    @Override
    public int hashCode() {
        return Arrays.hashCode(_ids);
    }

    /** Overridden method - see parent javadoc
      * @see java.lang.Object#equals(java.lang.Object)
      */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MultiID) {
            MultiID other = (MultiID) obj;
            if(other == null || this.getLength() != other.getLength())
                return false;
            Object a, b;
            for(int position = 0;position < this.getLength();position++) {
                a = this.getID(position);
                b = other.getID(position);
                if((a == null && b != null) || !a.equals(b)) // IF: a is null and b is not -OR- a does not equal b
                    return false;
            }
            return true;
        } else
            return false;
    }
}
