I think the issue happen when you have your workers running and start workflows at the same time

## Configuration

See [temporal.properties](./java-sdk-metrics/src/main/resources/temporal.properties) file.

## Start environment

- 1-start-grafana_prometheus.sh

  Go to http://localhost:3000/d/whtBuu0Vkddd/sdk-metrics?orgId=1

- 2-stop-workers.sh
- 3-build-project.sh

  Create worker's docker image
- 5-create-backlog.sh
- 6-start-workers.sh

  Open the script to set maxTaskQueueActivitiesPerSecond and activity latency
