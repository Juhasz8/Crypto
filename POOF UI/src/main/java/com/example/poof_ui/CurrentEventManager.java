package com.example.poof_ui;
import java.util.*;

public class CurrentEventManager {
    private CurrentEvent event_1 = new CurrentEvent("Event of category 1", 5);
    private CurrentEvent event_2 = new CurrentEvent("Event of category 2", 15);
    private CurrentEvent event_3 = new CurrentEvent("Event of category 3", 30);
    private CurrentEvent event_4 = new CurrentEvent("Event of category 4", 50);

    // Get an event
    public CurrentEvent getEvent() {
        Map<Integer, List<Integer>> dictionary = Map.of(
                5, List.of(0, 6),    // Key 5 corresponds to the range [0, 6)
                15, List.of(6, 21),  // Key 15 corresponds to the range [6, 21)
                30, List.of(21, 51), // Key 30 corresponds to the range [21, 51)
                50, List.of(51, 100) // Key 50 corresponds to the range [51, 100)
        );
        Random randomEventNumber = new Random();
        int myRandomEventNumber = randomEventNumber.nextInt(101); // Generate a random event number between 0 and 100 (inclusive)
        int myKey = 0;

        // Iterate through the dictionary entries
        for (Map.Entry<Integer, List<Integer>> entry : dictionary.entrySet()) {
            String key = String.valueOf(entry.getKey());
            List<Integer> value = entry.getValue();

            // Check if the random event number falls within the range of the current dictionary entry
            if (myRandomEventNumber > value.get(0) && myRandomEventNumber < value.get(1)) {
                myKey = Integer.parseInt(key); // Set myKey to the corresponding key value
            }
        }

        // Return the event based on the matched myKey value
        if (event_1.getProbability() == myKey) {
            return event_1;
        } else if (event_2.getProbability() == myKey) {
            return event_2;
        } else if (event_3.getProbability() == myKey) {
            return event_3;
        } else if (event_4.getProbability() == myKey) {
            return event_4;
        }

        return null; // Return null if no matching event is found
    }
}
