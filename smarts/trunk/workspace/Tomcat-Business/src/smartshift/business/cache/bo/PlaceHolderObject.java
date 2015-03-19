package smartshift.business.cache.bo;

public class PlaceHolderObject extends CachedObject {
    private final String _type;
    private final Integer _id;
    
    public PlaceHolderObject(Cache cache, String type, Integer id) {
        super(cache);
        _type = type;
        _id = id;
    }

    @Override
    public String typeCode() {
        return _type;
    }

    @Override
    public int getID() {
        return _id;
    }

    @Override
    public void save() {
    }

    @Override
    public void loadAllChildren() {
    }

}
