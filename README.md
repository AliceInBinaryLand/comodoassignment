### Backend

- Firstly you should configure Postgresql and rabbitmq.

- Update the configuration info into application.properties

	>spring.rabbitmq.host=127.0.0.1
	
	>spring.rabbitmq.port=5672
	
	>spring.rabbitmq.username=guest
	
	>spring.rabbitmq.password=guest
	
	>spring.datasource.url=jdbc:postgresql://localhost:5432/comodo	
	
	>spring.datasource.username=comodo
	
	>spring.datasource.password=4544008


- Build application with using maven.
> mvn clean package

- Build docker image
>docker build --tag=assignment-backend:latest .

-Run docker 
>docker run -p8098:8098 assignment-backend

### Frontend

-Build docker image 
>docker build --tag=assignment-frontend:latest .

-Run docker
>docker run -p8080:8080 assignment-frontend
