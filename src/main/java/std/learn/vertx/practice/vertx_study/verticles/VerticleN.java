package std.learn.vertx.practice.vertx_study.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VerticleN.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.info("Start {} ", getClass().getName() + " , Thread " + Thread.currentThread().getName() + " with config " + config().toString());
    startPromise.complete();
  }
}
