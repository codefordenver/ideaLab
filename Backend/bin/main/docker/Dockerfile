FROM postgres
COPY docker-entrypoint.initdb.d/ /docker-entrypoint.initdb.d/

COPY docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["docker-entrypoint.sh"]
ENV PGDATA=/data