variable "todo_app_env" {
  default = "todo-app-aws"
}

variable "region" {
  default = "us-east-1"
}

variable "vpc_cidr" {
  default = "10.20.0.0/16"
}

variable "private_subnet" {
  default = "10.20.20.0/24"
}

variable "install_image" {
  default = "ami-00e87074e52e6c9f9"
}

variable "instance_type" {
  default = "t2.micro"
}

variable "private_key" {
  type = map(string)
  default = {
    "name"       = "todo-app"            // Name as given while creating keys on AWS console
    "local_path" = "~/.ssh/todo-app.pem" // Location on the machine from where you are running terraform
  }
}

variable "kube_names" {
  type = list(string)
  default = [
    "ec2.instance1",
  ]
}

