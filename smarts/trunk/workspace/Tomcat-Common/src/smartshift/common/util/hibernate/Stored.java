package smartshift.common.util.hibernate;

public interface Stored {
    public void save();
    
    public void loadAllChildren();
}
