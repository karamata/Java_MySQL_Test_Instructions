# project information
- input folder contain access.log for demo

- sql folder contain sql schema

# compile and build
`./mvnw clean install`

# run sample access.log

## Step 1: start mysql server
`./start-mysql.sh`

## Step 2: run parser
`java -jar target/parser.jar --accesslog=input/access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100`

## Step 3: stop mysql server
`./stop-mysql.sh`