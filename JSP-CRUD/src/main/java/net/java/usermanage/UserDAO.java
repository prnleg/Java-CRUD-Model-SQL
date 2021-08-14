package net.java.usermanage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import net.java.usermanage.model.User;

// para uso do mySQL, talvez de para modificar para mongoDB
public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/sampledb";
	private String jdbcUser = "aula";
	private String jdbcSenha = "123";
	 
	
	private static final String INSERT_USER_SQL = "INSERT INTO users" 
			+ " (user_id, name, birth, mail, work) VALUES (?, ?, ?, ?, ?);";
	private static final String SELECT_USER_BY_ID = "SELECT user_id, name, birth, mail, work"
			+ " FROM users WHERE user_id = ? ;";
	private static final String SELECT_ALL_USERS = "SELECT * FROM users ;";
	//nao precisa do delete para esse
	//private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE user_id = ?;";
	private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?, mail = ?, "
			+ "birth = ?, work = ?, WHERE user_id = ?";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcSenha);
			
		} 
		catch (SQLException e) { e.printStackTrace();}
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		
		return connection;
	}

	//* SELECT by user_id
	public User selectUser(int id) {
		
		User user = null;
		try(Connection connection = getConnection(); 
				PreparedStatement prepStat = connection.prepareStatement(SELECT_USER_BY_ID);) {
			
			prepStat.setInt(1, id);
			System.out.println(prepStat);
			ResultSet res = prepStat.executeQuery();
			
			while (res.next()) {
				String name = res.getString("name");
				String birth = res.getString("birth");
				String mail = res.getString("mail");
				String work = res.getString("work");
				user = new User(id, name, birth, mail, work);
			}
			
		} catch (Exception e) { e.printStackTrace(); }
		
		return user;
	}


	//* SELECT ALL
	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();
		
		@SuppressWarnings("unused")
		User user = null;
		try(Connection connection = getConnection(); 
				PreparedStatement prepStat = connection.prepareStatement(SELECT_ALL_USERS);) {
			
			System.out.println(prepStat);
			ResultSet res = prepStat.executeQuery();
			
			while (res.next()) {
				int id = res.getInt("user_id");
				String name = res.getString("name");
				String birth = res.getString("birth");
				String mail = res.getString("mail");
				String work = res.getString("work");
				users.add(new User(id, name, birth, mail, work));
			}
			
		} catch (Exception e) { e.printStackTrace(); }
		
		return users;
	}
	
	
	//* INSERT
	public void insertUser(User user) throws SQLException {
		try(Connection connection = getConnection(); 
		PreparedStatement prepStat = connection.prepareStatement(INSERT_USER_SQL);){
			
			prepStat.setString(1, user.getName());
			prepStat.setString(2, user.getBirth());
			prepStat.setString(3, user.getEmail());
			prepStat.setString(4, user.getWork());
			
			prepStat.executeUpdate();
			
		} catch (Exception e) { e.printStackTrace(); }
		
		
	}
	
	//* UPDATE by user_id
	public boolean updateUser(User user) throws SQLException {
		boolean rowUp;
		
		try(Connection connection = getConnection(); 
		PreparedStatement prepStat = connection.prepareStatement(UPDATE_USERS_SQL);){
			
			prepStat.setString(1, user.getName());
			prepStat.setString(2, user.getBirth());
			prepStat.setString(3, user.getEmail());
			prepStat.setString(4, user.getWork());
			
			rowUp = prepStat.executeUpdate() > 0;
		}
		
		return rowUp;
	}

	//* (DELETE by user_id)
	public boolean deleteUser(int id) throws SQLException {
		boolean row;
		try(Connection connection = getConnection(); 
		PreparedStatement prepStat = connection.prepareStatement(UPDATE_USERS_SQL);){
			
			prepStat.setInt(1, id);
			row = prepStat.executeUpdate() > 0;
		}
		return row;
	}
	
}
