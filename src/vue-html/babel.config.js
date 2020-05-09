module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ]
}

{ "elapsedTime(ms)": 61733,
 "requestMethod": "POST",
  "requestSourceAddr": "10.210.66.95",
   "requestParams": "",
    "requestUri": "/api/funo-carrier-intf/carrierintf/ability/addOrDelBaseAreaAndServiceAreaByLngLat",
     "requestPathinfo": "/api/funo-carrier-intf/carrierintf/ability/addOrDelBaseAreaAndServiceAreaByLngLat",
      "reqOthersParams": { "authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6IlJPTEVfZnVubywiLCJzdWIiOiJmdW5vdXNlciIsImV4cCI6MzE1NjAyMTEyMn0.9MjszyOcrhGy_1jdK8NWRPrAx8mUVB6XncfTb6blBjCBuf5w-BCeN3oRDaoBM2Ek_Ti7-EBMxKQVx8o7_QR8RQ",
       "content-length": "390", "postman-token": "24a9ce18-1426-416f-8890-1e7d90953e9c", "host": "10.47.187.39:8041", "content-type": "application/xml", "connection": "keep-alive", "cache-control": "no-cache", "accept-encoding": "gzip, deflate", "user-agent": "PostmanRuntime/7.1.1", "accept": "*/*" },
        "respBody": "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ResponseContext><responseId>20200417120200052</responseId><responseCode>0</responseCode><responseMessage>执行成功</responseMessage><content><message><cgi>460-00-123456-68</cgi><resultCode>0</resultCode><resultMessage>执行成功</resultMessage></message><cellInfo><oriCgi>460-00-488219-43</oriCgi><genCgi>460-00-123456-68</genCgi><cityName>泉州</cityName><countyName>鲤城区</countyName></cellInfo></content></ResponseContext>", "requestSourcePort": 55721, "requestUrl": "http://10.47.187.39:8041/api/funo-carrier-intf/carrierintf/ability/addOrDelBaseAreaAndServiceAreaByLngLat", "requestOthersParams": "HTTP/1.1", "reqBody": "<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n<RequestContent>\r\n    <requestId>20200417111042</requestId>\r\n    <appId>10000001</appId>\r\n    <content>    <cgiInfo>\t <cgi>460-00-123456-68</cgi>\r\n    <homeCounty>鲤城区</homeCounty>\r\n    <homeCity>泉州</homeCity>\r\n    <lng>119.175448</lng>\r\n    <lat>26.055305</lat>\r\n    <actionType>add</actionType>\r\n    </cgiInfo>    </content></RequestContent>", "startTime": "20200417 11:46:59.445", "endTime": "20200417 11:48:01.178", "requestProtocol": "HTTP/1.1", "respCode": 200 }