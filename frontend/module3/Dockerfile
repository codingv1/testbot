FROM node 
WORKDIR /app
# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
# install app dependencies
COPY package.json ./
COPY package-lock.json ./
# install all dependencies
RUN npm install --silent
# optional
RUN npm install react-scripts -g --silent
# add app
# copy from current folder, but ignore content in .dockerignore
COPY . ./
# start app with port 3000
#EXPOSE 3000
# start app
CMD ["npm", "start"]

