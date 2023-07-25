all: clean

clean:
	mvn clean

compile:
	mvn compile

run: compile
	mvn spring-boot:run

build:
	mvn compile
	mvn package

run-jar: build
	java -jar target/spring-cashier-3.3.jar


# MySQL DB

mysql:
	docker run --platform=linux/amd64 -d --network starbucks-net --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0

mysql-shell:
	docker exec -it mysql bash


# Redis DB

redis-local:
	docker run --platform=linux/amd64 --name redis --network starbucks-net -td -p 6379:6379 redis

redis-official:
	docker run --platform=linux/amd64 --name redis --network starbucks-net -td -p 6379:6379 redis:4.0

redis-shell:
	docker exec -it redis bash 


# Jumpbox

jumpbox:
	docker run --platform=linux/amd64 --network starbucks-net --name jumpbox -t -d ubuntu

jumpbox-shell:
	docker exec -it jumpbox bash 


# Docker

docker-build: build
	docker build --platform=linux/amd64 -t spring-cashier .
	docker images

starbucks-network:
	docker network create --driver bridge starbucks-net

docker-run: docker-build
	docker run --platform=linux/amd64 --network starbucks-net -e "MYSQL_HOST=mysql" --name spring-cashier -td -p 8080:8080 spring-cashier

docker-clean:
	docker stop spring-cashier
	docker rm spring-cashier
	docker rmi spring-cashier

docker-shell:
	docker exec -it spring-cashier bash

docker-push:
	docker login
	docker build --platform=linux/amd64 -t $(account)/spring-cashier:v1.0 .
	docker push $(account)/spring-cashier:v1.0

# Compose

network-ls:
	docker network ls

network-create:
	docker network create --driver bridge $(network)

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale spring-cashier=2 -d

lb-up:
	docker-compose up -d lb

cashier-up:
	docker-compose up -d spring-cashier

redis-up:
	docker-compose up -d redis

mysql-up:
	docker-compose up -d mysql

compose-down:
	docker-compose down

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods spring-cashier

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it spring-cashier -- /bin/bash

pod-logs:
	kubectl logs -f spring-cashier

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=spring-cashier

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment spring-cashier-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service spring-cashier -o wide

service-delete:
	kubectl delete service spring-cashier

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress spring-cashier-ingress




