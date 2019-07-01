package database.api.services;

import api.Api.GetItemsRequest;
import api.Api.GetItemsResponse;
import api.Api.InsertItemRequest;
import api.Api.Metric;
import api.DcmApiDatabaseServiceGrpc;
import com.google.protobuf.Empty;
import database.api.repositories.MetricRepository;
import io.grpc.stub.StreamObserver;
import java.util.List;
import lombok.AllArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
@AllArgsConstructor
public class DcmApiDatabaseService extends DcmApiDatabaseServiceGrpc.DcmApiDatabaseServiceImplBase {
  @Autowired MetricRepository metricRepository;

  @Override
  public void insertItem(InsertItemRequest request, StreamObserver<Empty> responseObserver) {
    metricRepository.insertMetrics(request.getTopicName(), request.getMetricsList());

    responseObserver.onNext(Empty.newBuilder().build());
    responseObserver.onCompleted();
  }

  @Override
  public void getItems(GetItemsRequest request, StreamObserver<GetItemsResponse> responseObserver) {
    List<Metric> metrics =
        metricRepository.getMetricsByTopicAndTime(request.getTopicName(), request.getMinutesAgo());
    GetItemsResponse response = GetItemsResponse.newBuilder().addAllMetrics(metrics).build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
