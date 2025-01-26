To run the proyect you need docker installed: https://www.docker.com/

1.- Once you have downloaded the repository, you must create the docker image at the /demo-kavak address and execute the following command:

    docker build -t springio/demo-kavak

  note: If you have problems creating the image it may be necessary to compile the main-core project with the command "mvn clean install"

2- If the image was created correctly you should be able to view it with the following command:

    docker images

3- To run the project in a docker container, run the following command:

    docker run -p 8080:8080 springio/demo-kavak

4- It should be possible to see the application in any browser once the image is uploaded:

    http://localhost:8080/swagger-ui/index.html
