
FROM node:12.18.1
ENV NODE_ENV=production

WORKDIR /app

COPY ["messageStore.js", "sessionStore.js", "./"]

RUN npm install --production

COPY . .

CMD [ "node", "index.js" ]

EXPOSE 3000