package database.api.repositories;

import api.Api.Metric;
import database.api.dtos.MetricPoint;
import database.api.mappers.MetricMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MetricRepository {
  private static final String SELECT_QUERY =
      "select * from metrics where topic = '%s' and time >= now() - %sm";

  @Autowired InfluxDB influxDB;

  public void insertMetrics(String topic, List<Metric> metrics) {
    metrics
        .stream()
        .map(metric -> MetricMapper.pointFromMetric(topic, metric))
        .forEach(influxDB::write);
  }

  public List<Metric> getMetricsByTopicAndTime(String topic, int minutesAgo) {
    QueryResult queryResult =
        influxDB.query(new Query(String.format(SELECT_QUERY, topic, minutesAgo), "broker"));

    InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
    List<MetricPoint> metricPointList = resultMapper.toPOJO(queryResult, MetricPoint.class);

    return metricPointList
        .stream()
        .map(MetricMapper::metricFromMetricPoint)
        .collect(Collectors.toList());
  }
}
