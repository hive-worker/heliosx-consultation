# heliosx-consultation

## To run the project

1. Install JDK 21
2. run `./gradlew clean bootRun` to start the server

## Description

The project provides a Consultation API that allows the front end to get questions and submit answers for a
consultation.
The Project uses Spring Boot and Java 21.
The project is stateless.
It has been based on the Asthma consultation flow.

## Sample cURL commands

Swagger UI is available at http://localhost:8080/swagger-ui.html


* Get Page 1 Questions:
```
curl -X 'GET' \
  'http://localhost:8080/api/consultation/questions/page1?treatmentType=ALLERGY' \
  -H 'accept: */*'
```
* Get Page 2 Questions:
```
curl -X 'GET' \
  'http://localhost:8080/api/consultation/questions/page2?treatmentType=ALLERGY' \
  -H 'accept: */*'
```

* Submit Page 1 Answers:
```curl -X 'POST' \

'http://localhost:8080/api/consultation/answers/page1' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '[
{
"id": "1",
"section": "ABOUT_YOU",
"text": "Are you over 18?",
"treatmentType": "ALLERGY",
"correctAnswer": "Yes",
"sorryMessage": "You cannot use this service.",
"userSubmission": "Yes"
}
]'
```
* Submit Page 2 Answers:
 ```curl -X 'POST' \
  'http://localhost:8080/api/consultation/answers/page2' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '                             [
                                  {
                                    "id": "2",
                                    "section": "SYMPTOMS",
                                    "text": "Have you been sneezing more than usual?",
                                    "treatmentType": "ALLERGY",
                                    "options": [
                                      {"text": "Everyday", "score": 5},
                                      {"text": "Sometimes", "score": 3},
                                      {"text": "Rarely", "score": 1}
                                    ],
                                    "userSubmission": "Everyday"
                                  }
                                ]'
```

## Future improvements

* Being stateless it is easy to tamper with the parameters particularly `correctAnswer` parameter.
* Security - Ensure only the front end can call the API. This can be done by using a token or a secret key.
* There is no validation on the answers submitted. This can be done by adding validation annotations to the Question classes.
* The answers are submitted back to us from the front end. We would actually store the questions in a database and only
  have the id sent to the end user and not the entire question with correct answer!
* Usual improvements like logging, monitoring (Spring Actuator), more documentation, better test coverage etc.
* The answers are not stored so the doctor cannot review the data, whether it is or not appropriate to prescribe the medication.