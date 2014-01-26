package com.chiwanpark.hadoop.monitoring;

import org.apache.hadoop.mapred.JobTracker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class HadoopInstanceManager implements InitializingBean {

  private JobTracker jobTracker;

  public JobTracker getJobTracker() {
    return jobTracker;
  }

  public void setJobTracker(JobTracker jobTracker) {
    this.jobTracker = jobTracker;
  }

  public boolean isJobTrackerRunning() {
    return jobTracker != null;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    jobTracker = null;
  }
}
