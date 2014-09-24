package smartshift.api.util.params;

import javax.ws.rs.WebApplicationException;
import smartshift.api.util.APIResultUtil;

/**
 * @author fevans
 * @version Sept 18, 2014
 */
public class SimpleIntegerParam {
  private final Integer integer;
  private final String originalValue;
  
    /**
     * constructor for a new integer param
     * 
     * @param originalValue
     * @throws WebApplicationException
     */
  public SimpleIntegerParam(String originalValue) throws WebApplicationException {
    try {
      this.originalValue = originalValue;
      this.integer = new Integer(originalValue);
    } catch (IllegalArgumentException e) {
		throw APIResultUtil.getBadRequestException("Invalid integer: " + originalValue);
    }
  }
  
    /**
     * @return the integer
     */
  public Integer getInteger() {
    return integer;
  }
  
    /**
     * @return the original value
     */
  public String getOriginalValue() {
    return originalValue;
  }
}