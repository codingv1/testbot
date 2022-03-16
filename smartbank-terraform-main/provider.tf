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
  region  = "us-west-2"
  profile = "sbtraining"

  default_tags {
    tags = {
      department = "Jupiter"
      #product     = "1234"
      #application = "5678"
      environment = "dev"
      Terraform   = "true"
    }
  }


}
