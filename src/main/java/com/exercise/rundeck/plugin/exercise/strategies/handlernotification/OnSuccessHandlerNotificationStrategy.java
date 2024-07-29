package com.exercise.rundeck.plugin.exercise.strategies.handlernotification;

import java.util.Map;

import com.exercise.rundeck.plugin.exercise.interfaces.NotificationHandlerStrategy;

/**
 * Strategy implementation for the "onsuccess" notification event.
 */
public class OnSuccessHandlerNotificationStrategy implements NotificationHandlerStrategy {
  /**
   * Handles the "onsuccess" notification event.
   *
   * @param execution     The execution data map.
   * @param configuration The configuration data map.
   * @return true if the handling was successful, false otherwise.
   */
  @Override
  public boolean handle(Map execution, Map configuration) {
    Map job = (Map) execution.get("job");
    String jobName = (String) job.get("name");
    System.out.printf("Success! Job %s worked fine.\n", jobName);
    return true;
  }
}
