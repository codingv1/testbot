terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.3"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region = "us-west-2"
  profile = "sbtraining"

  default_tags {
    tags = {
      department  = "Jupiter"
      environment = "dev"
    }
  }
  data "terraform_remote_state" "vpc" {
  backend = "s3"
  config = {
    bucket = "jupiter-s3-bucket"
    #key    = "dev/project1-vpc/terraform.tfstate"
    region = "us-west-2"
  }
}


}
