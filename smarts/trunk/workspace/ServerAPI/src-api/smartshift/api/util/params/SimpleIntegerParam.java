package smartshift.api.util.params;

import javax.ws.rs.WebApplicationException;

import smartshift.api.util.APIResultUtil;

public class SimpleIntegerParam {
  private final Integer integer;
  private final String originalValue;
  
  public SimpleIntegerParam(String originalValue) throws WebApplicationException {
    try {
      this.originalValue = originalValue;
      this.integer = new Integer(originalValue);
    } catch (IllegalArgumentException e) {
		throw APIResultUtil.getBadRequestException("Invalid integer: " + originalValue);
    }
  }
  
  public Integer getInteger() {
    return integer;
  }
  
  public String getOriginalValue() {
    return originalValue;
  }
}