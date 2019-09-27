FROM alpine:3

RUN apk update && apk add gradle

COPY . /srv
WORKDIR /srv

CMD ["./start.sh"]