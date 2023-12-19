package com.antmendoza.temporal;

import com.antmendoza.temporal.config.FromEnv;
import com.antmendoza.temporal.config.ScopeBuilder;
import com.antmendoza.temporal.config.SslContextBuilderProvider;
import com.antmendoza.temporal.workflow.WorkflowHelloActivity;
import com.uber.m3.tally.Scope;
import com.uber.m3.util.ImmutableMap;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerFactoryOptions;
import io.temporal.worker.WorkerOptions;


public class WorkerSsl2 {

    public static final String TASK_QUEUE = "MyTaskQueue3";

    public static void main(String[] args) throws Exception {





        System.out.println("PORT : " + FromEnv.getWorkerPort());
        int port = Integer.parseInt(FromEnv.getWorkerPort());
        System.out.println("PORT : " + port);

        String actions_per_second = FromEnv.getActionsPerSecond();
        System.out.println("Actions per second" + actions_per_second);
        int actions = Integer.parseInt(actions_per_second);
        System.out.println("Actions per second" + actions);


        Scope metricsScope = new ScopeBuilder().create(port, ImmutableMap.of(
                "worker",
                "WorkerSsl_" + port)
        );
        metricsScope.gauge("ACTIONS_PER_SECOND").update(actions);


        WorkflowClient client = createClient( metricsScope);


//        System.out.println(">>> " + client.getWorkflowServiceStubs().healthCheck().getStatus());
        // worker factory that can be used to create workers for specific task queues
        WorkerFactoryOptions build = WorkerFactoryOptions.newBuilder()
                .build();
        WorkerFactory factory = WorkerFactory.newInstance(client, build);

        //       for (int i = 0; i <= 2; i++) {
        // Worker that listens on a task queue and hosts both workflow and activslity implementations.


        WorkerOptions build1 = WorkerOptions.newBuilder()
                .setMaxTaskQueueActivitiesPerSecond(actions)
                .setDisableEagerExecution(FromEnv.getDisableEagerDispatch())
                .build();

        Worker worker = factory.newWorker(TASK_QUEUE, build1);

        worker.registerWorkflowImplementationTypes(WorkflowHelloActivity.GreetingWorkflowImpl.class
                //,
                // HelloActivity2.GreetingWorkflowImpl2.class,
                //        HelloActivity3.GreetingWorkflowImpl3.class
        );
        worker.registerActivitiesImplementations(new WorkflowHelloActivity.GreetingActivitiesImpl());
        //       }
//

        // timeouts, retry & heartbeat impact
        factory.start();
    }

    public static WorkflowClient createClient( Scope metricsScope) {


        SslContextBuilderProvider sslContextBuilderProvider = new SslContextBuilderProvider();
        String namespace = sslContextBuilderProvider.getNamespace();

        if(namespace.equals("default")){
            WorkflowServiceStubs service =
                    WorkflowServiceStubs.newServiceStubs(
                            WorkflowServiceStubsOptions.newBuilder()
                                    .setMetricsScope(metricsScope)
                                    .build());


            WorkflowClient client =
                    WorkflowClient.newInstance(
                            service, WorkflowClientOptions.newBuilder()
                                    .build());
            return client;
        }


        WorkflowServiceStubs service =
                WorkflowServiceStubs.newServiceStubs(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setMetricsScope(metricsScope)
                                .setSslContext(sslContextBuilderProvider.getSslContext())
                                .setTarget(sslContextBuilderProvider.getTargetEndpoint())
                                .build());


        WorkflowClient client =
                WorkflowClient.newInstance(
                        service, WorkflowClientOptions.newBuilder()
                                .setNamespace(namespace)
                                .build());
        return client;




    }

}
