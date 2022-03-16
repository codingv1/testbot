terraform {
  backend "s3" {
    bucket         = "jupiter-s3-bucket"
    key            = "global/s3/terraform.tfstate"
    region         = "us-west-2"
    dynamodb_table = "jupiter-bucket_lock"
    encrypt        = true
    profile        = "sbtraining"
  }
}


