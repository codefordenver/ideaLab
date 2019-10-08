FROM alpine:3

RUN apk update && apk add gradle

RUN apk add nodejs npm && npm i -g nodemon

COPY . /srv
WORKDIR /srv

CMD ["./start.sh"]