

provider "aws" {
  region     = "us-west-2"
  access_key = "AKIAVDEYBXAEL3ULPMZD"
  secret_key = "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"
}


resource "aws_launch_template" "tracy_launch_template1" {
    name   = "tracy_launch_template1"
    image_id      = "ami-0359b3157f016ae46"
    key_name      = "trevor-rsa"
    instance_type = "t2.micro"
    network_interfaces {
      associate_public_ip_address = true
      subnet_id = "subnet-046dfce6952d67b3a"
      security_groups = ["sg-0cc5396459fb195b2"]
    }
    user_data = base64encode(
      <<-EOL
    #!/bin/bash
    sudo su
    curl --silent --location https://rpm.nodesource.com/setup_14.x | sudo bash
    sudo yum install -y nodejs
    sudo mkdir /usr/tmp/testing
    cd /usr/tmp/testing
    sudo wget https://gitee.com/jijixi/react-build/repository/archive/master.zip
    unzip master.zip
    cd react-build-master/
    sudo echo "export const API_URL = 'http://\$${lb_dns}:8080/'" > src/Constants.js
    sudo npm install -g serve
    sudo serve -l 80 -s build
    EOL
    )

    tags = {
      Name = "TracyReact"
    }


}
