package database.api;

import api.Api.GetItemsRequest;
import api.Api.GetItemsResponse;
import api.Api.InsertItemRequest;
import api.Api.Metric;
import api.DcmApiDatabaseServiceGrpc;
import api.DcmApiDatabaseServiceGrpc.DcmApiDatabaseServiceBlockingStub;
import io.grpc.ManagedChannelBuilder;

public class Test {
  private static final String TOPIC = "edjan";

  public static void main(String[] args) {
    DcmApiDatabaseServiceBlockingStub stub =
        DcmApiDatabaseServiceGrpc.newBlockingStub(
            ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext(true).build());

    // insert(stub);
    GetItemsRequest request =
        GetItemsRequest.newBuilder().setTopicName(TOPIC).setMinutesAgo(1).build();
    GetItemsResponse response = stub.getItems(request);
    System.out.println("DASJKGDSAJHGDJHASGDJHASGDJH");
    System.out.println(response);
    System.out.println("DASJKGDSAJHGDJHASGDJHASGDJH");
  }

  private static void insert(DcmApiDatabaseServiceBlockingStub stub) {
    final Metric metric =
        Metric.newBuilder()
            .setAverage(1.4f)
            .setMedian(2.4f)
            .setVariance(1.31f)
            .setStandardDeviation(.132f)
            .setMode(1f)
            .build();

    InsertItemRequest request =
        InsertItemRequest.newBuilder().setTopicName(TOPIC).addMetrics(metric).build();

    stub.insertItem(request);
  }
}
