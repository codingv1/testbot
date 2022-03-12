provider "aws" {
  region = "us-west-2"
  access_key = "AKIAVDEYBXAEL3ULPMZD"
  secret_key = "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"
}

module "jupiter_db" {
  source = "./modules/DB"
}


module "jupiter_application_load_balancer" {
  source   = "./modules/ApplicationLoadBalancer"
}


module "jupiter_auto_scaling_group" {
  source = "./modules/AutoScalingGroups"
  lb_dns = module.jupiter_application_load_balancer.load_balance_dns
  target_group_arn_api = module.jupiter_application_load_balancer.target_group_arn_api
  target_group_arn_react = module.jupiter_application_load_balancer.target_group_arn_react
  db_name = module.jupiter_db.db_name
  db_endpoint = module.jupiter_db.db_endpoint
}
