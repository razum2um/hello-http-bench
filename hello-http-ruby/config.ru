app = proc do |env|
  [ 200, {'Content-Type' => 'text/plain'}, ["Hello World #{Time.now.to_i}"] ]
end

run app
