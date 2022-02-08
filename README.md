# Viyad
Spring 2022 Project

# DataService API

## Purpose

Its the dockerized microservice responsible to fetch the data from the AWS datastore and process the data to generate the appropriate Weather visualization and then serve this visualization to the the client requesting the data.

## How it works

The endpoints are exposed to the user to consume. Following are the two API exposed in the service.

 - list :- This is the <b>GET</b> request, this will serve back all the latitude and longitude data of the radio station present in the United States Of America. Along the coordinates it will also return the name of the radar station, unique 4 char identifier of the station. and the unique identification number.
 - getData:- This is a <b>POST</b> request. This api serves back the visualization of the weather in the particular radar location.The API fetches the image of the visualization if the data is present in the data store. If no data is present for any given radarstation and the date time combination then the API returns a blank image. The following parameters are required for this API.
	 - <b>radarId :</b> This is the unique 4 char identifier of the radar station.
	 - <b>time:</b> This is the Date and time of when the user wishes to see the weather condition of the given radar location.
## How to install and Execute
		
The microservice is a self containing dockerized container, hence the user only needs to install the Docker application installed.

### Prerequisites

 - Docker Application. The [Docker Installation Official Guide](https://docs.docker.com/get-docker/), is the official installation guide provided by the Docker team. Please install the Docker application based on the operating system of the user and follow the steps given in the installation guide

### Software Requirements

 - Docker Application.

### Execution Steps

 - Once the Docker application is installed, the user needs to navigate to the root directory of the project and open the terminal in this directory.
 - In this location the users needs to run the command <b>docker-compose up</b>. 
 - This command will first download all the requirement of the microservice and create the image file, once the image file is created it will start a docker container and run the image file on that container. That's it for running the microservice.

## Implementation
	The microservice is written in Python language using Django Framework.
## Current Status
For now the user can fetch the data from the service based on the Radar Location and the Date time. 

## Future Status
In future we can extend the app by implementing the cache feature to quickly serve the client request, and extend the vizualization to more complex features.


 - Vignesh Reddy
 - Saiprasad Rane
 - Renuka Shrishtri
