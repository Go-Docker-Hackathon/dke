FROM nodesource/trusty

ADD ./codes /codes
WORKDIR /codes

RUN npm install
RUN npm install -g pm2
EXPOSE 3000

ENTRYPOINT ["npm", "start"]