# encoding: UTF-8

#require 'redis'
#require 'json'

#redis = Redis.new(:host => '127.0.0.1', :port => 6379, :db => 0)

#redis.set('pessoas', '312312')
#print redis.push('pessoas')

require 'sqlite3'

filename = 'tasks.db'
db = SQLite3::Database.new filename

#if !File.file?(filename) 
  # Create a table
  #rows = db.execute <<-SQL
  #  create table numbers (
  #    name varchar(30),
  #    val int
  #  );
  #SQL

rows = db.execute <<-SQL
  CREATE TABLE IF NOT EXISTS task (
      id  INTEGER PRIMARY KEY AUTOINCREMENT,
      title VARCHAR(50),
      completed INTEGER,
      description VARCHAR(150)
  );
SQL
#end 

# Execute a few inserts
#{
#  "one" => 1,
#  "two" => 2,
#}.each do |pair|
#  db.execute "insert into numbers values ( ?, ? )", pair
#end


#Execute a few inserts
#db.execute "insert into task (title, completed, description) values ( 'title1', 0, 'title1' )"
#db.execute "insert into task (title, completed, description) values ( 'title2', 1, 'title2' )"
#db.execute "insert into task (title, completed, description) values ( 'title3', 0, 'title3' )"

# Find a few rows
db.results_as_hash = true
db.execute( "select * from task" ) do |row|
  p row.reject!{ |k| k.is_a? Numeric }
end

require './Controller/Task'
id = 1
a = Controller::Task.new
a.task = id
