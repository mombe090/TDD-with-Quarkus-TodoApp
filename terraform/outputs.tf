output "public-ip-instance-1" {
  value = aws_instance.ec2["ec2.instance1"].public_ip
}

output "security_group" {
  value = aws_security_group.ec2.id
}
