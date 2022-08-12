resource "aws_security_group" "ec2" {
  name        = "ec2"
  description = "Rules for all ec2 machines"
  vpc_id      = aws_vpc.todo_app.id

  /* Open up all ports but only within VPC */
  ingress {
    from_port = 0
    to_port   = 0
    protocol  = -1
    cidr_blocks = [
      var.vpc_cidr
    ]
  }

  /* Allow ssh from anywhere. This is needed initially when keys
   are getting exchanged. It may restricted to VPC later */
  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  ingress {
    from_port = 8080
    to_port   = 8080
    protocol  = "tcp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  /* Allow ping */
  ingress {
    from_port = 8
    to_port   = 0
    protocol  = "icmp"
    cidr_blocks = [
      "0.0.0.0/0"
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "ec2"
  }
}
