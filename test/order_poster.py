import http.client
import uuid
import random
import json
import time

OFFER_ID = "test_offer1"

headers = {"Content-Type": "application/json"}

while True:
	orderServiceConnection = http.client.HTTPConnection('192.168.0.55:30002')
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
	time.sleep(0.1)