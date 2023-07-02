package Task1;
import java.sql.*;
import java.util.Scanner;

public class TaskManager
{
	


	    private static final String DB_URL = "jdbc:mysql://localhost:3306/management";
	    private static final String DB_USER = "root";
	    private static final String DB_PASS = "root";

	    public static void main(String[] args)
	    {
	        try 
	        {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/management", "root", "root");
	            
	            boolean exit = false;
	            while (!exit) 
	            {
	                System.out.println("\nTask Management System");
	                System.out.println("1. Add Task");
	                System.out.println("2. View Tasks");
	                System.out.println("3. Update Task");
	                System.out.println("4. Delete Task");
	                System.out.println("0. Exit");
	                System.out.print("Enter your choice: ");
	                
	                Scanner scanner = new Scanner(System.in);
	                int choice = scanner.nextInt();
	                
	                switch (choice) 
	                {
	                    case 1:
	                        addTask(connection);
	                        break;
	                    case 2:
	                        viewTasks(connection);
	                        break;
	                    case 3:
	                        updateTask(connection);
	                        break;
	                    case 4:
	                        deleteTask(connection);
	                        break;
	                    case 0:
	                        exit = true;
	                        System.out.println("Exiting the program");
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	           }
	            
	            connection.close();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	    
	    private static void addTask(Connection connection) throws SQLException
	    {
	    	    Scanner scanner = new Scanner(System.in);

	    	    System.out.print("Enter task name: ");
	    	    String name = scanner.nextLine();

	    	    String sql = "INSERT INTO tasks (Name) VALUES (?)";
	    	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) 
	    	    {
	    	        preparedStatement.setString(1, name);
	    	        int rowsAffected = preparedStatement.executeUpdate();

	    	        if (rowsAffected > 0)
	    	        {
	    	            System.out.println("Task added successfully!");
	    	        } else
	    	        {
	    	            System.out.println("Failed to add task. Please try again.");
	    	        }
	    	    }
	    	


	    }

	    private static void viewTasks(Connection connection) throws SQLException
	    {
	    	
	    	
	    	    String sql = "SELECT * FROM tasks";
	    	    try (Statement statement = connection.createStatement();
	    	         ResultSet resultSet = statement.executeQuery(sql)) {
	    	        while (resultSet.next()) {
	    	            int ID = resultSet.getInt("ID");
	    	            String name = resultSet.getString("Name");
	    	            String status = resultSet.getString("status");
	    	            System.out.println(ID + " | " + name + " | " + status);
	    	        }
	    	    }
	    	

	    }

	    private static void updateTask(Connection connection) throws SQLException
	    {
	    	
	    	    Scanner scanner = new Scanner(System.in);

	    	    System.out.print("Enter task ID: ");
	    	    int taskId = scanner.nextInt();
	    	    scanner.nextLine(); // Consume the newline character

	    	    System.out.print("Enter new status: ");
	    	    String newStatus = scanner.nextLine();

	    	    String sql = "UPDATE tasks SET status = ? WHERE ID = ?";
	    	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
	    	    {
	    	        preparedStatement.setString(1, newStatus);
	    	        preparedStatement.setInt(2, taskId);
	    	        int rowsAffected = preparedStatement.executeUpdate();

	    	        if (rowsAffected > 0)
	    	        {
	    	            System.out.println("Task updated successfully!");
	    	        } else 
	    	        {
	    	            System.out.println("Failed to update task. Please check the task ID and try again.");
	    	        }
	    	    }
	    	


	    }

	    private static void deleteTask(Connection connection) throws SQLException
	    {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter task ID: ");
	        int taskId = scanner.nextInt();

	        String sql = "DELETE FROM tasks WHERE ID= ?";

	    }
}
