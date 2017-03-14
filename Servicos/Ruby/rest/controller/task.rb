# encoding: UTF-8

module Controller

    class Task
        attr_reader :db

        def initialize()
            filename = 'tasks.db'
            @db = SQLite3::Database.new filename

            rows = db.execute <<-SQL
                CREATE TABLE IF NOT EXISTS task (
                    id  INTEGER PRIMARY KEY AUTOINCREMENT,
                    title VARCHAR(50),
                    completed INTEGER,
                    description VARCHAR(150)
                );
            SQL
        end

        def tasks
            response = []
            @db.results_as_hash = true
            @db.execute( 'SELECT * FROM task' ) do |row|
                response << (p row.reject!{ |k| k.is_a? Numeric })
            end
            response
        end

        def task(id)
        #def task=(id)
            response = ''
            @db.results_as_hash = true
            @db.execute( 'SELECT * FROM task WHERE id = ?', id ) do |row|
                response = (p row.reject!{ |k| k.is_a? Numeric })
            end
            response
        end

        def save(task)
            rows = []
            values = []

            task.each do |key, value|
                rows << key
                values << '"%s"' % [value]
            end
            
            @db.execute('INSERT INTO (%s) task VALUES (%s)' % [rows.join(','), values.join(',')])
        end

        def delete(id)
            response = ''
            @db.execute('DELETE FROM task WHERE id = ?', id ) do |row|
                response = (p row.reject!{ |k| k.is_a? Numeric })
            end
            response
        end
    end
    
end