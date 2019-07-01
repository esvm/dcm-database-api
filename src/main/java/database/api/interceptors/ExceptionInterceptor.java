package database.api.interceptors;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

@GRpcGlobalInterceptor
public class ExceptionInterceptor implements ServerInterceptor {
  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);
    return new SimpleForwardingServerCallListener<ReqT>(delegate) {
      @Override
      public void onHalfClose() {
        try {
          super.onHalfClose();
        } catch (Exception e) {
          System.err.println(e.getMessage());
          call.close(
              Status.INTERNAL.withCause(e).withDescription("internal error"), new Metadata());
        }
      }
    };
  }
}
