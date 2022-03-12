provider "aws" {
  region     = "us-west-2"
  access_key = "AKIAVDEYBXAEL3ULPMZD"
  secret_key = "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"
}


resource "aws_lb" "Jupiter_loadbalancer" {
  security_groups    = ["sg-0cc5396459fb195b2"]
  name               = "jupiter-load-balancer"
    tags = {
    Name = "jupiter-load-balancer"
  }
  enable_cross_zone_load_balancing = true
  internal           = false
  load_balancer_type = "application"
  enable_deletion_protection = false
  subnets                    = [
    "subnet-0a08481908c5ca0dd",
    "subnet-046dfce6952d67b3a"
  ]
}

resource "aws_lb_target_group" "this-react" {
  name     = "alb-tg-react"
  port     = 80
  protocol = "HTTP"
  vpc_id   = "vpc-0ae14fa34f12ef6c6" 
}
resource "aws_lb_target_group" "this-api" {
  name     = "alb-tg-api"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = "vpc-0ae14fa34f12ef6c6" 
}

resource "aws_lb_listener" "react" {
  load_balancer_arn = aws_lb.Jupiter_loadbalancer.arn
  port              = 80
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    //default param
    target_group_arn = aws_lb_target_group.this-react.arn
  }
}

resource "aws_lb_listener" "back_end" {
  load_balancer_arn = aws_lb.Jupiter_loadbalancer.arn
  port              = 8080
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.this-api.arn
  }
}
