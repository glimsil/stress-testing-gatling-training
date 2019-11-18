FROM mongo

COPY init.json /init.json
CMD mongoimport --host mongodb --db user --collection User --type json --file /init.json --jsonArray --drop
