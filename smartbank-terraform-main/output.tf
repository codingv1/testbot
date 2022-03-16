output "private_subnet_id" {
  description = "The ID of the subnet."
  value       = aws_subnet.private.id
}

output "alb_dns_name" {
  description = "The DNS name of Application LB"
  value       = aws_lb.alb.dns_name
}

output "db-name" {
  description = "Jupiter DB Name"
  value = aws_db_instance.jupiter_db.db_name
}

output "db_endpoint" {
  description = "Jupiter DB Endpoint"
  value = aws_db_instance.jupiter_db.endpoint
}

/* output "api_listener_arn" {
  description = "The arn of the api_listener."
  value = aws_alb_listener.api_listener.arn
} 

output "api_tg_arn" {
  description = "The arn of the api_target group."
  value = aws_lb_target_group.api_tg.arn
} */ 