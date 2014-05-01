/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonialants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George McDaid
 */
public class Utility {

    public static boolean DEBUG = true;
    private static boolean LOGGING = false;

    private static File LogFile;
    private static BufferedWriter fout;

    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String protocol = "jdbc:derby:";
    private static Connection conn = null;
    private static ArrayList statements = new ArrayList(); // list of Statements, PreparedStatements
    public static PreparedStatement psInsert = null;
    public static PreparedStatement psUpdate = null;
    public static PreparedStatement psSelect = null;
    private static Statement s = null;
    private static ResultSet rs = null;
    private static String framework = "embedded";
    
    //terrain column defs
    public static int TERRAIN_ID = 1;
    public static int TERRAIN_GRIDX = 2;
    public static int TERRAIN_GRIDY = 3;
    public static int TERRAIN_TYPE = 4;
    public static int TERRAIN_RESOURCES = 5;
    public static int TERRAIN_RLAYER = 6;
    public static int TERRAIN_FLAYER= 7;
    public static int TERRAIN_RINTENSITY = 8;
    public static int TERRAIN_FINTENSITY = 9;
    
    //ant column defs
    public static int ANT_ID = 1;
    public static int ANT_TYPE = 2;
    public static int ANT_PIXELX = 3;
    public static int ANT_PIXELY = 4;
    public static int ANT_INTENDEDBEARING = 5;
    public static int ANT_LIFESPAN = 6;
    public static int ANT_FOOD = 7;
    public static int ANT_DESTINATIONX = 8;
    public static int ANT_DESTINATIONY = 9;
    public static int ANT_ORIGINX = 10;
    public static int ANT_ORIGINY = 11;
    public static int ANT_RP_LEVEL = 12;
    public static int ANT_FP_LEVEL = 13;
    public static int ANT_STATE = 14;
    
    //gui info defs
    public static int GUI_ID = 1;
    public static int GUI_NAME = 1;
    public static int GUI_VALUE = 1;
    
    
    public static int GUI_NETRES = 2;
    public static int GUI_GATHER = 3;
    public static int GUI_BUILDER = 4;
    public static int GUI_LEAVES = 5;
    public static int GUI_SCORE = 7;
    
    public static int GUI_BALANCE = 7;
    public static int GUI_SPAWN = 7;
    public static int GUI_EVAP = 7;
    public static int GUI_LAY = 7;
    public static int GUI_LIFESPAN = 7;
    public static int GUI_FOOD = 7;
    
    public Utility() {}

    public static void o(Object s) {
        if (DEBUG) {
            System.out.println(s.toString());
        }
    }

    public static void o(int s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    public static void o() {
        if (DEBUG) {
            System.out.println();
        }
    }

    public static void initLog(String filename) {
        LogFile = new File(filename);
        if (LOGGING) {
            try {

                if (!LogFile.exists()) {
                    LogFile.createNewFile();
                }

                FileWriter fw = new FileWriter(LogFile.getAbsoluteFile());
                fout = new BufferedWriter(fw);

                fout.write("delta, netRes, ants, leaves, res, evap, lay\n");

                LOGGING = true;

            } catch (IOException ex) {
                Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void logCycle(int delta, int netResources, int ants, int leaves, int resources, double evap, int lay) {
        if (LOGGING) {
            try {
                fout.write(delta + "," + netResources + "," + ants + "," + leaves + "," + resources + "," + evap + "," + lay + "\n");
            } catch (IOException ex) {
                Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void closeLog() {
        try {
            if (LOGGING) {
                fout.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initDB() {
        try {
            Class.forName(driver).newInstance();

            conn = DriverManager.getConnection(protocol + "src/colonialdisplay/derbyAntDB;create=true");
            
            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
            conn.setAutoCommit(false);
            
            s = conn.createStatement();
            statements.add(s);

            // We create tables...
            try {

                s.execute("CREATE TABLE terrain"
                        + "(id INTEGER PRIMARY KEY,"
                        + "gridx INTEGER,"
                        + "gridy INTEGER,"
                        + "type VARCHAR(15),"
                        + "resources INTEGER,"
                        + "rlayer INTEGER,"
                        + "flayer INTEGER,"
                        + "rintensity DOUBLE,"
                        + "fintensity DOUBLE)");
            } catch (SQLException e) {
                printSQLException(e);
            }
            
            try {
                s.execute("CREATE TABLE ant"
                        + "(id INTEGER PRIMARY KEY,"
                        + "type VARCHAR(15),"
                        + "pixelx DOUBLE,"
                        + "pixely DOUBLE,"
                        + "intendedBearing INTEGER,"
                        + "lifespan INTEGER,"
                        + "food BOOLEAN,"
                        + "destinationx INTEGER,"
                        + "destinationy INTEGER,"
                        + "originx INTEGER,"
                        + "originy INTEGER,"
                        + "rp_level DOUBLE,"
                        + "fp_level DOUBLE,"
                        + "state INTEGER)");

            } catch (SQLException e) {
                printSQLException(e);
            }
            
            try {
                s.execute("CREATE TABLE user_pref"
                        + "(id INTEGER PRIMARY KEY,"
                        + "name VARCHAR(50),"
                        + "value DOUBLE)");
            } catch (SQLException e) {
                printSQLException(e);
            }
            
            conn.commit();
            
            s = conn.createStatement();
            statements.add(s);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void shutdownDB() {
        try {
            // the shutdown=true attribute shuts down Derby
            DriverManager.getConnection(protocol + "src/colonialdisplay/derbyAntDB;shutdown=true");
        } catch (SQLException se) {
            if (((se.getErrorCode() == 50000 || se.getErrorCode() == 45000)
                    && ("XJ015".equals(se.getSQLState()) || "08006".equals(se.getSQLState())))) {
                // we got the expected exception
                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            } else {
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
                System.err.println("Derby did not shut down normally");

            }
        } finally {
            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }

            // Statements and PreparedStatements
            int i = 0;
            while (!statements.isEmpty()) {
                // PreparedStatement extend Statement
                Statement st = (Statement) statements.remove(i);
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException sqle) {
                    printSQLException(sqle);
                }
            }

            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }

    }

    public static void prepareInsert(String statement) {
        try {
            psInsert = conn.prepareStatement(statement);
            statements.add(psInsert);
        } catch (SQLException ex) {
            printSQLException(ex);
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void prepareUpdate(String statement) {
        try {
            psUpdate = conn.prepareStatement(statement);
            statements.add(psUpdate);
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void prepareSelect(String statement) {
        try {
            psSelect = conn.prepareStatement(statement);
            statements.add(psSelect);
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            return s.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void printSQLException(SQLException e) {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null) {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
    
    public static void commit(){
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void truncate(String table){
        try {
            s.execute("truncate table "+table);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
