# ideaLab
ideaLab is a queuing app for 3D printing. It includes a user-facing form that allows the user to request a print job. It also consists of an employee-facing app that will enable them to change the statuses of print jobs, view print jobs, and modify print jobs.
The web app is frontend React and backend Java Spring.


# Getting up and running
Install docker and docker-compose (should be bundled). Clone the project.

Navigate to the ideaLab directory.

Perform the actions described in "Setting up Resource File."

Run the following command: docker-compose up

The project will then build and launch itself. You can access the frontend by going to localhost:3000 in your browser.

For Linux and mac, live reloading should work out of the box. So say you edit a frontend file, it should update automatically as long as it's not a package.json file, same for the java backend.


# Cloning
To get started, clone our repo. Using cd and dir (or ls if not on Windows) in your command line, got to the folder you want to clone the repo into and start by typing:  
```git clone http://github.com/codefordenver/ideaLab```.

# Code for Denver: Getting Started on Helping Project
To understand more about this project & how you can join, read this PowerPoint: https://docs.google.com/presentation/d/13J6mfre2-HwBbnY5ozxYmMI5CZcK1R_OdZiDlbpZNEA/edit#slide=id.g5e3ebcd7e4_0_111.

# Getting The Project Up and Running
Our project is split into 2 sections: frontend & backend.
We have our project set up with docker-compose. Navigate to the ideaLab folder and type
```docker
docker-compose up
```

If you are having any issues with getting the api or frontend to show, type
```docker
docker-compose stop
docker-compose build
docker-compose up --force-recreate
```

This should rebuild the images and recreate the containers so you have an up to instance running.


## Backend
Our backend is split into 2 sections: our API app & our database. Our API is written in Java Spring & Gradle. Our database uses open source POSTGRES and we have containerized it in Docker.

### Setting up Resource File
1. Request the latest dev resource file (if starting a new project see Backend/src/main/resources/sample_dropbox.properties) 
> :warning: When contributing to the existing project, make sure to make a copy of the sample properties file and do not rename directly.  If you rename and commit it will be deleted from the master branch.
2. Place the resource file and values in /Backend/src/main/resources/dropbox.properties (delete "sample_" if starting a new project)
3. To run dropbox yourself, create a dropbox app using the DBX Platform developer portal and get an access token
- Step 1: Navigate to the [DBX Platform](https://www.dropbox.com/developers/reference/getting-started?_tk=guides_lp&_ad=guides2&_camp=get_started#app%20console) and follow the directions to make a new app.
- Step 2: Select the regular Dropbox API
- Step 3: For this project you only need a single App folder
- Step 4: From the console, create an access token and paste that into the dropbox.properties file.

The final directory should like like the following once the dropbox.properties file is setup. ❗ The dropbox.properties file is on the .gitignore so keys will not be commited and the sample_dropbox.properties needs to always be commited for future users):
![Dropbox Directory Example](/images/sample_dropbox-properties.png)

If they needed to be used elsewhere, the values from the properties file can be accessed using the @Value annotation.  i.e.
```
  @Value("${dropbox.ACCESS_TOKEN}")
  private String ACCESS_TOKEN;
```

### API & Java Spring & Gradle
1. Make sure you have everything downloaded appropriately the first time. Go to our Google docs presentation if you still need to do that.
2. Using CD and DIR (or LS) in your command line, navigate to your local repo of ideaLab/Backend.
3. Once you are in the appropriate folder, type gradlew bootRun in your command line. (You do not need to start up our Docker container with the database because Gradle is automatically doing that for us.)
4. In your browser, type localhost:8080/api/print-jobs. You have arrived at our list of print jobs page.

### Database & POSTGRES & Docker
<i>In general, you should not have to go into this section. If for some reason you do, here are the instructions.</i>
1. Once you have docker-compose up and running, in a new command line type in
```docker
docker exec -it idealab_postgres_1 bash
```

2. Type
```
psql -U postgres
```

Now you can run basic postgres commands.

## Common docker errors for future devs
1. ERROR: Couldn't find env file: /Users/.../dev.env
  SOLUTION: Add an empty dev.env file in the top-level directory
2. 

## Frontend
1. The frontend is written in React.[If you need help with react, refer to its documentation.](https://reactjs.org/docs/getting-started.html "documentation")
2. To see our page, type into your web browser localhost:3000/#/login.
