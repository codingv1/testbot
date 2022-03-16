#! /bin/bash
# curl -fsSL https://rpm.nodesource.com/setup_17.x | bash -
# yum install -y nodejs
# mkdir /opt/ReactApp/
# wget https://github.com/GogHox/smart-bank-react/archive/refs/heads/main.zip 
# unzip main.zip -d /opt/ReactApp/
# cd /opt/ReactApp/smart-bank-react-main/
# npm install
# #sudo echo "export const API_URL = 'http://Saturn-ALB-API-1091840505.us-west-1.elb.amazonaws.com/'" > src/Constants.js
# #sudo echo "export const API_URL = 'http://jupiter-alb-557774012.us-west-1.elb.amazonaws.com:3000/'" > src/Constants.js
# #saturn-alb-557774012.us-west-1.elb.amazonaws.com
# sudo echo "export const API_URL = 'http://\$${lb_dns_name}:80/api/'" > src/Constants.js
# sudo npm run build
# sudo npm install -g serve
# sudo serve -s build -l 80
# npm run start &


sudo su
curl --silent --location https://rpm.nodesource.com/setup_14.x | sudo bash
sudo yum install -y nodejs
sudo mkdir /usr/tmp/testing
cd /usr/tmp/testing
sudo wget https://gitee.com/jijixi/react-build/repository/archive/master.zip
unzip master.zip
cd react-build-master/
sudo echo "export const API_URL = 'http://${alb_dns_name}'" > src/Constants.js
sudo npm install -g serve
sudo serve -l 80 -s build

