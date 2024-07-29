package com.exercise.rundeck.plugin.exercise.strategies.handlernotification;

import java.util.Map;

import com.exercise.rundeck.plugin.exercise.interfaces.NotificationHandlerStrategy;

/**
 * Strategy implementation for the "onstart" notification event.
 */
public class OnStartHandlerNotificationStrategy implements NotificationHandlerStrategy {
  /**
   * Handles the "onstart" notification event.
   *
   * @param execution     The execution data map.
   * @param configuration The configuration data map.
   * @return true if the handling was successful, false otherwise.
   */
  @Override
  public boolean handle(Map execution, Map configuration) {
    Map job = (Map) execution.get("job");
    String jobName = (String) job.get("name");
    System.out.printf("Job %s has been started by %s...\n", jobName, execution.get("user"));
    return true;
  }
}
