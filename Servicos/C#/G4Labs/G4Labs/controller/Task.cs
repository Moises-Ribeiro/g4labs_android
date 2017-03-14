using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Web;

namespace G4Labs.controller
{

    public class Task
    {
        private string DATABASE = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "tasks.db");

        public Task()
        {
            if (!File.Exists(DATABASE))
            {
                SQLiteConnection.CreateFile(DATABASE);
                using (SQLiteConnection connection = GetConnection())
                {
                    connection.Open();
                    using (SQLiteCommand command = new SQLiteCommand(connection))
                    {
                        command.CommandText = "CREATE TABLE IF NOT EXISTS task (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(50), completed INTEGER,description VARCHAR(150));";
                        command.ExecuteNonQuery();
                    }
                    connection.Close();
                }
            }
                
        }


        public IList<model.Task> GetTasks()
        {
            IList<model.Task> tasks = null;
            using (SQLiteConnection connection = GetConnection())
            {
                connection.Open();
                using (SQLiteCommand command = new SQLiteCommand(connection))
                {
                    command.CommandText = "SELECT * FROM task";
                    using (var reader = command.ExecuteReader())
                    {
                        tasks = new List<model.Task>();
                        while (reader.Read())
                        {
                            model.Task task = new model.Task();
                            task.Id = Convert.ToInt32(reader["id"]);
                            task.Title = Convert.ToString(reader["title"]);
                            task.Completed = Convert.ToInt32(reader["completed"]);
                            task.Description = Convert.ToString(reader["description"]);
                            tasks.Add(task);
                        }
                    }
                }
                connection.Close();
            }
            return tasks;
        }

        public model.Task GetTask(int id)
        {
            model.Task task = null;
            using (SQLiteConnection connection = GetConnection())
            {
                connection.Open();
                using (SQLiteCommand command = new SQLiteCommand(connection))
                {
                    command.CommandText = "SELECT * FROM task";
                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            task = new model.Task();
                            task.Id = Convert.ToInt32(reader["id"]);
                            task.Title = Convert.ToString(reader["title"]);
                            task.Completed = Convert.ToInt32(reader["completed"]);
                            task.Description = Convert.ToString(reader["description"]);
                        }
                    }
                }
                connection.Close();
            }
            return task;
        }

        private SQLiteConnection GetConnection()
        {
            return new SQLiteConnection("Data Source=" + DATABASE);
        }

    }
}