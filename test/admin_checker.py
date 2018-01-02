import http.client
import uuid
import random
import json
import time

OFFER_ID = "test_offer1"

headers = {}

while True:
	adminServiceConnection = http.client.HTTPConnection('192.168.0.55:30001')
	result = adminServiceConnection.request('GET', '/offers/' + OFFER_ID + '/orders')
	print("Current offers:" + str(len(json.loads(adminServiceConnection.getresponse().read()))))
	time.sleep(0.1)