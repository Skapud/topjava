
Тестовые запросы Curl. Метод MealRestController.
===============
### REST_URL = "/rest/meals";
####  getAll
<pre>curl -i -X GET localhost:8080/topjava/rest/meals \
-H "Accept: application/json" \
-H "Content-Type: application/json"</pre>
####  getBetween, @GetMapping("/filter")
<pre>curl -i -X GET localhost:8080/topjava/rest/meals/filter \
-H "Accept: application/json" \
-H "Content-Type: application/json"</pre>
####  get, @GetMapping("/{id}")
<pre>curl -i -X GET localhost:8080/topjava/rest/meals/100004 \
-H "Accept: application/json" \
-H "Content-Type: application/json"</pre>
#### delete, @DeleteMapping("/{id}")
<pre>curl -i -X DELETE localhost:8080/topjava/rest/meals/100007 \
-H "Accept: application/json" \
-H "Content-Type: application/json"</pre>
####  update, @PutMapping("/{id}")
<pre>curl -i -X PUT localhost:8080/topjava/rest/meals/100006 \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-d '{"id": 100006, "dateTime": "2025-11-26T16:15:00", \
"description": "Postman PUT test", "calories": 777}'</pre>
####  createWithLocation, @PostMapping
<pre>curl -i -X POST localhost:8080/topjava/rest/meals \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-d '{"id": "null","dateTime": "2025-11-26T16:30:00", \
"description": "Postman POST test","calories": 444}'</pre>