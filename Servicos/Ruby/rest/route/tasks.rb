# encoding: UTF-8
require './Controller/Task'

get '/api/tasks/?', :provides => 'application/json' do
	Controller::Task.new.tasks.to_json
end

get '/api/tasks/:id/?' do
	task = Controller::Task.new.task(params['id'])
	if task.nil?
		halt 404
	end
	task.to_json
end

post '/api/tasks/?' do
	body = JSON.parse request.body.read
	task = Controller::Task.new.save(body)

	status 201
	task.to_json
end

delete '/api/tasks/:id/?' do
	task = Controller::Task.new.task(params[:id])
	if task.nil?
		halt 404
	end

	halt 500 unless task.destroy

end