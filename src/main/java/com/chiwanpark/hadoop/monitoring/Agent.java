package com.chiwanpark.hadoop.monitoring;

import org.apache.hadoop.mapred.JobTracker;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Timer;
import java.util.TimerTask;

public class Agent {

  private static WebApplicationContext springContext = null;
  private static Server server = null;
  private static boolean flag = false;

  private static WebAppContext createWebAppContext() {
    WebAppContext context = new WebAppContext();

    context.setContextPath("/");
    context.setDescriptor("src/main/webapp/web.xml");
    context.setResourceBase("src/main/webapp/");
    context.setParentLoaderPriority(true);

    return context;
  }

  private static Server createHttpServer(int port, WebAppContext context) throws Exception {
    Server server = new Server(port);
    server.setHandler(context);

    server.start();

    return server;
  }

  public static void startTracker(final JobTracker jobTracker) throws Exception {
    if (!flag) {
      final WebAppContext context = createWebAppContext();

      flag = true;

      TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
          try {
            server = createHttpServer(8080, context);
            springContext = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());

            HadoopInstanceManager instanceManager = springContext.getBean(HadoopInstanceManager.class);
            instanceManager.setJobTracker(jobTracker);

            server.join();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };

      new Timer().schedule(timerTask, 1);
    } else {
      TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
          HadoopInstanceManager instanceManager = springContext.getBean(HadoopInstanceManager.class);
          instanceManager.setJobTracker(jobTracker);
        }
      };

      new Timer().schedule(timerTask, 100);
    }
  }

  public static void stopTracker() throws Exception {
    HadoopInstanceManager instanceManager = springContext.getBean(HadoopInstanceManager.class);

    instanceManager.setJobTracker(null);
    server.stop();
  }
}
