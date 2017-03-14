package br.com.g4labs.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Task {

	private Connection connection;

	public Task() {
		try {
			Class.forName("org.sqlite.JDBC");

			this.connection = getConnection();
			Statement statement = this.connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS task ( " + "id  INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "title VARCHAR(50), " + "completed INTEGER, " + "description VARCHAR(150) " + ");");
			statement.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<br.com.g4labs.model.Task> getTasks() {
		List<br.com.g4labs.model.Task> tasks = null;

		try {
			this.connection = getConnection();
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM task");

			tasks = new ArrayList<br.com.g4labs.model.Task>();
			while (rs.next()) {
				br.com.g4labs.model.Task task = new br.com.g4labs.model.Task();

				task.setId(rs.getInt("id"));
				task.setTitle(rs.getString("title"));
				task.setCompleted(rs.getInt("completed"));
				task.setDescription(rs.getString("description"));

				tasks.add(task);
			}
			rs.close();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}

		return tasks;
	}

	public br.com.g4labs.model.Task getTask(Integer id) {
		br.com.g4labs.model.Task task = null;

		try {
			this.connection = getConnection();
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM task WHERE id = " + id);

			while (rs.next()) {
				task = new br.com.g4labs.model.Task();

				task.setId(rs.getInt("id"));
				task.setTitle(rs.getString("title"));
				task.setCompleted(rs.getInt("completed"));
				task.setDescription(rs.getString("description"));
			}
			rs.close();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null && !connection.isClosed())
					connection.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}

		return task;
	}

	private Connection getConnection() throws SQLException, IOException {
		if (connection == null || connection.isClosed()) {

			File db = new File(System.getProperty("user.home"), "tasks.db");
			if (!db.exists()) {
				db.createNewFile();
			}

			String database = db.getAbsolutePath();
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
		}
		return this.connection;
	}
}
