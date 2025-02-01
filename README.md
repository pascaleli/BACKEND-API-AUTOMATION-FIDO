# BACKEND-API-AUTOMATION-FIDO

## PROJECT DOCUMENTATION  > BY PASCAL A. TAMEKLOE


- **api-authentication-controller** <br>
- **api-video-games-controller** <br>
- **Api-video-games-controller-v-2** <br>


***GitHub repository name: BACKEND-API-AUTOMATION-FIDO***


# General Description 


<p> This is a Test Framework made of 3 test suites with a total of 16 testcases 
3 main Tests clases.

- ***AthenticationControllerApiTest**
- ***GameControllerApiTest***
- ***GameControllerV2Test***

1. A class called **TestBase**, which allows me to store recurring cases which are similar across all test such as **Validating StatusCode or Content Types(JSON or XML) returned by the APIs** 

2. A configuration file called **config.properties** to store all my Test data including Enpoints.


3. JUNIT5 is used in this project which allowed the labeling of tests in the scripts and helped provide descriptions to the test appropriately. 

4. Used Allure for reporting in the framework.

5. Use Gherkin - BDD method to create my test cases which you will find [attached](https://docs.google.com/spreadsheets/d/1hDP7q2cgeLXu1pRsV0BjV678Jn_7hQr9xL0VFgFiWkw/edit?usp=sharing). 

***This framework is integrated with Jenkins for cicd build triggering; using Docker as Agent. 
A pom.xml which contains all my dependencies** </p>


#SETUP ON LOCAL 
<p>
- Clone the repository from Github BACKEND-API-AUTOMATION-FIDO
- Navigate into the API-TEST folder 
- Open the project in your editor 
- IntelJ for example 

<img width="1406" alt="Screenshot 2025-02-01 at 09 07 21" src="https://github.com/user-attachments/assets/a7e5a0f8-0cb1-4a12-995a-ba478a8950c8" />

- Fetch and Pull all changes [Git fetch | git pull]
- Install Allure on your Machine , to visualise the report 
- Install Maven on your machine [The command will differ depending on you machine]
  - brew install maven on Mac 
- Open the pom.xml and make sure to install the dependencies in the pom 
    - You can use maven clean install
- Run ***maven clean test***  to run the tests in the project
- The results will be generated in API-TEST/allure-results folder

- To view the report use this command from the API-Test folder in your terminal

- Allure serve <The Absolute Path of the folder called allure-results>
  - allure serve /Users/y/y/BACKEND-API-AUTOMATION-FIDO/API-TEST/allure-results

![Screenshot 2025-02-01 at 09 12 15](https://github.com/user-attachments/assets/88c5b0c2-1818-48a8-8699-9d61be3a3093)
</p>

 
# SETUP IN JENKINS USING DOCKER  [CI]


You will either need a cloud-based Jenkin platform or local base.
For this project we will use a Docker image for Jenkins 

- Install Docker app on your local 
-Start your Docker instance 
Use this command to create a Jenkins image in your docker 

  - docker run -d --name jenkins-container -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts

- Veify that the jenkins image is started and running 

  - docker ps would list all  the containers running in your Docker 
-Use the command below to get the password 
  - docker exec jenkins-container cat /var/jenkins_home/secrets/initialAdminPassword

<img width="1292" alt="Screenshot 2025-02-01 at 09 19 53" src="https://github.com/user-attachments/assets/6d80520c-178f-4893-8b4a-561ed70b23d3" />



- Vist http://localhost:8080/  on your device and login 
- Create a build and configure the build as follow.
  
 ![Feb-01-2025 00-11-23](https://github.com/user-attachments/assets/4695e969-62b1-48c1-b2e5-5f6fe65987a2)

- Trigger the build using the the green arrow 
- The build in the above configuration to be triggered each time a merge is done on the Repository on Github
![Screenshot 2025-02-01 at 09 22 59](https://github.com/user-attachments/assets/ea5d6c29-1071-4c22-8134-295b859f5ac1)


Once the build is done , visite the console page on the left after clicking on the build name 
You will see all the results of your test 

![Feb-01-2025 09-27-21](https://github.com/user-attachments/assets/720162fd-02e4-4487-9052-efff4c181e81)


13 tests have passed out of 16  test cases </p>

# NOTE: The 3 failed test-case are expectation from the API documents that do not align with the data that is actually retrieved by the API
