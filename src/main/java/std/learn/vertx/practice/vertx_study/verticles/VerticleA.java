package std.learn.vertx.practice.vertx_study.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Start {}", getClass().getName());
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      log.info("Start {}", VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    vertx.deployVerticle(new VerticleAB(), whenDeployed -> {
      log.info("Start {}", VerticleAB.class.getName());
    });

    startPromise.complete();
  }
}
