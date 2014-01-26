package com.chiwanpark.hadoop.monitoring.controller;

import com.chiwanpark.hadoop.monitoring.HadoopInstanceManager;

import org.apache.hadoop.mapred.JobTracker;
import org.apache.hadoop.mapreduce.ClusterMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/controller")
public class JobTrackerController {

  @Autowired
  private HadoopInstanceManager instanceManager;

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody Map<String, Object> get() {
    Map<String, Object> results = new HashMap<String, Object>();
    JobTracker jobTracker = instanceManager.getJobTracker();

    if (jobTracker == null) {
      results.put("status", false);
    } else {
      ClusterMetrics clusterMetrics = jobTracker.getClusterMetrics();


      results.put("taskTrackerCount", clusterMetrics.getTaskTrackerCount());
      results.put("blackListedTaskTrackerCount", clusterMetrics.getBlackListedTaskTrackerCount());
      results.put("grayListedTaskTrackerCount", clusterMetrics.getGrayListedTaskTrackerCount());
      results.put("decommissionedTaskTrackerCount", clusterMetrics.getDecommissionedTaskTrackerCount());
      results.put("mapSlotCapacity", clusterMetrics.getMapSlotCapacity());
      results.put("reduceSlotCapacity", clusterMetrics.getReduceSlotCapacity());
      results.put("occupiedMapSlots", clusterMetrics.getOccupiedMapSlots());
      results.put("occupiedReduceSlots", clusterMetrics.getOccupiedReduceSlots());
      results.put("runningMaps", clusterMetrics.getRunningMaps());
      results.put("runningReduces", clusterMetrics.getRunningReduces());
    }

    return results;
  }
}
