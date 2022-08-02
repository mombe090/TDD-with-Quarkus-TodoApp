
package_app:
	./mvnw clean install

build_app:
	docker build -f src/main/docker/Dockerfile.jvm -t mombe090/todoapp-tdd .
	docker tag mombe090/todoapp-tdd mombe090/todoapp-tdd:1.0.0
	docker tag mombe090/todoapp-tdd mombe090/todoapp-tdd:latest

run_using_docker:
	docker run -i -d --rm -p 9090:8080 mombe090/todoapp-tdd:1.0.0
