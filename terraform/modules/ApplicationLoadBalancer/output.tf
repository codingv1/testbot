output "target_group_arn_react" {
  description = "target_group_arn_react"
  value       = aws_lb_target_group.this-react.arn
}

output "target_group_arn_api" {
  description = "target_group_arn_api"
  value       = aws_lb_target_group.this-api.arn
}

output "load_balance_dns" {
  description = "load_balance_dns"
  value       = aws_lb.Jupiter_loadbalancer.dns_name
}