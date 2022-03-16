#!/bin/bash
curl -sL https://rpm.nodesource.com/setup_14.x | sudo -E bash -
sudo yum install -y git
sudo yum install -y nodejs
cd /home/ec2-user
git clone https://github.com/JeremyKuah/smartbankreact-mercury.git
cd smartbankreact-mercury
pwd > /tmp/path.txt
sudo npm install
sudo echo "export const API_URL = 'http://jupiter-alb-1246979725.us-west-2.elb.amazonaws.com:3000'" > src/Constants.js
sudo echo "export const API_URL = 'http://jupiter-alb-1246979725.us-west-2.elb.amazonaws.com:3000'" > /tmp/url.txt
sudo npm run build
sudo npm install -g serve
sudo serve -s build -l 3000