package std.learn.vertx.practice.vertx_study.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusExample extends AbstractVerticle {

//  private final Logger logger = LoggerFactory.getLogger(EventBusExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());
  }

  static class RequestVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(RequestVerticle.class);
    static final String ADDRESS = "my.request.address";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final String message = "Hello World!";
      logger.debug("Sending: {}", message);
      eventBus.<String>request(ADDRESS, message, reply -> {
        logger.debug("Response {}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(ResponseVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(RequestVerticle.ADDRESS, message -> {
        logger.debug("Received Message: {}", message.body());
        message.reply("Received your message, Thanks!");
      });
    }
  }
}
