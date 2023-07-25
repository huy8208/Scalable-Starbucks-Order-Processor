docker-build:
	mvn compile
	mvn clean package -DskipTests
	docker build -t rabbitmq-worker .
docker-push:
	docker login
	docker tag rabbitmq-worker huynguyen8208/rabbitmq-worker:latest
	docker push huynguyen8208/rabbitmq-worker:latest
