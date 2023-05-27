package std.learn.vertx.practice.vertx_study.eventloops;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(getClass().getName());

  public static void main(String[] args) {
    var vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
    );
    vertx.deployVerticle(EventLoopExample.class.getName(), new DeploymentOptions().setInstances(4));
//    vertx.close();
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    logger.info("Start {}", getClass().getName());
    startPromise.complete();

//    Thread.sleep(5000);
  }
}
