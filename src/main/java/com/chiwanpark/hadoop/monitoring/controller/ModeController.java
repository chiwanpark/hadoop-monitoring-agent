package com.chiwanpark.hadoop.monitoring.controller;

import com.chiwanpark.hadoop.monitoring.HadoopInstanceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mode")
public class ModeController {

  @Autowired
  private HadoopInstanceManager instanceManager;

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody Map<String, Object> get() {
    Map<String, Object> result = new HashMap<String, Object>();

    result.put("jobTracker", instanceManager.isJobTrackerRunning());

    return result;
  }
}
