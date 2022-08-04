provider "aws" {
  region = var.region
}

resource "aws_vpc" "todo_app" {
  cidr_block           = var.vpc_cidr
  instance_tenancy     = "default" 
  enable_dns_hostnames = true
  tags = {
    Name = var.todo_app_env
  }
}

resource "aws_subnet" "private" {
  vpc_id                  = aws_vpc.todo_app.id
  cidr_block              = var.private_subnet
  map_public_ip_on_launch = "true"
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.todo_app.id
  tags = {
    Name = "${var.todo_app_env}_gw"
  }
}

resource "aws_default_route_table" "route_table" {
  default_route_table_id = aws_vpc.todo_app.default_route_table_id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gw.id
  }

  tags = {
    Name = var.todo_app_env
  }
}



