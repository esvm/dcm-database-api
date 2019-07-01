package database.api.configs;

import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDbConfig {
  @Value("${influx-db.data-source.url}")
  private String url;

  @Value("${influx-db.data-source.username}")
  private String username;

  @Value("${influx-db.data-source.password}")
  private String password;

  @Value("${influx-db.data-source.database}")
  private String database;

  @Bean(destroyMethod = "close")
  public InfluxDB influxDb() {
    return InfluxDBFactory.connect(url, username, password)
        .setDatabase(database)
        .setRetentionPolicy("autogen")
        .enableBatch(100, 200, TimeUnit.MILLISECONDS);
  }
}
