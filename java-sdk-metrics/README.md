# Example to connect with temporal cloud

It has two main components:

- the starter that starts the workflow [GreetingWorkflow](./src/main/java/com/antmendoza/temporal/HelloActivity.java)
- the worker that connects to the server and pulls and execute tasks.

## Configuration

See [temporal.properties](./src/main/resources/temporal.properties) file.

## Start client, create backlog

`mvn compile exec:java -Dexec.mainClass="com.antmendoza.temporal.WorkerSsl"`

