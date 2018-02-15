#!/usr/bin/env bash

create_priority() {
	echo 'Creating priority '$1'...'
	curl -d '{"name": "'$1'", "priority": '$2', "status": "'$3'"}' -H "Content-Type: application/json" -X POST http://localhost:8080/prs-priorities/priorities
}

ask_route() {
	request='{
	  "action": "ASK",
	  "content": {
	    "car": {
	      "id": '$1',
	      "type": "'$2'",
	      "currentPosition": {
	        "longitude": 12,
	        "latitude": 13
	      }
	    },
	    "destination": {
	      "longitude": 1,
	      "latitude": 1
	    }
	  }
	}'

	if(($1 == 1)); then
		echo 'Example of request to ask for a route:' 
		echo 'curl -d "'$request'" -H "Content-Type: application/json" -X POST http://localhost:8081/prs-routes/routes'
		read -p ""
	fi

	echo 'Car '$1' asking for a route...'
	curl -d "$request" -H "Content-Type: application/json" -X POST http://localhost:8081/prs-routes/routes
}

update_position(){
	request='{"content": {"status": "'$3'", "car": '$1', "trafficLight": '$2'}}'
	
	if(($1 == 1 && $2 != 3)); then
		echo 'Example of '$3' request:' 
		echo 'curl -d "'$request'" -H "Content-Type: application/json" -X POST http://localhost:8082/prs-traffic-lights/observations'
		read -p ''
	fi
	
	if [ "$3" == "SEEN" ]; then 
		echo 'Car '$1' seen at trafficLight '$2		
	else
		echo 'Car '$1' passed trafficLight '$2		
	fi
	
	curl -d "$request" -H "Content-Type: application/json" -X POST http://localhost:8082/prs-traffic-lights/observations
}

 
echo "Declare different type of priorities..."
read -p ""

echo 'Example of request to create a priority:' 
echo 'curl -d "'{"name": "'FIREFIGHTERS'", "priority": '100', "status": "'EMERGENCY'"}'" -H "Content-Type: application/json" -X POST http://localhost:8080/prs-priorities/priorities'
read -p ""

create_priority FIREFIGHTERS 100 EMERGENCY 
read -p ""
create_priority GREEN_CARS 20 PRIVILEGED 
read -p ""
create_priority CARPOOLING 10 PRIVILEGED


read -p ""
echo "Ask routes..."
read -p ""

ask_route 1 "FIREFIGHTERS"
read -p "" 
ask_route 2 "GREEN_CARS" 
read -p ""
ask_route 3 "GREEN_CARS" 
read -p ""
ask_route 4 "CARPOOLING" 


read -p ""
echo "Launch position update..."
read -p ""

update_position 1 1 "SEEN"
read -p ""
update_position 2 2 "SEEN"
read -p ""
update_position 1 1 "PASSED"
read -p ""
update_position 2 2 "PASSED"
read -p ""

update_position 3 4  "SEEN"
read -p ""
update_position 4 5 "SEEN"
read -p ""
update_position 1 3 "SEEN"
read -p ""
update_position 2 3 "SEEN"
read -p ""


update_position 3 4  "PASSED"
read -p ""
update_position 1 3 "PASSED"
read -p ""
update_position 2 3 "PASSED"
read -p ""
update_position 4 5 "PASSED"



# Check everything is ok