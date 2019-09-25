# ideaLab
This is a queuing app for 3D printing. It includes a user facing form that allows someone to request a print job. It also includes a employee facing app that allows them to change statuses of print jobs, view print jobs, and modify print jobs.

The web app is frontend React and backend Java Spring.


# Cloning
To get started, clone our repo. Using cd and dir (or ls if not on Windows) in you command line, got to the folder you want to clone the repo into and start by typing:  
```git clone http://github.com/codefordenver/ideaLab```.

# Code for Denver: Getting Started on Helping Project
To understand more about this project & how you can join, read this PowerPoint: https://docs.google.com/presentation/d/13J6mfre2-HwBbnY5ozxYmMI5CZcK1R_OdZiDlbpZNEA/edit#slide=id.g5e3ebcd7e4_0_111.

# Getting The Project Up and Running
Our project is split into 2 sections: frontend & backend.

## Backend
Our backend is split into 2 sections: our API app & our database. Our API is written in Java Spring & Gradle. Our database uses open source POSTGRES and we have containerized it in Docker.

### Setting up Resource File
1. Request the latest dev resource file (if starting a new project see Backend/src/main/resources/sample_dropbox.properties) 
> :warning: When contributing to the existing project, make sure to make a copy of the sample properties file and do not rename directly.  If you rename and commit it will be deleted from the master branch.
2. Place the resource file and values in /Backend/src/main/resources/dropbox.properties (delete "sample_" if starting a new project)
3. To run dropbox yourself, create a dropbox app using the DBX Platform developer portal and get an access token: 
https://www.dropbox.com/developers/reference/getting-started?_tk=guides_lp&_ad=guides2&_camp=get_started#app%20console
- Step 1: Choose an API - Select the regular Dropbox API
- Step 2: For this project you only need a single App folder
- Step 3: From the console, create an access token and paste that into the dropbox.properties file.

4. Values from the properties file can be accessed using the @Value annotation.  i.e.
```
  @Value("${dropbox.ACCESS_TOKEN}")
  private String ACCESS_TOKEN;
```

### API & Java Spring & Gradle
1. Make sure you have everything downloaded appropriately the first time. Go to our Google docs presentation if you still need to do that.
2. Using CD and DIR (or LS) in your command line, navigate to your local repo of ideaLab/Backend.
3. Once you are in the appropriate folder, type gradlew bootRun in your command line. (You do not need to start up our Docker container with the database because Gradle is automatically doing that for us.)
4. In your browser, type localhost:8080/greeting. You have arrived at our Hello World page.

### Database & POSTGRES & Docker
<i>In general, you should not have to go into this section. If for some reason you do, here are the instructions.</i>
1. Make sure you have everything downloaded appropriately the first time. Go to our Google docs presentation if you still need to do that.
2. Run `docker create -v /var/lib/postgresql/data --name PostgresData alpine`
3. And then run `docker run --rm  --name postgres -e POSTGRES_PASSWORD=docker -d -p 5432:5432 --volumes-from PostgresData  postgres:12`
4. In your IDE of choice run the app.


## Frontend
