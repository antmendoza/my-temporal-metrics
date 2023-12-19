/*
 *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
 *
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package com.antmendoza.temporal;

import com.antmendoza.temporal.config.ScopeBuilder;
import com.antmendoza.temporal.config.SslContextBuilderProvider;
import com.antmendoza.temporal.workflow.IGreetingWorkflow;
import com.uber.m3.tally.Scope;
import com.uber.m3.util.ImmutableMap;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

import static com.antmendoza.temporal.WorkerSsl2.TASK_QUEUE;
import static com.antmendoza.temporal.WorkerSsl2.createClient;

public class Starter2 {


    public static void main(String[] args) {



        Scope metricsScope =null;

        WorkflowClient client = createClient( metricsScope);



        while (true) {


            WorkflowOptions workflowOptions =
                    WorkflowOptions.newBuilder()
                            //.setWorkflowId("localhost.test.1"+a)
                            //.setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                            .setTaskQueue(TASK_QUEUE)
                            .build();


            IGreetingWorkflow workflow = client.newWorkflowStub(IGreetingWorkflow.class, workflowOptions);
            WorkflowClient.start(workflow::getGreeting, "input");
            System.out.println("Starting workflow... ");
            WorkflowStub untyped = WorkflowStub.fromTyped(workflow);


        }

    }


    private static boolean isaBoolean() {
        return true;
    }


}
