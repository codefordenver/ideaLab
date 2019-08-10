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

### API & Java Spring & Gradle
1. Make sure you have everything downloaded appropriately the first time. Go to our Google docs presentation if you still need to do that.
2. Using CD and DIR (or LS) in your command line, navigate to your local repo of ideaLab/Backend.
3. run `./gradlew clean`
5. run `./gradlew build`
6. run `docker build -f Dockerfile -t idealab-api .`
7. run `docker-compose up`
4. In your browser, type `localhost:8080/greeting`. You have arrived at our Hello World page.

### Autorelaod with you IDE
Our spring boot application is running inside of a Docker container. In order to see your changes reflected in the app (and to enable auto reloading) you'll need to connect your IDE to the container and trigger a re-build on save.
#### Eclipse
1. Open Eclipse
2. For workspace go with the default and click "Launch"
3. In the top nav select File > import
4. Select Gradle > Existing Gradle Project and click next
5. In "Project root directory" specify the path to the "Backend" folder of our project (ex I'm on a linux computer and my path looks like "/home/code-for-denver/code/code-for-denver/ideaLab/Backend")
6. Click "Finish"
7. If the "Welcome" tab doesn't close click the little "x" next to it's name
8. In the top nav bar click on Run > Run Configurations
9. Select "Java Application" on the left hand nav
10. At the top of the left hand nav click on the icon that looks like a blank white piece of paper with a gold star in the top right. If you hover over the icon the hover text should be "New launch configuration"
11. In the new configuration change the name to something you like (I named mine "remote")
12. Under Project enter "Backend"
13. Under Main Class enter "org.springframework.boot.devtools.RemoteSpringApplication"
14. Select the Arguments tab
15. In Program Arguments enter "http://localhost:8080/"
16. Click "Apply" (don't close the window we're coming back to it)
17. Back in your terminal in the Backend folder run `docker-compose up`
18. Back in Eclipse click "Run"
19. Hopefully everything worked! You should now be able to edit the Java files and see the changes appear immediately. Try going to `localhost:8080/greeting` and see the current output of our app. Then go to src/main/java/idealab/api/controller/GreetingController.java. Edit the String template to something like "Hello <your-name>". Save the file and go back to your browser. Reload the window and your should see the new greeting!
#### IntelliJ
Coming soon please reach out to e-carlin if you would like it prioritized
#### VSCode
Coming soon please reach out to e-carlin if you would like it prioritized


### Database & POSTGRES & Docker
<i>In general, you should not have to go into this section. If for some reason you do, here are the instructions.</i>
1. Make sure you have everything downloaded appropriately the first time. Go to our Google docs presentation if you still need to do that.

## Frontend
