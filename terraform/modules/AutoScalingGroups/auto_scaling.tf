

module "react_launch_template" {
  source              = "./LaunchTemplate"
  lb_dns = var.lb_dns
}

module "api_launch_template" {
  source              = "./LaunchTemplate2"
  lb_dns = var.lb_dns
  db_name = var.db_name
  db_endpoint = var.db_endpoint
}


resource "aws_autoscaling_policy" "jupiter_react_auto_scaling_policy" {
  autoscaling_group_name = aws_autoscaling_group.react_asg.name
  name                   = "Jupiter-scaling-policy"
  policy_type            = "TargetTrackingScaling"
  target_tracking_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ASGAverageCPUUtilization"
    }

    target_value = 50.0
  }
}
resource "aws_autoscaling_policy" "jupiter_scaling_policy_api" {
  autoscaling_group_name = aws_autoscaling_group.api_autoscaling_group.name
  name                   = "Jupiter-scaling-policy"
  policy_type            = "TargetTrackingScaling"
  target_tracking_configuration {
    predefined_metric_specification {
      predefined_metric_type = "ASGAverageCPUUtilization"
    }

    target_value = 50.0
  }
}

resource "aws_autoscaling_group" "react_asg" {
  name = "jupiter-react-auto-scaling-group"
  launch_template {
    id = module.react_launch_template.react_launch_template_id
  }
  vpc_zone_identifier = ["subnet-046dfce6952d67b3a"]
  target_group_arns = [
    "${var.target_group_arn_react}"
  ]
  max_size                  = 3
  min_size                  = 1
  desired_capacity          = 2
  health_check_type         = "EC2"
  health_check_grace_period = 300
  tag {
    key                 = "Name"
    value               = "jupiter-react-asg"
    propagate_at_launch = true
  }
}

resource "aws_autoscaling_group" "api_autoscaling_group" {
  name = "Jupiter-api-ASG"
  launch_template {
    id = module.api_launch_template.api_launch_template_id
  }
  vpc_zone_identifier = [
    "subnet-046dfce6952d67b3a",
    "subnet-0a08481908c5ca0dd"
  ]
  target_group_arns = [
    "${var.target_group_arn_api}"
  ]
  max_size                  = 3
  min_size                  = 1
  desired_capacity          = 2
  health_check_type         = "EC2"
  health_check_grace_period = 300
  tag {
    key                 = "Name"
    value               = "jupiter-api-asg"
    propagate_at_launch = true
  }
}

