# syntax=docker/dockerfile:1

# Stage 1: Build
FROM node:16 AS build
WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build -- --configuration production

# Stage 2: Runtime
FROM nginx:alpine

LABEL description="Broker Front-End (Angular)"

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist/broker-front-end /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]