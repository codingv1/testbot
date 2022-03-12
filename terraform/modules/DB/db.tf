resource "aws_db_subnet_group" "jupiter-subnet-group2" {
  name        = "jupiter-subnet-group2"
  description = "jupiter subnet group"
  subnet_ids  = ["subnet-0a08481908c5ca0dd", "subnet-046dfce6952d67b3a"]
}

resource "aws_db_instance" "smartbankdb2" {
  allocated_storage = 20
  # db-name = "smartbankdb"
  engine                 = "postgres"
  identifier             = "jupiter-smartbank-db"
  engine_version         = "13"
  instance_class         = "db.t3.medium"
  name                   = "jupiter-smartbank-db"
  username               = "postgres"
  password               = "postgres"
  skip_final_snapshot    = true
  multi_az               = true
  vpc_security_group_ids = ["sg-0cc5396459fb195b2"]
  db_subnet_group_name   = "jupiter-subnet-group2"
  # storage_type         = "io1"
  publicly_accessible = false
}
