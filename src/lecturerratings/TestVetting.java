/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturerratings;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Alex
 */
public class TestVetting
{

    Scanner scn = new Scanner(System.in);

    protected static int numStudents;
    protected static int[][] rates;
    private Connection connection;

    public void run() throws Exception
    {
        dbConnection();
        insertToDatabase();
        show();
    }

    //Establishes Connection with a local Database            
    private void dbConnection() throws Exception
    {

        String url = "jdbc:mysql://localhost:3306/assessment";
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
        {
            System.out.println("Could not load driver.");
        }

        try
        {
            connection = DriverManager.getConnection(url, "root", "password");
            System.out.println("connected to DB");
        } catch (SQLException e)
        {
            System.out.println("could not connect to DB");
        }

    }

    //Creates a table in the connected Database if one does not exist
    private void createTable() throws Exception
    {
        try
        {
            PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS lecturer(student int not null, assessment1 int not null,assessment2 int not null,assessment3 int not null,assessment4 int not null,assessment5 int not null);");
            createTable.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("failed to create table");
            System.out.println(e);
        } finally
        {
            System.out.println("createTable ran.");
        }

    }

    //Deletes the contents of the table if any
    private void clearTable() throws Exception
    {
        try
        {
            PreparedStatement clear = connection.prepareStatement("delete from assessment.lecturer;");
            clear.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e);
        } finally
        {
            System.out.println("Table cleared.");
        }

    }

    //Calls enterRates() and pushes entered data to the table
    private void insertToDatabase() throws Exception
    {
        createTable();
        clearTable();
        enterRates();
        try
        {
            for (int i = 0; i < numStudents; i++)
            {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO lecturer(student, assessment1, assessment2, assessment3, assessment4, assessment5) VALUES ('" + (i + 1) + "', '" + rates[i][0] + "', '" + rates[i][1] + "', '" + rates[i][2] + "', '" + rates[i][3] + "', '" + rates[i][4] + "');");
                insert.executeUpdate();
                System.out.println("Table updated");
            }

        } catch (SQLException e)
        {
            System.out.println("fail!");
            System.out.println(e);
        } finally
        {
            System.out.println("Insert database function complete.");
        }
    }

    //Allows user to enter the lecturer assessments for each student
    private void enterRates()
    {
        System.out.println("Enter the number of students.");
        do
        {
            numStudents = scn.nextInt();

            if (numStudents < 1)
            {
                System.out.println("There must be at least one student");
            } else if (numStudents > 10)
            {
                System.out.println("There cannot be more than ten students");
            }
        } while (numStudents < 1 | numStudents > 10);

        System.out.println("Enter your 5 ratings for the lecturer");

        rates = new int[numStudents][5];

        for (int i = 0; i < numStudents; i++)
        {
            System.out.println("Student: " + (i + 1));
            for (int j = 0; j < 5; j++)
            {
                do
                {
                    rates[i][j] = scn.nextInt();
                    if (rates[i][j] > 10)
                    {
                        System.out.println("Rating cannot be more than 10");
                    } else if (rates[i][j] < 0)
                    {
                        System.out.println("Rating cannot be less than 0");
                    }
                } while (rates[i][j] < 0 | rates[i][j] > 10);
            }
        }
    }

    //Selects and displays data stored in the Database table
    private void outPutTable()
    {
        try
        {
            PreparedStatement SELECT = connection.prepareStatement("SELECT * FROM lecturer;");
            ResultSet result = SELECT.executeQuery();
            System.out.println("===================================================================================================");
            System.out.printf("Output From Database table: lecturers");
            System.out.printf("%nStudent          Assessment1      Assessment2      Assessment3      Assessment4      Assessment5 %n");
            while (result.next())
            {
                System.out.printf("%-17s", result.getString("student"));
                System.out.printf("%-17s", result.getString("assessment1"));
                System.out.printf("%-17s", result.getString("assessment2"));
                System.out.printf("%-17s", result.getString("assessment3"));
                System.out.printf("%-17s", result.getString("assessment4"));
                System.out.printf("%-17s", result.getString("assessment5"));
                System.out.println("");
            }
        } catch (Exception e)
        {
            System.out.println("print from table failed");
            System.out.println(e);
        }
    }

    //calls methods that process and display output
    private void show()
    {
        Vetting vet = new Vetting();
        vet.outputRates();
        vet.processRates();
        outPutTable();
    }
}
