package database.api.dtos;

import lombok.Getter;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Getter
@Measurement(name = "metrics")
public class MetricPoint {
  @Column(name = "average")
  private double average;

  @Column(name = "median")
  private double median;

  @Column(name = "variance")
  private double variance;

  @Column(name = "standard_deviation")
  private double standardDeviation;

  @Column(name = "mode")
  private double mode;
}
