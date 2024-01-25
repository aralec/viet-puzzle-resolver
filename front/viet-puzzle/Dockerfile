FROM node:alpine AS builder

WORKDIR /app

COPY . .

RUN npm install && npm run build

FROM nginx:alpine
EXPOSE 3000
COPY --from=builder /app/dist/viet-puzzle/browser /usr/share/nginx/html/

COPY /nginx.conf /etc/nginx/conf.d/default.conf
