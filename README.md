# Viyad
Spring 2022 Project

# Persistence API

## Purpose

This API is used for saving user created dashboards

## How it works

User will save the data on the UI and it will be saved in persistence database 
 

## How to install and Execute

Install Docker
https://docs.docker.com/get-docker/

### Software Requirements
Docker

### Execution Steps

1. Depending on the operating system run the following commands:

    1. Mac OS: docker-compose -f docker-compose-mac.yml up
    2. Other Operating Systems: docker-compose -f docker-compose.yml up

## Implementation

This applications is built using Python Flask and MySQL which is containerized using Docker

### Current status
Currently all the basic APIs are developed for fetching the data and saving the data

### Future plans
If need arises, more parameters can be saved in persistence DB

## Testing
Basic Test Cases have been written in test_rest.py.
Also, we have performed testing on UI

## Team

Saiprasad Rane
Vignesh Reddy
Renuka Srishti