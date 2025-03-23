"use strict";

self.addEventListener("push", event => {
  try {
    if (event.data) {
      let data = event.data.json();
      let title = data.title;

      let options = {
        body: data.body,
        icon: data.iconURL,
        tag: "thrsqr",
        renotify: true,
        requireInteraction: true,
        vibrate: [67, 33, 67],
        data: {
          clickURL: `/event/${data.eventId}`
        }
      };

      event.waitUntil(
        self.registration.showNotification(title, options)
      );
    }
  } catch (error) {
    console.log("Could not process push event:\n", error);
  }
});

self.addEventListener('notificationclick', function (event) {
  event.preventDefault();
  event.notification.close();
  event.waitUntil(clients.openWindow(event.notification.data.clickURL));
});
