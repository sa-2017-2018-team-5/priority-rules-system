#!/bin/bash

create_priority() {

	# echo '{"name": "'$1'", "priority": '$2', "status": "'$3'"}'
	curl -d '{"name": "'$1'", "priority": '$2', "status": "'$3'"}' -H "Content-Type: application/json" -X POST http://localhost:8080/prs-priorities/priorities
    
}

ask_route() {
	car_type="";
	if (($1 % 4  == 0)); then 
		car_type="CARPOOLING"
	elif (($1 % 4 == 2)) || (($1 % 4 == 3)); then
		car_type="GREEN_CARS"
	else
		car_type="FIREFIGHTERS"
	fi

	echo "$car_type : $1"
	request='{
	  "action": "ASK",
	  "content": {
	    "car": {
	      "id": '$1',
	      "type": "'$car_type'",
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

	# echo "$request"
	curl -d "$request" -H "Content-Type: application/json" -X POST http://localhost:8081/prs-routes/routes
}

update_position(){
	tl_id=""
	if (($1 % 4 == 0)); then 
		tl_id="5"
	elif (($1 % 4 == 1)); then
		tl_id="3"
	elif (($1 % 4 == 2)); then
		tl_id="3"		
	else
		tl_id="4"		
	fi

	request='{"content": {"status": "'$2'", "car": '$1', "trafficLight": '$tl_id'}}'
	
	sleep 3
	curl -d "$request" -H "Content-Type: application/json" -X POST http://localhost:8082/prs-traffic-lights/observations
}

cars_nb="10"; # start with 10
 
echo "Start admin client and declare different type of priorities..."
create_priority FIREFIGHTERS 100 EMERGENCY 
create_priority GREEN_CARS 20 PRIVILEGED 
create_priority CARPOOLING 10 PRIVILEGED


echo "Start $cars_nb driver client, give id, ask route..."
for i in {1..1000};
	do
		ask_route "$i" &
	done

wait ${!}
# exit;

# echo "Start 1 tl-group..." # later 2 and scale
# gnome-terminal --window-with-profile=holdProfile --title "Traffic light group" -e "docker attach tlg"

# echo "Start a supervision module..." # later 2 and scale
# gnome-terminal --window-with-profile=holdProfile --title "Supervision" -e "docker attach supervision"


echo "Launch position update..."

for i in {1..1000};
	do
		update_position $i "SEEN" &
	done

wait ${!}

for i in {1..1000};
	do
		update_position $i "PASSED" &
	done

# Check everything is ok