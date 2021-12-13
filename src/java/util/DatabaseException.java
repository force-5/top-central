package util;

public class DatabaseException extends Exception
{
  /**
   * Serial id
   */
  private static final long serialVersionUID = 838904293060250128L;

  
  /**
   * Construct an exception based on another exception.
   * 
   * @param e The other exception.
   */
  public DatabaseException(Exception e)
  {
    super(e);
  }
}
