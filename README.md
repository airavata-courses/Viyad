# Viyad
Spring 2022 Project

# Weather Visualization UI

## Purpose

This Web Application is used for displaying weather visualizations to the user as per their request.

## How it works
Initially, user will login and enter My Reports Page where already existing reports of respective users are displayed.
On the Create Page user can select the location from the map using the markers present in the map. The marker represents the locations of the radar stations throughout the USA. Now when the user chooses a particular radar station by clicking on the marker, this then get the data from the data service and shows the visualization on the map. If no data is present for the given combination of radar location and the date time selected by the user the visualization will show a blank white image which represents that no data is present and to try some other combination.
All the API calls made in the UI are made through API Gateway which acts as a middleware between UI and all other microservices
 

## How to install and Execute

Install nodejs in the machine
https://nodejs.org/en/download/

Further install Vuejs in new terminal
npm install -g @vue/cli

Then install node packages
npm install

Then run the application
npm run serve

Further, check on which port on localhost, the app is hosted and copy the url and paste it in Google chrome instance with web security been Disabled.

Steps to Open Chrome with disabled web Security as per Operating Systems:

1. Mac 
open -na Google\ Chrome --args --user-data-dir=/tmp/temporary-chrome-profile-dir --disable-web-security --disable-site-isolation-trials

2. Linux

pkill chrome

/usr/bin/google-chrome-stable --disable-web-security --user-data-dir=/home/jossnaz/.config/google-chrome/




Now copy paste the URL in the chrome instance

### Software Requirements
Node
Vuejs

### Current status

Currently, user can login and register successfully.


### Future plans
After successful login and resgiter, url is not redirecting to report page. But functionality is working fine.
Right now you have to go to links manually:
http://localhost:portnumber/create
for example:
http://localhost:8080/create


## Testing
User can view report.
User can successfully login.
User can generate report.
User can save report.
Visualization is also working fine.


## Team

Saiprasad Rane
Vignesh Reddy
Renuka Srishti
