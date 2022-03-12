output "db_name" {
  description = "db_name"
  value       = aws_db_instance.smartbankdb2.name
}
output "db_endpoint" {
  description = "db_endpoint"
  value       = aws_db_instance.smartbankdb2.endpoint
}