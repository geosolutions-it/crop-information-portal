
CROP_URL="http://localhost:8080/opensdi2-web/mvc/cip/crops/"
CROPS=('{"id":"crop1","label":"CROPPuno", "seasons":"RABI"}' '{"id":"crop2","label":"CROP 2", "seasons":["RABI","KHARIF"]}' '{"id":"crop3","label":"CROP 3", "seasons":"KHARIF"}')
for i in "${CROPS[@]}" 
do
	curl -XPOST -H 'Accept: application/json' -H 'Content-type: application/json' --data  "$i" $CROP_URL;
done
VAR_URL="http://localhost:8080/opensdi2-web/mvc/cip/agromet/"
VARS=('{"factor":"Tmax_avg","label":"Max Temperature","aggregation":"avg","unit":"°C"}' '{"factor":"Tmin_avg","label":"Min Temperature","aggregation":"avg","unit":"ºC"' '{"factor":"Daylen_avg","label":"Day length","aggregation":"avg","unit":"hr"}' ''{"factor":"other_sum","label":"Day length","aggregation":"sum","unit":"hr"}'' )
for i in "${VARS[@]}"
do
        curl -XPOST -H 'Accept: application/json' -H 'Content-type: application/json' --data  "$i" $VAR_URL;
done

