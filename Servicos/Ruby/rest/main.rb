# encoding: UTF-8

require 'sinatra'
#require 'redis'
require 'sqlite3'
require 'json'

set :bind, '0.0.0.0'
set :port, 8080

#redis = Redis.new(:host => '127.0.0.1', :port => 6379, :db => 0)
#redis = Redis.new(:url => 'redis://:p4ssw0rd@10.0.1.1:6380/15')

require './route/tasks'
