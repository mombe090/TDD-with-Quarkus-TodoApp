resource "aws_instance" "ec2" {
  for_each               = toset(var.kube_names)
  ami                    = var.install_image
  instance_type          = var.instance_type
  key_name               = lookup(var.private_key, "name")
  vpc_security_group_ids = [aws_security_group.ec2.id]
  subnet_id              = aws_subnet.private.id
  root_block_device {
    volume_type           = "standard"
    volume_size           = 24
    delete_on_termination = true
  }
  tags = {
    Name      = each.value
    component = var.todo_app_env
  }

  provisioner "file" {
    source      = "~/.ssh/id_rsa.pub"
    destination = "/tmp/id_rsa.pub"
    connection {
      type        = "ssh"
      user        = "centos"
      host        = self.public_ip
      private_key = file(lookup(var.private_key, "local_path"))
    }
  }

  provisioner "file" {
    source      = "ec2-script.sh"
    destination = "/tmp/ec2-script.sh"
    connection {
      type        = "ssh"
      user        = "centos"
      host        = self.public_ip
      private_key = file(lookup(var.private_key, "local_path"))
    }
  }

  provisioner "remote-exec" {
    inline = [
      "chmod +x /tmp/ec2-script.sh",
      format("%s %s", "sudo /tmp/ec2-script.sh", each.value)
    ]
  }

  connection {
    type        = "ssh"
    user        = "centos"
    host        = self.public_ip
    private_key = file(lookup(var.private_key, "local_path"))
  }
}
