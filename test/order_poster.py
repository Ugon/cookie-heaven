import http.client
import uuid
import random
import json
import time

HOSTPORT = '192.168.181.132:30002'
OFFER_ID = "test_offer1"

adminServiceConnection = http.client.HTTPConnection(HOSTPORT)
adminServiceConnection.request('POST', '/login?login=ugon&pass=ala123')
result = adminServiceConnection.getresponse()
authHeader = result.getheader("Authorization")
print(authHeader)

headers = {"Content-Type": "application/json", "Authorization": authHeader}

while True:
	orderServiceConnection = http.client.HTTPConnection(HOSTPORT)
	uid = str(uuid.uuid4())
	body = {
		"id": uid,
		"firstname": "test_firstname",
		"lastname": "test_lastname",
		"offerId": OFFER_ID,
		"quantity": random.randint(1, 100)
	}
	orderServiceConnection.request('POST', '/orders', body=json.dumps(body), headers=headers)
	print("Request sent: " + uid + " " + str(orderServiceConnection.getresponse().status))
	time.sleep(0.5)
