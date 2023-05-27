package std.learn.vertx.practice.vertx_study.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerThreadExample extends AbstractVerticle {

  private final Logger logger = LoggerFactory.getLogger(getClass().getName());

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerThreadExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(WorkerVerticle.class.getName(),
      new DeploymentOptions()
        .setWorker(true)
        .setWorkerPoolSize(1)
        .setWorkerPoolName("my-worker-verticle"));
    startPromise.complete();
    executeBlockingCode();
  }

  private void executeBlockingCode() {
    vertx.executeBlocking(event -> {
      logger.info("Executing blocking code");
      try {
        Thread.sleep(5000);
        event.complete();
      } catch (InterruptedException e) {
        logger.error("Failed : ", e);
        event.fail(e);
      }
    }, asyncResult -> {
      if(asyncResult.succeeded()) {
        logger.debug("Blocking call done");
      } else {
        logger.debug("Blocking call failed due to : ", asyncResult.cause());
      }
    });
  }
}
