terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "3.47.0"
    }
  }
}

provider "aws" {
  region     = "us-west-2"
  access_key = ""
  secret_key = ""
}


resource "aws_launch_template" "trevor_launch_template_api" {
    name   = "trevor_launch_template1"
    image_id      = "ami-0359b3157f016ae46"
    instance_type = "t2.micro"
    key_name      = "trevor-rsa"
    network_interfaces {
      associate_public_ip_address = true
      subnet_id = "subnet-046dfce6952d67b3a"
      security_groups = ["sg-0cc5396459fb195b2"]
    }
    user_data = base64encode(
      <<-EOL
 #!/bin/bash
sudo yum -y update
sudo yum -y install java 
sudo yum install -y git
git clone https://gitee.com/uyao791_admin/aws-bankend-api.git
cd aws-bankend-api
sudo echo "spring.datasource.url=jdbc:postgresql://\$${db_endpoint}:5432/\$${db_name}
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
logging.level.org.hibernate.SQL=DEBUG
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE" > application.properties

sudo amazon-linux-extras install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
sudo docker build -t springdocker .
sudo docker run -p 80:8080 springdocker -v /home/ec2-user/aws-bankend-api:/data/java/config -d
  EOL
    )

    tags = {
      Name = "jupiter-react"
    }

}
