
package_app:
	./mvnw clean install

build_app:
	docker build -f src/main/docker/Dockerfile.jvm -t mombe090/todoapp-tdd .
	docker tag mombe090/todoapp-tdd mombe090/todoapp-tdd:1.0.0
	docker tag mombe090/todoapp-tdd mombe090/todoapp-tdd:latest

run_using_docker:
	docker run -i -d --rm -p 9090:8080 mombe090/todoapp-tdd:1.0.0

create-key-pay:
	aws ec2 create-key-pair \
    --key-name todo-app \
    --query 'KeyMaterial'  \
    --output text > ~/.ssh/todo-app.pem && \
    chmod 600 ~/.ssh/todo-app.pem

terraform:
	terraform init && terraform plan

ansible-cmd:
	cd ansible && ansible -m ping all && ansible-playbook playbooks/docker.playbook.yaml
