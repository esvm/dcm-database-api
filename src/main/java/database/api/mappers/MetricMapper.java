package database.api.mappers;

import api.Api.Metric;
import database.api.dtos.MetricPoint;
import java.util.concurrent.TimeUnit;
import org.influxdb.dto.Point;

public class MetricMapper {
  private MetricMapper() {
    throw new AssertionError();
  }

  public static Point pointFromMetric(String topic, Metric metric) {
    return Point.measurement("metrics")
        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        .addField(getFieldName(Metric.AVERAGE_FIELD_NUMBER), metric.getAverage())
        .addField(getFieldName(Metric.MEDIAN_FIELD_NUMBER), metric.getMedian())
        .addField(getFieldName(Metric.VARIANCE_FIELD_NUMBER), metric.getVariance())
        .addField(
            getFieldName(Metric.STANDARD_DEVIATION_FIELD_NUMBER), metric.getStandardDeviation())
        .addField(getFieldName(Metric.MODE_FIELD_NUMBER), metric.getMode())
        .tag("topic", topic)
        .build();
  }

  public static Metric metricFromMetricPoint(MetricPoint metricPoint) {
    return Metric.newBuilder()
        .setAverage((float) metricPoint.getAverage())
        .setMedian((float) metricPoint.getMedian())
        .setVariance((float) metricPoint.getVariance())
        .setStandardDeviation((float) metricPoint.getStandardDeviation())
        .setMode((float) metricPoint.getMode())
        .build();
  }

  private static String getFieldName(int fieldNumber) {
    return Metric.getDescriptor().findFieldByNumber(fieldNumber).getName();
  }
}
