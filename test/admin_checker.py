import http.client
import uuid
import random
import json
import time

HOSTPORT = '192.168.181.132:30001'
#HOSTPORT = 'localhost:8001'
OFFER_ID = "test_offer1"

headers = {}

adminServiceConnection = http.client.HTTPConnection(HOSTPORT)
adminServiceConnection.request('POST', '/login?login=ugon&pass=ala123')
result = adminServiceConnection.getresponse()
authHeader = result.getheader("Authorization")
print(authHeader)



while True:
	adminServiceConnection = http.client.HTTPConnection(HOSTPORT)
	adminServiceConnection.request('GET', '/offers/' + OFFER_ID + '/orders', headers={"Authorization": authHeader})
	print("Current offers:" + str(len(json.loads(adminServiceConnection.getresponse().read()))))
	time.sleep(0.5)
