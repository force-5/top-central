package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeedUtil {

    public static void createTable(Database source, Database target, String table) throws DatabaseException {
        String sql;

        // if the table already exists, then drop it
        if (target.tableExists(table)) {
            sql = source.generateDrop(table);
            target.execute(sql);
        }

        // now create the table
        sql = source.generateCreate(table);
        target.execute(sql);
    }

    public static Boolean truncateTable(Database target, String table) throws DatabaseException {
        String truncateSQL = "TRUNCATE TABLE " + table;
        PreparedStatement statement = null;
        statement = target.prepareStatement(truncateSQL);
        target.execute(truncateSQL);
        System.out.println("Truncated data from table " + table);
        try {
            if (statement != null)
                statement.close();
        }
        catch (SQLException e) {
            throw (new DatabaseException(e));
        }
        return true;
    }

    public static List<String> createTables(Database source, Database target) throws DatabaseException {
        System.out.println("Create tables.");
        Collection<String> list = source.listTables();
        List<String> tables = new ArrayList<String>();
        for (String table : list) {
            try {
                System.out.println("Create table: " + table);
                createTable(source, target, table);
                tables.add(table);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        return tables;
    }

    public static void copyTableData(Database source, Database target, List<String> tables) throws DatabaseException, SQLException {
        for (String table : tables) {
            copyTable(source, target, table, table);
        }
    }

    public static void exportDatabase(Database source, Database target) throws DatabaseException, SQLException {
        List<String> tables = createTables(source, target);
        copyTableData(source, target, tables);
    }


    public static void copyTable(Database source, Database target, String sourceTable, String targetTable) throws RuntimeException, SQLException, DatabaseException {
        if (target.tableExists(targetTable)) {
            truncateTable(target, targetTable);
        } else {
            createTable(source, target, targetTable);
        }

        StringBuffer selectSQL = new StringBuffer();
        StringBuffer insertSQL = new StringBuffer();
        StringBuffer values = new StringBuffer();

        Collection<String> columns = source.listColumns(sourceTable);
        columns.remove("ID");
        columns.remove("INSERT_DT");
        columns.remove("UPDATE_DT");

        System.out.println("Begin copy: " + sourceTable);

        selectSQL.append("SELECT ");
        insertSQL.append("INSERT INTO ");
        insertSQL.append(targetTable);
        insertSQL.append("(");

        boolean first = true;
        for (String column : columns) {
            if (!first) {
                selectSQL.append(",");
                insertSQL.append(",");
                values.append(",");
            } else
                first = false;

            selectSQL.append(column);
            insertSQL.append(column);
            values.append("?");
        }
        selectSQL.append(" FROM ");
        selectSQL.append(sourceTable);

        insertSQL.append(") VALUES (");
        insertSQL.append(values);
        insertSQL.append(")");

        // now copy
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = target.prepareStatement(insertSQL.toString());
            rs = source.executeQuery(selectSQL.toString());

            int rows = 0;

            while (rs.next()) {
                rows++;
                for (int i = 1; i <= columns.size(); i++) {
                    statement.setString(i, rs.getString(i));
                }
                statement.execute();
            }

            System.out.println("Copied " + rows + " rows.");
            System.out.println("");
        }
        catch (SQLException e) {
            throw (new DatabaseException(e));
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e) {
                throw (new DatabaseException(e));
            }
            try {
                if (rs != null)
                    statement.close();
            }
            catch (SQLException e) {
                throw (new DatabaseException(e));
            }
        }
    }
}

