package com.exercise.rundeck.plugin.exercise.interfaces;

import java.util.Map;
/**
 * Strategy interface for handling notifications in the HttpNotificationPlugin at Rundeck.
 */
public interface NotificationHandlerStrategy {
  /**
     * Handles the notification event.
     *
     * @param execution    The execution data map.
     * @param configuration The configuration data map.
     * @return true if the handling was successful, false otherwise.
     */
  boolean handle(Map execution, Map configuration);
}
