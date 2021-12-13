package util;


/**
 * Database class for the any Database database. If the database needs any special handling;
 * then you should implement your database specific class.
 *
 */
public class GenericDatabase extends Database
{

  /**
   * Abstrct method to process a database type. Sometimes database
   * types are not reported exactly as they need to be for proper
   * syntax. This method corrects the database type and size.
   * @param type The type reported
   * @param i The size of this column
   * @return The properly formatted type, for this database
   */
  public String processType(String type,int size)
  {
    String usigned = "UNSIGNED";
    int i = type.indexOf(usigned);
    if( i!=-1 )
      type = type.substring(0,i)+type.substring(i+usigned.length());

    if( type.equalsIgnoreCase("varchar") && (size==65535) )
      type = "TEXT";

    return type.trim();
  }
}